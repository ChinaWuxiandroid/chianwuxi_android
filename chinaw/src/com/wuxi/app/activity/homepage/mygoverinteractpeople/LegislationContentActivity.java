/**
 * 
 */
package com.wuxi.app.activity.homepage.mygoverinteractpeople;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.LegislationCommentService;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.LegidlstionInfoFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.LegidlstionReplyFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NetException;

/**
 * 立法征求意见 内容界面
 * 
 * @author 罗森
 * 
 */
public class LegislationContentActivity extends BaseItemContentActivity
		implements OnCheckedChangeListener {

	private RadioGroup radioGroup = null;

	private RadioButton info_radiobtn = null;
	private RadioButton comment_radiobtn = null;

	private View popview = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private Button comment_btn = null;

	private int[] radiobtnids = { 
			R.id.forum_content_info_radiobtn,
			R.id.forum_content_comment_radiobtn };

	private Politics politics = null;

	/**
	 * @param politics
	 *            the politics to set
	 */
	public void setPolitics(Politics politics) {
		this.politics = politics;
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
		return "立法征求意见";
	}

	@Override
	protected void findMainContentViews(View view) {
		super.findMainContentViews(view);

		initLayout(view);

		LegidlstionInfoFragment legidlstionInfoFragment = new LegidlstionInfoFragment();
		legidlstionInfoFragment.setPolitics(getPolitics());
		onTransaction(legidlstionInfoFragment);
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout(View view) {
		radioGroup = (RadioGroup) view
				.findViewById(R.id.forum_content_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);

		info_radiobtn = (RadioButton) view
				.findViewById(R.id.forum_content_info_radiobtn);
		info_radiobtn.setText("征集主题");

		comment_radiobtn = (RadioButton) view
				.findViewById(R.id.forum_content_comment_radiobtn);
		comment_radiobtn.setText("网友意见和反馈");

		comment_btn = (Button) view
				.findViewById(R.id.forum_content_comment_btn);
		comment_btn.setText("填写意见");
		comment_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopWindow(LegislationContentActivity.this);
				int[] xy = new int[2];
				comment_btn.getLocationOnScreen(xy);
				popWindow.showAtLocation(comment_btn, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0,
						comment_btn.getHeight() * 2 + 50);
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

		popview = LayoutInflater.from(con).inflate(
				R.layout.forum_content_popwindow_layout, null);

		Button submitBtn = (Button) popview.findViewById(R.id.forum_submit_btn);
		submitBtn.setText("提交");

		final EditText submitContent = (EditText) popview
				.findViewById(R.id.forum_popwindow_content_edit);

		popWindowManager = PopWindowManager.getInstance();

		final PopupWindow popupWindow = new PopupWindow(popview,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

		popWindowManager.addPopWindow(popupWindow);

		popupWindow.setContentView(popview);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.naviga_leftitem_back));

		popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupWindow.setTouchable(true); // 设置PopupWindow可触摸
		popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String mainid = politics.getId();
				String access_token = SystemUtil
						.getAccessToken(LegislationContentActivity.this);
				String content = submitContent.getText().toString();

				LegislationCommentService service = new LegislationCommentService(
						LegislationContentActivity.this);

				LoginDialog loginDialog = new LoginDialog(
						LegislationContentActivity.this);

				try {

					if (!loginDialog.checkLogin()) {
						loginDialog.showDialog();
						popupWindow.dismiss();
					} else {
						if (content.equals("")) {
							Toast.makeText(LegislationContentActivity.this,
									"提交失败，您未输入任何信息", Toast.LENGTH_SHORT).show();
						} else {
							service.submitData(access_token, mainid, content);

							Toast.makeText(LegislationContentActivity.this,
									"提交成功，正在审核...", Toast.LENGTH_SHORT).show();

						}
					}

				} catch (NetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		return popupWindow;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		GIPRadioButtonStyleChange radioButtonStyleChange = new GIPRadioButtonStyleChange(
				R.drawable.gip_button_selected_bk, 0, Color.WHITE,
				R.color.gip_second_frame_button_brown);

		radioButtonStyleChange.refreshRadioButtonStyle(mainView, radiobtnids,
				checkedId);

		switch (checkedId) {
		case R.id.forum_content_info_radiobtn:
			findMainContentViews(mainView);
			break;

		case R.id.forum_content_comment_radiobtn:
			LegidlstionReplyFragment legidlstionReplyFragment = new LegidlstionReplyFragment();
			legidlstionReplyFragment.setPolitics(getPolitics());
			onTransaction(legidlstionReplyFragment);
			break;
		}
	}

	/**
	 * 获取帖子数据
	 * 
	 * @return
	 */
	private Politics getPolitics() {
		politics = (Politics) getIntent().getExtras().get("politics");
		return politics;
	}

	/**
	 * 跳转界面
	 * 
	 * @param fragment
	 */
	protected void onTransaction(BaseFragment fragment) {
		FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.forum_content_fragment, fragment);
		ft.commitAllowingStateLoss();
	}

}
