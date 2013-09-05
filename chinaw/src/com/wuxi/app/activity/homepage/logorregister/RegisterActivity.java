package com.wuxi.app.activity.homepage.logorregister;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.engine.UserService;
import com.wuxi.app.util.MD5Encoder;
import com.wuxi.domain.User;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 注册
 * 
 */
public class RegisterActivity extends BaseSlideActivity implements
		OnClickListener {

	protected static final int REGIST_SUCCESS = 1;// 登录成功

	protected static final int REGIST_FAIL = 0;

	private EditText et_username, et_truename, et_password, et_confimpassword,
			et_findpass_question,

			et_findpass_answer, et_email, et_tel, et_phone;

	private Button regist_btn_submit, regist_btn_cancle;
	private ProgressDialog pd;

	private User regUser;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REGIST_SUCCESS:
				handlerRegist();
				break;
			case REGIST_FAIL:
				Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT)
						.show();
				break;
			}
			pd.dismiss();
		};
	};

	
	
	@Override
	protected void findMainContentViews(View view) {
	
		et_username = (EditText) view.findViewById(R.id.et_username);
		et_truename = (EditText) view.findViewById(R.id.et_truename);
		et_password = (EditText) view.findViewById(R.id.et_password);
		et_confimpassword = (EditText) view
				.findViewById(R.id.et_confimpassword);
		et_findpass_question = (EditText) view
				.findViewById(R.id.et_findpass_question);
		et_findpass_answer = (EditText) view
				.findViewById(R.id.et_findpass_answer);
		et_email = (EditText) view.findViewById(R.id.et_email);
		et_tel = (EditText) view.findViewById(R.id.et_tel);
		et_phone = (EditText) view.findViewById(R.id.et_phonenumber);

		regist_btn_submit = (Button) view.findViewById(R.id.regist_btn_submit);
		regist_btn_cancle = (Button) view.findViewById(R.id.regist_btn_cancle);

		regist_btn_submit.setOnClickListener(this);
		regist_btn_cancle.setOnClickListener(this);
		pd = new ProgressDialog(this);
		pd.setMessage("正在注册......");
		
	}

	

	protected void handlerRegist() {

		Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.regist_btn_submit:// 提交
			if (validate()) {
				doRegist();
			}
			break;
		case R.id.regist_btn_cancle:
			reset();

			break;

		}

	}

	private void reset() {

		et_username.setText("");
		et_truename.setText("");
		et_password.setText("");
		et_confimpassword.setText("");
		et_findpass_question.setText("");
		et_findpass_answer.setText("");
		et_email.setText("");
		et_tel.setText("");
		et_phone.setText("");
	}

	/**
	 * 
	 * wanglu 泰得利通 处理注册
	 */

	private void doRegist() {

		pd.show();
		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				UserService userService = new UserService(RegisterActivity.this);
				try {
					regUser = userService.resgistUser(getParams());
					if (regUser != null) {
						msg.what = REGIST_SUCCESS;
					} else {
						msg.obj = "注册失败，稍后再试";
						msg.what = REGIST_FAIL;
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = REGIST_FAIL;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = REGIST_FAIL;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = REGIST_FAIL;
					handler.sendMessage(msg);
				} catch (ResultException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = REGIST_FAIL;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
					msg.obj = "错误稍后再试";
					msg.what = REGIST_FAIL;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 构建参数列表
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> getParams() throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", et_username.getText().toString());
		String enPassword = et_password.getText().toString();
		params.put("password", enPassword);
		params.put("realname", et_truename.getText().toString());

		if (!"".equals(et_findpass_answer.getText().toString())) {
			params.put("answer", et_findpass_answer.getText().toString());
		}

		if (!"".equals(et_findpass_question.getText().toString())) {
			params.put("question", et_findpass_question.getText().toString());
		}

		if (!"".equals(et_email.getText().toString())) {
			params.put("email", et_email.getText().toString());
		}

		if (!"".equals(et_tel.getText().toString())) {
			params.put("tel", et_tel.getText().toString());
		}

		if (!"".equals(et_phone.getText().toString())) {
			params.put("phone", et_phone.getText().toString());
		}

		return params;
	}

	/**
	 * 
	 * wanglu 泰得利通 输入检查判断
	 * 
	 * @return
	 */
	private boolean validate() {

		if (et_username.getText().toString().equals("")) {

			et_username.requestFocus();
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		} else if (et_truename.getText().toString().equals("")) {
			et_truename.requestFocus();
			Toast.makeText(this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
			return false;
		} else if (et_password.getText().toString().equals("")) {
			et_password.requestFocus();
			Toast.makeText(this, "请输入登录密码", Toast.LENGTH_SHORT).show();
			return false;
		} else if (et_confimpassword.getText().toString().equals("")) {
			et_confimpassword.requestFocus();
			Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
			return false;
		} else if (!et_confimpassword.getText().toString()
				.equals(et_password.getText().toString())) {
			et_confimpassword.requestFocus();
			Toast.makeText(this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.index_login_regist_layout;
	}

	@Override
	protected String getTitleText() {
		return "登录/注册";
	}

}
