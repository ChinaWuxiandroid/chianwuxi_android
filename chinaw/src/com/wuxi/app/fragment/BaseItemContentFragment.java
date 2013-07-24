package com.wuxi.app.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.wuxi.app.R;


/**
 * 各种新闻条目点击进入后的  新闻内容页面的头部Fragment  所有内容Fragment都应继承该类
 * @author 杨宸 智佳
 * */
public abstract class BaseItemContentFragment extends BaseSlideFragment{
	
	protected ImageView setting_btn, share_btn, download_btn;
		
	@Override
	protected int getLayoutId() {
		
		return getContentLayoutId();
	}

	@Override
	protected String getTitleText() {
		
		return getContentTitleText();
	}
	
	protected abstract int getContentLayoutId();
	
	protected abstract String getContentTitleText();
	
	@Override
	public void initBtn() {
		super.initBtn();
		setting_btn = (ImageView) view.findViewById(R.id.content_setting);
		share_btn = (ImageView) view.findViewById(R.id.content_share);
		download_btn = (ImageView) view.findViewById(R.id.content_download);
				
		setting_btn.setOnClickListener(SettingClick);
		share_btn.setOnClickListener(ShareClick);
		download_btn.setOnClickListener(DownloadClick);
		
	}
	
	private OnClickListener SettingClick = new OnClickListener() {

		@Override
		public void onClick(View v) {		
			Toast.makeText(context, "设置，该功能暂未实现", 1000).show();
		}
	};
		
	private OnClickListener ShareClick = new OnClickListener() {

		@Override
		public void onClick(View v) {		
			Toast.makeText(context, "分享，该功能暂未实现", 1000).show();
		}
	};
		
	private OnClickListener DownloadClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(context, "下载，该功能暂未实现", 1000).show();
		}
	};

}
