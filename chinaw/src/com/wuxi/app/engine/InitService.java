package com.wuxi.app.engine;

import java.io.File;
import java.util.Calendar;

import com.wuxi.app.util.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

/**
 * 
 * @author wanglu 泰得利通 APP初始化业务类
 * 
 */
public class InitService extends Service {

	private SharedPreferences sp;

	public InitService(Context context) {
		super(context);
		sp = context
				.getSharedPreferences(
						Constants.SharepreferenceKey.SHARE_CONFIG,
						Context.MODE_PRIVATE);

	}

	/**
	 * 
	 * wanglu 泰得利通 初始化方法
	 */
	public void init() {

		clearCache();
	}

	/**
	 * 
	 * wanglu 泰得利通 清除缓存 超过3天以上清除缓存文件 每隔3天以上清除一下缓存
	 */
	public void clearCache() {
		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.DATE);
		long nowLoginTime = calendar.getTimeInMillis();
		Editor ed = sp.edit();
		long lastLoginTime = sp.getLong(
				Constants.SharepreferenceKey.LAST_LOGIN_TIME, 0);
		if (lastLoginTime != 0) {// 上次登录时间不为空

			long days = (nowLoginTime - lastLoginTime) / (1000 * 60 * 60 * 24);
			int between_day = Integer.parseInt(String.valueOf(days));
			if (between_day >= 3) {// 超过三天
				ed.putLong(Constants.SharepreferenceKey.LAST_LOGIN_TIME,
						nowLoginTime);// 存入登录时间
				ed.commit();
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					File file = new File(Constants.APPFiles.CACHE_FILE_PATH);
					if (file.exists()) {
						File cacheFiles[] = file.listFiles();
						for (File cacheFile : cacheFiles) {
							cacheFile.delete();
						}
					}

				}

			}

		} else {// 首次使用APP
			ed.putLong(Constants.SharepreferenceKey.LAST_LOGIN_TIME,
					nowLoginTime);// 存入登录时间
			ed.commit();

		}

	}
}
