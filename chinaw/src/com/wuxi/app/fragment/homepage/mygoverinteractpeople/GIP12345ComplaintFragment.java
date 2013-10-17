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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.GIP12345AllMailContentActivity;
import com.wuxi.app.adapter.ComplaintLetterListAdapter;
import com.wuxi.app.adapter.MailTypeAdapter;
import com.wuxi.app.adapter.QueryMailContentTypeAdapter;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.engine.MailTypeService;
import com.wuxi.app.engine.PartLeaderMailService;
import com.wuxi.app.engine.QueryMailContentTypeService;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.domain.MailTypeWrapper;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.QueryMailContentTypeWrapper;
import com.wuxi.domain.LetterWrapper.Letter;
import com.wuxi.domain.MailTypeWrapper.MailType;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.domain.QueryMailContentTypeWrapper.QueryMailContentType;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 12345来信办理平台 主Fragment --建议咨询投诉 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345ComplaintFragment extends RadioButtonChangeFragment
		implements OnItemClickListener, OnClickListener, OnScrollListener {

	private static final String TAG = "GIP12345ComplaintFragment";

	private final int[] radioButtonIds = {
			R.id.gip_12345_complaint_radioButton_latestMailList,
			R.id.gip_12345_complaint_radioButton_mustKonwMail,
			R.id.gip_12345_complaint_radioButton_mayorBoxRule,
			R.id.gip_12345_complaint_radioButton_organizationDuty };

	private static final int HIDEN_CONTENT_ID = R.id.complaint_web_fragment;

	private ListView mListView;
	private ProgressBar list_pb;
	private LetterWrapper letterWrapper;
	private List<Letter> letters;

	private FrameLayout webFrameLayout = null;
	private FrameLayout notwebFrameLayout = null;
	
	private ComplaintLetterListAdapter adapter;

	private static final int LETTER_LOAD_SUCESS = 10;
	private static final int LETTER_LOAD_ERROR = 11;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

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

	private Button dealMailBtn = null;
	private Button queryMailBtn = null;

	private TextView advisoryNum = null;
	private TextView mayorNum = null;
	private TextView leaderNum = null;

	private View popview = null;

	private List<AllCount> allCounts;

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
	private RadioGroup radioGroup = null;

	private PartLeaderMailWrapper leaderMailWrapper = null;
	private List<PartLeaderMail> depts = null;

	private QueryMailContentTypeWrapper contentTypeWrapper = null;
	private List<QueryMailContentType> contentTypes = null;

	private MailTypeWrapper mailTypeWrapper = null;
	private List<MailType> mailTypes = null;

	private static String DEFAULT_DEPT_FIFTER = "无限制";
	private String deptStrFifter = DEFAULT_DEPT_FIFTER;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private int contentType = 0; // 内容类型，缺省为0-信件列表 1-写信须知 2-办理规则 3-机构职责
	
	
	private View myconsultloadMoreView;
	private ProgressBar pb_consultloadmoore;
	private Button myconsultloadMoreButton;

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

			case LETTER_LOAD_SUCESS:
				showLettersList();
				break;

			case LETTER_LOAD_ERROR:
				list_pb.setVisibility(View.VISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_complaint_radioButton_latestMailList:
			contentType = 0;
			webFrameLayout.setVisibility(View.GONE);
			notwebFrameLayout.setVisibility(View.VISIBLE);
			break;

		case R.id.gip_12345_complaint_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			webFrameLayout.setVisibility(View.VISIBLE);
			notwebFrameLayout.setVisibility(View.GONE);
			break;

		case R.id.gip_12345_complaint_radioButton_mayorBoxRule:
			contentType = 2;
			changeContent(contentType);
			webFrameLayout.setVisibility(View.VISIBLE);
			notwebFrameLayout.setVisibility(View.GONE);
			break;

		case R.id.gip_12345_complaint_radioButton_organizationDuty:
			contentType = 3;
			changeContent(contentType);
			webFrameLayout.setVisibility(View.VISIBLE);
			notwebFrameLayout.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_complaint_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_complaint_radioGroup;
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
		radioGroup = (RadioGroup) view
				.findViewById(R.id.gip_12345_complaint_radioGroup);
		
		webFrameLayout = (FrameLayout) view.findViewById(R.id.complaint_web_fragment);
		notwebFrameLayout = (FrameLayout) view.findViewById(R.id.complaint_fragment);

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
				Toast.makeText(context, "该功能暂未实现", Toast.LENGTH_SHORT).show();
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

		queryMailBtn = (Button) view
				.findViewById(R.id.gip_12345_complaint_button_queryMail);
		queryMailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (linearLayout.getVisibility() == LinearLayout.GONE) {
					linearLayout.setVisibility(LinearLayout.VISIBLE);
					radioGroup.setVisibility(LinearLayout.GONE);
				} else {
					linearLayout.setVisibility(LinearLayout.GONE);
					radioGroup.setVisibility(LinearLayout.VISIBLE);
				}
			}
		});

		dealMailBtn = (Button) view
				.findViewById(R.id.gip_12345_complaint_button_statisticMail);
		dealMailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopWindow(context);
				int[] xy = new int[2];
				queryMailBtn.getLocationOnScreen(xy);
				popWindow.showAtLocation(queryMailBtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, queryMailBtn.getHeight() * 2 + 10);
				dealMailBtn.setVisibility(View.GONE);
			}
		});

		// 初始化布局
		initLayout();

		// 第一次加载数据
		loadFirstData(0, PAGE_NUM);

		loadAllCountData();
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view
				.findViewById(R.id.gip_12345_complaint_listView);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_12345_complaint_listView_pb);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * 加载数据
	 */
	private void loadData(final int startIndex, final int endIndex) {
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
					String url = Constants.Urls.SUGGESTLETTER_URL + "?start="
							+ startIndex + "&end=" + endIndex;
					letterWrapper = letterService.getLetterLitstWrapper(url);
					if (null != letterWrapper) {
						handler.sendEmptyMessage(LETTER_LOAD_SUCESS);

					} else {
						message.obj = "error";
						handler.sendEmptyMessage(LETTER_LOAD_ERROR);
					}

				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LETTER_LOAD_ERROR);
				} catch (JSONException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LETTER_LOAD_ERROR);
					e.printStackTrace();
				} catch (NODataException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LETTER_LOAD_ERROR);
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 显示列表
	 */
	private void showLettersList() {
		letters = letterWrapper.getData();
		if (letters == null || letters.size() == 0) {
			Toast.makeText(context, "对不起，暂无建议咨询投诉信息", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (isFirstLoad) {
				adapter = new ComplaintLetterListAdapter(letters, context);
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
		}

		if (letterWrapper.isNext()) {
			if (mListView.getFooterViewsCount()!= 0) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			}else {
				mListView.addFooterView(getMyConsultFootView());
			}
			
		} else {
			if (adapter != null) {
				mListView.removeFooterView(loadMoreView);
			}
			
		}
	}

	/**
	 * @方法： getMyConsultFootView
	 * @描述： 获取我的咨询列表底部视图
	 * @return
	 */
	private View getMyConsultFootView() {
		myconsultloadMoreView = View.inflate(context,
				R.layout.list_loadmore_layout, null);
		pb_consultloadmoore = (ProgressBar) myconsultloadMoreView
				.findViewById(R.id.pb_loadmoore);

		myconsultloadMoreButton = (Button) myconsultloadMoreView
				.findViewById(R.id.loadMoreButton);
		myconsultloadMoreButton.setOnClickListener(this);
		return myconsultloadMoreView;
	}
	
	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (letterWrapper != null && letterWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
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
		// acceptDepartmentSpinner
		// .setOnItemSelectedListener(partment_Spinner_adapter);

		replyDepartmentSpinner.setAdapter(partment_Spinner_adapter);
		// replyDepartmentSpinner
		// .setOnItemSelectedListener(partment_Spinner_adapter);

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
			// 设置下拉列表内容
			deptStrFifter = depts.get(position).getDepname();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	/**
	 * 切换界面
	 * 
	 * @param type
	 */
	private void changeContent(int type) {
		GoverInterPeopleWebFragment goverInterPeopleWebFragment = new GoverInterPeopleWebFragment();
		switch (type) {
		case 0:
			init();
			break;
		case 1:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/wap/zmhd/6148281.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 2:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/wap/zmhd/6148282.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 3:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/wap/zxzx/jgzn/index.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
			break;
		}
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

	/*
	 * 显示所有回复统计信息
	 */
	private void showAllCounts() {

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
	 * 先加载所有统计信息，下面的信息，按 统计按钮后加载
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

}
