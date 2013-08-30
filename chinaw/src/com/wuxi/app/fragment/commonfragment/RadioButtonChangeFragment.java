package com.wuxi.app.fragment.commonfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

/**
 * 用于RadioButton切換的  fragment  ,目前用于我的政民互动里
 * @author 杨宸 智佳
 * */

public abstract class RadioButtonChangeFragment extends MainMineFragment
implements OnCheckedChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public View view = null;
	protected LayoutInflater mInflater;
	private RadioGroup radioGroup;
	private   int[] radioButtonIds;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(getLayoutId(), null);
		mInflater = inflater;
		context = getActivity();
		if(getRadioButtonIds()!=null){
			radioButtonIds=getRadioButtonIds();
			radioGroup = (RadioGroup) view.findViewById(getRadioGroupId());
			radioGroup.setOnCheckedChangeListener(this);
		}
		init();
		return view;
	}
	
	protected abstract int getLayoutId();
	
	protected abstract int getRadioGroupId();
	
	protected abstract int[] getRadioButtonIds();
	
	protected abstract int getContentFragmentId();

	protected abstract void init();
	
	/*
	 * 默认的是褐色风格的radioButton，因为蓝色风格的 重新方便，因为GIPRadioButtonStyleChange默认为蓝色风格的
	 * */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		GIPRadioButtonStyleChange radioButtonStyleChange
		=new GIPRadioButtonStyleChange(R.drawable.gip_button_selected_bk,
				0,Color.WHITE,R.color.gip_second_frame_button_brown);
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
	}
	
	/*
	 * replaceFragment
	 * */
	protected void onTransaction(MainMineFragment fragment) {
		if(getContentFragmentId()!=0){
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(getContentFragmentId(), fragment);
			ft.commitAllowingStateLoss();
		}	
	}

}
