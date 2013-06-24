package com.wuxi.app.fragment.index.type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.R;

public class PublicGoverMsgFragment extends BaseSlideFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_publicgovermsg_layout, null);
		InitBtn();
		setFragmentTitle("政府信息公开");
		return view;
	}
	
	
}
