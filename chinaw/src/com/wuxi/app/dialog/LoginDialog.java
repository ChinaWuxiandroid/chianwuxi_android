package com.wuxi.app.dialog;

import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.Constants.FragmentName;

/**
 * 
 * @author wanglu 泰得利通 登录对话框
 * 
 */
public class LoginDialog {

	private Context context;
	private BaseSlideFragment baseSlideFragment;
	private SharedPreferences sp;

	public LoginDialog(Context context, BaseSlideFragment baseSlideFragment) {
		this.context = context;
		this.baseSlideFragment = baseSlideFragment;

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

	public String getAccessToken() {
		sp = context
				.getSharedPreferences(
						Constants.SharepreferenceKey.SHARE_CONFIG,
						Context.MODE_PRIVATE);

		String accessToken = sp.getString(
				Constants.SharepreferenceKey.ACCESSTOKEN, "");
		return accessToken;

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
				baseSlideFragment.slideLinstener.replaceFragment(null, -1,
						FragmentName.LOGIN_FRAGMENT, null);//转向登录界面

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
