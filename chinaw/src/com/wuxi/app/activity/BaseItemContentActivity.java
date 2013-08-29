package com.wuxi.app.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.wuxi.app.R;

/**
 * 
 * @author wanglu 泰得利通
 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00 使用与内容页，头部有分晓，设置，列表按钮的内容页的基类
 * 
 */
public abstract class BaseItemContentActivity extends BaseSlideActivity {

	protected ImageView setting_btn, share_btn, download_btn;

	@Override
	protected int getLayoutId() {

		return getContentLayoutId();
	}

	@Override
	protected void findMainContentViews(View view) {

		setting_btn = (ImageView) view.findViewById(R.id.content_setting);
		share_btn = (ImageView) view.findViewById(R.id.content_share);
		download_btn = (ImageView) view.findViewById(R.id.content_download);

		setting_btn.setOnClickListener(SettingClick);
		share_btn.setOnClickListener(ShareClick);
		download_btn.setOnClickListener(DownloadClick);

	}

	@Override
	protected String getTitleText() {

		return getContentTitleText();
	}

	protected abstract int getContentLayoutId();

	protected abstract String getContentTitleText();

	private OnClickListener SettingClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(
				BaseItemContentActivity.this, "设置，该功能暂未实现", Toast.LENGTH_SHORT)
					.show();
		}
	};

	private OnClickListener ShareClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(
				BaseItemContentActivity.this, "分享，该功能暂未实现", Toast.LENGTH_SHORT)
					.show();
		}
	};

	private OnClickListener DownloadClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(
				BaseItemContentActivity.this, "下载，该功能暂未实现", Toast.LENGTH_SHORT)
					.show();
		}
	};

}
