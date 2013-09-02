package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.SubmitListService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.exception.NetException;

/**
 * 公众监督
 * 
 * @author 杨宸 智佳
 * */
public class GoverInterPeoplePublicSuperviseFragment extends BaseFragment
		implements OnClickListener {

	protected View view;
	protected LayoutInflater mInflater;
	private Context context;

	private static final int DATA_SUBMIT_SUCCESS = 2;
	private static final int DATA_SUBMIT_FAILED = 3;

	private EditText sentUserName_et, tel_et, email_et, content_et;
	private ImageButton submit_ibtn, reset_ibtn;
	private ProgressBar pb;

	private LoginDialog loginDialog;

	private String sentUserName = "", tel = "", email = "", content = "";

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_SUBMIT_SUCCESS:
				pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
				break;
			case DATA_SUBMIT_FAILED:
				pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
				break;

			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.goverinterpeople_supervise_layout,
				null);
		mInflater = inflater;
		context = getActivity();

		initView();
		return view;
	}

	public void initView() {
		loginDialog = new LoginDialog(context);// 实例化登录对话框

		if (!loginDialog.checkLogin()) {
			loginDialog.showDialog();
		}

		sentUserName_et = (EditText) view
				.findViewById(R.id.gip_publicsupervise_et_sentUser);
		tel_et = (EditText) view
				.findViewById(R.id.gip_publicsupervise_et_sentTel);
		email_et = (EditText) view
				.findViewById(R.id.gip_publicsupervise_et_email);
		content_et = (EditText) view
				.findViewById(R.id.gip_publicsupervise_et_content);

		submit_ibtn = (ImageButton) view
				.findViewById(R.id.gip_publicsupervise_ibtn_submit);
		reset_ibtn = (ImageButton) view
				.findViewById(R.id.gip_publicsupervise_et_reset);

		pb = (ProgressBar) view.findViewById(R.id.gip_publicsupervise_pb);

		submit_ibtn.setOnClickListener(this);
		reset_ibtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gip_publicsupervise_ibtn_submit:
			// 关闭软键盘
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(submit_ibtn.getWindowToken(), 0);
			// 检测登录状态
			if (loginDialog.checkLogin()) {
				submitData();
			} else {
				loginDialog.showDialog();
			}
			break;
		case R.id.gip_publicsupervise_et_reset:
			resetEditInfo();
			break;
		}
	}

	public void resetEditInfo() {
		sentUserName_et.setText("");
		tel_et.setText("");
		email_et.setText("");
		content_et.setText("");
	}

	/**
	 * 提交
	 * */
	public void submitData() {
		if (!judgeDataLegal()) {
			pb.setVisibility(ProgressBar.VISIBLE);
			new Thread(new Runnable() {

				@Override
				public void run() {
					SubmitListService submitListService = new SubmitListService(
							context);
					try {
						boolean success = false;
						success = submitListService.submitByUrl(getUrl(
								Constants.Urls.CITIZEN_APPLY_SUBMIT_URL,
								SystemUtil.getAccessToken(context)));
						if (success) {
							handler.sendEmptyMessage(DATA_SUBMIT_SUCCESS);
						} else {
							handler.sendEmptyMessage(DATA_SUBMIT_FAILED);
						}
					} catch (NetException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	// 获取提交公民在线申请 的url
	public String getUrl(String urlhead, String access_token) {
		String url = urlhead + "?access_token=" + access_token;
		url = url + "&SentUserName=" + sentUserName + "&SentUserTel=" + tel
				+ "&SentUserEmail=" + email + "&SentContent=" + content;
		return url;
	}

	/**
	 * 判断输入 是否为空
	 * */
	public boolean judgeDataLegal() {
		boolean inputError = false;
		sentUserName = sentUserName_et.getText().toString();
		tel = tel_et.getText().toString();
		email = email_et.getText().toString();
		content = content_et.getText().toString();

		if (!inputError && "".equals(sentUserName)) {
			System.out.println();
			Toast.makeText(context, "姓名 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(tel)) {
			Toast.makeText(context, "联系电话 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(email)) {
			Toast.makeText(context, "E-mail 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(content)) {
			Toast.makeText(context, "问题 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		}
		return inputError;
	}

}
