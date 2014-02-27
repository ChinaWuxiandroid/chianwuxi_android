package com.wuxi.app.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.popwin.SharePopWindow;

/**
 * 
 * @author wanglu 泰得利通
 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00 使用与内容页，头部有分晓，设置，列表按钮的内容页的基类
 * 
 */
public abstract class BaseItemContentActivity extends BaseSlideActivity {

	protected ImageView setting_btn, share_btn, download_btn;
	protected RelativeLayout rl_down, rl_setting, rl_search_share;
	private SharePopWindow sharePopWindow;

	@Override
	protected int getLayoutId() {

		return getContentLayoutId();
	}

	@Override
	protected void findMainContentViews(View view) {
		rl_down = (RelativeLayout) view.findViewById(R.id.rl_down);
		rl_setting = (RelativeLayout) view.findViewById(R.id.rl_setting);
		rl_search_share = (RelativeLayout) view
				.findViewById(R.id.rl_search_share);

		setting_btn = (ImageView) view.findViewById(R.id.content_setting);
		share_btn = (ImageView) view.findViewById(R.id.content_share);
		download_btn = (ImageView) view.findViewById(R.id.content_download);

		rl_search_share.setOnClickListener(ShareClick);
		setting_btn.setOnClickListener(SettingClick);

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
			Toast.makeText(BaseItemContentActivity.this, "设置，该功能暂未实现",
					Toast.LENGTH_SHORT).show();
		}
	};

	private OnClickListener ShareClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (sharePopWindow != null) {
				sharePopWindow.dissmissPopWindow();
				sharePopWindow = null;
			} else {
				sharePopWindow = new SharePopWindow(
						BaseItemContentActivity.this);
				int location[] = new int[2];
				v.getLocationInWindow(location);
				sharePopWindow.showPopWindow(v, location[0], location[1] - 30);

			}

		}
	};

	@Override
	protected void onBack() {

		super.onBack();

		if (sharePopWindow != null) {
			sharePopWindow.dissmissPopWindow();
			sharePopWindow = null;
		}
	}

	private OnClickListener DownloadClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(BaseItemContentActivity.this, "下载，该功能暂未实现",
					Toast.LENGTH_SHORT).show();
		}
	};

}
