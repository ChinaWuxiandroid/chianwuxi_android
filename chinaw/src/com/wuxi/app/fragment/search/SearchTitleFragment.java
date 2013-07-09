package com.wuxi.app.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.fragment.MainSearchFragment;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;

/**
 * 搜索栏目的title fragment
 * @author 杨宸 智佳
 * */
public class SearchTitleFragment extends MainSearchFragment{
	public View view = null;
	public SlideLevelFragment fragment;
	public ImageView OPearn_btn, Member_btnm,back_btn;
	public TextView Title_text;

	protected LayoutInflater mInflater;
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_title_layout, null);
		mInflater = inflater;
		context = getActivity();
		
		initView();
		return view;
	}
	
	public void initView() {  
		OPearn_btn = (ImageView) view.findViewById(R.id.search_open_close_left_btn);
		Member_btnm = (ImageView) view.findViewById(R.id.search_member_btn);
		Title_text = (TextView) view.findViewById(R.id.search_Title_Text);
		back_btn = (ImageView)view.findViewById(R.id.search_back_btn);
		OPearn_btn.setOnClickListener(LeftClick);
		Member_btnm.setOnClickListener(MemberClick);
		back_btn.setOnClickListener(BackClick);
	}

	private OnClickListener LeftClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			OpenOrCloseMenu(v);
		}
	};
	private OnClickListener MemberClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			MemberMenu(v);
		}
	};
	
	private OnClickListener BackClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			
		}
	};
	
	
	

	public void OpenOrCloseMenu(View souce) {
		 fragment.OpearnLeft();
	}

	public void MemberMenu(View souce) {
		 fragment.OpearnRight();
	}
	
	public void setFragmentTitle(String Title){
		Title_text.setText(Title);
	}

	public void setFragment(SlideLevelFragment fragment) {
		this.fragment = fragment;
	}
}
