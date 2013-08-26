package com.wuxi.app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.engine.InitService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.MainIndexFragment;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;
import com.wuxi.app.listeners.HomeTabChangListner;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.Constants.CacheKey;
import com.wuxi.app.util.SystemUtil;

/**
 * 主要架构
 * 
 * @author 方庆银
 * 
 */
public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener, OnClickListener, HomeTabChangListner {

	private RadioGroup radioGroup;

	public FragmentManagers fragmentManagers;
	private InitService initService;

	// private static final long BACK_PRESSED_INTERVAL_MILLIS = 1500;

	// private static final String TAG = "MainActivity";
	// private long mLastBackPressedTimeMillis = 0;
	private BaseSlideFragment currentBaseSlideFragment;

	private RadioButton main_tab_index, main_tab_search, main_tab_login_reg,
			main_tab_mine, main_tab_more;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main_fragment_layout);

		radioGroup = (RadioGroup) findViewById(R.id.main_tab_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);

		main_tab_index = (RadioButton) findViewById(R.id.main_tab_index);
		main_tab_search = (RadioButton) findViewById(R.id.main_tab_search);
		main_tab_login_reg = (RadioButton) findViewById(R.id.main_tab_login_reg);
		main_tab_mine = (RadioButton) findViewById(R.id.main_tab_mine);
		main_tab_more = (RadioButton) findViewById(R.id.main_tab_more);
		init();
		main_tab_index.setOnClickListener(this);
		main_tab_search.setOnClickListener(this);
		main_tab_login_reg.setOnClickListener(this);
		main_tab_mine.setOnClickListener(this);
		main_tab_more.setOnClickListener(this);

		initService = new InitService(this);
		initService.init();// 初始化APP操作
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		try {
			super.onConfigurationChanged(newConfig);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			}
		} catch (Exception ex) {
		}
	}

	public FragmentActivity getContext() {
		return MainActivity.this;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		for (int index = 0; index < group.getChildCount(); index++) {

			RadioButton rb = (RadioButton) group.getChildAt(index);
			if (rb.isChecked()) {
				rb.setTextColor(Color.parseColor("#EB5212"));
			} else {
				rb.setTextColor(Color.BLACK);
			}

		}

	}

	private void init() {
		main_tab_index.setTextColor(Color.parseColor("#EB5212"));
		fragmentManagers = FragmentManagers.getInstance();
		fragmentManagers.setFragmentActivity(getContext());
		MainIndexFragment mainIndexFragment = new MainIndexFragment();
		mainIndexFragment.setHomeTabChangListner(this);
		ChangeFragment(mainIndexFragment, R.id.main_tab_index);

	}

	public void clearFragment() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				fragmentManagers.RemoveAllFragment();
			}
		}).start();

	}

	/**
	 * 在主界面的tab基础上切换界面 fragment
	 * 
	 * @param saveFragment
	 * @param id
	 */
	public void ChangeFragment(BaseFragment saveFragment, int id) {
		/*
		 * if (id == mCurrentFragmentId) return; mCurrentFragmentId = id;
		 */
		fragmentManagers.ChangeFragment(saveFragment);
	}

	@Override
	public void onBackPressed() {

		if (fragmentManagers.fragments == null
				|| fragmentManagers.fragments.size() <= 0) {
			
			showExitDialog();

		} else {
			fragmentManagers.BackPress(fragmentManagers.fragments
					.get(fragmentManagers.fragments.size() - 1));
			currentBaseSlideFragment = null;
			return;
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 退出程序
	 */
	private void showExitDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.logo);
		builder.setTitle("提示");
		builder.setMessage("您确定要退出吗?");
		builder.setCancelable(false);

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						System.exit(0);

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

	@Override
	public void onClick(View v) {

		if (CacheUtil.get(CacheKey.HOME_MENUITEM_KEY) == null) {
			Toast.makeText(this, "数据异常，请重启，或检查网络", Toast.LENGTH_SHORT).show();
			return;
		}
		SlideLevelFragment slideLevelFragment = new SlideLevelFragment();
		switch (v.getId()) {

		case R.id.main_tab_index:
			currentBaseSlideFragment = null;
			init();
			break;

		case R.id.main_tab_search:

			slideLevelFragment
					.setFragmentName(Constants.FragmentName.MAINSEARCH_FRAGMENT);
			slideLevelFragment.setHomeTabChangListner(this);
			if (currentBaseSlideFragment != null) {
				currentBaseSlideFragment.slideLinstener.replaceFragment(null,
						-1, Constants.FragmentName.MAINSEARCH_FRAGMENT, null);
			} else {
				fragmentManagers.IntentFragment(slideLevelFragment);
			}

			break;

		case R.id.main_tab_login_reg:// 登录注册

			slideLevelFragment
					.setFragmentName(Constants.FragmentName.LOGIN_FRAGMENT);

			slideLevelFragment.setHomeTabChangListner(this);
			if (currentBaseSlideFragment != null) {
				currentBaseSlideFragment.slideLinstener.replaceFragment(null,
						-1, Constants.FragmentName.LOGIN_FRAGMENT, null);
			} else {
				fragmentManagers.IntentFragment(slideLevelFragment);
			}

			break;

		case R.id.main_tab_mine:

			if ("".equals(SystemUtil.getAccessToken(this))) {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				slideLevelFragment
						.setFragmentName(Constants.FragmentName.LOGIN_FRAGMENT);
				slideLevelFragment.setHomeTabChangListner(this);
				if (currentBaseSlideFragment != null) {
					currentBaseSlideFragment.slideLinstener.replaceFragment(
							null, -1, Constants.FragmentName.LOGIN_FRAGMENT,
							null);
				} else {
					fragmentManagers.IntentFragment(slideLevelFragment);
				}
			} else {
				slideLevelFragment
						.setFragmentName(Constants.FragmentName.MAINMINEFRAGMENT);
				slideLevelFragment.setHomeTabChangListner(this);
//				fragmentManagers.ChangeFragment(slideLevelFragment);

				if (currentBaseSlideFragment != null) {
					currentBaseSlideFragment.slideLinstener.replaceFragment(
							null, -1, Constants.FragmentName.MAINMINEFRAGMENT,
							null);
				} else {
					fragmentManagers.IntentFragment(slideLevelFragment);
				}
			}

			break;

		case R.id.main_tab_more:

			slideLevelFragment
					.setFragmentName(Constants.FragmentName.SYSTEMSETF_RAGMENT);
			slideLevelFragment.setHomeTabChangListner(this);

			if (currentBaseSlideFragment != null) {
				currentBaseSlideFragment.slideLinstener.replaceFragment(null,
						-1, Constants.FragmentName.SYSTEMSETF_RAGMENT, null);
			} else {
				fragmentManagers.IntentFragment(slideLevelFragment);
			}
			break;

		}

	}

	@Override
	public void setCurrentFragment(BaseSlideFragment baseSlideFragment) {

		this.currentBaseSlideFragment = baseSlideFragment;
	}
}
