package com.wuxi.app.dialog;

import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.logorregister.LoginActivity;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;

/**
 * 
 * @author wanglu 泰得利通 登录对话框
 * 
 */
public class LoginDialog {

	private Context context;

	

	public LoginDialog(Context context) {
		this.context = context;
		

	}

	/**
	 * 
	 * wanglu 泰得利通 检查登录
	 * 
	 * @return
	 */
	public boolean checkLogin() {

		if (getAccessToken().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	private String getAccessToken() {
		return SystemUtil.getAccessToken(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 弹出对话框
	 */
	public void showDialog() {

		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.logo);
		builder.setTitle("提示");
		builder.setMessage("您还没有登录!");
		builder.setCancelable(false);
		File file = new File(Constants.APPFiles.DOWNLOAF_FILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(context, LoginActivity.class);
				MainTabActivity.instance.addView(intent);

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();

	}
}
