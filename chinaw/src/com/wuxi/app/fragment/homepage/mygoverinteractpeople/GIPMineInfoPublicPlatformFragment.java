package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
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

import com.wuxi.app.R;
import com.wuxi.app.adapter.MyApplyOpenAdapter;
import com.wuxi.app.adapter.MyOpinionOpenAdapter;
import com.wuxi.app.engine.MyApplyOpenService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.MyApplyOpenWrapper;
import com.wuxi.domain.MyOpinionOpenWrapper;
import com.wuxi.domain.MyApplyOpenWrapper.MyApplyOpen;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment --信息公开平台 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIPMineInfoPublicPlatformFragment extends
		RadioButtonChangeFragment implements OnClickListener {

	private static final String TAG = "GIPMineInfoPublicPlatformFragment:";

	private final int[] radioButtonIds = {
			R.id.gip_mine_infopublic_radioButton_declaration,
			R.id.gip_mine_infopublic_radioButton_worksuggestion };

	private ListView applyListView;
	private ProgressBar progressBar;

	private MyApplyOpenWrapper applyOpenWrapper;
	private List<MyApplyOpen> applyOpens;

	private static final int APPLY_LOAD_SUCESS = 0;
	private static final int APPLY_LOAD_ERROR = 1;

	private static final int START = 0; // 获取话题的起始坐标

	private int applyVisibleLastIndex;
	private int applyVisibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoadApply = true;// 是不是首次加载数据
	private boolean isSwitchApply = false;// 切换
	private boolean isLoadingApply = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private MyApplyOpenAdapter applyOpenAdapter;

	private ListView opinionListView;

	private ArrayList<MyOpinionOpenWrapper> mOpinionOpenList = null;
	private ArrayList<MyOpinionOpenWrapper> mOpinionOpenListAll = null;

	private MyOpinionOpenAdapter mOpinionOpenAdapter = null;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case APPLY_LOAD_SUCESS:
				showApplyList();
				break;
			case APPLY_LOAD_ERROR:
				progressBar.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			case 100:
				showOpinionData();
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {
		case R.id.gip_mine_infopublic_radioButton_declaration:
			init();
			break;
		case R.id.gip_mine_infopublic_radioButton_worksuggestion:
			loadFirstOpinionData(START, PAGE_NUM);
			break;

		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_infopublicplatform_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_infopublic_radioGroup;
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
		initLayout();

		loadFirstApplyData(START, PAGE_NUM);
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		// progressBar = (ProgressBar) view
		// .findViewById(R.id.gip_mine_infopublic_list_progressbar);

		progressBar = (ProgressBar) view
				.findViewById(R.id.gip_mine_infopublic_list_progressbar);

		applyListView = (ListView) view
				.findViewById(R.id.gip_mine_infopublic_apply_listview);
		applyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});
		applyListView.addFooterView(getApplyListFootView());// 为listView添加底部视图
		applyListView.setOnScrollListener(new ApplyonScrollListener());// 增加滑动监听

		opinionListView = (ListView) view
				.findViewById(R.id.gip_mine_infopublic_opinion_listview);

	}

	/**
	 * @方法： getApplyListFootView
	 * @return
	 */
	private View getApplyListFootView() {
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		loadMoreButton.setOnClickListener(this);
		return loadMoreView;
	}

	/**
	 * 第一次加载我的信息公开意见
	 * 
	 * @方法： loadFirstOpinionData
	 * @param start
	 * @param end
	 */
	private void loadFirstOpinionData(int start, int end) {
		loadOpinionData(start, end);
	}

	/**
	 * 加载我的信息公开意见
	 * 
	 * @方法： loadOpinionData
	 * @param start
	 * @param end
	 */
	private void loadOpinionData(final int start, final int end) {
		if (isFirstLoadApply || isSwitchApply) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		if (mOpinionOpenList != null) {
			mOpinionOpenList.clear();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				isLoadingApply = true;
				Message message = handler.obtainMessage();

				MyApplyOpenService service = new MyApplyOpenService(context);

				String url = Constants.Urls.DOMAIN_URL
						+ "/api/applyopen/myapplybox.json?access_token=c33611d7d42042539b4102251a9dd113";
				try {
					mOpinionOpenList = service.getMyOpinionOpenWrapper(url);
					if (mOpinionOpenList != null) {
						handler.sendEmptyMessage(100);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(APPLY_LOAD_ERROR);
					}
				} catch (NetException e) {
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(APPLY_LOAD_ERROR);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 显示我的信息公开意见
	 * 
	 * @方法： showOpinionData
	 */
	private void showOpinionData() {
		if (mOpinionOpenListAll == null) {
			mOpinionOpenListAll = new ArrayList<MyOpinionOpenWrapper>();
		}

		if (mOpinionOpenList.size() > 0) {
			mOpinionOpenListAll.addAll(mOpinionOpenList);
			if (mOpinionOpenAdapter == null) {
				mOpinionOpenAdapter = new MyOpinionOpenAdapter(context,
						mOpinionOpenListAll);
				opinionListView.setAdapter(mOpinionOpenAdapter);
			} else {
				mOpinionOpenAdapter.addListItem(mOpinionOpenListAll);
			}

			// 判断是否还有下一页
			if (mOpinionOpenListAll.get(mOpinionOpenListAll.size()).isNext()) {

			}

		} else {

		}

	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstApplyData(int start, int end) {
		loadApplyData(start, end);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载立法征求意见数据
	 * @param url
	 */
	private void loadApplyData(final int start, final int end) {
		if (isFirstLoadApply || isSwitchApply) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoadingApply = true;// 正在加载数据
				Message message = handler.obtainMessage();

				MyApplyOpenService service = new MyApplyOpenService(context);

				// 构建立法征求意见数据查询URL
				String url = Constants.Urls.MY_APPLY_OPEN_URL
						+ "?access_token=" + SystemUtil.getAccessToken(context)
						+ "&start=" + start + "&end=" + end;

				try {
					applyOpenWrapper = service.getMyApplyOpenWrapper(url);
					if (null != applyOpenWrapper) {
						handler.sendEmptyMessage(APPLY_LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(APPLY_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(APPLY_LOAD_ERROR);
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
	 * @描述： 显示立法征求意见列表
	 */
	private void showApplyList() {
		applyOpens = applyOpenWrapper.getMyApplyOpens();
		if (applyOpens == null || applyOpens.size() == 0) {
			Toast.makeText(context, "您还没有申请任何事项！", Toast.LENGTH_SHORT).show();
			progressBar.setVisibility(View.GONE);
		} else {
			if (isFirstLoadApply) {
				applyOpenAdapter = new MyApplyOpenAdapter(context, applyOpens);
				isFirstLoadApply = false;
				applyListView.setAdapter(applyOpenAdapter);
				progressBar.setVisibility(View.GONE);
				isLoadingApply = false;
			} else {
				if (isSwitchApply) {
					applyOpenAdapter.setMyApplyOpens(applyOpens);
					progressBar.setVisibility(View.GONE);
				} else {
					for (MyApplyOpen open : applyOpens) {
						applyOpenAdapter.addItem(open);
					}
				}

				applyOpenAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				applyListView.setSelection(applyVisibleLastIndex
						- applyVisibleItemCount + 1); // 设置选中项
				isLoadingApply = false;
			}
		}

		if (applyOpenWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			if (applyOpenAdapter != null) {
				applyListView.removeFooterView(loadMoreView);
			}

		}
	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreApplyData(View view) {
		if (isLoadingApply) {
			return;
		} else {
			loadApplyData(applyVisibleLastIndex + 1, applyVisibleLastIndex + 1
					+ PAGE_NUM);
		}
	}

	private class ApplyonScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			applyVisibleItemCount = visibleItemCount;
			applyVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = applyOpenAdapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (applyOpenWrapper != null && applyOpenWrapper.isNext()) {// 还有下一条记录
				isSwitchApply = false;
				loadMoreButton.setText("loading.....");
				loadMoreApplyData(v);
			}
			break;
		}
	}
}
