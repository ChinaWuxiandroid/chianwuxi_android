package com.wuxi.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.wuxi.app.engine.UpdateInfoService;
import com.wuxi.domain.UpdateInfo;

/**
 * 
 * @author wanglu 泰得利通 app管理类
 * 
 */
public class AppManager {

	private static AppManager instance;
	private Context context;

	private AppManager() {
	}

	public synchronized static  AppManager getInstance(Context context) {

		if (instance == null) {
			instance = new AppManager();
			instance.context = context;
		}

		return instance;

	}

	/**
	 * 
	 * wanglu 泰得利通 获取软件版本
	 * 
	 * @return 软件版本号
	 */
	public String getVersion() {
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
	 * wanglu 泰得利通 检查软件是否要更新
	 * 
	 * @return true更新，false不需要更新
	 */
	public boolean isUpdate(UpdateInfo updateInfo) {

		String oldVerson = getVersion();
		UpdateInfoService updateInfoService = new UpdateInfoService(context);
		try {
			UpdateInfo  newUpdateInfo = updateInfoService
					.getUpdateInfo(R.string.updateurl);
			if (!newUpdateInfo.getVersion().equals(oldVerson)) {
				if(updateInfo!=null){
					updateInfo.setUrl(newUpdateInfo.getUrl());
					updateInfo.setDescription(newUpdateInfo.getDescription());
					updateInfo.setVersion(newUpdateInfo.getVersion());
				}
				
				return true;
			} else {

				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
