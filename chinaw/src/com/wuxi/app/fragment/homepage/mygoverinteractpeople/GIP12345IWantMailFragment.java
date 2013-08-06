package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.MyLetter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 12345来信办理平台 主Fragment --我要写信 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345IWantMailFragment extends RadioButtonChangeFragment {
	private int contentType=0;   //内容类型，缺省为0-我要写信  1-写信须知   2-办理规则
	protected static final int HIDEN_CONTENT_ID=R.id.gip_12345_iwantmail_content_framelayout;
	private LinearLayout defaultLayout;

	private final static int SEND_SUCCESS=1;
	private final static int SEND_FAILED=0;


	private MyLetter myLetter;

	RadioGroup mailType_radioGroup;
	public final String doprojectid_mayorBox = "6b8e124e-1e5c-4a11-8dd3-c6623c809eff"; // 市长信箱
	public final String doprojectid_suggestAndComplaint = "bfffa273-086a-47cb-a7a8-7ae8140550db"; // 市长信箱

	RadioGroup isOpen_radioGroup;
	public final int open = 1;
	public final int notopen = 0;

	RadioGroup isReplyMail_radioGroup;
	public final int replyMail = 1;
	public final int notreplyMail = 0;

	RadioGroup isReplyMsg_radioGroup;
	public final int replyMsg = 1;
	public final int notreplyMsg = 0;

	Spinner mailBoxType;

	EditText title_editText;
	EditText content_editText;

	ImageButton upload;
	ImageButton send;


	private final int[] radioButtonIds = {

			R.id.gip_12345_iwantmail_radioButton_iwantmail,
			R.id.gip_12345_iwantmail_radioButton_mustKonwMail,
			R.id.gip_12345_iwantmail_radioButton_mayorBoxRule };

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
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


	public class OnClick implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {

			case R.id.gip_12345_iwantmail_imageBtn_upload:
				Toast.makeText(context, "上传附件（功能暂未实现）", 1000).show();
				break;
			case R.id.gip_12345_iwantmail_imageBtn_send:
				myLetter.setAccess_token(Constants.SharepreferenceKey.TEST_ACCESSTOKEN);
				myLetter.setTitle(title_editText.getText().toString());
				myLetter.setContent(content_editText.getText().toString());
				try {
					submitMyLetter();
				} catch (NetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}

	}



	public void submitMyLetter() throws NetException, JSONException, NODataException{

		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LetterService service=new LetterService(context);
				try {
					if(service.submitMyLetter(myLetter)){
						handler.sendEmptyMessage(SEND_SUCCESS);					
					}						
					else {
						handler.sendEmptyMessage(SEND_FAILED);	
					}

				} catch (NetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}}).start();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_iwantmail_radioButton_iwantmail:
			contentType=0;
			defaultLayout.setVisibility(View.VISIBLE);
			init();
			break;

		case R.id.gip_12345_iwantmail_radioButton_mustKonwMail:
			contentType=1;
			defaultLayout.setVisibility(View.GONE);
			changeContent(contentType);
			break;

		case R.id.gip_12345_iwantmail_radioButton_mayorBoxRule:
			contentType=2;
			defaultLayout.setVisibility(View.GONE);
			changeContent(contentType);
			break;

		case R.id.gip_12345_iwantmail_radiobutton_mayorbox:
			myLetter.setDoprojectid(doprojectid_mayorBox);
			break;
		case R.id.gip_12345_iwantmail_radiobutton_suggestAndComplaint:
			myLetter.setDoprojectid(doprojectid_suggestAndComplaint);
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

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_12345_iwantmail_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_12345_iwantmail_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		// TODO Auto-generated method stub
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

		defaultLayout=(LinearLayout) view
				.findViewById(R.id.gip_12345_iwantmail_layout_iwantmail);




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

		upload = (ImageButton) view
				.findViewById(R.id.gip_12345_iwantmail_imageBtn_upload);
		send = (ImageButton) view
				.findViewById(R.id.gip_12345_iwantmail_imageBtn_send);

		mailType_radioGroup.setOnCheckedChangeListener(this);
		isOpen_radioGroup.setOnCheckedChangeListener(this);
		isReplyMail_radioGroup.setOnCheckedChangeListener(this);
		isReplyMsg_radioGroup.setOnCheckedChangeListener(this);

		upload.setOnClickListener(new OnClick());
		send.setOnClickListener(new OnClick());

		mailBoxType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int p,
					long arg3) {
				// TODO Auto-generated method stub
				myLetter.setDoprojectid(String.valueOf((p + 1)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void changeContent(int type){
		GoverInterPeopleWebFragment goverInterPeopleWebFragment=new GoverInterPeopleWebFragment();
		if(type==1){			
			goverInterPeopleWebFragment.setUrl("http://www.wuxi.gov.cn/zmhd/6148280.shtml");
			bindFragment(goverInterPeopleWebFragment);
		}
		else if(type==2){
			goverInterPeopleWebFragment.setUrl("http://www.wuxi.gov.cn/zmhd/6148283.shtml");
			bindFragment(goverInterPeopleWebFragment);
		}
		else{
			init();
		}
	}

	private void bindFragment(Fragment fragment) {		
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commit();	
	}
}
