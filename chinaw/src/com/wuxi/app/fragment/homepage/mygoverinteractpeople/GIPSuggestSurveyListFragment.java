/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GIPSuggestSurveyListFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述: 网上调查列表Fragment
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-9 上午11:45:07
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.GIPSuggestSurveyDetailActivity;
import com.wuxi.app.adapter.InternetPoliticsListAdapter;
import com.wuxi.app.engine.InternetSurveySerivce;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.InternetSurveyWrapper;
import com.wuxi.domain.InternetSurveyWrapper.InternetSurvey;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GIPSuggestSurveyListFragment
 * @描述： 网上调查列表Fragment
 * @作者： 罗森
 * @创建时间： 2013 2013-9-9 上午11:45:07
 * @修改时间：
 * @修改描述：
 * 
 */
@SuppressLint("HandlerLeak")
public class GIPSuggestSurveyListFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {

	private final static String TAG = "GIPSuggestSurveyListFragment";

	private Context context;

	private View view;

	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	private ListView mListView;
	private ProgressBar list_pb;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private InternetSurveyWrapper internetSurveyWrapper = null;
	private List<InternetSurvey> internetSurveys = null;

	private InternetPoliticsListAdapter adapter;

	private int type = 0;

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(int type) {
		this.type = type;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				showPoloticsList();
				break;
			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_suggest_survey_listview_layout,
				null);
		context = getActivity();

		initLayout();

		loadFirstData(0, PAGE_NUM);

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view
				.findViewById(R.id.gip_suggest_survey_listview);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_suggest_survey_progress);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载网上调查数据
	 * @param startIndex
	 * @param endIndex
	 */
	private void loadData(final int startIndex, final int endIndex) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				InternetSurveySerivce internetSurveySerivce = new InternetSurveySerivce(
						context);
				try {
					internetSurveyWrapper = internetSurveySerivce
							.getInternetSurveyWrapper(type, startIndex,
									endIndex);
					if (null != internetSurveyWrapper) {
						handler.sendEmptyMessage(DATA__LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @方法： showPoloticsList
	 * @描述： 显示列表数据
	 */
	private void showPoloticsList() {
		internetSurveys = internetSurveyWrapper.getInternetSurveys();
		if (internetSurveys == null || internetSurveys.size() == 0) {
			Toast.makeText(context, "对不起，暂无网上调查信息", Toast.LENGTH_LONG).show();
		} else {
			if (isFirstLoad) {
				adapter = new InternetPoliticsListAdapter(context,
						internetSurveys);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setInternetSurveys(internetSurveys);
					list_pb.setVisibility(View.GONE);
				} else {
					for (InternetSurvey survey : internetSurveys) {
						adapter.addItem(survey);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (internetSurveyWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			if (adapter != null) {
				mListView.removeFooterView(loadMoreView);
			}

		}
	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (internetSurveyWrapper != null && internetSurveyWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		InternetSurvey internetSurveyWrapper = (InternetSurvey) arg0
				.getItemAtPosition(arg2);

		Intent intent = new Intent(getActivity(),
				GIPSuggestSurveyDetailActivity.class);

		intent.putExtra("surveryId", "" + internetSurveyWrapper.getSurveryId());

		MainTabActivity.instance.addView(intent);
	}

}
