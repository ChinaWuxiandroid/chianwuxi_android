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
 * 我的政民互动  主Fragment --征求意见平台  fragment
 * @author 杨宸 智佳
 * */

public class GIPMineSuggestionPlatformFragment extends GoverInterPeopleMineFragment
implements OnCheckedChangeListener{
	public View view = null;

	protected LayoutInflater mInflater;
	private RadioGroup radioGroup;
	private final  int[] radioButtonIds={
			R.id.gip_mine_suggestion_radioButton_internetsurvy,
			R.id.gip_mine_suggestion_radioButton_peoplewill,
			R.id.gip_mine_suggestion_radioButton_lawwill
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_mine_suggestionplatform_layout, null);
		mInflater = inflater;
		context = getActivity();

		radioGroup = (RadioGroup) view.findViewById(R.id.gip_mine_suggestion_radioGroup);
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

		case R.id.gip_mine_suggestion_radioButton_internetsurvy:
			//			init();
			break;

		case R.id.gip_mine_suggestion_radioButton_peoplewill:	
			break;
		case R.id.gip_mine_suggestion_radioButton_lawwill:	
			break;
		}
	}
}
