package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.util.GIPRadioButtonStyleChange;
import com.wuxi.domain.ApplyDept;

/**
 * @类名： GoverMsgApplyTableFragment
 * @描述： 依申请公开 申请界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-27 下午2:13:36
 * @修改时间： 
 * @修改描述：
 */
public class GoverMsgApplyTableFragment extends BaseFragment implements OnCheckedChangeListener,OnClickListener{
	
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;

	private ImageView back_imgview;
	private RadioGroup tablecontent_raidogroup;
	private RadioButton citizen_btn,legalPerson_btn;

	private static final int[] radioButtonIds={R.id.govermsg_applytable_radioButton_citizen,R.id.govermsg_applytable_radioButton_legalperson};
	private static final int TALBE_CONTENT_ID=R.id.govermsg_applytable_content_framelayout;
	
	private ApplyDept applyDept;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.govermsg_applytable_layout, null);
		mInflater = inflater;
		context = getActivity();
		
		Bundle bundle=this.getArguments();
		if(bundle!=null){
			applyDept=(ApplyDept) bundle.get("applyDept");
		}

		initView();
		return view;
	}

	public void initView(){
		back_imgview=(ImageView)view.findViewById(R.id.govermsg_applytable_back_btn);
		tablecontent_raidogroup=(RadioGroup)view.findViewById(R.id.govermsg_applytable_radioGroup);

		back_imgview.setOnClickListener(this);
		tablecontent_raidogroup.setOnCheckedChangeListener(this);

		loadCitizenTable();
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view, radioButtonIds, checkedId);

		switch(checkedId){
		case R.id.govermsg_applytable_radioButton_citizen:
			loadCitizenTable();
			break;
		case R.id.govermsg_applytable_radioButton_legalperson:
			loadLegalPersonTable();
			break;
		}
	}

	public void loadCitizenTable(){
		GoverMsgApplyCitizenTableFragment goverMsgApplyCitizenTableFragment=new GoverMsgApplyCitizenTableFragment();
		goverMsgApplyCitizenTableFragment.setDept(applyDept);
		onTransaction(goverMsgApplyCitizenTableFragment);
	}

	public void loadLegalPersonTable(){
		GoverMsgApplyLePersonTableFragment goverMsgApplyLePersonTableFragment=new GoverMsgApplyLePersonTableFragment();
		goverMsgApplyLePersonTableFragment.setDept(applyDept);
		onTransaction(goverMsgApplyLePersonTableFragment);
	}

	/*
	 * replaceFragment
	 * */
	protected void onTransaction(BaseFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(TALBE_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/*
	 * romoveFragment
	 * */
	protected void onRomove() {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.remove(GoverMsgApplyTableFragment.this);
		ft.commitAllowingStateLoss();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.govermsg_applytable_back_btn:
			onRomove();
			break;
		}

	}
}
