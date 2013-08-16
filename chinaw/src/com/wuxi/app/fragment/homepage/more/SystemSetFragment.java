package com.wuxi.app.fragment.homepage.more;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.DownLoadTask;
import com.wuxi.app.engine.UpdateInfoService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.TextFormateUtil;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.UpdateInfo;

/**
 * 
 * @author wanglu 泰得利通 系统设置
 */
public class SystemSetFragment extends BaseSlideFragment implements
		OnClickListener {

	protected static final int UPDATE_APK = 0;
	protected static final int NO_UPDATE_APK = 1;
	private static final int DOWLOAD_ERROR = 2;
	private static final String TAG = "SystemSetFragment";
	private RelativeLayout sys_menu_set, sys_clear_cache, sys_score, sys_share,
			sys_idea, sys_software_update, sys_about_us, sys_site_map,
			sys_use_help, sys_join_twiiter;
	private TextView tv_verison, tv_cache;
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
				Toast.makeText(context, "没有要升级的版本", Toast.LENGTH_SHORT).show();
				isLoading = false;
				break;
			case DOWLOAD_ERROR:
				Toast.makeText(context, "下载出错", Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	@Override
	public void initUI() {
		super.initUI();
		sys_menu_set = (RelativeLayout) view.findViewById(R.id.sys_menu_set);
		tv_cache = (TextView) view.findViewById(R.id.tv_cache);
		tv_cache.setText(getCacheSize());// 获取缓存文件大小
		sys_clear_cache = (RelativeLayout) view
				.findViewById(R.id.sys_clear_cache);

		// sys_score = (RelativeLayout) view.findViewById(R.id.sys_score);
		// sys_share = (RelativeLayout) view.findViewById(R.id.sys_share);
		// sys_idea = (RelativeLayout) view.findViewById(R.id.sys_idea);
		sys_software_update = (RelativeLayout) view
				.findViewById(R.id.sys_software_update);
		sys_about_us = (RelativeLayout) view.findViewById(R.id.sys_about_us);
		sys_site_map = (RelativeLayout) view.findViewById(R.id.sys_site_map);
		// sys_use_help = (RelativeLayout) view.findViewById(R.id.sys_use_help);
		// sys_join_twiiter = (RelativeLayout) view
		// .findViewById(R.id.sys_join_twiiter);
		tv_verison = (TextView) view.findViewById(R.id.tv_verison);
		sys_menu_set.setOnClickListener(this);

		sys_clear_cache.setOnClickListener(this);

		/*
		 * sys_score.setOnClickListener(this);
		 * sys_share.setOnClickListener(this);
		 * sys_idea.setOnClickListener(this);
		 */
		sys_software_update.setOnClickListener(this);
		sys_about_us.setOnClickListener(this);
		sys_site_map.setOnClickListener(this);
		/*
		 * sys_use_help.setOnClickListener(this);
		 * sys_join_twiiter.setOnClickListener(this);
		 */

		tv_verison.setText(getVersion());

		pd = new ProgressDialog(context);

		pd.setMessage("正在下载");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

	}

	@Override
	protected int getLayoutId() {
		return R.layout.index_sytem_set;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.sys_menu_set:// 常用栏设置

			super.slideLinstener.replaceFragment(null, -1,
					Constants.FragmentName.MENUITEMSET_FRAGMENT, null);
			break;

		case R.id.sys_software_update:// 软件升级
			checkUpdate();
			break;
		case R.id.sys_about_us:// 关于我们
			slideLinstener.replaceFragment(null, -1,
					Constants.FragmentName.ABOUTUSFRAGMENT, null);
			break;
		case R.id.sys_clear_cache:// 清楚缓存
			clearCache();
			break;
		case R.id.sys_site_map:// 网站地图
			slideLinstener.replaceFragment(null, -1,
					Constants.FragmentName.SITEMAP_FRAGMENT, null);

			break;

		}
	}

	/**
	 * 
	 * wanglu 泰得利通 清楚缓存
	 */
	private void clearCache() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			AlertDialog.Builder builder = new Builder(context);
			builder.setIcon(R.drawable.logo);
			builder.setTitle("提示");
			builder.setMessage("确认要清除缓存吗？");
			builder.setCancelable(false);

			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							File file = new File(
									Constants.APPFiles.CACHE_FILE_PATH);
							File cacheFiles[] = file.listFiles();
							for (File cacheFile : cacheFiles) {
								cacheFile.delete();
							}

							Toast.makeText(context, "清除完成", Toast.LENGTH_SHORT)
									.show();
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
			Toast.makeText(context, "SDK不存在", Toast.LENGTH_SHORT).show();
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

			return TextFormateUtil.getDataSize(totalByte);

		}

		return "0M";

	}

	@Override
	protected String getTitleText() {

		return "系统设置";
	}

	/**
	 * 
	 * wanglu 泰得利通 获取软件版本
	 * 
	 * @return
	 */
	private String getVersion() {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo paInfo = pm.getPackageInfo(context.getPackageName(), 0);
			return paInfo.versionName;
		} catch (NameNotFoundException e) {

			e.printStackTrace();
			return "未知版本";
		}

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
		this.getActivity().finish();
		startActivity(intent);

	}

	/**
	 * 
	 * wanglu 泰得利通 是否要更新
	 * 
	 * @return
	 */
	public boolean isUpdate() {

		String oldVerson = getVersion();
		UpdateInfoService updateInfoService = new UpdateInfoService(context);
		try {
			updateInfo = updateInfoService.getUpdateInfo(R.string.updateurl);
			if (!updateInfo.getVersion().equals(oldVerson)) {

				return true;
			} else {

				return false;
			}

		} catch (Exception e) {

			Toast.makeText(context, "监测更新出错", Toast.LENGTH_SHORT).show();

			e.printStackTrace();
			return false;
		}

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

				boolean update = isUpdate();
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
		AlertDialog.Builder builder = new Builder(context);
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
							Toast.makeText(context, "SDK不存在", 1).show();

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
				install(file);// ��װ�°汾���
			} catch (Exception e) {

				e.printStackTrace();
				handler.sendEmptyMessage(DOWLOAD_ERROR);
				pd.dismiss();

			}

		}

	}

}
