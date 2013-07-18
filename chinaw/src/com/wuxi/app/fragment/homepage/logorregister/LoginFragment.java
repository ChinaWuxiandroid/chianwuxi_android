package com.wuxi.app.fragment.homepage.logorregister;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.UserService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.MD5Encoder;
import com.wuxi.domain.User;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 登录
 */
public class LoginFragment extends BaseSlideFragment implements OnClickListener {

	private static final int LOGIN_SUCCESS = 0;// 登录成功
	protected static final int LOGIN_FAIL = 1;// 登录失败
	private Button login_btn_login;
	private Button login_btn_regist;
	private EditText login_et_username;// 用户名
	private EditText login_et_pwd;// 密码
	private ProgressBar pb_login;// 登录等待
	private User user;
	private SharedPreferences sp;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOGIN_SUCCESS:

				pb_login.setVisibility(ProgressBar.GONE);// 隐藏进度条
				loginHandler();// 登录成功处理
				break;
			case LOGIN_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				pb_login.setVisibility(ProgressBar.GONE);// 隐藏进度条
				break;
			}

		};
	};

	@Override
	public void initUI() {
		slideLinstener.closeSlideMenu();//关掉左右
		super.initUI();
		login_btn_login = (Button) view.findViewById(R.id.login_btn_login);
		login_btn_regist = (Button) view.findViewById(R.id.login_btn_regist);
		login_et_pwd = (EditText) view.findViewById(R.id.login_et_pwd);// 密码
		login_et_username = (EditText) view
				.findViewById(R.id.login_et_username);// 用户名
		pb_login = (ProgressBar) view.findViewById(R.id.pb_login);

		login_btn_login.setOnClickListener(this);
		login_btn_regist.setOnClickListener(this);
	}

	private void loginHandler() {
		CacheUtil.put(Constants.CacheKey.LOGIN_USER, user);// 将登录的用户存入缓存中

		sp = context
				.getSharedPreferences(
						Constants.SharepreferenceKey.SHARE_CONFIG,
						Context.MODE_PRIVATE);

		Editor editor = sp.edit();

		editor.putString(Constants.SharepreferenceKey.ACCESSTOKEN,
				user.getAccessToken());
		editor.putString(Constants.SharepreferenceKey.REFRESHTOKEN,
				user.getRefreshToken());
		editor.putString(Constants.SharepreferenceKey.USERNAME,
				user.getUserName());
		editor.commit();// tijiao

		Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_btn_login:
			if (validate()) {
				doLogin();
			}
			break;
		case R.id.login_btn_regist:
			RegisterFragment registFragment = new RegisterFragment();
			managers.IntentFragment(registFragment);
			break;
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 登录
	 */
	private void doLogin() {
		pb_login.setVisibility(ProgressBar.VISIBLE);
		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				try {

					String userName = login_et_username.getText().toString();
					String password = login_et_pwd.getText().toString();
					UserService userService = new UserService(context);
					user = userService.login(userName,
							MD5Encoder.getLoginPWDMD5(password, "utf-8"));

					if (user != null) {
						msg.what = LOGIN_SUCCESS;

					} else {
						msg.obj = "用户名密码有误！";
						msg.what = LOGIN_FAIL;
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = LOGIN_FAIL;
					handler.sendMessage(msg);

				} catch (NODataException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = LOGIN_FAIL;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.obj = Constants.ExceptionMessage.DATA_FORMATE;
					msg.what = LOGIN_FAIL;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
					msg.obj = "登录错误再试";
					msg.what = LOGIN_FAIL;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 验证登录
	 * 
	 * @return
	 */
	private boolean validate() {

		if (login_et_username.getText().toString().equals("")) {
			Toast.makeText(context, "请输入用户名!", Toast.LENGTH_SHORT).show();
			login_et_username.requestFocus();
			return false;
		} else if (login_et_pwd.getText().toString().equals("")) {
			Toast.makeText(context, "请输入密码!", Toast.LENGTH_SHORT).show();
			login_et_pwd.requestFocus();
			return false;
		}

		return true;

	}

	@Override
	protected String getTitleText() {

		return "登录/注册";
	}

	@Override
	protected int getLayoutId() {

		return R.layout.index_login_layout;

	}
}