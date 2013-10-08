/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PortLeaderMailListFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述: 政民互动 12345来信办理平台 部门领导信箱 某部门最新信件列表
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-23 上午11:12:56
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.adapter.MailTypeAdapter;
import com.wuxi.app.adapter.QueryMailContentTypeAdapter;
import com.wuxi.app.engine.MailTypeService;
import com.wuxi.app.engine.PartLeaderMailService;
import com.wuxi.app.engine.QueryMailContentTypeService;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.MailTypeWrapper;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.QueryMailContentTypeWrapper;
import com.wuxi.domain.MailTypeWrapper.MailType;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.domain.QueryMailContentTypeWrapper.QueryMailContentType;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： PortLeaderMailListFragment
 * @描述： 政民互动 12345来信办理平台 部门领导信箱 某部门最新信件列表界面
 * @作者： 罗森
 * @创建时间： 2013-8-23 上午11:12:56
 * @修改时间：
 * @修改描述：
 * 
 */
public class PartLeaderMailFragment extends RadioButtonChangeFragment {

	private static final String TAG = "PartLeaderMailListFragment";

	private static final int HIDEN_CONTENT_ID = R.id.mayor_box_fragment;

	private static final int ALLCOUNT_LOAD_SUCESS = 0; // 答复率总数统计
	private static final int DATA_LOAD_ERROR = 1;

	// 部门数据加载成功标志
	private static final int LOAD_DEPT_SUCCESS = 2;
	// 部门数据加载失败标志
	private static final int LOAD_DEPT_FAILED = 3;

	// 内容类型数据加载成功标志
	private static final int LOAD_CONTENT_TYPE_SUCCESS = 4;
	// 内容类型数据加载失败标志
	private static final int LOAD_CONTENT_TYPE_FAILED = 5;

	// 信件类型数据加载成功标志
	private static final int LOAD_MAIL_TYPE_SUCCESS = 6;
	// 信件类型数据加载失败标志
	private static final int LOAD_MAIL_TYPE_FAILED = 7;

	private static String DEFAULT_DEPT_FIFTER = "无限制";

	private Button dealMailBtn = null;
	private Button queryMailBtn = null;

	private TextView advisoryNum = null;
	private TextView mayorNum = null;
	private TextView leaderNum = null;

	// 关键字输入框
	private EditText keyWordEdit = null;
	// 信件编号输入框
	private EditText mailNoEdit = null;
	// 答复时间的开始时间输入框
	private EditText timeBeginEdit = null;
	// 答复时间的结束时间输入框
	private EditText timeEndEdit = null;

	// 常见问题复选框
	private CheckBox questionCheckbox = null;

	// 信件查询按钮
	private Button queryMailsBtn = null;

	// 信箱分类下拉列表
	private Spinner boxSortSpinner = null;
	// 信件分类下拉列表
	private Spinner emailSortSpinner = null;
	// 受理部门下拉列表
	private Spinner acceptDepartmentSpinner = null;
	// 答复部门下拉列表
	private Spinner replyDepartmentSpinner = null;

	private LinearLayout linearLayout = null;
	private LinearLayout radioLayout = null;

	private RadioButton radioBtn = null;

	private View popview = null;

	private List<AllCount> allCounts;

	private PartLeaderMail leaderMail;

	private PartLeaderMailWrapper leaderMailWrapper = null;
	private List<PartLeaderMail> depts = null;

	private QueryMailContentTypeWrapper contentTypeWrapper = null;
	private List<QueryMailContentType> contentTypes = null;

	private MailTypeWrapper mailTypeWrapper = null;
	private List<MailType> mailTypes = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private int contentType = 0; // 内容类型，缺省为0-信件列表 1-写信须知 2-办理规则

	private final int[] radioButtonIds = {
			R.id.gip_12345_mayorbox_radioButton_mailList,
			R.id.gip_12345_mayorbox_radioButton_mustKonwMail,
			R.id.gip_12345_mayorbox_radioButton_mayorBoxRule };
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {

			case LOAD_DEPT_SUCCESS:
				showDept();
				break;

			case LOAD_DEPT_FAILED:
				Toast.makeText(context, "部门信息加载失败", Toast.LENGTH_SHORT).show();
				break;

			case ALLCOUNT_LOAD_SUCESS:

				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case LOAD_CONTENT_TYPE_SUCCESS:
				showContentType();
				break;

			case LOAD_CONTENT_TYPE_FAILED:
				Toast.makeText(context, "内容类型加载失败", Toast.LENGTH_SHORT).show();
				break;

			case LOAD_MAIL_TYPE_SUCCESS:
				showMailType();
				break;

			case LOAD_MAIL_TYPE_FAILED:
				Toast.makeText(context, "信件类型加载失败", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_mayorbox_radioButton_mailList:
			contentType = 0;
			init();
			break;

		case R.id.gip_12345_mayorbox_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			break;

		case R.id.gip_12345_mayorbox_radioButton_mayorBoxRule:
			contentType = 2;
			changeContent(contentType);
			break;
		}
	}

	/**
	 * 切换界面
	 * 
	 * @param type
	 */
	public void changeContent(int type) {
		GoverInterPeopleWebFragment goverInterPeopleWebFragment = new GoverInterPeopleWebFragment();
		switch (type) {
		case 0:
			init();
			break;
		case 1:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/wap/zmhd/6148278.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 2:
//<<<<<<< HEAD
////			Intent intent = new Intent(getActivity(), MainMineActivity.class);
////			intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 5);
////			intent.putExtra(Constants.CheckPositionKey.LEVEL_TWO__KEY, 1);// 这个意思让你选中左侧第二个菜单也就是12345办理平台
////			intent.putExtra(Constants.CheckPositionKey.LEVEL_THREE_KEY, 6);// 这个意思让你选中我要写信
////
////			startActivity(intent);
//			
//=======

			Intent intent = new Intent(getActivity(), MainMineActivity.class);
			intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 5);
		
			intent.putExtra(Constants.CheckPositionKey.LEVEL_TWO__KEY, 1);// 这个意思让你选中左侧第二个菜单也就是12345办理平台
			intent.putExtra(Constants.CheckPositionKey.LEVEL_THREE_KEY, 6);// 这个意思让你选中我要写信
			MainTabActivity.instance.addView(intent);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_mayorbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_mayorbox_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		linearLayout = (LinearLayout) view.findViewById(R.id.query_mail_layout);
		radioLayout = (LinearLayout) view
				.findViewById(R.id.mayorbox_radiobtn_linearlayout);

		// 关键字输入框
		keyWordEdit = (EditText) view
				.findViewById(R.id.query_mail_key_word_edit);
		// 信件编号输入框
		mailNoEdit = (EditText) view.findViewById(R.id.query_mail_no_edit);
		// 答复时间的开始时间输入框
		timeBeginEdit = (EditText) view
				.findViewById(R.id.query_mail_reply_time_begin_edit);
		// 答复时间的结束时间输入框
		timeEndEdit = (EditText) view
				.findViewById(R.id.query_mail_reply_time_end_edit);

		// 常见问题复选框
		questionCheckbox = (CheckBox) view
				.findViewById(R.id.query_mail_question_checkbox);

		// 信件查询按钮
		queryMailsBtn = (Button) view.findViewById(R.id.query_mail_btn);
		queryMailsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "该功能暂未开通", Toast.LENGTH_SHORT).show();
			}
		});

		// 信箱分类下拉列表
		boxSortSpinner = (Spinner) view
				.findViewById(R.id.query_mail_box_sort_spinner);

		// 信件分类下拉列表
		emailSortSpinner = (Spinner) view
				.findViewById(R.id.query_mail_email_sort_spinner);

		// 受理部门下拉列表
		acceptDepartmentSpinner = (Spinner) view
				.findViewById(R.id.query_mail_accept_department_spinner);

		// 答复部门下拉列表
		replyDepartmentSpinner = (Spinner) view
				.findViewById(R.id.query_mail_reply_department_spinner);

		loadContentTypeData();
		loadMailTypeData();
		loadDeptData();

		radioBtn = (RadioButton) view
				.findViewById(R.id.gip_12345_mayorbox_radioButton_mayorBoxRule);
		radioBtn.setText("我要写信");

		queryMailBtn = (Button) view
				.findViewById(R.id.gip_12345_mayorbox_button_queryMail);
		queryMailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (linearLayout.getVisibility() == LinearLayout.GONE) {
					linearLayout.setVisibility(LinearLayout.VISIBLE);
					radioLayout.setVisibility(LinearLayout.GONE);
				} else {
					linearLayout.setVisibility(LinearLayout.GONE);
					radioLayout.setVisibility(LinearLayout.VISIBLE);
				}
			}
		});

		dealMailBtn = (Button) view
				.findViewById(R.id.gip_12345_mayorbox_button_statisticMail);
		dealMailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopWindow(context);
				int[] xy = new int[2];
				queryMailBtn.getLocationOnScreen(xy);
				popWindow.showAtLocation(queryMailBtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, queryMailBtn.getHeight() * 2 + 31);
				dealMailBtn.setVisibility(View.GONE);
			}
		});

		PartLeaderMailListFragment leaderMailListFragment = new PartLeaderMailListFragment();

		leaderMailListFragment.setLeaderMail(getLeaderMail());
		bindFragment(leaderMailListFragment);

		loadAllCountData();
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * 创建弹出窗体
	 * 
	 * @param con
	 * @return
	 */
	private PopupWindow makePopWindow(Context con) {
		PopupWindow popupWindow = null;

		popview = LayoutInflater.from(con).inflate(
				R.layout.gip_mayormail_deal_statistics_layout, null);

		advisoryNum = (TextView) popview
				.findViewById(R.id.advisory_complain_mail_text);

		mayorNum = (TextView) popview.findViewById(R.id.mayor_box_mail_text);

		leaderNum = (TextView) popview.findViewById(R.id.leader_box_mail_text);

		showAllCounts();

		popWindowManager = PopWindowManager.getInstance();

		popWindowManager.addPopWindow(popupWindow);

		popupWindow = new PopupWindow(con);

		popupWindow.setContentView(popview);

		popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);

		popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupWindow.setTouchable(true); // 设置PopupWindow可触摸
		popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		return popupWindow;
	}

	/**
	 * @方法： showDept
	 * @描述： 显示部门数据
	 */
	private void showDept() {
		PartLeaderMailWrapper mailWrapper = new PartLeaderMailWrapper();
		PartLeaderMail leaderMail = mailWrapper.new PartLeaderMail();
		leaderMail.setDepid("0");
		leaderMail.setDepname(DEFAULT_DEPT_FIFTER);
		depts.add(0, leaderMail);

		DeptAdapter partment_Spinner_adapter = new DeptAdapter();

		acceptDepartmentSpinner.setAdapter(partment_Spinner_adapter);
		acceptDepartmentSpinner
				.setOnItemSelectedListener(partment_Spinner_adapter);

		replyDepartmentSpinner.setAdapter(partment_Spinner_adapter);
		replyDepartmentSpinner
				.setOnItemSelectedListener(partment_Spinner_adapter);

	}

	/**
	 * @方法： showContentType
	 * @描述： 显示内容类型数据
	 */
	private void showContentType() {
		contentTypeWrapper = new QueryMailContentTypeWrapper();
		QueryMailContentType contentType = contentTypeWrapper.new QueryMailContentType();

		contentType.setTypeid("0");
		contentType.setTypename(DEFAULT_DEPT_FIFTER);

		contentTypes.add(0, contentType);

		QueryMailContentTypeAdapter adapter = new QueryMailContentTypeAdapter(
				context, contentTypes);

		boxSortSpinner.setAdapter(adapter);
	}

	/**
	 * @方法： showMailType
	 * @描述： 显示信件类型数据
	 */
	private void showMailType() {
		mailTypeWrapper = new MailTypeWrapper();
		MailType mailType = mailTypeWrapper.new MailType();

		mailType.setTypeid("0");
		mailType.setTypename(DEFAULT_DEPT_FIFTER);

		mailTypes.add(0, mailType);

		MailTypeAdapter adapter = new MailTypeAdapter(context, mailTypes);

		emailSortSpinner.setAdapter(adapter);
	}

	/**
	 * 显示所有回复统计信息
	 */
	public void showAllCounts() {

		for (AllCount count : allCounts) {
			if (count.getName().equals("领导信箱"))
				advisoryNum.setText(String.valueOf(count.getCount()) + "封");
			else if (count.getName().equals("咨询投诉"))
				mayorNum.setText(String.valueOf(count.getCount()) + "封");
			else if (count.getName().equals("市长信箱"))
				leaderNum.setText(String.valueOf(count.getCount()) + "封");
		}
	}

	/**
	 * 加载所有统计信息
	 * */
	private void loadAllCountData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(
						context);
				try {
					allCounts = replyStatisticsService
							.getAllCount(Constants.Urls.LETTERS_ALLCOUNT_URL);
					if (null != allCounts) {
						handler.sendEmptyMessage(ALLCOUNT_LOAD_SUCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}

				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @方法： loadMailTypeData
	 * @描述： 加载信件类型数据
	 */
	private void loadMailTypeData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				MailTypeService typeService = new MailTypeService(context);
				try {
					mailTypeWrapper = typeService.getMailTypeWrapper();

					if (mailTypeWrapper != null) {
						mailTypes = mailTypeWrapper.getMailTypes();
						handler.sendEmptyMessage(LOAD_MAIL_TYPE_SUCCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "没有获取到信件类型数据";
						handler.sendEmptyMessage(LOAD_MAIL_TYPE_FAILED);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LOAD_MAIL_TYPE_FAILED);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_MAIL_TYPE_SUCCESS;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @方法： loadContentTypeData
	 * @描述： 加载内容类型数据
	 */
	private void loadContentTypeData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				QueryMailContentTypeService typeService = new QueryMailContentTypeService(
						context);
				try {

					contentTypeWrapper = typeService.getQueryMailContentType();

					if (contentTypeWrapper != null) {
						contentTypes = contentTypeWrapper.getContentTypes();
						handler.sendEmptyMessage(LOAD_CONTENT_TYPE_SUCCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "没有获取到内容类型数据";
						handler.sendEmptyMessage(LOAD_CONTENT_TYPE_FAILED);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LOAD_CONTENT_TYPE_FAILED);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_CONTENT_TYPE_SUCCESS;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @return leaderMail
	 */
	public PartLeaderMail getLeaderMail() {
		return leaderMail;
	}

	/**
	 * @param leaderMail
	 *            要设置的 leaderMail
	 */
	public void setLeaderMail(PartLeaderMail leaderMail) {
		this.leaderMail = leaderMail;
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
				PartLeaderMailService mailService = new PartLeaderMailService(
						context);
				try {
					leaderMailWrapper = mailService.getPartLeaderMailWrapper();

					if (leaderMailWrapper != null) {
						depts = leaderMailWrapper.getPartLeaderMails();
						msg.what = LOAD_DEPT_SUCCESS;
					} else {
						msg.what = LOAD_DEPT_FAILED;
						msg.obj = "没有获取到部门数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_FAILED;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_SUCCESS;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
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
	public class DeptAdapter extends BaseAdapter implements
			OnItemSelectedListener {

		@Override
		public int getCount() {
			return depts.size();
		}

		@Override
		public Object getItem(int position) {
			return depts.get(position);
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
			PartLeaderMail dept = depts.get(position);
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
			viewHolder.tv_dept.setText(dept.getDepname());

			return convertView;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

}
