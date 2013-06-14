package com.wuxi.app.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

public class SecondLevelFragment extends BaseFragment implements
		OnClickListener {

	private View view;

	private Button back_btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.secondlevel_gridview_layout, null);
		back_btn = (Button) view.findViewById(R.id.secondlevel_back_btn);
		back_btn.setOnClickListener(this);
		return view;
	}

	public void setParame(Object... objects) {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.secondlevel_back_btn:
			managers.BackPress(this);
			break;

		default:
			break;
		}
	}

}