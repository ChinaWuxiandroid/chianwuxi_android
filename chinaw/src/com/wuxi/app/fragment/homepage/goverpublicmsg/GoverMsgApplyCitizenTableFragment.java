package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

public class GoverMsgApplyCitizenTableFragment extends BaseFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;
	
	//可选项
	private Spinner deptSpinnner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.onlineapply_citizentable_layout, null);
		mInflater = inflater;
		context = getActivity();
		
		initView();
		return view;
	}

	public void initView(){
	}
}
