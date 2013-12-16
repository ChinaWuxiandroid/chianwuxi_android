package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextWatcher;
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

	private View view;
	private LayoutInflater mInflater;
	private Context context;

	private static final int DATA_SUBMIT_SUCCESS = 2;
	private static final int DATA_SUBMIT_FAILED = 3;

	private EditText sentUserName_et, tel_et, email_et, content_et;
	private ImageButton submit_ibtn, reset_ibtn;
	private ProgressBar pb;

	private LoginDialog loginDialog;

	private String sentUserName = "", tel = "", email = "", content = "";

	private TextWatcher phoneTextWatcher;

	private InputMethodManager imm;

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
				break;
			case DATA_SUBMIT_FAILED:
				pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
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

	/**
	 * @方法： initView
	 * @描述： 初始化布局控件
	 */
	private void initView() {
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

		// phoneTextWatcher = new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// String phoneNumber = tel_et.getText().toString();
		// if (!isPhoneNumberValid(phoneNumber)) {
		// Toast.makeText(context, "您输入的电话号码格式不对，请重新输入！",
		// Toast.LENGTH_SHORT).show();
		// tel_et.setText("");
		// }
		// }
		// };
		// tel_et.addTextChangedListener(phoneTextWatcher);
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
			submitData();
			break;
		case R.id.gip_publicsupervise_et_reset:
			resetEditInfo();
			break;
		}
	}

	/**
	 * @方法： resetEditInfo
	 * @描述： 设置数据
	 */
	private void resetEditInfo() {
		sentUserName_et.setText("");
		tel_et.setText("");
		email_et.setText("");
		content_et.setText("");
	}

	/**
	 * 提交
	 * */
	private void submitData() {
		if (!judgeDataLegal()) {
			pb.setVisibility(ProgressBar.VISIBLE);
			new Thread(new Runnable() {

				@Override
				public void run() {
					SubmitListService submitListService = new SubmitListService(
							context);
					try {
						submitListService.submitByUrl(getUrl(
								Constants.Urls.CITIZEN_APPLY_SUBMIT_URL,
								SystemUtil.getAccessToken(context)));
						Toast.makeText(context, "提交成功，正在审核...",
								Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(DATA_SUBMIT_SUCCESS);
					} catch (NetException e) {
						handler.sendEmptyMessage(DATA_SUBMIT_FAILED);
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	// 获取提交公民在线申请 的url
	private String getUrl(String urlhead, String access_token) {
		String url = urlhead + "?access_token=" + access_token;
		url = url + "&SentUserName=" + sentUserName + "&SentUserTel=" + tel
				+ "&SentUserEmail=" + email + "&SentContent=" + content;
		return url;
	}

	/**
	 * 判断输入 是否为空
	 * */
	private boolean judgeDataLegal() {
		boolean inputError = false;
		sentUserName = sentUserName_et.getText().toString();
		tel = tel_et.getText().toString();
		email = email_et.getText().toString();
		content = content_et.getText().toString();

		// 检查电话
		Pattern pattern = Pattern
				.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");

		Matcher matcher = pattern.matcher(tel);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group());
		}
		int tel_len = bf.length();

		// 检查邮箱
		Pattern pattern1 = Pattern
				.compile("[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}");
		Matcher matcher1 = pattern1.matcher(email);
		StringBuffer bf1 = new StringBuffer(64);
		while (matcher1.find()) {
			bf1.append(matcher1.group());
		}
		int email_len = bf1.length();

		if (!inputError && "".equals(sentUserName)) {
			Toast.makeText(context, "姓名 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(tel)) {
			Toast.makeText(context, "联系电话 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (tel_len < 1) {
			Toast.makeText(context, "请输入正确的联系电话", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(email)) {
			Toast.makeText(context, "E-mail 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (email_len < 1) {
			Toast.makeText(context, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(content)) {
			Toast.makeText(context, "问题 不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		}
		return inputError;
	}
	// /**
	// * 检查字符串是否为电话号码的方法,并返回true or false的判断值
	// */
	// public static boolean isPhoneNumberValid(String phoneNumber) {
	// boolean isValid = false;
	// /**
	// * 可接受的电话格式有: ^//(? : 可以使用 "(" 作为开头 (//d{3}): 紧接着三个数字 //)? : 可以使用")"接续
	// * [- ]? : 在上述格式后可以使用具选择性的 "-". (//d{3}) : 再紧接着三个数字 [- ]? : 可以使用具选择性的
	// * "-" 接续. (//d{5})$: 以五个数字结束. 可以比较下列数字格式: (123)456-7890, 123-456-7890,
	// * 1234567890, (123)-456-7890
	// */
	// String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";
	//
	// /**
	// * 可接受的电话格式有: ^//(? : 可以使用 "(" 作为开头 (//d{3}): 紧接着三个数字 //)? : 可以使用")"接续
	// * [- ]? : 在上述格式后可以使用具选择性的 "-". (//d{4}) : 再紧接着四个数字 [- ]? : 可以使用具选择性的
	// * "-" 接续. (//d{4})$: 以四个数字结束. 可以比较下列数字格式: (02)3456-7890, 02-3456-7890,
	// * 0234567890, (02)-3456-7890
	// */
	// String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";
	//
	// CharSequence inputStr = phoneNumber;
	// /* 创建Pattern */
	// Pattern pattern = Pattern.compile(expression);
	// /* 将Pattern 以参数传入Matcher作Regular expression */
	// Matcher matcher = pattern.matcher(inputStr);
	// /* 创建Pattern2 */
	// Pattern pattern2 = Pattern.compile(expression2);
	// /* 将Pattern2 以参数传入Matcher2作Regular expression */
	// Matcher matcher2 = pattern2.matcher(inputStr);
	// if (matcher.matches())// || matcher2.matches())
	// {
	// isValid = true;
	// }
	// return isValid;
	// }
	//
	// @Override
	// public void onResume() {
	// super.onResume();
	// Timer timer = new Timer();
	// timer.schedule(new TimerTask() {
	//
	// @Override
	// public void run() {
	// imm = (InputMethodManager) context.getSystemService(
	// Context.INPUT_METHOD_SERVICE);
	// imm.showSoftInput(tel_et, 0);
	// }
	//
	// }, 1000);
	// }

}
