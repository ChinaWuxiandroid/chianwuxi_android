package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
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
import com.wuxi.app.engine.QueryLetterDepService;
import com.wuxi.app.engine.SubmitListService;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgApplyCitizenTableFragment.DeptAdapter;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgApplyCitizenTableFragment.DeptAdapter.ViewHolder;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.ApplyDept;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GoverMsgApplyLePersonTableFragment
 * @描述： 依申请公开 法人申请界面
 * @作者： 罗森
 * @创建时间： 2013 2013-10-10 下午1:44:10
 * @修改时间：
 * @修改描述：
 */
@SuppressLint("ValidFragment")
public class GoverMsgApplyLePersonTableFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	private LayoutInflater mInflater;
	private Context context;

	private static final int SUBMIT_SUCCESS = 3;
	private static final int SUBMIT_FAILED = 4;

	private List<ApplyDept> depts;

	private List<PartLeaderMail> deptList = null;

	// 提交变量
	String organname = "", organid = "", ceo = "", linkman = "",
			unitaddress = "", tel = "", fax = "", email = "", describe = "",
			use = "";
	String check_paper = "", check_mail = "", check_dis = "", check_post = "",
			check_express = "";

	// 可选项
	private Spinner solveByDept;
	private CheckBox paper_ckBox, mail_ckBox, dis_ckBox, post_ckBox,
			express_ckBox;

	// 必选项
	private EditText organname_et, organid_et, ceo_et, linkman_et,
			unitaddress_et, tel_et, fax_et, email_et, describe_et, use_et;
	private TextView applyDate_txt;

	private ImageButton submit_ibtn, cancel_ibtn;
	private ProgressBar pb;

	private Calendar calendar;
	private int year, month, day;

	private ApplyDept applyDept;

	private LoginDialog loginDialog;

	public void setDept(ApplyDept applyDept) {
		this.applyDept = applyDept;
	}

	private PartLeaderMailWrapper leaderMailWrapper = null;

	private static String DEFAULT_DEPT_FIFTER = "无限制";

	private String depId = null;

	private String doprojectid = null;

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

			case 100:
				PartLeaderMailWrapper mailWrapper = new PartLeaderMailWrapper();
				PartLeaderMail leaderMail = mailWrapper.new PartLeaderMail();
				leaderMail.setDepid("0");
				leaderMail.setDepname(DEFAULT_DEPT_FIFTER);
				deptList.add(0, leaderMail);
				DeptAdapter partment_Spinner_adapter = new DeptAdapter();
				solveByDept.setAdapter(partment_Spinner_adapter);
				break;
			}
		}
	};

	public GoverMsgApplyLePersonTableFragment(String doprojectid) {
		this.doprojectid = doprojectid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.onlineapply_lepersontalbe_layout, null);
		mInflater = inflater;
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
		}

		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);

		pb = (ProgressBar) view.findViewById(R.id.legalperson_infosubmit_pb);
		submit_ibtn = (ImageButton) view
				.findViewById(R.id.legalperson_imgbutton_submit);
		cancel_ibtn = (ImageButton) view
				.findViewById(R.id.legalperson_imgbutton_cancel);
		// 可选项-----------------------------------------------------------
		solveByDept = (Spinner) view
				.findViewById(R.id.legalperson_solve_bydept);

		paper_ckBox = (CheckBox) view
				.findViewById(R.id.legalperson_apply_offertype_paper);
		mail_ckBox = (CheckBox) view
				.findViewById(R.id.legalperson_apply_offertype_mail);
		dis_ckBox = (CheckBox) view
				.findViewById(R.id.legalperson_apply_offertype_disk);
		post_ckBox = (CheckBox) view
				.findViewById(R.id.legalperson_apply_getinfotype_post);
		express_ckBox = (CheckBox) view
				.findViewById(R.id.legalperson_apply_getinfotype_express);
		// 可选项-----------------------------------------------------------

		// 必选项-----------------------------------------------------------
		organname_et = (EditText) view
				.findViewById(R.id.legalperson_apply_organname);
		organid_et = (EditText) view
				.findViewById(R.id.legalperson_apply_organid);
		ceo_et = (EditText) view.findViewById(R.id.legalperson_apply_ceo);
		linkman_et = (EditText) view
				.findViewById(R.id.legalperson_apply_linkman);
		unitaddress_et = (EditText) view
				.findViewById(R.id.legalperson_apply_unitaddress);
		tel_et = (EditText) view.findViewById(R.id.legalperson_apply_tel);
		fax_et = (EditText) view.findViewById(R.id.legalperson_apply_fax);
		email_et = (EditText) view.findViewById(R.id.legalperson_apply_email);
		describe_et = (EditText) view
				.findViewById(R.id.legalperson_apply_content);
		use_et = (EditText) view.findViewById(R.id.legalperson_apply_title);

		applyDate_txt = (TextView) view
				.findViewById(R.id.legalperson_apply_applydate);
		applyDate_txt.setText("" + year + "-" + month + "-" + day);

		applyDate_txt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onCreateDialog();
			}
		});

		// solveByDept.setText(applyDept.getDepName());

		loadDeptData();

		solveByDept.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				depId = deptList.get(arg2).getDepid().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// 必选项-----------------------------------------------------------
		submit_ibtn.setOnClickListener(this);
		cancel_ibtn.setOnClickListener(this);
	}

	/**
	 * @方法： loadDeptData
	 * @描述： 加载受理部门和答复部门数据
	 */
	private void loadDeptData() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				QueryLetterDepService depService = new QueryLetterDepService(
						context);
				try {
					leaderMailWrapper = depService.getPartLeaderMailWrapper();

					if (leaderMailWrapper != null) {
						deptList = leaderMailWrapper.getPartLeaderMails();
						handler.sendEmptyMessage(100);
					} else {
						Toast.makeText(context, "没有获取到部门数据", Toast.LENGTH_SHORT)
								.show();
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @类名： DeptAdapter
	 * @描述： 受理部门及答复部门下拉列表适配器
	 * @作者： 罗森
	 * @创建时间： 2013-8-27 上午9:27:35
	 * @修改时间：
	 * @修改描述：
	 * 
	 */
	public class DeptAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return deptList.size();
		}

		@Override
		public Object getItem(int position) {
			return deptList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * @类名： ViewHolder
		 * @描述： 下拉列表布局
		 * @作者： 罗森
		 * @创建时间： 2013 2013-8-27 上午9:28:24
		 * @修改时间：
		 * @修改描述：
		 * 
		 */
		public class ViewHolder {
			TextView tv_dept;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PartLeaderMail dept = deptList.get(position);
			ViewHolder viewHolder = null;

			if (convertView == null) {
				// 加载下拉列表布局文件
				convertView = View.inflate(context,
						R.layout.comstuom_spinner_item_layout, null);
				viewHolder = new ViewHolder();
				TextView tv = (TextView) convertView.findViewById(R.id.sp_tv);
				viewHolder.tv_dept = tv;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.tv_dept.setTextColor(Color.BLACK);
			viewHolder.tv_dept.setTextSize(12);
			viewHolder.tv_dept.setText(dept.getDepname());

			return convertView;
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
				beginDateListener, year, month - 1, day);
		beginDialog.setIcon(R.drawable.logo);
		beginDialog.show();
		return beginDialog;
	}

	/**
	 * 申请日期对话框监听器
	 */
	private DatePickerDialog.OnDateSetListener beginDateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			GoverMsgApplyLePersonTableFragment.this.year = year;
			GoverMsgApplyLePersonTableFragment.this.month = monthOfYear;
			GoverMsgApplyLePersonTableFragment.this.day = dayOfMonth;
			updateBeginDateDisplay();
		}
	};

	/**
	 * @方法： updateBeginDateDisplay
	 * @描述： 更新开始日期显示
	 */
	private void updateBeginDateDisplay() {
		StringBuffer sb = new StringBuffer().append(year).append("-")
				.append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
				.append("-").append((day < 10) ? "0" + day : day);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.legalperson_imgbutton_submit:
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
		case R.id.legalperson_imgbutton_cancel:
			pb.setVisibility(ProgressBar.INVISIBLE);
			Toast.makeText(context, "取消提交", Toast.LENGTH_SHORT).show();
			break;
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

						List<NameValuePair> pairs = new ArrayList<NameValuePair>();
						pairs.add(new BasicNameValuePair("access_token",
								SystemUtil.getAccessToken(context)));
						pairs.add(new BasicNameValuePair("doprojectid",
								doprojectid));
						pairs.add(new BasicNameValuePair("depid", depId));
						pairs.add(new BasicNameValuePair("organname", organname));
						pairs.add(new BasicNameValuePair("organid", organid));
						pairs.add(new BasicNameValuePair("ceo", ceo));
						pairs.add(new BasicNameValuePair("linkman", linkman));
						pairs.add(new BasicNameValuePair("unitaddress",
								unitaddress));
						// pairs.add(new BasicNameValuePair("postalcode",
						// postcode));
						pairs.add(new BasicNameValuePair("tel", tel));
						pairs.add(new BasicNameValuePair("fax", fax));
						pairs.add(new BasicNameValuePair("email", email));
						pairs.add(new BasicNameValuePair("content", describe));
						pairs.add(new BasicNameValuePair("title", use));
						String offertype = null;
						for (int i = 0; i < list1.size(); i++) {
							if (i == 0) {
								offertype += list1.get(i);
							} else {
								offertype += "/" + list1.get(i);
							}
						}
						pairs.add(new BasicNameValuePair("offertype", offertype));
						String getinfotype = null;
						for (int i = 0; i < list2.size(); i++) {
							if (i == 0) {
								getinfotype += list2.get(i);
							} else {
								getinfotype += "/" + list2.get(i);
							}
						}
						pairs.add(new BasicNameValuePair("getinfotype",
								getinfotype));

						submitListService.submitByUrlPost(
								Constants.Urls.LEGALPERSONAPPLY_SUBMIT_URL,
								pairs);
						//
						// submitListService.submitByUrl(getUrl(
						// Constants.Urls.LEGALPERSONAPPLY_SUBMIT_URL,
						// SystemUtil.getAccessToken(context),
						// doprojectid, depId));
						//
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
	// private String getUrl(String urlhead, String access_token,
	// String doProjectId, String depid) {
	// String url = urlhead + "?access_token=" + access_token
	// + "&doprojectid=" + doProjectId + "&depid=" + depid;
	//
	// url = url + "&organname=" + organname + "&organid=" + organid + "&ceo="
	// + ceo + "&linkman=" + linkman + "&unitaddress=" + unitaddress
	// + "&tel=" + tel + "&fax=" + fax + "&email=" + email
	// + "&content=" + describe + "&title=" + use;
	// url = url + "&offertype=" + check_paper + "/" + check_mail + "/"
	// + check_dis;
	// url = url + "&getinfotype=" + check_post + "/" + check_express;
	// return url;
	// }

	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<String> list2 = new ArrayList<String>();

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
		organname = organname_et.getText().toString();
		organid = organid_et.getText().toString();
		ceo = ceo_et.getText().toString();
		linkman = linkman_et.getText().toString();
		unitaddress = unitaddress_et.getText().toString();
		tel = tel_et.getText().toString();
		fax = fax_et.getText().toString();
		email = email_et.getText().toString();
		describe = describe_et.getText().toString();
		use = use_et.getText().toString();

		if (!inputError && "".equals(organname)) {
			Toast.makeText(context, "机构名称不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(organid)) {
			Toast.makeText(context, "组织机构代码不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(ceo)) {
			Toast.makeText(context, "法人代表不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(linkman)) {
			Toast.makeText(context, "联系人姓名不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(unitaddress)) {
			Toast.makeText(context, "联系人电话不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(fax)) {
			Toast.makeText(context, "传真不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(email)) {
			Toast.makeText(context, "电子邮件不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(describe)) {
			Toast.makeText(context, "所需内容信息的描述不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!inputError && "".equals(use)) {
			Toast.makeText(context, "所需信息的用途不能为空", Toast.LENGTH_SHORT).show();
			inputError = true;
		} else if (!isPhone(tel_et.getText().toString())) {
			inputError = true;
		} else if (!isEmail(email_et.getText().toString())) {
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
