package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.MailTypeAdapter;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.engine.MailTypeService;
import com.wuxi.app.engine.PartLeaderMailService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.MailTypeWrapper;
import com.wuxi.domain.MyLetter;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.MailTypeWrapper.MailType;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 12345来信办理平台 我要写信 我要写信子界面碎片碎片
 * 
 * @author 智佳 杨宸
 * 
 */
@SuppressLint("ShowToast")
public class GIP12345IWantMailLayoutFragment extends BaseFragment implements
		OnClickListener, OnCheckedChangeListener {

	private final static String TAG = "GIP12345IWantMailLayoutFragment";

	protected View view = null;
	protected LayoutInflater mInflater = null;
	private Context context = null;

	private final static int SEND_SUCCESS = 1;
	private final static int SEND_FAILED = 0;

	// 部门数据加载成功标志
	private static final int LOAD_DEPT_SUCCESS = 2;
	// 部门数据加载失败标志
	private static final int LOAD_DEPT_FAILED = 3;

	// 信件类型数据加载成功标志
	private static final int LOAD_MAIL_TYPE_SUCCESS = 6;
	// 信件类型数据加载失败标志
	private static final int LOAD_MAIL_TYPE_FAILED = 7;

	private MyLetter myLetter = null;

	private PartLeaderMailWrapper leaderMailWrapper = null;
	private List<PartLeaderMail> depts = null;

	private RadioGroup mailType_radioGroup = null;
	private static final String DOPROJECTID_MAYORBOX = "6b8e124e-1e5c-4a11-8dd3-c6623c809eff"; // 市长信箱
	private static final String DOPROJECTID_SUGGEST_AND_COMPLAINT = "bfffa273-086a-47cb-a7a8-7ae8140550db"; // 建议咨询投诉

	private RadioGroup isOpen_radioGroup = null;
	public final int open = 1;
	public final int notopen = 0;

	private RadioGroup isReplyMail_radioGroup = null;
	public final int replyMail = 1;
	public final int notreplyMail = 0;

	private RadioGroup isReplyMsg_radioGroup = null;
	public final int replyMsg = 1;
	public final int notreplyMsg = 0;

	private Spinner mailBoxType = null;

	private EditText title_editText = null;
	private EditText content_editText = null;

	private ImageButton send = null;

	private Spinner partLeaderMailBoxSpinner = null;

	private MailTypeWrapper mailTypeWrapper = null;
	private List<MailType> mailTypes = null;		

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj != null) {
			}
			switch (msg.what) {
			case SEND_SUCCESS:
				Toast.makeText(context, "提交成功！", 2000).show();
				break;
			case SEND_FAILED:
				Toast.makeText(context, "提交失败！", 2000).show();
				break;
			case LOAD_DEPT_SUCCESS:
				showDept();
				break;

			case LOAD_DEPT_FAILED:
				Toast.makeText(context, "部门信息加载失败", Toast.LENGTH_SHORT).show();
				break;

			case LOAD_MAIL_TYPE_SUCCESS:
				showMailType();
				break;

			case LOAD_MAIL_TYPE_FAILED:
				Toast.makeText(context, "信件类型加载失败", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_iwantmail_appui_layout, null);
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
		myLetter = new MyLetter();

		mailType_radioGroup = (RadioGroup) view
				.findViewById(R.id.gip_12345_iwantmail_radiogroup_mailType);
		isOpen_radioGroup = (RadioGroup) view
				.findViewById(R.id.gip_12345_iwantmail_radiogroup_isopen);
		isReplyMail_radioGroup = (RadioGroup) view
				.findViewById(R.id.gip_12345_iwantmail_radiogroup_isNeedMailRaply);
		isReplyMsg_radioGroup = (RadioGroup) view
				.findViewById(R.id.gip_12345_iwantmail_radiogroup_isNeedMsgRaply);

		mailBoxType = (Spinner) view
				.findViewById(R.id.gip_12345_iwantmail_spinner_type);

		// 信箱分类适配器实例
		// MyAryAdapter mailBoxType_Spinner_adapter = new MyAryAdapter(context,
		// android.R.layout.simple_spinner_item, mailBoxTypes);

		// mailBoxType_Spinner_adapter
		// .setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);
		// //设置信箱分类下拉框适配器
		// mailBoxType.setAdapter(mailBoxType_Spinner_adapter);
		mailBoxType.setVisibility(View.VISIBLE);

		partLeaderMailBoxSpinner = (Spinner) view
				.findViewById(R.id.gip_12345_iwantmail_spinner_leaderbox);

		loadMailTypeData();
		loadDeptData();

		title_editText = (EditText) view
				.findViewById(R.id.gip_12345_iwantmail_editText_title);
		content_editText = (EditText) view
				.findViewById(R.id.gip_12345_iwantmail_editText_content);

		send = (ImageButton) view
				.findViewById(R.id.gip_12345_iwantmail_imageBtn_send);

		mailType_radioGroup.setOnCheckedChangeListener(this);
		isOpen_radioGroup.setOnCheckedChangeListener(this);
		isReplyMail_radioGroup.setOnCheckedChangeListener(this);
		isReplyMsg_radioGroup.setOnCheckedChangeListener(this);

		send.setOnClickListener(this);

		mailBoxType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int p,
					long arg3) {
				myLetter.setDoprojectid(String.valueOf((p + 1)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	/**
	 * @方法： showDept
	 * @描述： 显示部门数据
	 */
	private void showDept() {
		// PartLeaderMailWrapper mailWrapper = new PartLeaderMailWrapper();
		// PartLeaderMail leaderMail = mailWrapper.new PartLeaderMail();
		// leaderMail.setDepid("0");
		// leaderMail.setDepname(DEFAULT_DEPT_FIFTER);
		// depts.add(0, leaderMail);

		DeptAdapter partment_Spinner_adapter = new DeptAdapter();

		partLeaderMailBoxSpinner.setAdapter(partment_Spinner_adapter);
		partLeaderMailBoxSpinner
				.setOnItemSelectedListener(partment_Spinner_adapter);

	}

	/**
	 * @方法： showMailType
	 * @描述： 显示信件类型数据
	 */
	private void showMailType() {
		MailTypeAdapter adapter = new MailTypeAdapter(context, mailTypes);

		mailBoxType.setAdapter(adapter);
	}

	/**
	 * @方法： loadDeptData
	 * @描述： 加载部门领导信箱下拉框数据
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.gip_12345_iwantmail_imageBtn_send:

			myLetter.setAccess_token(Constants.SharepreferenceKey.TEST_ACCESSTOKEN);
			myLetter.setTitle(title_editText.getText().toString());
			myLetter.setContent(content_editText.getText().toString());
			try {
				if (myLetter.getTitle().equals("")) {
					Toast.makeText(context, "信件标题不能为空", Toast.LENGTH_SHORT)
							.show();
				} else if (myLetter.getContent().equals("")) {
					Toast.makeText(context, "信件内容不能为空", Toast.LENGTH_SHORT)
							.show();
				} else {
					submitMyLetter();
				}
			} catch (NetException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NODataException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * @方法： submitMyLetter
	 * @描述： 提交用户写的信件
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	private void submitMyLetter() throws NetException, JSONException,
			NODataException {

		new Thread(new Runnable() {

			@Override
			public void run() {
				LetterService service = new LetterService(context);
				try {

					boolean isSecces = service.submitMyLetter(myLetter);
					if (isSecces) {
						handler.sendEmptyMessage(SEND_SUCCESS);
					} else {
						handler.sendEmptyMessage(SEND_FAILED);
					}

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

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.gip_12345_iwantmail_radiobutton_mayorbox:
			myLetter.setDoprojectid(DOPROJECTID_MAYORBOX);
			break;
		case R.id.gip_12345_iwantmail_radiobutton_suggestAndComplaint:
			myLetter.setDoprojectid(DOPROJECTID_SUGGEST_AND_COMPLAINT);
			break;

		case R.id.gip_12345_iwantmail_radiobutton_open:
			myLetter.setOpenState(open);
			break;
		case R.id.gip_12345_iwantmail_radiobutton_notOpen:
			myLetter.setOpenState(notopen);
			break;

		case R.id.gip_12345_iwantmail_radiobutton_notNeedMail:
			myLetter.setSentMailBack(notreplyMail);
			break;
		case R.id.gip_12345_iwantmail_radiobutton_needMail:
			myLetter.setSentMailBack(replyMail);
			break;

		case R.id.gip_12345_iwantmail_radiobutton_needMsg:
			myLetter.setMsgStatus(replyMsg);
			break;
		case R.id.gip_12345_iwantmail_radiobutton_notNeedMsg:
			myLetter.setMsgStatus(notreplyMsg);
			break;
		}
	}

	/**
	 * @类名： DeptAdapter
	 * @描述： 部门下拉框适配器类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-2 下午3:15:48
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
			viewHolder.tv_dept.setTextSize(8);
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

	/**
	 * @类名： MyAryAdapter
	 * @描述： 信箱分类下拉框适配器类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-2 下午3:29:55
	 * @修改时间：
	 * @修改描述：
	 * 
	 */
	public class MyAryAdapter extends ArrayAdapter<String> {

		Context context;
		String[] items = new String[] {};

		public MyAryAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);

			this.items = objects;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);

			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);

			return convertView;
		}

	}

}
