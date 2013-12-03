package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.SubmitListService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.ApplyDept;
import com.wuxi.exception.NetException;

/**
 * @类名： GoverMsgApplyCitizenTableFragment
 * @描述： 依申请公开 公民申请界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-27 下午2:11:09
 * @修改时间：
 * @修改描述：
 */
public class GoverMsgApplyCitizenTableFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	// private LayoutInflater mInflater;
	private Context context;

	private static final int SUBMIT_SUCCESS = 3;
	private static final int SUBMIT_FAILED = 4;
	// private List<ApplyDept> depts;

	// 提交变量
	String name = "", workadd = "", papername = "", papernum = "",
			address = "", postcode = "", phone = "", fax = "", email = "",
			describe = "", use = "", applydate = "";
	String check_paper = "", check_mail = "", check_dis = "", check_post = "",
			check_express = "";

	// 可选项
	private Spinner solveByDept;
	private CheckBox paper_ckBox, mail_ckBox, dis_ckBox, post_ckBox,
			express_ckBox;

	// 必选项
	private EditText name_et, workadd_et, papername_et, papernum_et,
			address_et, postcode_et, phone_et, fax_et, email_et, describe_et,
			use_et;
	private TextView applyDate_txt;

	private ImageButton submit_ibtn, cancel_ibtn;
	private ProgressBar pb;

	private Calendar calendar;
	// private int year, month, day;

	private ApplyDept applyDept;

	private LoginDialog loginDialog;

	public void setDept(ApplyDept applyDept) {
		this.applyDept = applyDept;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUBMIT_SUCCESS:
				Toast.makeText(context, "提交成功，正在审核...", Toast.LENGTH_SHORT)
						.show();
				pb.setVisibility(ProgressBar.INVISIBLE);
				break;
			case SUBMIT_FAILED:
				Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
				pb.setVisibility(ProgressBar.INVISIBLE);
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.onlineapply_citizentable_layout, null);
		// mInflater = inflater;
		context = getActivity();

		initView();
		return view;
	}

	/**
	 * @方法： initView
	 * @描述： 初始化视图
	 */
	private void initView() {

		loginDialog = new LoginDialog(context);// 实例化登录对话框

		if (!loginDialog.checkLogin()) {
			loginDialog.showDialog();
		} else {
			calendar = Calendar.getInstance();

			byear = calendar.get(Calendar.YEAR);
			bmonth = calendar.get(Calendar.MONTH) + 1;
			bday = calendar.get(Calendar.DAY_OF_MONTH);

			pb = (ProgressBar) view.findViewById(R.id.citizen_infosubmit_pb);
			submit_ibtn = (ImageButton) view
					.findViewById(R.id.worksuggestbox_imgbtn_submit);
			cancel_ibtn = (ImageButton) view
					.findViewById(R.id.worksuggestbox_imgbtn_cancel);
			// 可选项-----------------------------------------------------------
			solveByDept = (Spinner) view
					.findViewById(R.id.citizen_solve_bydept);

			paper_ckBox = (CheckBox) view
					.findViewById(R.id.citizen_info_supply_paper_checkbox);
			mail_ckBox = (CheckBox) view
					.findViewById(R.id.citizen_info_supply_email_checkbox);
			dis_ckBox = (CheckBox) view
					.findViewById(R.id.citizen_info_supply_disk_checkbox);
			post_ckBox = (CheckBox) view
					.findViewById(R.id.citizen_info_get_post_checkbox);
			express_ckBox = (CheckBox) view
					.findViewById(R.id.citizen_info_get_express_checkbox);
			// 可选项-----------------------------------------------------------

			// 必选项-----------------------------------------------------------
			name_et = (EditText) view.findViewById(R.id.citizen_name_edit);
			workadd_et = (EditText) view
					.findViewById(R.id.citizen_workadd_edit);
			papername_et = (EditText) view
					.findViewById(R.id.citizen_papers_name_edit);
			papernum_et = (EditText) view
					.findViewById(R.id.citizen_papers_num_edit);
			address_et = (EditText) view
					.findViewById(R.id.citizen_address_edit);
			postcode_et = (EditText) view
					.findViewById(R.id.citizen_postcode_edit);
			phone_et = (EditText) view.findViewById(R.id.citizen_phone_edit);
			fax_et = (EditText) view.findViewById(R.id.citizen_fax_edit);
			email_et = (EditText) view.findViewById(R.id.citizen_email_edit);
			describe_et = (EditText) view
					.findViewById(R.id.citizen_info_describe_edit);
			use_et = (EditText) view.findViewById(R.id.citizen_info_use_edit);

			applyDate_txt = (TextView) view
					.findViewById(R.id.citizen_apply_time_txt);

			applyDate_txt.setText("" + byear + "-" + bmonth + "-" + bday);

			applyDate_txt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onCreateDialog();
				}
			});

			// solveByDept.setText(applyDept.getDepName());

			// 必选项-----------------------------------------------------------
			submit_ibtn.setOnClickListener(this);
			cancel_ibtn.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.worksuggestbox_imgbtn_submit:
			// 关闭软键盘
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(submit_ibtn.getWindowToken(), 0);

			submitData();

			break;
		case R.id.worksuggestbox_imgbtn_cancel:
			pb.setVisibility(ProgressBar.INVISIBLE);
			Toast.makeText(context, "取消提交", Toast.LENGTH_SHORT).show();
			break;
		case R.id.citizen_apply_time_txt:
			// showDateSelectDialog(applyDate_txt);
			break;
		}

	}

	/**
	 * @方法： onCreateDialog
	 * @描述： 创建时间选择对话框
	 * @param id
	 * @return Dialog
	 */
	private Dialog onCreateDialog() {
		DatePickerDialog beginDialog = new DatePickerDialog(context,
				beginDateListener, byear, bmonth - 1, bday);
		beginDialog.setIcon(R.drawable.logo);
		beginDialog.show();
		return beginDialog;
	}

	private int byear;
	private int bmonth;
	private int bday;

	/**
	 * 申请日期对话框监听器
	 */
	private DatePickerDialog.OnDateSetListener beginDateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			GoverMsgApplyCitizenTableFragment.this.byear = year;
			GoverMsgApplyCitizenTableFragment.this.bmonth = monthOfYear;
			GoverMsgApplyCitizenTableFragment.this.bday = dayOfMonth;
			updateBeginDateDisplay();
		}
	};

	/**
	 * @方法： updateBeginDateDisplay
	 * @描述： 更新开始日期显示
	 */
	private void updateBeginDateDisplay() {
		StringBuffer sb = new StringBuffer().append(byear).append("-")
				.append((bmonth + 1) < 10 ? "0" + (bmonth + 1) : (bmonth + 1))
				.append("-").append((bday < 10) ? "0" + bday : bday);
		// timeBeginEdit.setText(sb);
		applyDate_txt.setText(sb);
		// 获取日期格式实例
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 时间实例
		Date date = null;
		try {
			// 将字符串按照一定的格式转换成时间对象，即long数据
			date = format.parse(sb.toString());
			// 设置查询条件的开始时间的值
			// letterCondition.setStarttime(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * */
	private void submitData() {
		if (!judgeDataLegal()) {
			getCheckBoxResult();
			pb.setVisibility(ProgressBar.VISIBLE);
			new Thread(new Runnable() {

				@Override
				public void run() {
					SubmitListService submitListService = new SubmitListService(
							context);
					try {
						submitListService.submitByUrl(getUrl(
								Constants.Urls.CITIZEN_APPLY_SUBMIT_URL,
								SystemUtil.getAccessToken(context),
								applyDept.getDoProjectId(),
								applyDept.getDepId()));
						handler.sendEmptyMessage(SUBMIT_SUCCESS);

					} catch (NetException e) {
						handler.sendEmptyMessage(SUBMIT_FAILED);
						e.printStackTrace();
					} catch (JSONException e) {
						handler.sendEmptyMessage(SUBMIT_FAILED);
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	// 获取提交公民在线申请 的url
	private String getUrl(String urlhead, String access_token,
			String doProjectId, String depid) {
		String url = urlhead + "?access_token=" + access_token
				+ "&doprojectid=" + doProjectId + "&depid=" + depid;
		url = url + "&username=" + name + "&workunit=" + workadd
				+ "&certificatetype=" + papername + "&certificateno="
				+ papernum + "&address=" + address + "&postalcode=" + postcode
				+ "&tel=" + phone + "&fax=" + fax + "&email=" + email
				+ "&content=" + describe + "&title=" + use;
		url = url + "&offertype=" + check_paper + "/" + check_mail + "/"
				+ check_dis;
		url = url + "&getinfotype=" + check_post + "/" + check_express;
		return url;
	}

	/**
	 * @方法： getCheckBoxResult
	 * @描述： 获取复选框的值
	 */
	private void getCheckBoxResult() {
		if (paper_ckBox.isChecked()) {
			check_paper = "纸面";
		}
		if (mail_ckBox.isChecked()) {
			check_mail = "电子邮件";
		}
		if (dis_ckBox.isChecked()) {
			check_dis = "光盘";
		}
		if (post_ckBox.isChecked()) {
			check_post = "邮寄";
		}
		if (express_ckBox.isChecked()) {
			check_express = "快递";
		}
	}

	/**
	 * 判断输入 是否为空
	 * */
	private boolean judgeDataLegal() {
		boolean inputError = false;
		name = name_et.getText().toString();
		workadd = workadd_et.getText().toString();
		papername = papername_et.getText().toString();
		papernum = papernum_et.getText().toString();
		address = address_et.getText().toString();
		postcode = postcode_et.getText().toString();
		phone = phone_et.getText().toString();
		fax = fax_et.getText().toString();
		email = email_et.getText().toString();
		describe = describe_et.getText().toString();
		use = use_et.getText().toString();
		applydate = applyDate_txt.getText().toString();

		if (!inputError && "".equals(name)) {
			Toast.makeText(context, "姓名不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(workadd)) {
			Toast.makeText(context, "工作单位不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(papername)) {
			Toast.makeText(context, "证件名称不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(papernum)) {
			Toast.makeText(context, "证件号码不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(address)) {
			Toast.makeText(context, "联系地址不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(postcode)) {
			Toast.makeText(context, "邮政编码不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(phone)) {
			Toast.makeText(context, "联系电话不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(fax)) {
			Toast.makeText(context, "传真不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(email)) {
			Toast.makeText(context, "电子邮件不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(applydate)) {
			Toast.makeText(context, "申请时间不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(describe)) {
			Toast.makeText(context, "所需内容信息的描述不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(use)) {
			Toast.makeText(context, "所需信息的用途不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!isPhone(phone_et.getText().toString())) {
			inputError = true;
		} else if (!isEmail(email_et.getText().toString())) {
			inputError = true;
		} else if (!isPostcode(postcode_et.getText().toString())) {
			inputError = true;
		}
		return inputError;
	}

	/**
	 * 
	 * 检查用户输入的电话号码,是电话号码返回true
	 * 
	 * @方法： isPhone
	 * @描述： TODO
	 * @param phoneText
	 * @return
	 */
	private boolean isPhone(String phoneText) {
		Pattern pattern = Pattern
				.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");

		Matcher matcher = pattern.matcher(phoneText);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group());
		}
		int len = bf.length();
		if (len > 0) {
			return true;
		} else {
			Toast.makeText(context, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/**
	 * 检查用户输入的邮箱，如果是邮箱返回true
	 * 
	 * @方法： isEmail
	 * @描述： TODO
	 * @param emailText
	 * @return
	 */
	private boolean isEmail(String emailText) {
		Pattern pattern = Pattern
				.compile("[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}");
		Matcher matcher = pattern.matcher(emailText);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group());
		}
		int len = bf.length();
		if (len > 0) {
			return true;
		} else {
			Toast.makeText(context, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/**
	 * 检查用户输入的邮编，正确返回true
	 * 
	 * @方法： isPostcode
	 * @描述： TODO
	 * @param postcodeText
	 * @return
	 */
	private boolean isPostcode(String postcodeText) {
		Pattern pattern = Pattern.compile("^[1-9]\\d{5}$");
		Matcher matcher = pattern.matcher(postcodeText);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group());
		}
		int len = bf.length();
		if (len > 0) {
			return true;
		} else {
			Toast.makeText(context, "请输入正确的邮编", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}

// public void showDateSelectDialog(final TextView textview){
// new DatePickerDialog(getActivity(), new OnDateSetListener() {
// @Override
// public void onDateSet(DatePicker view, int year,
// int monthOfYear, int dayOfMonth) {
// monthOfYear=monthOfYear+1;
// String monthStr="",dayStr="";
// if(monthOfYear<10)
// monthStr="0"+monthOfYear;
// else
// monthStr=""+monthOfYear;
// if(dayOfMonth<10)
// dayStr="0"+dayOfMonth;
// else
// dayStr=""+dayOfMonth;
//
// textview.setText(""+year +monthStr+dayStr);
// }
// }, year, month, day).show();
// }

