/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.VideoSubmitIdeaService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.exception.NetException;

/**
 * 走进直播间之栏目首页
 * 
 * @author 智佳 罗森
 * @changetime 2013年8月9日 16:47
 * 
 */
public class GoverInterPeopleVideoLiveHomeFragment extends
		RadioButtonChangeFragment {

	private static final int HOME_CONTENT_ID = R.id.gip_vedio_live_home_fragment;

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

	// 弹出窗体布局控件
	private TextView theme;
	private TextView text1;

	// 存放该界面的RadioBtnID的数组
	private final int[] radioBtnIds = {
			R.id.gip_video_live_home_radioBtn_vediolive,
			R.id.gip_video_live_home_radioBtn_memoir,
			R.id.gip_video_live_home_radioBtn_message };

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
		// 创建视频直播界面碎片的实例
		GoverInterPeopleVideoLiveHomeLiveFragment gipvlhlFragment = new GoverInterPeopleVideoLiveHomeLiveFragment();
		// 绑定视频直播界面碎片
		bindFragment(gipvlhlFragment);
		initLayout();
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HOME_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * 切换内容
	 */
	private void changeContent(int type) {
		switch (type) {
		case 0:
			init();
			break;

		case 1:
			LiveHomeMemoirFragment liveHomeMemoirFragment = new LiveHomeMemoirFragment();
			bindFragment(liveHomeMemoirFragment);
			break;

		case 2:
			LiveHomeLeaveMessageFragment leaveMessageFragment = new LiveHomeLeaveMessageFragment();
			bindFragment(leaveMessageFragment);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		case R.id.gip_video_live_home_radioBtn_vediolive:
			type = 0;
			home_saybtn.setVisibility(View.GONE);
			home_askbtn.setVisibility(View.GONE);
			changeContent(type);
			break;

		case R.id.gip_video_live_home_radioBtn_memoir:
			type = 1;
			home_saybtn.setVisibility(View.GONE);
			home_askbtn.setVisibility(View.GONE);
			changeContent(type);
			break;

		case R.id.gip_video_live_home_radioBtn_message:
			type = 2;
			home_saybtn.setVisibility(View.VISIBLE);
			home_askbtn.setVisibility(View.VISIBLE);
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

		final EditText nameEdit = (EditText) popview
				.findViewById(R.id.video_comment_pop_name);

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

				int[] xy = new int[2];
				home_askbtn.getLocationOnScreen(xy);

				popWindow.showAtLocation(home_askbtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, home_askbtn.getHeight() * 3);
			}
		});
	}

}
