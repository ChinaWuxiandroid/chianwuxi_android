package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.MyLetter;
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

	protected View view = null;
	protected LayoutInflater mInflater = null;
	private Context context = null;

	private final static int SEND_SUCCESS = 1;
	private final static int SEND_FAILED = 0;

	private MyLetter myLetter = null;

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

		view.findViewById(R.id.gip_12345_iwantmail_radiobutton_leaderbox)
		.setVisibility(View.GONE);
		
		mailBoxType = (Spinner) view
				.findViewById(R.id.gip_12345_iwantmail_spinner_type);

		ArrayAdapter mailBoxType_Spinner_adapter = ArrayAdapter
				.createFromResource(context, R.array.mailBoxType,
						R.layout.my_spinner_small_item);
		mailBoxType_Spinner_adapter
				.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);
		mailBoxType.setAdapter(mailBoxType_Spinner_adapter);
		mailBoxType.setVisibility(View.VISIBLE);

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
					if (service.submitMyLetter(myLetter)) {
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
}
