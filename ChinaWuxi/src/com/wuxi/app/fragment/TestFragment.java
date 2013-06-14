package com.wuxi.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

public class TestFragment extends BaseFragment implements OnClickListener {

	Button button;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.testfragment, null);
		button = (Button) view.findViewById(R.id.back_btn);
		button.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.managers.BackPress(this);
		System.out.println("点击了返回》》》》》》");
	}
	
	
}
