package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.WorkSuggestionService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.MailBoxParameterItem;
import com.wuxi.domain.MenuItem;
import com.wuxi.domain.WorkSuggestionBoxWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 工作意见邮箱 Fragment 布局
 * 
 * @author 杨宸 智佳
 * */

public class WorkSuggestionBoxFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	private LayoutInflater mInflater;
	private Context context;
	private MenuItem parentMenuItem;
	private WorkSuggestionBoxWrapper boxWrapper;
	private List<MailBoxParameterItem> parameterItems;

	private static final int DATA_LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;
	private static final int DATA_SUBMIT_SUCCESS = 2;
	private static final int DATA_SUBMIT_FAILED = 3;

	private static final int CHANNELCONTENT_ID = R.id.govermsg_custom_content;

	private ProgressBar processBar;
	private LinearLayout layout;
	private RelativeLayout submit_layout;

	private ImageButton submit_btn;
	private ImageButton cancel_btn;

	private LoginDialog loginDialog;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				processBar.setVisibility(View.GONE);
				showLayout();
				break;
			case DATA_LOAD_ERROR:
				processBar.setVisibility(View.GONE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			case DATA_SUBMIT_SUCCESS:
				processBar.setVisibility(View.GONE);
				Toast.makeText(context, "提交成功,正在审核...", Toast.LENGTH_SHORT)
						.show();
				break;
			case DATA_SUBMIT_FAILED:
				processBar.setVisibility(View.GONE);
				Toast.makeText(context, "提交失败！", Toast.LENGTH_SHORT).show();
				break;

			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.worksuggestionbox_layout, null);
		mInflater = inflater;
		context = getActivity();

		initLayout();
		return view;

	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局
	 */
	private void initLayout() {
		loginDialog = new LoginDialog(context);
		if (!loginDialog.checkLogin()) {
			loginDialog.showDialog();
		}
		processBar = (ProgressBar) view
				.findViewById(R.id.worksuggestbox_progressbar);
		submit_layout = (RelativeLayout) view
				.findViewById(R.id.worksuggestbox_submit_layout);

		processBar.setVisibility(View.VISIBLE);
		loadData();
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 */
	private void loadData() {

		if (boxWrapper != null && CacheUtil.get(boxWrapper.getId()) != null) {// 从缓存中查找子菜单
			boxWrapper = (WorkSuggestionBoxWrapper) CacheUtil.get(boxWrapper
					.getId());
			processBar.setVisibility(View.INVISIBLE);
			showLayout();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				WorkSuggestionService boxService = new WorkSuggestionService(
						context);
				try {
					boxWrapper = boxService
							.getBoxWrapper(Constants.Urls.GOVERMSG_WORKSUGGESTIONBOX_LAYOUT_URL);
					if (boxWrapper != null) {
						handler.sendEmptyMessage(DATA_LOAD_SUCESS);
						CacheUtil.put(boxWrapper.getId(), boxWrapper);// 放入缓存
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = "暂无信息";
						msg.what = DATA_LOAD_ERROR;
						handler.sendMessage(msg);
					}
				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * @方法： showLayout
	 * @描述： 显示布局
	 */
	private void showLayout() {
		parameterItems = boxWrapper.getParameters();
		if (parameterItems != null) {
			layout = (LinearLayout) view
					.findViewById(R.id.worksuggestbox_layout);
			for (MailBoxParameterItem item : parameterItems) {
				// 根据InputType先判断单行文本还是多行文本
				LinearLayout subLayout = null;
				if (item.getInputType().equals("SingleTextBox")) {
					subLayout = (LinearLayout) mInflater.inflate(
							R.layout.worksuggestionbox_singleline_layout, null)
							.findViewById(
									R.id.worksuggestionbox_singletxtbox_layout);

				} else if (item.getInputType().equals("MultiTextBox")) {
					subLayout = (LinearLayout) mInflater.inflate(
							R.layout.worksuggestionbox_mutilline_layout, null)
							.findViewById(
									R.id.worksuggestionbox_mutiltxtbox_layout);
				}
				if (subLayout != null) {
					TextView title_tv = (TextView) subLayout.getChildAt(0);
					EditText content_et = (EditText) subLayout.getChildAt(1);

					if (item.getAlias().equals("您的电话")) {
						content_et.setInputType(InputType.TYPE_CLASS_PHONE);
					} else if (item.getAlias().equals("您的邮箱")) {
						content_et
								.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
					}
					title_tv.setText(item.getAlias() + ":");
					content_et.setText(item.getValueList());

					layout.addView(subLayout);

				}
			}
		}
		submit_layout.setVisibility(View.VISIBLE);
		initView();
	}

	/**
	 * @方法： initView
	 * @描述： 初始化视图
	 */
	private void initView() {
		submit_btn = (ImageButton) view
				.findViewById(R.id.worksuggestbox_imgbutton_submit);
		cancel_btn = (ImageButton) view
				.findViewById(R.id.worksuggestbox_imgbutton_cancel);
		submit_btn.setOnClickListener(this);
		cancel_btn.setOnClickListener(this);

	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.worksuggestbox_imgbutton_submit:
			if (loginDialog.checkLogin()) {
				String access_token = SystemUtil.getAccessToken(context);
				try {
					submitMail(access_token);
				} catch (NetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			} else {
				loginDialog.showDialog();
			}
			break;
		case R.id.worksuggestbox_imgbutton_cancel:
			break;
		}
	}

	/**
	 * @方法： submitMail
	 * @描述： 提交数据
	 * @param access_token
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	private void submitMail(final String access_token) throws NetException,
			JSONException, NODataException {
		if (!judgeIsLegal()) {
			processBar.setVisibility(View.VISIBLE);
			new Thread(new Runnable() {
				@Override
				public void run() {
					WorkSuggestionService workSuggestionService = new WorkSuggestionService(
							context);
					try {
						workSuggestionService.submitMailBox(access_token,
								boxWrapper);
						handler.sendEmptyMessage(DATA_SUBMIT_SUCCESS);

					} catch (NetException e) {
						handler.sendEmptyMessage(DATA_SUBMIT_FAILED);
						e.printStackTrace();
					} catch (JSONException e) {
						handler.sendEmptyMessage(DATA_SUBMIT_FAILED);
						e.printStackTrace();
					} catch (NODataException e) {
						handler.sendEmptyMessage(DATA_SUBMIT_FAILED);
						e.printStackTrace();
					}
				}
			}).start();
		} else {
			processBar.setVisibility(View.GONE);
		}
	}

	/**
	 * @方法： judgeIsLegal
	 * @描述： 判断必填项是否为空
	 * @return
	 */
	private boolean judgeIsLegal() {
		boolean submitError = false;
		int index = 0;
		for (MailBoxParameterItem item : boxWrapper.getParameters()) {
			LinearLayout subLayout = (LinearLayout) layout.getChildAt(index);
			EditText content_et = (EditText) subLayout.getChildAt(1);

			item.setValueList(content_et.getText().toString());
			
			// 对必填选项进行空值判断
			if (item.getRequiredForm() == 1) {
				if (content_et.getText().toString().equals("")) {
					Toast.makeText(context, item.getAlias() + "  不能为空！",
							Toast.LENGTH_SHORT).show();
					submitError = true;
				}
			}
			
			// 如果用户输入了电话号码，则检查用户输入的是否为电话号码
			if (item.getAlias().equals("您的电话")) {
				if (content_et.getText().toString().trim().length() > 0) {
					Pattern pattern = Pattern
							.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");

					Matcher matcher = pattern.matcher(content_et.getText()
							.toString());
					StringBuffer bf = new StringBuffer(64);
					while (matcher.find()) {
						bf.append(matcher.group());
					}
					int len = bf.length();
					if (len > 0) {
						submitError = false;
					} else {
						Toast.makeText(context, "请输入正确的电话号码",
								Toast.LENGTH_SHORT).show();
						submitError = true;
					}
				}
			}

			// 如果用户输入了邮箱，则检查用户输入的邮箱是否标准
			if (item.getAlias().equals("您的邮箱")) {
				if (content_et.getText().toString().trim().length() > 0) {
					Pattern pattern = Pattern
							.compile("[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}");
					Matcher matcher = pattern.matcher(content_et.getText()
							.toString());
					StringBuffer bf = new StringBuffer(64);
					while (matcher.find()) {
						bf.append(matcher.group());
					}
					int len = bf.length();
					if (len > 0) {
						submitError = false;
					} else {
						Toast.makeText(context, "请输入正确的邮箱", Toast.LENGTH_SHORT)
								.show();
						submitError = true;
					}
				}
			}

			index++;
		}
		return submitError;
	}

	private static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";

		String expression2 = "^\\(?(\\d{2})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";

		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);

		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}

}
