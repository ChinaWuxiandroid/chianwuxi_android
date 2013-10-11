package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.GIP12345AllMailContentActivity;
import com.wuxi.app.adapter.MailTypeAdapter;
import com.wuxi.app.adapter.MayorLettersListAdapter;
import com.wuxi.app.adapter.QueryMailContentTypeAdapter;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.engine.MailTypeService;
import com.wuxi.app.engine.QueryLetterDepService;
import com.wuxi.app.engine.QueryMailContentTypeService;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.domain.MailTypeWrapper;
import com.wuxi.domain.LetterWrapper.Letter;
import com.wuxi.domain.MailTypeWrapper.MailType;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.domain.QueryLetterCondition;
import com.wuxi.domain.QueryMailContentTypeWrapper;
import com.wuxi.domain.QueryMailContentTypeWrapper.QueryMailContentType;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 12345来信办理平台 主Fragment --市长信箱 fragment
 * 
 * @author 杨宸 智佳
 * */

@SuppressLint("SimpleDateFormat")
public class GIP12345MayorMaiBoxFragment extends RadioButtonChangeFragment
		implements OnScrollListener, OnClickListener, OnItemClickListener {

	private static final String TAG = "GIP12345MayorMaiBoxFragment";

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

	// 日期选择对话框显示标志
	private static final int SHOW_BEGIN_DATE_PICK = 8;
	private static final int SHOW_END_DATE_PICK = 9;

	private static final int BEGIN_TIME_DIALOG_ID = 0;
	private static final int END_TIME_DIALOG_ID = 1;

	private int byear;
	private int bmonth;
	private int bday;

	private int eyear;
	private int emonth;
	private int eday;

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

	private Button beginTimeBtn = null;
	private Button endTimeBtn = null;

	// 常见问题复选框
	private CheckBox questionCheckbox = null;

	// 信件查询按钮
	public Button queryMailsBtn = null;

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

	private FrameLayout frameLayout = null;

	private ListView mListView;
	private ProgressBar list_pb;
	
	private LetterWrapper letterWrapper;
	private List<Letter> letters ;
	
	private MayorLettersListAdapter adapter;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	// 数据加载成功标识
	private static final int LETTER_LOAD_SUCESS = 10;
	// 数据加载失败标识
	private static final int LETTER_LOAD_ERROR = 11;

	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private View popview = null;

	private List<AllCount> allCounts = null;

	private PartLeaderMailWrapper leaderMailWrapper = null;
	private List<PartLeaderMail> depts = null;

	private QueryMailContentTypeWrapper contentTypeWrapper = null;
	private List<QueryMailContentType> contentTypes = null;

	private MailTypeWrapper mailTypeWrapper = null;
	private List<MailType> mailTypes = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private static String DEFAULT_DEPT_FIFTER = "无限制";

	private int contentType = 0; // 内容类型，缺省为0-信件列表 1-写信须知 2-办理规则

	// 信件查询实体类实例
	private QueryLetterCondition letterCondition = new QueryLetterCondition();

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

			case SHOW_BEGIN_DATE_PICK:
				showDialog(BEGIN_TIME_DIALOG_ID);
				break;

			case SHOW_END_DATE_PICK:
				showDialog(END_TIME_DIALOG_ID);
				break;

			case LETTER_LOAD_SUCESS:
				showLettersList();
				break;

			case LETTER_LOAD_ERROR:
				list_pb.setVisibility(View.VISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
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
					.setUrl("http://www.wuxi.gov.cn/wap/zmhd/6148280.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 2:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/wap/zmhd/6148283.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
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
		initLayout();

		setBeginDate();
		setEndDate();

		GIP12345MayorMailListFragment gip12345MayorMailListFragment = new GIP12345MayorMailListFragment();
		bindFragment(gip12345MayorMailListFragment);

		loadAllCountData();
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {

		frameLayout = (FrameLayout) view
				.findViewById(R.id.mayor_query_letter_fragment);

		mListView = (ListView) view
				.findViewById(R.id.gip_12345_query_letter_listView);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_12345_query_letter_listView_pb);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);

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

		beginTimeBtn = (Button) view
				.findViewById(R.id.query_mail_reply_begin_time_btn);
		beginTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (beginTimeBtn.equals((Button) v)) {
					msg.what = GIP12345MayorMaiBoxFragment.SHOW_BEGIN_DATE_PICK;
				}
				handler.sendMessage(msg);
			}
		});

		endTimeBtn = (Button) view
				.findViewById(R.id.query_mail_reply_end_time_btn);
		endTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (endTimeBtn.equals((Button) v)) {
					msg.what = GIP12345MayorMaiBoxFragment.SHOW_END_DATE_PICK;
				}
				handler.sendMessage(msg);
			}
		});

		// 常见问题复选框
		questionCheckbox = (CheckBox) view
				.findViewById(R.id.query_mail_question_checkbox);

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
				dealMailBtn.getLocationOnScreen(xy);
				popWindow.showAtLocation(dealMailBtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, dealMailBtn.getHeight() * 2 + 31);
				dealMailBtn.setVisibility(View.GONE);
			}
		});

		linearLayout = (LinearLayout) view.findViewById(R.id.query_mail_layout);
		radioLayout = (LinearLayout) view
				.findViewById(R.id.mayorbox_radiobtn_linearlayout);

		// 内容类型下拉框监听
		boxSortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				letterCondition.setContenttype(contentTypes.get(position)
						.getTypeid());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// 信件类型下拉框监听
		emailSortSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						letterCondition.setLettertype(mailTypes.get(position)
								.getTypeid());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		// 受理部门下拉框监听
		acceptDepartmentSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						letterCondition
								.setDepid(depts.get(position).getDepid());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		// 答复部门下拉框监听
		replyDepartmentSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						letterCondition.setDodepid(depts.get(position)
								.getDepid());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		// 信件查询按钮
		queryMailsBtn = (Button) view.findViewById(R.id.query_mail_btn);
		// 信件查询按钮监听
		queryMailsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				letterCondition.setKeyword(keyWordEdit.getText().toString());
				letterCondition.setCode(mailNoEdit.getText().toString());

				if (questionCheckbox.isChecked()) {
					letterCondition.setCommon(-1);
				} else {
					letterCondition.setCommon(1);
				}
				
				loadFirstData(0, PAGE_NUM);

				view.findViewById(R.id.mayor_box_fragment).setVisibility(
						View.GONE);
				frameLayout.setVisibility(View.VISIBLE);
				linearLayout.setVisibility(LinearLayout.GONE);
				radioLayout.setVisibility(LinearLayout.VISIBLE);

			}
		});

	}

	/**
	 * @方法： loadFirstData
	 * @描述： 首次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * 加载数据
	 */
	public void loadData(final int startIndex, final int endIndex) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				LetterService letterService = new LetterService(context);
				try {
					Looper.prepare();
					String url = "";
					if (letterCondition.getKeyword().equals("")) {
						url = Constants.Urls.MAYOR_MAILBOX_URL + "?start="
								+ startIndex + "&end=" + endIndex + "&keyword="
								+ letterCondition.getKeyword()
								+ "&contenttype="
								+ letterCondition.getContenttype()
								+ "&lettertype="
								+ letterCondition.getLettertype()
								+ "&starttime="
								+ letterCondition.getStarttime() + "&endtime="
								+ letterCondition.getEndtime() + "&code="
								+ letterCondition.getCode() + "&depid="
								+ letterCondition.getDepid() + "&dodepid="
								+ letterCondition.getDodepid() + "&common="
								+ letterCondition.getCommon();
					} else {
						url = Constants.Urls.MAYOR_MAILBOX_URL + "?start="
								+ startIndex + "&end=" + endIndex + "&keyword="
								+ letterCondition.getKeyword() + "&lettertype="
								+ letterCondition.getLettertype()
								+ "&starttime="
								+ letterCondition.getStarttime() + "&endtime="
								+ letterCondition.getEndtime() + "&code="
								+ letterCondition.getCode() + "&depid="
								+ letterCondition.getDepid() + "&dodepid="
								+ letterCondition.getDodepid() + "&common="
								+ letterCondition.getCommon();
					}

					System.out.println("查询：" + url);
					
					letterWrapper = letterService.getLetterLitstWrapper(url);
					if (null != letterWrapper) {
						handler.sendEmptyMessage(LETTER_LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(LETTER_LOAD_ERROR);
					}
					Looper.loop();
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LETTER_LOAD_ERROR);

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	public void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
		}
	}

	/**
	 * 显示列表
	 */
	public void showLettersList() {
		
		letters = letterWrapper.getData();
		
		for (int i = 0; i < letters.size(); i++) {
			System.out.println("打印："+letters.get(i).getTitle());
		}
		
		if (letters != null && letters.size() > 0) {
			if (isFirstLoad) {
				adapter = new MayorLettersListAdapter(letters, context);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setLetters(letters);
					list_pb.setVisibility(View.GONE);
				} else {
					for (Letter letter : letters) {
						adapter.addItem(letter);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		} else {
			mListView.setVisibility(View.GONE);
			Toast.makeText(context, "对不起，暂无市长信箱信息", Toast.LENGTH_SHORT).show();
		}

		if (letterWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			mListView.removeFooterView(loadMoreView);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Letter letter = (Letter) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(),
				GIP12345AllMailContentActivity.class);
		intent.putExtra("letter", letter);

		MainTabActivity.instance.addView(intent);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (letterWrapper != null && letterWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
			}
			break;
		}
	}

	/**
	 * @方法： setDateTime
	 * @描述： 设置开始日期
	 */
	private void setBeginDate() {
		final Calendar calendar = Calendar.getInstance();

		byear = calendar.get(Calendar.YEAR);
		bmonth = calendar.get(Calendar.MONTH);
		bday = calendar.get(Calendar.DAY_OF_MONTH);

		updateBeginDateDisplay();
	}

	/**
	 * @方法： updateBeginDateDisplay
	 * @描述： 更新开始日期显示
	 */
	private void updateBeginDateDisplay() {
		StringBuffer sb = new StringBuffer().append(byear).append("-")
				.append((bmonth + 1) < 10 ? "0" + (bmonth + 1) : (bmonth + 1))
				.append("-").append((bday < 10) ? "0" + bday : bday);
		timeBeginEdit.setText(sb);

		// 获取日期格式实例
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 时间实例
		Date date = null;
		try {
			// 将字符串按照一定的格式转换成时间对象，即long数据
			date = format.parse(sb.toString());
			// 设置查询条件的开始时间的值
			letterCondition.setStarttime(date.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法： setEndDate
	 * @描述： 设置结束日期
	 */
	private void setEndDate() {
		final Calendar calendar = Calendar.getInstance();

		eyear = calendar.get(Calendar.YEAR);
		emonth = calendar.get(Calendar.MONTH);
		eday = calendar.get(Calendar.DAY_OF_MONTH);

		updateEndDateDisplay();
	}

	/**
	 * @方法： updateEndDateDisplay
	 * @描述： 更新结束日期显示
	 */
	private void updateEndDateDisplay() {
		StringBuffer sb = new StringBuffer().append(eyear).append("-")
				.append((emonth + 1) < 10 ? "0" + (emonth + 1) : (emonth + 1))
				.append("-").append((eday < 10) ? "0" + eday : eday);
		timeEndEdit.setText(sb);

		// 获取日期格式实例
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 时间实例
		Date date = null;

		try {
			// 将字符串按照一定的格式转换成时间对象，即long数据
			date = format.parse(sb.toString());
			// 设置查询条件的结束时间的值
			letterCondition.setEndtime(date.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始日期对话框监听器
	 */
	private DatePickerDialog.OnDateSetListener beginDateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			GIP12345MayorMaiBoxFragment.this.byear = year;
			GIP12345MayorMaiBoxFragment.this.bmonth = monthOfYear;
			GIP12345MayorMaiBoxFragment.this.bday = dayOfMonth;
			updateBeginDateDisplay();
		}
	};

	/**
	 * 结束日期对话框监听器
	 */
	private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			GIP12345MayorMaiBoxFragment.this.eyear = year;
			GIP12345MayorMaiBoxFragment.this.emonth = monthOfYear;
			GIP12345MayorMaiBoxFragment.this.eday = dayOfMonth;
			updateEndDateDisplay();
		}
	};

	/**
	 * @方法： onCreateDialog
	 * @描述： 创建时间选择对话框
	 * @param id
	 * @return Dialog
	 */
	private Dialog onCreateDialog(int id) {
		switch (id) {
		case BEGIN_TIME_DIALOG_ID:
			DatePickerDialog beginDialog = new DatePickerDialog(context,
					beginDateListener, byear, bmonth, bday);
			beginDialog.setIcon(R.drawable.logo);
			beginDialog.show();
			return beginDialog;
		case END_TIME_DIALOG_ID:
			DatePickerDialog endDialog = new DatePickerDialog(context,
					endDateListener, eyear, emonth, eday);
			endDialog.setIcon(R.drawable.logo);
			endDialog.show();
			return endDialog;
		}
		return null;
	}

	/**
	 * @方法： showDialog
	 * @描述： 显示日期选择对话框
	 * @param id
	 */
	private void showDialog(int id) {
		onCreateDialog(id);
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
	 * 创建信件处理统计弹出窗体
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

		replyDepartmentSpinner.setAdapter(partment_Spinner_adapter);

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
	 * 先加载所有统计信息
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
	public class DeptAdapter extends BaseAdapter {

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
			viewHolder.tv_dept.setTextSize(12);
			viewHolder.tv_dept.setText(dept.getDepname());

			return convertView;
		}

	}

}
