package com.wuxi.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.listeners.SlideLinstener;

public abstract class BaseSlideFragment extends BaseFragment {

	protected View view ;
	public SlideLinstener slideLinstener;
	protected ImageView opearn_btn, member_btnm, back_btn;
	public TextView Title_text;
	protected LayoutInflater mInflater;
	protected Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(getLayoutId(), null);
		this.mInflater=inflater;
		context=getActivity();
		initUI();
		initBtn();
		
		
		return view;
	}
	
	public void initUI(){
		
	}

	/**
	 * 
	 * wanglu 泰得利通 返回布局文件ID，该布局文件头部必须有菜单按钮，回退按钮，及登录按钮 子类必须实现
	 * 
	 * @return
	 */
	protected abstract int getLayoutId();

	public void initBtn() {
		opearn_btn = (ImageView) view.findViewById(R.id.open_close_left_btn);
		member_btnm = (ImageView) view.findViewById(R.id.member_btn);
		Title_text = (TextView) view.findViewById(R.id.Title_Text);
		back_btn = (ImageView) view.findViewById(R.id.back_btn);
		opearn_btn.setOnClickListener(LeftClick);
		member_btnm.setOnClickListener(MemberClick);
		back_btn.setOnClickListener(BackClick);
		
		Title_text.setText(getTitleText());
	}

	/**
	 * 
	 *wanglu 泰得利通 
	 *标题 子类实现
	 * @return
	 */
	protected abstract  String getTitleText() ;
	

	private OnClickListener LeftClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			OpenOrCloseMenu(v);
		}
	};
	private OnClickListener MemberClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MemberMenu(v);
		}
	};

	private OnClickListener BackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			onBack();
		}
	};

	public void onBack(){
		slideLinstener.onBack();
	}
	public void OpenOrCloseMenu(View souce) {
		slideLinstener.OpearnLeft();
	}

	public void MemberMenu(View souce) {
		slideLinstener.OpearnRight();
	}

	public void setFragment(SlideLinstener slideLinstener) {
		this.slideLinstener = slideLinstener;
	}
}
