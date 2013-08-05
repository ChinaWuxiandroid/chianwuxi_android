package com.wuxi.app.fragment.homepage.goversaloon;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.GoversaoonOnlineASKDetailService;
import com.wuxi.app.fragment.BaseSlideFragment;
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
public class MyOnlineAskFragment extends BaseSlideFragment implements
		android.widget.RadioGroup.OnCheckedChangeListener {

	private RadioGroup gover_btn_rg;
	private LinearLayout gover_myonlineask_detail;// 咨询类容相信信息视图
	private LinearLayout gover_myonline_goonask;// 继续咨询视图

	protected static final int LOAD_DETAIL_SUCCESS = 1;
	protected static final int LOAD_DETAIL_FAIL = 0;
	private TextView tv_itemid, tv_content, tv_answerContent;

	private Myconsult myconsult;
	private GoversaoonOnlineASKDetail goversaoonOnlineASKDetail;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD_DETAIL_SUCCESS:
				showAskDetail();
				break;
			case LOAD_DETAIL_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public void initUI() {
		Bundle b = this.getArguments();
		myconsult = (Myconsult) b.get("selectMyconsult");
		int showIndex = b.getInt("showType");
		super.slideLinstener.closeSlideMenu();
		super.initUI();
		gover_btn_rg = (RadioGroup) view.findViewById(R.id.gover_btn_rg);
		gover_myonlineask_detail = (LinearLayout) view
				.findViewById(R.id.gover_myonlineask_detail);
		gover_myonline_goonask = (LinearLayout) view
				.findViewById(R.id.gover_myonline_goonask);

		gover_btn_rg.setOnCheckedChangeListener(this);
		tv_itemid = (TextView) view.findViewById(R.id.tv_itemid);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		tv_answerContent = (TextView) view.findViewById(R.id.tv_answerContent);
		if (showIndex == 0) {

			gover_btn_rg.check(R.id.gover_sallon_my_ask_detail);
		} else if (showIndex == 1) {

			gover_btn_rg.check(R.id.gover_sallon_my_goon_ask);
		}

	}

	private void showAskDetail() {
		tv_itemid.setText(tv_itemid.getText() + " ("
				+ goversaoonOnlineASKDetail.getItemid() + ")");
		if (goversaoonOnlineASKDetail.getContent() != null) {
			String sendTime = goversaoonOnlineASKDetail.getSentTime();
			String paseTime = "";

			StringBuffer sb = new StringBuffer();
			sb.append(tv_content.getText()).append(" ")
					.append(goversaoonOnlineASKDetail.getContent() + "\n");

			if (sendTime != null && sendTime.equals("")) {
				paseTime = TimeFormateUtil.formateTime(sendTime,
						TimeFormateUtil.DATE_TIME_PATTERN);
				sb.append("[" + paseTime + "]");
			}
			tv_content.setText(sb.toString());
		}

		if (goversaoonOnlineASKDetail.getAnswerContent() != null) {

			String sendTime = goversaoonOnlineASKDetail.getAnswerDate();
			String paseTime = "";

			StringBuffer sb = new StringBuffer();
			sb.append(tv_answerContent.getText())
					.append(" ")
					.append(goversaoonOnlineASKDetail.getAnswerContent() + "\n");

			if (sendTime != null && sendTime.equals("")) {
				paseTime = TimeFormateUtil.formateTime(sendTime,
						TimeFormateUtil.DATE_TIME_PATTERN);
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
		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				GoversaoonOnlineASKDetailService goversaoonOnlineASKDetailService = new GoversaoonOnlineASKDetailService(
						context);

				try {
					goversaoonOnlineASKDetail = goversaoonOnlineASKDetailService
							.getGoversaoonOnlineASKDetail(id,
									MyGoverSaloonFragment.ACCESS_TOKEN);
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

}
