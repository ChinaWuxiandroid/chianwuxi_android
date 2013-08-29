/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.ForumCommentService;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.GIPRadioButtonStyleChange;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.ForumWrapper.Forum;
import com.wuxi.exception.NetException;

/**
 * 公众论坛帖子详细内容碎片
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumContentFragment extends BaseItemContentFragment implements
		OnCheckedChangeListener {

	private RadioGroup radioGroup = null;

	private View popview = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private Button comment_btn = null;

	private Forum forum;

	private int[] radiobtnids = { R.id.forum_content_info_radiobtn,
			R.id.forum_content_comment_radiobtn };

	/**
	 * @param forum
	 *            the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentLayoutId()
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.forum_content_layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentTitleText()
	 */
	@Override
	protected String getContentTitleText() {
		return "公众论坛";
	}

	@Override
	public void initUI() {
		super.initUI();
		initLayout();

		ForumOrdinaryPostFragment forumOrdinaryFragment = new ForumOrdinaryPostFragment();
		forumOrdinaryFragment.setForum(getForum());
		onTransaction(forumOrdinaryFragment);
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		radioGroup = (RadioGroup) view
				.findViewById(R.id.forum_content_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);

		comment_btn = (Button) view
				.findViewById(R.id.forum_content_comment_btn);
		comment_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopWindow(context);
				int[] xy = new int[2];
				comment_btn.getLocationOnScreen(xy);
				popWindow.showAtLocation(comment_btn, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0,
						comment_btn.getHeight() * 2 + 20);
			}
		});
	}

	/**
	 * 创建弹出窗体
	 * 
	 * @param con
	 * @return
	 */
	private PopupWindow makePopWindow(Context con) {
		PopupWindow popupWindow = null;

		popview = LayoutInflater.from(con).inflate(
				R.layout.forum_content_popwindow_layout, null);

		Button submitBtn = (Button) popview.findViewById(R.id.forum_submit_btn);

		final EditText submitContent = (EditText) popview
				.findViewById(R.id.forum_popwindow_content_edit);
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String id = forum.getId();
				String access_token = SystemUtil.getAccessToken(context);
				String type = forum.getViewpath();
				String content = submitContent.getText().toString();

				ForumCommentService commentService = new ForumCommentService(
						context);

				LoginDialog loginDialog = new LoginDialog(context,
						baseSlideFragment);

				try {
					if (!forum.getViewpath().equals("/SurveryContent")) {
						if (loginDialog.checkLogin()) {
							if (!content.equals("")) {
								boolean isSubnit = commentService
										.submitComment(id, access_token, type,
												content);

								Toast.makeText(context, "提交成功，正待审核...",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "提交失败，您没有输入任何信息",
										Toast.LENGTH_SHORT).show();
							}

						} else {
							// loginDialog.showDialog();
							Toast.makeText(context, "提交失败，您未登录，请登录后再发表评论，谢谢！",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(context, "调查问卷类帖子功能暂未实现",
								Toast.LENGTH_SHORT).show();
					}

				} catch (NetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		popWindowManager = PopWindowManager.getInstance();

		popWindowManager.addPopWindow(popupWindow);

		popupWindow = new PopupWindow(con);

		popupWindow.setContentView(popview);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.naviga_leftitem_back));
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);

		popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupWindow.setTouchable(true); // 设置PopupWindow可触摸
		popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		return popupWindow;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		GIPRadioButtonStyleChange radioButtonStyleChange = new GIPRadioButtonStyleChange(
				R.drawable.gip_button_selected_bk, 0, Color.WHITE,
				R.color.gip_second_frame_button_brown);
		radioButtonStyleChange.refreshRadioButtonStyle(view, radiobtnids,
				checkedId);

		switch (checkedId) {
		case R.id.forum_content_info_radiobtn:
			initUI();
			break;

		case R.id.forum_content_comment_radiobtn:
			if (!forum.getViewpath().equals("/SurveryContent")) {
				ForumOrdinaryReplayFragment forumOrdinaryReplayFragment = new ForumOrdinaryReplayFragment();
				forumOrdinaryReplayFragment.setForum(getForum());
				onTransaction(forumOrdinaryReplayFragment);
			} else {
				Toast.makeText(context, "调查问卷类帖子暂未实现该功能", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		}
	}

	/**
	 * 获取帖子数据
	 * 
	 * @return
	 */
	private Forum getForum() {
		forum = (Forum) getArguments().get("forum");
		return forum;
	}

	/**
	 * 跳转界面
	 * 
	 * @param fragment
	 */
	protected void onTransaction(BaseFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.forum_content_fragment, fragment);
		ft.commit();
	}

}
