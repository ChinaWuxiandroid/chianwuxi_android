/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.adapter.LiveHomeLeaveMessageListAdapter;
import com.wuxi.app.adapter.LiveHomeMemoirListAdapter;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.LeaveMessageService;
import com.wuxi.app.engine.MemoirService;
import com.wuxi.app.engine.VideoSubmitIdeaService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.net.HttpUtils;
import com.wuxi.app.net.NetworkUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.LeaveMessageWrapper;
import com.wuxi.domain.LeaveMessageWrapper.LeaveMessage;
import com.wuxi.domain.MemoirWrapper;
import com.wuxi.domain.MemoirWrapper.Memoir;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 走进直播间之栏目首页
 * 
 * @author 智佳 罗森
 * @changetime 2013年8月9日 16:47
 * 
 */
public class GoverInterPeopleVideoLiveHomeFragment extends
		RadioButtonChangeFragment implements OnClickListener {

	private static final String TAG = "GoverInterPeopleVideoLiveHomeFragment:";

	// 我来说说按钮
	private Button home_saybtn = null;

	// 我来提问按钮
	private Button home_askbtn = null;

	// 内容类型：0->节目预告界面；1->访谈实录界面；2->留言提问界面
	private int type = 0;

	private View popview = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	// 问题类型：1->说说；2->提问
	private int questionType = 1;

	private LinearLayout liveLayout;

	// 弹出窗体布局控件
	private TextView theme;
	private TextView text1;

	private EditText nameEdit = null;

	// 预告主题
	private TextView advance_themeTextView = null;
	// 预告时间
	private TextView advance_timeTextView = null;
	// 预告嘉宾
	private TextView advance_guestTextView = null;
	// 预告更多
	private TextView advance_moreTextView = null;

	// 直播主题
	private TextView live_themeTextView = null;
	// 直播时间
	private TextView live_timeTextView = null;
	// 直播嘉宾
	private TextView live_guestTextView = null;
	// 直播视频
	private ImageView homeLiveImageView = null;
	// 点击观看视频
	private TextView live_watchTextView = null;

	// 存放该界面的RadioBtnID的数组
	private final int[] radioBtnIds = {
			R.id.gip_video_live_home_radioBtn_vediolive,
			R.id.gip_video_live_home_radioBtn_memoir,
			R.id.gip_video_live_home_radioBtn_message };

	// private ProgressBar messageProgressBar = null;

	private ListView messageListView = null;

	private LeaveMessageWrapper messageWrapper = null;

	private List<LeaveMessage> leaveMessages = null;

	private LiveHomeLeaveMessageListAdapter messageAdapter;

	// 数据加载成功标志
	private static final int MESSAGE_LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int MESSAGE_LOAD_ERROR = 1;

	// 获取提问留言的起始坐标
	private static final int START = 0;
	private int messagevisibleLastIndex;
	private int messagevisibleItemCount;// 当前显示的总条数
	private final static int MESSAGE_PAGE_NUM = 5;

	private boolean isFirstLoadMessage = true;// 是不是首次加载数据
	private boolean isSwitchMessage = false;// 切换
	private boolean isLoadingMessage = false;

	private View loadMoreMessageView;// 加载更多视图
	private Button loadMoreMessageButton;

	private ListView mListView = null;

	private MemoirWrapper memoirWrapper = null;

	private List<Memoir> memoirs = null;

	private LiveHomeMemoirListAdapter adapter;

	// 数据加载成功标志
	private static final int DATA_LOAD_SUCESS = 2;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 3;

	// 获取实录的起始坐标
	private int startIndex = 0;
	private int visibleLastIndex;
	private int memoirvisibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;
	private ProgressBar pb_load;

	private boolean isMore = false;

	private boolean isMoreLeave = false;

	private int isFalg = 0;

	private int isShowMore = 0;

	private boolean isInit = false;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case MESSAGE_LOAD_SUCESS:
				showLeaveMessageList();
				break;

			case MESSAGE_LOAD_ERROR:
				pb_load.setVisibility(View.GONE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case DATA_LOAD_SUCESS:
				showMemoirList();
				break;

			case DATA_LOAD_ERROR:
				pb_load.setVisibility(View.GONE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected int getLayoutId() {
		return R.layout.gip_vedio_live_home_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_vedio_live_home_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioBtnIds;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		initLayout();
		initMessageLayout();
		initMemoirLayout();
		loadVideoInfo();
	}

	/**
	 * 切换内容
	 */
	private void changeContent(int type) {
		switch (type) {
		case 0:
			init();
			if (!isFirstLoadMessage || !isFirstLoad) {
				if (isFirstLoadMessage) {
					messageListView.removeFooterView(loadMoreMessageView);
				}
				if (isFirstLoad) {
					mListView.removeFooterView(loadMoreView);
				}
			}
			isInit = true;
			isShowMore = 0;
			home_saybtn.setVisibility(View.GONE);
			home_askbtn.setVisibility(View.GONE);
			liveLayout.setVisibility(View.VISIBLE);
			messageListView.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
			break;

		case 1:
			if (isFirstLoadMessage) {
				loadFirstMessageData(START, MESSAGE_PAGE_NUM);
			} else {
				if (isShowMore == 0) {
					messageListView.removeFooterView(loadMoreMessageView);
				}
				mListView.removeFooterView(loadMoreView);
			}
			isFalg += 1;
			if (isFalg == 2) {
				isShowMore = 1;
			} else {
				if (isInit) {
					isShowMore = 0;
				}
			}
			isInit = false;
			home_saybtn.setVisibility(View.GONE);
			home_askbtn.setVisibility(View.GONE);
			liveLayout.setVisibility(View.GONE);
			messageListView.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
			break;

		case 2:
			if (isFirstLoad) {
				loadFirstData(startIndex, PAGE_NUM);
			} else {
				if (isShowMore == 0) {
					mListView.removeFooterView(loadMoreView);
				}
				messageListView.removeFooterView(loadMoreMessageView);
			}
			isFalg += 1;
			if (isFalg == 2) {
				isShowMore = 2;
			} else {
				if (isInit) {
					isShowMore = 0;
				}
			}
			isInit = false;
			home_saybtn.setVisibility(View.VISIBLE);
			home_askbtn.setVisibility(View.VISIBLE);
			liveLayout.setVisibility(View.GONE);
			messageListView.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		case R.id.gip_video_live_home_radioBtn_vediolive:
			type = 0;
			changeContent(type);
			break;

		case R.id.gip_video_live_home_radioBtn_memoir:
			type = 1;
			changeContent(type);
			break;

		case R.id.gip_video_live_home_radioBtn_message:
			type = 2;
			changeContent(type);
			break;
		}
	}

	/**
	 * @方法： makePopWindow
	 * @描述： 创建弹出窗口
	 * @param context
	 * @return
	 */
	private PopupWindow makePopWindow(final Context context, final int type) {
		// 加载弹出窗口的布局文件
		popview = LayoutInflater.from(context).inflate(
				R.layout.video_comment_pop_layout, null);
		// 初始化弹出窗口中的各个控件
		theme = (TextView) popview.findViewById(R.id.video_comment_pop_theme);
		text1 = (TextView) popview
				.findViewById(R.id.video_comment_pop_textview1);

		Button submitBtn = (Button) popview
				.findViewById(R.id.video_comment_pop_submit_btn);

		Button cancelBtn = (Button) popview
				.findViewById(R.id.video_comment_pop_cancel_btn);

		nameEdit = (EditText) popview.findViewById(R.id.video_comment_pop_name);

		final EditText contentEdit = (EditText) popview
				.findViewById(R.id.video_comment_pop_content);

		// 获取PopWindowManager的实例
		popWindowManager = PopWindowManager.getInstance();

		// 设置弹出窗口的长和宽
		final PopupWindow popupWindow = new PopupWindow(popview,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		// 添加弹出窗口到界面
		popWindowManager.addPopWindow(popupWindow);

		// 设置弹出窗口的背景图片
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.naviga_leftitem_back));
		// 设置PopupWindow可获得焦点
		popupWindow.setFocusable(true);
		// 设置PopupWindow可触摸
		popupWindow.setTouchable(true);
		// 设置非PopupWindow区域可触摸
		popupWindow.setOutsideTouchable(true);

		// 处理提交按钮的事件监听
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 实例化数据提交服务对象
				VideoSubmitIdeaService service = new VideoSubmitIdeaService(
						context);
				// 初始化LoginDialog对象
				LoginDialog dialog = new LoginDialog(context);

				String url = null;

				// 当type=1时，即我要说说
				if (type == 1) {
					// 如果昵称为空，给出提示
					if (nameEdit.getText().toString().equals("")) {
						Toast.makeText(context, "提交失败，您没有输入昵称！",
								Toast.LENGTH_SHORT).show();
					}
					// 如果内容为空，给出提示
					else if (contentEdit.getText().toString().equals("")) {
						Toast.makeText(context, "提交失败，您没有输入内容！",
								Toast.LENGTH_SHORT).show();
					} else {
						// 构建数据提交url
						url = Constants.Urls.VIDEO_SUBMIT_IDEA_URL.replace(
								"{interViewId}",
								"32480e19-76b8-45d9-b7d1-a6c54933f9f7")
								+ "?content="
								+ contentEdit.getText().toString()
								+ "&questionType="
								+ type
								+ "&nickName="
								+ nameEdit.getText().toString();
						try {
							// 提交数据
							service.submitIdea(url);
							// 关闭弹出窗口
							popupWindow.dismiss();
							Toast.makeText(context, "提交说说成功，正在审核...",
									Toast.LENGTH_SHORT).show();
						} catch (NetException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				// 当type=2，即我要提问
				else if (type == 2) {
					// 如果用户没有登录
					if (!dialog.checkLogin()) {
						// 显示提醒对话框
						dialog.showDialog();
						// 关闭弹出窗口
						popupWindow.dismiss();
					}
					// 如果标题为空，给出提示
					else if (nameEdit.getText().toString().equals("")) {
						Toast.makeText(context, "提交失败，您没有输入标题！",
								Toast.LENGTH_SHORT).show();
					}
					// 如果内容为空，给出提示
					else if (contentEdit.getText().toString().equals("")) {
						Toast.makeText(context, "提交失败，您没有输入内容！",
								Toast.LENGTH_SHORT).show();
					} else {
						// 构建提交数据url
						url = Constants.Urls.VIDEO_SUBMIT_IDEA_URL.replace(
								"{interViewId}",
								"32480e19-76b8-45d9-b7d1-a6c54933f9f7")
								+ "?content="
								+ contentEdit.getText().toString()
								+ "&questionType="
								+ type
								+ "&access_token="
								+ SystemUtil.getAccessToken(context);
						try {
							// 提交数据
							service.submitIdea(url);
							// 关闭弹出窗口
							popupWindow.dismiss();
							Toast.makeText(context, "提交留言成功，正在审核...",
									Toast.LENGTH_SHORT).show();
						} catch (NetException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		// 处理取消按钮的事件监听
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭弹出窗口
				popupWindow.dismiss();
				Toast.makeText(context, "您取消了提交数据的操作！", Toast.LENGTH_SHORT)
						.show();
			}
		});

		return popupWindow;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {

		home_saybtn = (Button) view.findViewById(R.id.vedio_live_home_saybtn);
		pb_load = (ProgressBar) view.findViewById(R.id.gip_pb);

		// 我要说说按钮事件监听处理
		home_saybtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				questionType = 1;
				popWindow = makePopWindow(context, questionType);
				int[] xy = new int[2];
				home_saybtn.getLocationOnScreen(xy);

				popWindow.showAtLocation(home_saybtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, home_saybtn.getHeight() * 3);
			}
		});

		home_askbtn = (Button) view.findViewById(R.id.vedio_live_home_askbtn);
		// 我要提问按钮事件监听处理
		home_askbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				questionType = 2;
				popWindow = makePopWindow(context, questionType);

				theme.setText("我要提问");
				text1.setText("标题：");
				nameEdit.setHint("请输入标题");

				int[] xy = new int[2];
				home_askbtn.getLocationOnScreen(xy);

				popWindow.showAtLocation(home_askbtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, home_askbtn.getHeight() * 3);
			}
		});

		liveLayout = (LinearLayout) view
				.findViewById(R.id.gip_home_live_layout);
		// messageLayout = (RelativeLayout)
		// view.findViewById(R.id.message_fragment);
		// memoirLayout = (RelativeLayout)
		// view.findViewById(R.id.memoir_layout);

		// 节目预告模块
		advance_themeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_theme_text);
		advance_timeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_time_text);
		advance_guestTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_guest_text);
		advance_moreTextView = (TextView) view
				.findViewById(R.id.vedio_bdroom_home_live_more_text);
		advance_moreTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						PromoMoreVideoActivity.class);
				MainTabActivity.instance.addView(intent);
				advance_moreTextView.setTextColor(Color.BLUE);
			}
		});

		// 视频直播模块
		live_themeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_theme_content);
		live_timeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_time_content);
		live_guestTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_guest_content);
		live_watchTextView = (TextView) view
				.findViewById(R.id.vedio_dbroom_home_live_click_text);
		live_watchTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new getVideoPlayer().execute();
				live_watchTextView.setTextColor(Color.BLUE);
			}
		});
	}

	private class getVideoPlayer extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			String url = Constants.Urls.DOMAIN_URL
					+ "/api/interview/32480e19-76b8-45d9-b7d1-a6c54933f9f7/video.json";

			NetworkUtil mUtil = NetworkUtil.getInstance();
			String data = null;
			if (mUtil.isConnet(context)) {
				HttpUtils mHttpUtils = HttpUtils.getInstance();
				data = mHttpUtils.executeGetToString(url, 5000);
			} else {
				Toast.makeText(context, "连接网络失败", Toast.LENGTH_SHORT).show();
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.length() > 5) {
				try {
					JSONObject object = new JSONObject(result);
					String url = object.getString("result");
					if (url.length() > 5) {
						boadcastVoide(url);
						// Intent intent = new Intent();
						// intent.setClass(getActivity(),
						// PromoVideoPlayerActivity.class);
						// intent.putExtra("videoUrl", url);
						// startActivity(intent);
					} else {
						Toast.makeText(context, "暂无视频", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (Exception e) {
					Toast.makeText(context, "解析数据失败", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	/**
	 * 播放视频
	 * 
	 * @方法： boadcastVoide
	 */
	private void boadcastVoide(String path) {
		Intent it = new Intent();
		it.setAction(Intent.ACTION_VIEW);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Uri uri = Uri.parse(path);
		it.setType("video/mp4");
		it.setDataAndType(uri, "video/mp4");
		startActivity(it);
	}

	/**
	 * 
	 * 获取数据--节目预告
	 * 
	 * @方法： load
	 * @描述： TODO
	 */
	private void loadVideoInfo() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message message = handler.obtainMessage();
				LeaveMessageService messageService = new LeaveMessageService(
						context);
				try {
					messageService.getVideoPreview(0, 10);
				} catch (NetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstMessageData(int start, int end) {
		loadMessageData(start, end);
	}

	/**
	 * 加载数据
	 */
	private void loadMessageData(final int startIndex, final int endIndex) {
		if (isFirstLoadMessage || isSwitchMessage) {
			pb_load.setVisibility(View.VISIBLE);
		} else {
			pb_load.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoadingMessage = true;// 正在加载数据
				Message message = handler.obtainMessage();
				LeaveMessageService messageService = new LeaveMessageService(
						context);
				try {
					messageWrapper = messageService.getLeaveMessageWrapper(
							"32480e19-76b8-45d9-b7d1-a6c54933f9f7", startIndex,
							endIndex);
					if (messageWrapper != null) {
						handler.sendEmptyMessage(MESSAGE_LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(MESSAGE_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(MESSAGE_LOAD_ERROR);
				} catch (JSONException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(MESSAGE_LOAD_ERROR);
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(MESSAGE_LOAD_ERROR);
				}
			}
		}).start();
	}

	/**
	 * 加载布局控件
	 */
	private void initMessageLayout() {
		messageListView = (ListView) view
				.findViewById(R.id.gip_live_home_message_listview);

		messageListView.addFooterView(getMessageListFootView());// 为listView添加底部视图
		messageListView.setOnScrollListener(new MessageOnScrollListener());// 增加滑动监听

	}

	/**
	 * @方法： getMessageListFootView
	 * @描述： TODO
	 * @return
	 */
	private View getMessageListFootView() {
		loadMoreMessageView = View.inflate(context,
				R.layout.list_loadmore_layout, null);
		loadMoreMessageButton = (Button) loadMoreMessageView
				.findViewById(R.id.loadMoreButton);

		loadMoreMessageButton.setOnClickListener(this);
		return loadMoreMessageView;
	}

	/**
	 * 显示列表数据
	 */
	private void showLeaveMessageList() {

		leaveMessages = messageWrapper.getLeaveMessages();

		if (leaveMessages == null || leaveMessages.size() == 0) {
			Toast.makeText(context, "对不起，暂无留言提问信息", Toast.LENGTH_SHORT).show();
			pb_load.setVisibility(View.GONE);
		} else {
			if (isFirstLoadMessage) {
				messageAdapter = new LiveHomeLeaveMessageListAdapter(context,
						leaveMessages);
				isFirstLoadMessage = false;
				messageListView.setAdapter(messageAdapter);
				pb_load.setVisibility(View.GONE);
				isLoadingMessage = false;
			} else {
				if (isSwitchMessage) {
					messageAdapter.setLeaveMessages(leaveMessages);
				} else {
					for (LeaveMessage leaveMessage : leaveMessages) {
						messageAdapter.addItem(leaveMessage);
					}
				}

				pb_load.setVisibility(View.GONE);

				messageAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				messageListView.setSelection(messagevisibleLastIndex
						- messagevisibleItemCount + 1); // 设置选中项
				isLoadingMessage = false;
			}
		}

		isMore = messageWrapper.isNext();

		if (!isFirstLoadMessage) {
			if (leaveMessages != null && leaveMessages.size() > 0
					&& messageWrapper.isNext()) {
				// if (messageListView.getFooterViewsCount() != 0) {
				if (isMore) {
					// messageListView.removeFooterView(loadMoreMessageView);
					pb_load.setVisibility(ProgressBar.GONE);
					loadMoreMessageButton.setText("点击加载更多");
				}
				// } else {
				// messageListView.addFooterView(getMessageListFootView());
				// }

			} else {
				if (messageAdapter != null) {
					messageListView.removeFooterView(loadMoreMessageView);
				}

			}
		} else {
			if (isMore) {
				pb_load.setVisibility(ProgressBar.GONE);
				loadMoreMessageButton.setText("点击加载更多");
			} else {
				messageListView.removeFooterView(loadMoreMessageView);
			}
		}

	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreMessageData(View view) {
		if (isLoadingMessage) {
			return;
		} else {
			loadMessageData(messagevisibleLastIndex + 1,
					messagevisibleLastIndex + 1 + MESSAGE_PAGE_NUM);
		}
	}

	private class MessageOnScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			messagevisibleItemCount = visibleItemCount;
			messagevisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = messageAdapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 加载更多访谈实录
		case R.id.loadMoreButton:
			if (messageWrapper != null && messageWrapper.isNext()) {// 还有下一条记录
				isSwitchMessage = false;
				loadMoreMessageButton.setText("loading.....");
				loadMoreMessageData(v);
			}
			break;

		// 留言提问
		case R.id.loadapply_MoreButton:
			if (memoirWrapper != null && memoirWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	/**
	 * 加载布局文件
	 */
	private void initMemoirLayout() {
		mListView = (ListView) view
				.findViewById(R.id.gip_live_home_memoir_listview);

		mListView.addFooterView(getMemoirListFootView());// 为listView添加底部视图
		mListView.setOnScrollListener(new MemoirOnScrollListner());// 增加滑动监听

	}

	private View getMemoirListFootView() {
		loadMoreView = View.inflate(context,
				R.layout.myapply_list_loadmore_layout, null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadapply_MoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_applyloadmoore);
		loadMoreButton.setOnClickListener(this);
		return loadMoreView;
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
	 * 加载数据
	 */
	private void loadData(final int startIndex, final int endIndex) {
		if (isFirstLoad || isSwitch) {
			pb_load.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				MemoirService memoirService = new MemoirService(context);
				try {
					memoirWrapper = memoirService.getMemoirWrapper(
							"32480e19-76b8-45d9-b7d1-a6c54933f9f7", startIndex,
							endIndex);
					if (memoirWrapper != null) {
						handler.sendEmptyMessage(DATA_LOAD_SUCESS);
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
	 * 显示实录列表
	 */
	private void showMemoirList() {

		memoirs = memoirWrapper.getMemoirs();
		if (memoirs == null || memoirs.size() == 0) {
			Toast.makeText(context, "对不起，暂无访谈实录信息", Toast.LENGTH_SHORT).show();
		} else {
			if (isFirstLoad) {
				adapter = new LiveHomeMemoirListAdapter(context, memoirs);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				pb_load.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setMemoirs(memoirs);
					pb_load.setVisibility(View.GONE);
				} else {
					for (Memoir memoir : memoirs) {
						adapter.addItem(memoir);
					}
				}

				pb_load.setVisibility(View.GONE);
				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex
						- memoirvisibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		isMoreLeave = memoirWrapper.isNext();

		// if (memoirWrapper.isNext()) {

		if (!isFirstLoad) {
			if (isMoreLeave) {

				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				mListView.removeFooterView(loadMoreView);
			}
		} else {
			if (isMoreLeave) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				mListView.removeFooterView(loadMoreView);
			}
		}
		// } else {
		// if (adapter != null) {
		// mListView.removeFooterView(loadMoreView);
		// }
		// }
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

	private class MemoirOnScrollListner implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			memoirvisibleItemCount = visibleItemCount;
			visibleLastIndex = firstVisibleItem + memoirvisibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		}

	}

}
