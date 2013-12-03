package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GIPMinePublicForumAdapter;
import com.wuxi.app.adapter.MyJoinTopicAdapter;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.net.HttpUtils;
import com.wuxi.app.net.NetworkUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.GIPMinePublicForumInfo;
import com.wuxi.domain.MyJoinTopicInfo;

/**
 * 我的政民互动 主Fragment --公众论坛 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIPMinePublicForumFragment extends RadioButtonChangeFragment {

	private final int[] radioButtonIds = {
			R.id.gip_mine_publicforum_radioButton_myTheme,
			R.id.gip_mine_publicforum_radioButton_themeTJoin,
			/* R.id.gip_mine_publicforum_radioButton_collectTheme, */
			R.id.gip_mine_radioButton_suggestionPlatform };

	// TAB的选项
	private RadioGroup mRadioGroup_publicforum;
	private RadioButton mRadioButton_myTheme;// 我的主题
	private RadioButton mRadioButton_themeTJoin;// 我参与的主题
	private RadioButton mRadioButton_collectTheme;// 我收藏的主题
	private RadioButton mRadioButton_suggestionPlatform;// 我参与的热点

	private ListView mListView;

	// private boolean isFirstLoadApply = true;// 是不是首次加载数据
	// private boolean isSwitchApply = false;// 切换

	private ProgressDialog mProgressDialog = null;

	private final int MY_THEME = 0;// 我的主题
	private int MY_THEME_START = 0;
	private int MY_THEME_END = 10;

	private final int MY_JOIN_THEME = 1;// 我参与的主题
	private int MY_JOIN_THEME_START = 0;
	private int MY_JOIN_THEME_END = 10;

	private final int MY_JOIN_TOPIC = 2;// 我参与的话题
	private int MY_JOIN_TOPIC_START = 0;
	private int MY_JOIN_TOPIC_END = 10;

	private GIPMinePublicForumAdapter adapter = null;
	private MyJoinTopicAdapter mTopicAdapter = null;

	private ArrayList<GIPMinePublicForumInfo> myThemeList = null;
	private ArrayList<GIPMinePublicForumInfo> myThemeListAll = new ArrayList<GIPMinePublicForumInfo>();
	private ArrayList<GIPMinePublicForumInfo> myJOINThemeList = null;
	private ArrayList<GIPMinePublicForumInfo> myJOINThemeListAll = new ArrayList<GIPMinePublicForumInfo>();

	private ArrayList<MyJoinTopicInfo> myJOINTopicList = new ArrayList<MyJoinTopicInfo>();
	private ArrayList<MyJoinTopicInfo> myJOINTopicListAll = new ArrayList<MyJoinTopicInfo>();

	private ProgressBar loadMoreBar;
	private Button loadMoreButton;

	private boolean isMore = true;

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		// 我的主题
		case R.id.gip_mine_publicforum_radioButton_myTheme:
			if (mTopicAdapter != null) {
				mTopicAdapter = null;
			}
			if (adapter != null) {
				adapter = null;
			}
			if (myThemeListAll != null) {
				myThemeListAll.clear();
			}
			MY_THEME_START = 0;
			MY_THEME_END = 10;
			init();
			break;

		// 我参与的主题
		case R.id.gip_mine_publicforum_radioButton_themeTJoin:
			if (mTopicAdapter != null) {
				mTopicAdapter = null;
			}
			if (adapter != null) {
				adapter = null;
			}
			if (myJOINThemeListAll != null) {
				myJOINThemeListAll.clear();
			}
			MY_JOIN_THEME_START = 0;
			MY_JOIN_THEME_END = 10;
			new GetData(MY_JOIN_THEME_START, MY_JOIN_THEME_END, 1,
					MY_JOIN_THEME).execute();
			break;

		// // 我收藏的主题
		// case R.id.gip_mine_publicforum_radioButton_collectTheme:
		//
		// break;

		// 我参与的热点
		case R.id.gip_mine_radioButton_suggestionPlatform:
			if (adapter != null) {
				adapter = null;
			}
			if (mTopicAdapter != null) {
				mTopicAdapter = null;
			}
			if (myJOINTopicListAll != null) {
				myJOINTopicListAll.clear();
			}
			MY_JOIN_TOPIC_START = 0;
			MY_JOIN_TOPIC_END = 10;
			new GetData(MY_JOIN_TOPIC_START, MY_JOIN_TOPIC_END, 2,
					MY_JOIN_TOPIC).execute();
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_publicforum_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_publicforum_radioGroup;
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
		initlayout();
		new GetData(MY_THEME_START, MY_THEME_END, 0, MY_THEME).execute();
	}

	/**
	 * 初始化控件
	 * 
	 * @方法： initlayout
	 */
	private void initlayout() {
		mRadioGroup_publicforum = (RadioGroup) view
				.findViewById(R.id.gip_mine_publicforum_radioGroup);
		mRadioButton_myTheme = (RadioButton) view
				.findViewById(R.id.gip_mine_publicforum_radioButton_myTheme);
		mRadioButton_themeTJoin = (RadioButton) view
				.findViewById(R.id.gip_mine_publicforum_radioButton_themeTJoin);
		// mRadioButton_collectTheme = (RadioButton) view
		// .findViewById(R.id.gip_mine_publicforum_radioButton_collectTheme);
		mRadioButton_suggestionPlatform = (RadioButton) view
				.findViewById(R.id.gip_mine_radioButton_suggestionPlatform);
		mListView = (ListView) view
				.findViewById(R.id.goverinterpeople_mine_12345_listview);
	}

	/**
	 * 读取数据
	 * 
	 * @类名： GetData
	 * @作者： 陈彬
	 * @创建时间： 2013 2013-11-25 下午3:37:32
	 * @修改时间：
	 * @修改描述：
	 */
	private class GetData extends AsyncTask<String, Integer, String> {

		private int start, end, type, role;

		/**
		 * 构造方法
		 * 
		 * @方法：
		 * @描述：
		 * @param start
		 *            数据的开始
		 * @param end
		 *            数据的结束
		 * @param type
		 *            数据的类型（0：我的帖子，1：我参与的帖子）
		 * @param role
		 *            查询的类型
		 */
		public GetData(int start, int end, int type, int role) {
			this.start = start;
			this.end = end;
			this.type = type;
			this.role = role;
		}

		@Override
		protected String doInBackground(String... params) {
			String url = null;
			if (role == MY_THEME || role == MY_JOIN_THEME) {
				url = Constants.Urls.DOMAIN_URL
						+ "/api/publicbbs/mylist.json?type=" + type + "&start="
						+ start + "&end=" + end
						+ "&access_token=34b13a90dc9b4ce88ac40bbe6873d9d5";
			} else if (role == MY_JOIN_TOPIC) {
				url = Constants.Urls.DOMAIN_URL
						+ "/api/hotreview/myreply.json?access_token=f4ce16e042c1430ba18c418872d03aa6&start="
						+ MY_JOIN_TOPIC_START + "&end=" + MY_JOIN_TOPIC_END;
			}
			String data = null;
			try {
				NetworkUtil networkUtil = NetworkUtil.getInstance();
				if (networkUtil.checkInternet(context)) {
					HttpUtils mUtils = HttpUtils.getInstance();
					data = mUtils.executeGetToString(url, 5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.length() < 5) {
				Toast.makeText(context, "暂时无数据", Toast.LENGTH_SHORT).show();
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
			} else {
				if (myThemeList != null) {
					myThemeList.clear();
				}
				if (myJOINTopicList != null) {
					myJOINTopicList.clear();
				}
				if (myJOINThemeList != null) {
					myJOINThemeList.clear();
				}
				if (role == MY_THEME) {
					myThemeList = new GIPMinePublicForumInfo()
							.resolveData(result);
					mHandler.sendEmptyMessage(MY_THEME);
					if (isMore) {
						mListView
								.addFooterView(getListViewFoot_myTheme(MY_THEME));
						isMore = false;
					}
				}
				if (role == MY_JOIN_THEME) {
					myJOINThemeList = new GIPMinePublicForumInfo()
							.resolveData(result);
					mHandler.sendEmptyMessage(MY_JOIN_THEME);
					if (isMore) {
						mListView
								.addFooterView(getListViewFoot_myTheme(MY_JOIN_THEME));
						isMore = false;
					}
				}
				if (role == MY_JOIN_TOPIC) {
					myJOINTopicList = new MyJoinTopicInfo().resolveData(result);
					mHandler.sendEmptyMessage(MY_JOIN_TOPIC);
					if (isMore) {
						mListView
								.addFooterView(getListViewFoot_myTheme(MY_JOIN_TOPIC));
						isMore = false;
					}
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(context);
			}
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setMessage("读取数据...");
			mProgressDialog.show();
		}

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 我的主题
			case MY_THEME:
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
				if (myThemeList != null) {
					if (adapter == null) {
						adapter = new GIPMinePublicForumAdapter(context,
								myThemeListAll);
						mListView.setAdapter(adapter);
					} else {
						adapter.clear();
						adapter.addItem(myThemeListAll);
					}
				}
				if (myThemeList != null && myThemeList.size() > 0) {
					myThemeListAll.addAll(myThemeList);

					if (myThemeList.get(myThemeList.size() - 1).isNext()) {
						loadMoreButton.setVisibility(View.VISIBLE);
					} else {
						loadMoreButton.setVisibility(View.GONE);
					}
				} else {
					mListView.removeFooterView(loadMoreView);
					isMore = true;
					loadMoreButton.setVisibility(View.GONE);
				}
				break;
			// 我参与的主题
			case MY_JOIN_THEME:
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
				if (myJOINThemeListAll != null) {
					if (adapter == null) {
						adapter = new GIPMinePublicForumAdapter(context,
								myJOINThemeListAll);
						mListView.setAdapter(adapter);
					} else {
						adapter.clear();
						adapter.addItem(myJOINThemeListAll);
					}
				}
				if (myJOINThemeList != null && myJOINThemeList.size() > 0) {
					myJOINThemeListAll.addAll(myJOINThemeList);
					if (myJOINThemeList.get(myJOINThemeList.size() - 1)
							.isNext()) {
						loadMoreButton.setVisibility(View.VISIBLE);
					} else {
						loadMoreButton.setVisibility(View.GONE);
					}
				} else {
					mListView.removeFooterView(loadMoreView);
					isMore = true;
					loadMoreButton.setVisibility(View.GONE);
				}
				break;
			// 我参与的热题
			case MY_JOIN_TOPIC:
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
				if (myJOINTopicList != null) {
					if (mTopicAdapter == null) {
						mTopicAdapter = new MyJoinTopicAdapter(context,
								myJOINTopicListAll);
						mListView.setAdapter(mTopicAdapter);
					} else {
						mTopicAdapter.addItem(myJOINTopicListAll);
					}
				}
				if (myJOINTopicList != null && myJOINTopicList.size() > 0) {
					myJOINTopicListAll.addAll(myJOINTopicList);
					if (myJOINTopicList.get(myJOINTopicList.size() - 1)
							.isNext()) {
						loadMoreButton.setVisibility(View.VISIBLE);
					} else {
						loadMoreButton.setVisibility(View.GONE);
					}
				} else {
					mListView.removeFooterView(loadMoreView);
					isMore = true;
					loadMoreButton.setVisibility(View.GONE);
				}
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 加载更多
	 * 
	 * @方法： getListViewFoot
	 * @return
	 */
	View loadMoreView = null;

	private View getListViewFoot_myTheme(final int id) {
		loadMoreView = View.inflate(context,
				R.layout.myapply_list_loadmore_layout, null);
		loadMoreBar = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_applyloadmoore);
		loadMoreBar.setVisibility(View.GONE);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadapply_MoreButton);
		loadMoreButton.setText("点击加载更多");
		loadMoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (id == MY_THEME) {
					// 去掉底部
					mListView.removeFooterView(loadMoreView);
					isMore = true;
					// 改变参数
					MY_THEME_START = MY_THEME_START + MY_THEME_END;
					MY_THEME_END += MY_THEME_END;
					// 异步加载
					new GetData(MY_THEME_START, MY_THEME_END, 0, MY_THEME)
							.execute();
				} else if (id == MY_JOIN_THEME) {
					// 去掉底部
					mListView.removeFooterView(loadMoreView);
					isMore = true;
					// 改变参数
					MY_JOIN_THEME_START = MY_JOIN_THEME_START
							+ MY_JOIN_THEME_END;
					MY_JOIN_THEME_END += MY_JOIN_THEME_END;
					// 异步加载
					new GetData(MY_JOIN_THEME_START, MY_JOIN_THEME_END, 1,
							MY_JOIN_THEME).execute();
				} else if (id == MY_JOIN_TOPIC) {
					// 去掉底部
					mListView.removeFooterView(loadMoreView);
					isMore = true;
					// 改变参数
					MY_JOIN_TOPIC_START = MY_JOIN_TOPIC_START
							+ MY_JOIN_TOPIC_END;
					MY_JOIN_TOPIC_END += MY_JOIN_TOPIC_END;
					// 异步加载
					new GetData(MY_JOIN_TOPIC_START, MY_JOIN_TOPIC_END, 1,
							MY_JOIN_TOPIC).execute();
				}
			}
		});
		return loadMoreView;
	}
}
