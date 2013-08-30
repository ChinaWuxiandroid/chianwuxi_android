package com.wuxi.app.fragment.homepage.mygoverinteractpeople;


import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

/**
 *12345来信办理平台   主Fragment 
 * @author 杨宸 智佳
 * */

public class GoverInterPeople12345Fragment extends RadioButtonChangeFragment {
	
	/**
	 * @字段： serialVersionUID
	 * @类型： long
	 * @描述： 序列化序号 
	 */
	private static final long serialVersionUID = 1L;
	
	private final  int[] radioButtonIds={
			R.id.goverinterpeople_12345_radioButton_mayorMailBox,
			R.id.goverinterpeople_12345_radioButton_complaint,
			R.id.goverinterpeople_12345_radioButton_partmentLeaderMailBox,
			R.id.goverinterpeople_12345_radioButton_countryMayorMailBox,
			R.id.goverinterpeople_12345_radioButton_hotMail,
			R.id.goverinterpeople_12345_radioButton_answerStatistics,
			R.id.goverinterpeople_12345_radioButton_iwantmail
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_12345_radioButton_mayorMailBox:
			init();
			break;

		case R.id.goverinterpeople_12345_radioButton_complaint:	
			MainMineFragment complaintFragment=new GIP12345ComplaintFragment();
			onTransaction(complaintFragment);
			break;

		case R.id.goverinterpeople_12345_radioButton_partmentLeaderMailBox:
			MainMineFragment partLeaderMailboxFragment=new GIP12345PartLeaderMailboxFragment();
			onTransaction(partLeaderMailboxFragment);
			break;

		case R.id.goverinterpeople_12345_radioButton_countryMayorMailBox:
			MainMineFragment cMayorMailBoxFragment=new GIP12345CMayorMailBoxFragment();
			onTransaction(cMayorMailBoxFragment);
			break;

		case R.id.goverinterpeople_12345_radioButton_hotMail:
			MainMineFragment hotMailFragment=new GIP12345HotMail();
			onTransaction(hotMailFragment);
			break;
		case R.id.goverinterpeople_12345_radioButton_answerStatistics:
			MainMineFragment answerStatisticsFragment=new GIP12345AnswerStatisticsFragment();
			onTransaction(answerStatisticsFragment);
			break;		
		case R.id.goverinterpeople_12345_radioButton_iwantmail:
			MainMineFragment iWantMailFragment=new GIP12345IWantMailFragment();
			onTransaction(iWantMailFragment);
			break;
		}

	}


	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_12345_layout;
	}


	@Override
	protected int getRadioGroupId() {
		return R.id.goverinterpeople_12345_radioGroup;
	}


	@Override
	protected int[] getRadioButtonIds() {
		return radioButtonIds;
	}


	@Override
	protected int getContentFragmentId() {
		return R.id.goverinterpeople_12345_content_fragment;
	}


	@Override
	protected void init() {
		MainMineFragment mayorBoxFragment=new GIP12345MayorMaiBoxFragment();
		onTransaction(mayorBoxFragment);
	}



}
