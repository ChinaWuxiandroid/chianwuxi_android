package com.wuxi.app.activity.homepage.goversaloon;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.engine.GoversaoonOnlineASKDetailService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.GoversaoonOnlineASKDetail;
import com.wuxi.domain.Myconsult;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 我的在线咨询
 * 
 */
public class MyOnlineAskActivity extends BaseSlideActivity implements
		android.widget.RadioGroup.OnCheckedChangeListener, OnClickListener {

	private RadioGroup gover_btn_rg;

	private LinearLayout gover_myonlineask_detail;// 咨询类容相信信息视图

	private LinearLayout gover_myonline_goonask;// 继续咨询视图

	protected static final int LOAD_DETAIL_SUCCESS = 1;

	protected static final int LOAD_DETAIL_FAIL = 0;

	protected static final int COMMIT_SUCCESS = 2;

	protected static final int COMMIT_ERROR = 3;

	private TextView tv_itemid, tv_content, tv_answerContent;

	private Myconsult myconsult;

	private GoversaoonOnlineASKDetail goversaoonOnlineASKDetail;

	private TextView tv_item;

	private Button online_ask_submit, online_ask_reset;

	private EditText et_content;

	private ProgressBar pb_onlineask;

	private String accessToken;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD_DETAIL_SUCCESS:
				showAskDetail();
				break;
			case COMMIT_SUCCESS:
				pb_onlineask.setVisibility(ProgressBar.GONE);
				Toast.makeText(
					MyOnlineAskActivity.this, "提交成功", Toast.LENGTH_SHORT)
						.show();
				break;
			case COMMIT_ERROR:
			case LOAD_DETAIL_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(
					MyOnlineAskActivity.this, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected void findMainContentViews(View view) {

		Bundle b = getIntent().getExtras();
		accessToken = SystemUtil.getAccessToken(this);
		myconsult = (Myconsult) b.get("selectMyconsult");
		int showIndex = b.getInt("showType");

		gover_btn_rg = (RadioGroup) view.findViewById(R.id.gover_btn_rg);
		gover_myonlineask_detail = (LinearLayout) view.findViewById(R.id.gover_myonlineask_detail);
		gover_myonline_goonask = (LinearLayout) view.findViewById(R.id.gover_myonline_goonask);
		tv_item = (TextView) view.findViewById(R.id.tv_item);
		gover_btn_rg.setOnCheckedChangeListener(this);
		tv_itemid = (TextView) view.findViewById(R.id.tv_itemid);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		tv_answerContent = (TextView) view.findViewById(R.id.tv_answerContent);
		online_ask_submit = (Button) view.findViewById(R.id.online_ask_submit);
		online_ask_reset = (Button) view.findViewById(R.id.online_ask_reset);
		et_content = (EditText) view.findViewById(R.id.et_content);
		pb_onlineask = (ProgressBar) view.findViewById(R.id.pb_onlineask);
		online_ask_submit.setOnClickListener(this);
		online_ask_reset.setOnClickListener(this);
		if (showIndex == 0) {

			gover_btn_rg.check(R.id.gover_sallon_my_ask_detail);
		} else if (showIndex == 1) {

			gover_btn_rg.check(R.id.gover_sallon_my_goon_ask);
		}

	}

	private void showAskDetail() {
		pb_onlineask.setVisibility(ProgressBar.GONE);
		tv_itemid.setText("办件序号:" + " ("
				+ goversaoonOnlineASKDetail.getItemid() + ")");
		tv_item.setText(goversaoonOnlineASKDetail.getItemid());
		if (goversaoonOnlineASKDetail.getContent() != null) {
			String sendTime = goversaoonOnlineASKDetail.getSentTime();
			String paseTime = "";

			StringBuffer sb = new StringBuffer();
			sb.append("咨询内容: ")
					.append(" ")
					.append(goversaoonOnlineASKDetail.getContent() + "\n");

			if (sendTime != null && sendTime.equals("")) {
				paseTime = TimeFormateUtil.formateTime(
					sendTime, TimeFormateUtil.DATE_TIME_PATTERN);
				sb.append("[" + paseTime + "]");
			}
			tv_content.setText(sb.toString());
		}

		if (goversaoonOnlineASKDetail.getAnswerContent() != null) {

			String sendTime = goversaoonOnlineASKDetail.getAnswerDate();
			String paseTime = "";

			StringBuffer sb = new StringBuffer();
			sb.append("答复内容: ")
					.append(" ")
					.append(goversaoonOnlineASKDetail.getAnswerContent() + "\n");

			if (sendTime != null && sendTime.equals("")) {
				paseTime = TimeFormateUtil.formateTime(
					sendTime, TimeFormateUtil.DATE_TIME_PATTERN);
				sb.append("[" + paseTime + "]");
			}
			tv_answerContent.setText(sb.toString());
		}

	}

	@Override
	protected int getLayoutId() {
		return R.layout.gover_myonline_ask_layout;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {

		case R.id.gover_sallon_my_ask_detail:
			gover_myonlineask_detail.setVisibility(LinearLayout.VISIBLE);
			gover_myonline_goonask.setVisibility(LinearLayout.GONE);
			if (goversaoonOnlineASKDetail == null) {
				loadMyOnlineAskDetail(myconsult.getId());
			}

			break;
		case R.id.gover_sallon_my_goon_ask:
			gover_myonlineask_detail.setVisibility(LinearLayout.GONE);
			gover_myonline_goonask.setVisibility(LinearLayout.VISIBLE);
			if (goversaoonOnlineASKDetail == null) {
				loadMyOnlineAskDetail(myconsult.getId());
			}
			break;
		}

		for (int index = 0; index < group.getChildCount(); index++) {

			RadioButton rb = (RadioButton) group.getChildAt(index);
			if (rb.isChecked()) {
				rb.setTextColor(Color.WHITE);
			} else {
				rb.setTextColor(Color.BLACK);
			}
		}

	}

	private void loadMyOnlineAskDetail(final String id) {
		pb_onlineask.setVisibility(ProgressBar.VISIBLE);
		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				GoversaoonOnlineASKDetailService goversaoonOnlineASKDetailService = new GoversaoonOnlineASKDetailService(
					MyOnlineAskActivity.this);

				try {
					goversaoonOnlineASKDetail = goversaoonOnlineASKDetailService.getGoversaoonOnlineASKDetail(
						id, accessToken);
					if (goversaoonOnlineASKDetail != null) {
						msg.what = LOAD_DETAIL_SUCCESS;
					} else {
						msg.what = LOAD_DETAIL_FAIL;
						msg.obj = "获取数据失败";

					}
					handler.sendMessage(msg);

				} catch (NetException e) {

					e.printStackTrace();
					msg.what = LOAD_DETAIL_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {

					e.printStackTrace();
					msg.what = LOAD_DETAIL_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NODataException e) {

					e.printStackTrace();
					msg.what = LOAD_DETAIL_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	@Override
	protected String getTitleText() {

		return "我的在线咨询";
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.online_ask_submit:
			if (et_content.getText().toString().equals("")) {
				Toast.makeText(
					MyOnlineAskActivity.this, "请输入咨询内容", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			commitAsk();

			break;
		case R.id.online_ask_reset:
			et_content.setText("");
			break;
		}

	}

	private void commitAsk() {
		if (goversaoonOnlineASKDetail == null) {
			Toast.makeText(this, "没有咨询信息，提交失败", Toast.LENGTH_SHORT).show();
			return;
		}

		pb_onlineask.setVisibility(ProgressBar.VISIBLE);

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				GoversaoonOnlineASKDetailService goversaoonOnlineASKDetailService = new GoversaoonOnlineASKDetailService(
					MyOnlineAskActivity.this);
				try {
					GoversaoonOnlineASKDetail goversaoonOnlineDetail = goversaoonOnlineASKDetailService.commitGoversaoonOnlineASKDetail(
						goversaoonOnlineASKDetail.getId(),
						goversaoonOnlineASKDetail.getItemtype(),
						et_content.getText().toString(), accessToken);
					if (goversaoonOnlineDetail != null) {
						msg.what = COMMIT_SUCCESS;
					} else {
						msg.what = COMMIT_ERROR;
						msg.obj = "提交失败，稍后再试";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = COMMIT_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);

				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = COMMIT_ERROR;
					msg.obj = Constants.ExceptionMessage.DATA_FORMATE;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

}
