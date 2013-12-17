package com.wuxi.app.activity.homepage.more;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.AppManager;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.engine.DownLoadTask;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.app.util.TextFormateUtil;
import com.wuxi.domain.UpdateInfo;

/**
 * 
 * @author wanglu 泰得利通 系统设置
 */
public class SystemSetActivity extends BaseSlideActivity implements
		OnClickListener {

	protected static final int UPDATE_APK = 0;

	protected static final int NO_UPDATE_APK = 1;

	private static final int DOWLOAD_ERROR = 2;

	private TableRow sys_menu_set, sys_clear_cache, sys_software_update,
			sys_about_us, sys_site_map;

	private TextView tv_verison, tv_cache;
	private Button soft_btn_login_out;

	private UpdateInfo updateInfo;

	private ProgressDialog pd;

	private boolean isLoading = false;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case UPDATE_APK:
				showUpdatDialog();
				isLoading = false;
				break;
			case NO_UPDATE_APK:
				Toast.makeText(SystemSetActivity.this, "没有要升级的版本",
						Toast.LENGTH_SHORT).show();
				isLoading = false;
				break;
			case DOWLOAD_ERROR:
				Toast.makeText(SystemSetActivity.this, "下载出错",
						Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	@Override
	protected void findMainContentViews(View view) {

		tv_cache = (TextView) view.findViewById(R.id.tv_cache);
		tv_cache.setText(getCacheSize());// 获取缓存文件大小

		sys_menu_set = (TableRow) view.findViewById(R.id.sys_menu_set);
		sys_clear_cache = (TableRow) view.findViewById(R.id.sys_clear_cache);

		sys_software_update = (TableRow) view
				.findViewById(R.id.sys_software_update);
		sys_about_us = (TableRow) view.findViewById(R.id.sys_about_us);
		sys_site_map = (TableRow) view.findViewById(R.id.sys_site_map);

		soft_btn_login_out = (Button) view
				.findViewById(R.id.soft_btn_login_out);
		soft_btn_login_out.setOnClickListener(this);

		tv_verison = (TextView) view.findViewById(R.id.tv_verison);

		sys_menu_set.setOnClickListener(this);

		sys_clear_cache.setOnClickListener(this);

		sys_software_update.setOnClickListener(this);
		sys_about_us.setOnClickListener(this);
		sys_site_map.setOnClickListener(this);

		tv_verison.setText(AppManager.getInstance(this).getVersion());
		initLoginUser();

		pd = new ProgressDialog(this);

		pd.setMessage("正在下载");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.index_sytem_set;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.sys_menu_set:// 常用栏设置

			intent = new Intent(SystemSetActivity.this,
					MenuItemSetActivity.class);
			break;

		case R.id.sys_software_update:// 软件升级
			checkUpdate();
			break;
		case R.id.sys_about_us:// 关于我们

			intent = new Intent(SystemSetActivity.this, AboutUsActivity.class);
			break;
		case R.id.sys_clear_cache:// 清楚缓存
			clearCache();
			break;
		case R.id.sys_site_map:// 网站地图

			intent = new Intent(SystemSetActivity.this, SiteMapActivity.class);
			break;

		case R.id.soft_btn_login_out:// 注销
			if (SystemUtil.getLoginUser(this).equals("")) {
				Toast.makeText(this, "您还未登录 ", Toast.LENGTH_SHORT).show();
				return;
			}

			SystemUtil.logout(this);
			Toast.makeText(this, "注销成功", Toast.LENGTH_SHORT).show();
			initLoginUser();
			break;

		}

		if (intent != null) {
			MainTabActivity.instance.addView(intent);
		}
	}

	private void initLoginUser() {
		String loginUser = SystemUtil.getLoginUser(this);
		if (!loginUser.equals("")) {

		} else {

			soft_btn_login_out.setText("未登录");
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 清楚缓存
	 */
	private void clearCache() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			AlertDialog.Builder builder = new Builder(this);
			builder.setIcon(R.drawable.logo);
			builder.setTitle("提示");
			builder.setMessage("确认要清除缓存吗？");
			builder.setCancelable(false);

			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							AppManager.getInstance(SystemSetActivity.this)
									.clearCacheFile(false);// 清楚菜单和频道的缓存

							Toast.makeText(SystemSetActivity.this, "清除完成",
									Toast.LENGTH_SHORT).show();
							tv_cache.setText(getCacheSize());// 获取缓存文件大小

						}
					});

			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}

					});

			builder.create().show();

		} else {
			Toast.makeText(SystemSetActivity.this, "SDK不存在", Toast.LENGTH_SHORT)
					.show();
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 获取缓存大小
	 * 
	 * @return
	 */
	private String getCacheSize() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(Constants.APPFiles.CACHE_FILE_PATH);
			File cacheFiles[] = file.listFiles();

			long totalByte = 0;
			for (File cacheFile : cacheFiles) {
				totalByte += cacheFile.length();
			}

			return TextFormateUtil.getDataSize(totalByte) + " ";

		}

		return "0M";

	}

	@Override
	protected String getTitleText() {

		return "系统设置";
	}

	/**
	 * 
	 * wanglu 泰得利通 安装APK
	 * 
	 * @param file
	 */
	private void install(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		this.finish();
		startActivity(intent);

	}

	/**
	 * 监测更新 wanglu 泰得利通
	 */
	private void checkUpdate() {
		if (isLoading) {
			return;
		}
		isLoading = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				updateInfo = new UpdateInfo();
				boolean update = AppManager.getInstance(SystemSetActivity.this)
						.isUpdate(updateInfo);
				if (update) {
					handler.sendEmptyMessage(UPDATE_APK);
				} else {
					handler.sendEmptyMessage(NO_UPDATE_APK);
				}
			}
		}).start();
	}

	/**
	 * 更新对话框 wanglu 泰得利通
	 */
	private void showUpdatDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.logo);
		builder.setTitle("更新消息");
		builder.setMessage("版本" + updateInfo.getVersion() + " 更新信息:"
				+ updateInfo.getDescription());
		builder.setCancelable(false);

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {

							File file = new File(
									Constants.APPFiles.DOWNLOAF_FILE_PATH);
							if (!file.exists()) {
								file.mkdirs();
							}
							DownLoadThreadTask dowTask = new DownLoadThreadTask(
									updateInfo.getUrl(),
									Constants.APPFiles.DOWNLOAF_FILE_PATH
											+ "chinawuxi.apk");

							new Thread(dowTask).start();
							pd.show();

						} else {
							Toast.makeText(SystemSetActivity.this, "SDK不存在",
									Toast.LENGTH_SHORT).show();

						}

					}
				});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				});

		builder.create().show();

	}

	private class DownLoadThreadTask implements Runnable {

		private String path;

		private String filePath;

		public DownLoadThreadTask(String path, String filePath) {

			this.path = path;
			this.filePath = filePath;
		}

		@Override
		public void run() {

			try {
				File file = DownLoadTask.dowLoadNewSoft(path, filePath, pd);
				pd.dismiss();
				install(file);
			} catch (Exception e) {

				e.printStackTrace();
				handler.sendEmptyMessage(DOWLOAD_ERROR);
				pd.dismiss();

			}

		}

	}

}
