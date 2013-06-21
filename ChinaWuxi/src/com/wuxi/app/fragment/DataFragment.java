package com.wuxi.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wuxi.app.util.LeftMenuData;

public class DataFragment extends Fragment {
	public static DataFragment Instance(int index){
		DataFragment fragment=new DataFragment();
		  
		Bundle args = new Bundle();
		args.putInt("index", index);
		
		fragment.setArguments(args);
		return fragment;
				
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(container==null){
			return null;
		}
		
		ScrollView scroller = new ScrollView(getActivity());
		TextView text = new TextView(getActivity());
		
		scroller.addView(text);
		text.setText(LeftMenuData.DATA[getArguments().getInt("index")]);
		text.setTextSize(18);
		
		return scroller;
		
	}

}
