package com.wuxi.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.FragmentManagers;
import com.wuxi.app.R;

public class MainSearchFragment extends BaseFragment implements OnClickListener{

	private Button button;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_search_fragment_layout, null);
		button = (Button) view.findViewById(R.id.btn);
		button.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("点击了按钮...跳转到测试fragment");
		managers.IntentFragment(new TestFragment());
	}

	@Override
	public void setManagers(FragmentManagers managers) {
		// TODO Auto-generated method stub
		super.setManagers(managers);
	}
	
	
	
	
	
	
}
