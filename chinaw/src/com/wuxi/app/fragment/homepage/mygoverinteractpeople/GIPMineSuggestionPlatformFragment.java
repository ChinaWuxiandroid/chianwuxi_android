package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.LegislationContentActivity;
import com.wuxi.app.adapter.GIPMineSugMyInfoListAdapter;
import com.wuxi.app.adapter.MineSugSurAdapter;
import com.wuxi.app.engine.MineSugSurService;
import com.wuxi.app.engine.PoliticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.MineSugSurWapper;
import com.wuxi.domain.MineSugSurWapper.MineSugSur;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment --征求意见平台 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIPMineSuggestionPlatformFragment extends
		RadioButtonChangeFragment implements OnClickListener {

	private static final String TAG = "GIPMineSuggestionPlatformFragment";

	private final int[] radioButtonIds = {
			R.id.gip_mine_suggestion_radioButton_internetsurvy,
			R.id.gip_mine_suggestion_radioButton_peoplewill,
			R.id.gip_mine_suggestion_radioButton_lawwill };

	// 我参与的网上调查列表控件
	private ListView surveyListView = null;
	// 我参与的立法征求意见和民意调查列表控件
	private ListView lawListView = null;
	// 进度条控件
	private ProgressBar progressBar = null;

	// 我参与的网上调查实体类
	private MineSugSurWapper surWapper;
	// 我参与的网上调查列表数据
	private List<MineSugSur> mineSugSurs;

	// 我参与的立法征求意见和民意调查实体类
	private PoliticsWrapper politicsWrapper;
	// 我参与的立法征求意见和民意调查列表数据
	private List<Politics> politicss;

	// 加载我参与的网上调查数据成功
	private static final int SURVEY_LOAD_SUCESS = 0;
	// 加载我参与的网上调查数据失败
	private static final int SURVEY_LOAD_ERROR = 1;
	// 加载我参与的立法征求意见和民意调查数据成功
	private static final int LAW_LOAD_SUCESS = 2;
	// 加载我参与的立法征求意见和民意调查数据失败
	private static final int LAW_LOAD_ERROR = 3;

	// 加载数据开始位
	private static final int START = 0;

	// 我参与的网上调查最后一条数据
	private int surveyVisibleLastIndex;
	// 我参与的网上调查的显示条数
	private int surveyVisibleItemCount;

	// 我参与的立法征求意见和民意调查最后一条数据
	private int lawVisibleLastIndex;
	// 我参与的立法征求意见和民意调查的显示条数
	private int lawVisibleItemCount;

	// 每页显示数据的条数
	private final static int PAGE_NUM = 10;

	// 是否首次加载我参与的网上调查数据
	private boolean isFirstLoadSurvey = true;
	// 是否切换
	private boolean isSwitchSurvey = false;
	// 是否正在加载我参与的网上调查数据
	private boolean isLoadingSurvey = false;

	// 我参与的网上调查列表底部视图
	private View surveyLoadMoreView;
	// 我参与的网上调查列表底部加载更多按钮
	private Button surveyLoadMoreButton;
	// 我参与的网上调查列表底部加载更多数据进度条
	private ProgressBar surveyLoadmoreProgress;
	// 我参与的网上调查列表适配器
	private MineSugSurAdapter surAdapter;

	// 是否首次加载我参与的立法征求意见和民意调查数据
	private boolean isFirstLoadLaw = true;
	// 是否切换
	private boolean isSwitchLaw = false;
	// 是否正在加载我参与的立法征求意见和民意调查数据
	private boolean isLoadingLaw = false;

	// 我参与的立法征求意见和民意调查列表底部视图
	private View lawLoadMoreView;
	private Button lawLoadMoreButton;
	private ProgressBar lawLoadmoreProgress;

	// 我参与的立法征求意见和民意调查列表适配器
	private GIPMineSugMyInfoListAdapter sugMyInfoListAdapter;

	// 类型：0-->立法征求意见；1-->民意征集
	private int type;

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(int type) {
		this.type = type;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case SURVEY_LOAD_SUCESS:
				showSugSurveyList();
				break;

			case LAW_LOAD_SUCESS:
				showPoloticsList();
				break;

			case SURVEY_LOAD_ERROR:

			case LAW_LOAD_ERROR:
				progressBar.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}
		};
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {

		// 我参与的网上调查
		case R.id.gip_mine_suggestion_radioButton_internetsurvy:
			init();
			surveyListView.setVisibility(View.VISIBLE);
			lawListView.setVisibility(View.GONE);
			break;

		// 我参与的民意征集
		case R.id.gip_mine_suggestion_radioButton_peoplewill:
			setType(1);
			loadLawFirstData(START, PAGE_NUM);
			surveyListView.setVisibility(View.GONE);
			lawListView.setVisibility(View.VISIBLE);
			break;

		// 我参与的立法征求意见
		case R.id.gip_mine_suggestion_radioButton_lawwill:
			setType(0);
			loadLawFirstData(START, PAGE_NUM);
			surveyListView.setVisibility(View.GONE);
			lawListView.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_suggestionplatform_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_suggestion_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {

		initSurveyLayout();

		loadSurveyFirstData(START, PAGE_NUM);
	}

	/**
	 * @方法： initSurveyLayout
	 * @描述： 初始化布局控件
	 */
	private void initSurveyLayout() {
		progressBar = (ProgressBar) view
				.findViewById(R.id.gip_mine_suggestion_progressbar);

		surveyListView = (ListView) view
				.findViewById(R.id.gip_mine_suggestion_survey_listview);
		surveyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				Toast.makeText(context, "该功能暂未实现！", Toast.LENGTH_SHORT).show();
			}
		});
		surveyListView.addFooterView(getSurveyListViewFootView());// 为listView添加底部视图
		surveyListView.setOnScrollListener(new SurveyOnScrollListener());// 增加滑动监听

		lawListView = (ListView) view
				.findViewById(R.id.gip_mine_suggestion_law_listview);
		lawListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				Politics politics = (Politics) adapterView
						.getItemAtPosition(position);

				Intent intent = new Intent(getActivity(),
						LegislationContentActivity.class);
				intent.putExtra("politics", politics);

				MainTabActivity.instance.addView(intent);
			}
		});
		lawListView.addFooterView(getLawListFootView());// 为listView添加底部视图
		lawListView.setOnScrollListener(new LawOnScrollListener());// 增加滑动监听
	}

	/**
	 * @方法： loadSurveyFirstData
	 * @描述： 第一次加载我参与的网上调查数据
	 * @param start
	 * @param end
	 */
	private void loadSurveyFirstData(int start, int end) {
		loadSurveyData(start, end);
	}

	/**
	 * @方法： loadSurveyData
	 * @描述： 加载我参与的网上调查数据
	 * @param url
	 */
	private void loadSurveyData(final int start, final int end) {
		if (isFirstLoadSurvey || isSwitchSurvey) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			surveyLoadmoreProgress.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoadingSurvey = true;// 正在加载数据
				Message message = handler.obtainMessage();
				MineSugSurService service = new MineSugSurService(context);

				String url = Constants.Urls.MY_INTERNET_SURVEY_URL
						+ "?access_token=" + SystemUtil.getAccessToken(context)
						+ "&start=" + start + "&end=" + end;

				try {
					surWapper = service.getMineSugSurWapper(url);
					if (surWapper != null) {
						handler.sendEmptyMessage(SURVEY_LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(SURVEY_LOAD_ERROR);
					}
				} catch (NetException e) {
					e.printStackTrace();
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(SURVEY_LOAD_ERROR);
				} catch (JSONException e) {
					e.printStackTrace();
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(SURVEY_LOAD_ERROR);
				} catch (NODataException e) {
					e.printStackTrace();
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(SURVEY_LOAD_ERROR);
				}
			}

		}).start();
	}

	/**
	 * @方法： showSugSurveyList
	 * @描述： 显示我参与的网上调查列表
	 */
	private void showSugSurveyList() {
		mineSugSurs = surWapper.getMineSugSurs();
		if (mineSugSurs == null || mineSugSurs.size() == 0) {
			Toast.makeText(context, "您未参与任何网上调查主题！", Toast.LENGTH_SHORT).show();
			progressBar.setVisibility(View.GONE);
		} else {
			if (isLoadingSurvey) {
				surAdapter = new MineSugSurAdapter(context, mineSugSurs);
				isFirstLoadSurvey = false;
				surveyListView.setAdapter(surAdapter);
				progressBar.setVisibility(View.GONE);
				isLoadingSurvey = false;
			} else {
				if (isSwitchSurvey) {
					surAdapter.setMineSugSurs(mineSugSurs);
					progressBar.setVisibility(View.GONE);
				} else {
					for (MineSugSur mineSugSur : mineSugSurs) {
						surAdapter.addItem(mineSugSur);
					}
				}
				surAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				surveyListView.setSelection(surveyVisibleLastIndex
						- surveyVisibleItemCount + 1); // 设置选中项
				isLoadingSurvey = false;
			}
		}
		if (surWapper.isNext()) {
			if (surveyListView.getFooterViewsCount() != 0) {
				surveyLoadmoreProgress.setVisibility(ProgressBar.GONE);
				surveyLoadMoreButton.setText("点击加载更多");
			} else {
				surveyListView.addFooterView(getSurveyListViewFootView());
			}

		} else {
			if (surAdapter != null) {
				surveyListView.removeFooterView(surveyLoadMoreView);
			}

		}
	}

	/**
	 * @方法： getMyConsultFootView
	 * @描述： 获取我参与的网上调查列表的底部视图
	 * @return
	 */
	private View getSurveyListViewFootView() {
		surveyLoadMoreView = View.inflate(context,
				R.layout.list_loadmore_layout, null);
		surveyLoadMoreButton = (Button) surveyLoadMoreView
				.findViewById(R.id.loadMoreButton);
		surveyLoadmoreProgress = (ProgressBar) surveyLoadMoreView
				.findViewById(R.id.pb_loadmoore);
		surveyLoadMoreButton.setOnClickListener(this);
		return surveyLoadMoreView;
	}

	/**
	 * @类名： SurveyOnScrollListener
	 * @描述： 我参与的网上调查列表滚动监听类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-17 上午11:44:00
	 * @修改时间：
	 * @修改描述：
	 */
	private class SurveyOnScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			surveyVisibleItemCount = visibleItemCount;
			surveyVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = surAdapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		}

	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多我参与的网上调查数据
	 * @param view
	 */
	private void loadSurveyMoreData(View view) {
		if (isLoadingSurvey) {
			return;
		} else {
			loadSurveyData(surveyVisibleLastIndex + 1, surveyVisibleLastIndex
					+ 1 + PAGE_NUM);
		}
	}

	/**
	 * @方法： loadLawFirstData
	 * @描述： 第一次加载我参与的立法征求意见和民意调查数据
	 * @param start
	 * @param end
	 */
	private void loadLawFirstData(int start, int end) {
		loadLawData(start, end);
	}

	/**
	 * @方法： loadLawData
	 * @描述： 加载我参与的立法征求意见和民意调查数据
	 * @param url
	 */
	private void loadLawData(final int start, final int end) {
		if (isFirstLoadLaw || isSwitchLaw) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			lawLoadmoreProgress.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoadingLaw = true;// 正在加载数据
				Message message = handler.obtainMessage();
				PoliticsService politicsService = new PoliticsService(context);

				// 构建立法征求意见数据查询URL
				String url = Constants.Urls.POLITICS_MY_LIST_URL
						+ "?access_token=" + SystemUtil.getAccessToken(context)
						+ "&type=" + getType() + "&start=" + start + "&end="
						+ end;

				try {
					politicsWrapper = politicsService.getPoliticsWrapper(url);
					if (null != politicsWrapper) {
						handler.sendEmptyMessage(LAW_LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(LAW_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LAW_LOAD_ERROR);
				} catch (JSONException e) {
					e.printStackTrace();
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LAW_LOAD_ERROR);
				} catch (NODataException e) {
					e.printStackTrace();
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LAW_LOAD_ERROR);
				}
			}
		}).start();
	}

	/**
	 * @方法： showPoloticsList
	 * @描述： 显示我参与的立法征求意见和民意调查列表
	 */
	private void showPoloticsList() {
		politicss = politicsWrapper.getData();
		if (politicss == null || politicss.size() == 0) {
			if (getType() == 0) {
				Toast.makeText(context, "您未参与任何立法征求意见主题！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(context, "您未参与任何民意征集主题！", Toast.LENGTH_SHORT)
						.show();
			}
			progressBar.setVisibility(View.GONE);
		} else {
			if (isFirstLoadLaw) {
				sugMyInfoListAdapter = new GIPMineSugMyInfoListAdapter(context,
						politicss);
				isFirstLoadLaw = false;
				lawListView.setAdapter(sugMyInfoListAdapter);
				progressBar.setVisibility(View.GONE);
				isLoadingLaw = false;
			} else {
				if (isSwitchLaw) {
					sugMyInfoListAdapter.setPolitics(politicss);
					progressBar.setVisibility(View.GONE);
				} else {
					for (Politics pol : politicss) {
						sugMyInfoListAdapter.addItem(pol);
					}
				}

				sugMyInfoListAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				lawListView.setSelection(lawVisibleLastIndex
						- lawVisibleItemCount + 1); // 设置选中项
				isLoadingLaw = false;
			}
		}

		if (politicsWrapper.isNext()) {
			if (lawListView.getFooterViewsCount() != 0) {
				lawLoadmoreProgress.setVisibility(ProgressBar.GONE);
				lawLoadMoreButton.setText("点击加载更多");
			} else {
				lawListView.addFooterView(getLawListFootView());
			}

		} else {
			if (sugMyInfoListAdapter != null) {
				lawListView.removeFooterView(lawLoadMoreView);
			}

		}
	}

	/**
	 * @方法： getLawListFootView
	 * @描述： 获取我参与的立法征求意见和民意调查底部视图
	 * @return
	 */
	private View getLawListFootView() {
		lawLoadMoreView = View.inflate(context,
				R.layout.myapply_list_loadmore_layout, null);
		lawLoadMoreButton = (Button) lawLoadMoreView
				.findViewById(R.id.loadapply_MoreButton);
		lawLoadmoreProgress = (ProgressBar) lawLoadMoreView
				.findViewById(R.id.pb_applyloadmoore);
		lawLoadMoreButton.setOnClickListener(this);
		return lawLoadMoreView;
	}

	/**
	 * @方法： loadMoreLawData
	 * @描述： 加载更多我参与的立法征求意见和民意调查数据
	 * @param view
	 */
	private void loadMoreLawData(View view) {
		if (isLoadingLaw) {
			return;
		} else {
			loadLawData(lawVisibleLastIndex + 1, lawVisibleLastIndex + 1
					+ PAGE_NUM);
		}
	}

	/**
	 * @类名： LawOnScrollListener
	 * @描述： 我参与的立法征求意见和民意调查列表滚动监听类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-17 下午2:07:23
	 * @修改时间：
	 * @修改描述：
	 */
	private class LawOnScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			lawVisibleItemCount = visibleItemCount;
			lawVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = sugMyInfoListAdapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (surWapper != null && surWapper.isNext()) {// 还有下一条记录
				isSwitchSurvey = false;
				surveyLoadMoreButton.setText("loading.....");
				loadSurveyMoreData(v);
			}
			break;

		case R.id.loadapply_MoreButton:
			if (politicsWrapper != null && politicsWrapper.isNext()) {// 还有下一条记录
				isSwitchLaw = false;
				lawLoadMoreButton.setText("loading.....");
				loadMoreLawData(v);
			}
			break;
		}
	}

}
