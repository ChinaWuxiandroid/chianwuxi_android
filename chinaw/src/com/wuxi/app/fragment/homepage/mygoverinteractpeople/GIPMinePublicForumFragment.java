package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.R;
import com.wuxi.app.util.GIPRadioButtonStyleChange;


/**
 * 我的政民互动  主Fragment --公众论坛  fragment
 * @author 杨宸 智佳
 * */


public class GIPMinePublicForumFragment extends GoverInterPeopleMineFragment
implements OnCheckedChangeListener{

	public View view = null;

	protected LayoutInflater mInflater;
	private RadioGroup radioGroup;
	private final  int[] radioButtonIds={
			R.id.gip_mine_publicforum_radioButton_myTheme,
			R.id.gip_mine_publicforum_radioButton_themeTJoin,
			R.id.gip_mine_publicforum_radioButton_collectTheme,
			R.id.gip_mine_radioButton_suggestionPlatform
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_mine_publicforum_layout, null);
		mInflater = inflater;
		context = getActivity();

		radioGroup = (RadioGroup) view.findViewById(R.id.gip_mine_publicforum_radioGroup);
		radioGroup.setOnCheckedChangeListener(this);

		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange
		=new GIPRadioButtonStyleChange(R.drawable.gip_button_selected_bk,
				0,Color.WHITE,R.color.gip_second_frame_button_brown);

		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.gip_mine_publicforum_radioButton_myTheme:
			//			init();
			break;

		case R.id.gip_mine_publicforum_radioButton_themeTJoin:	
			break;
		case R.id.gip_mine_publicforum_radioButton_collectTheme:	
			break;
		case R.id.gip_mine_radioButton_suggestionPlatform:	
			break;
		}
	}
}
