package com.wuxi.app;

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

import com.wuxi.app.fragment.MainIndexFragment;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;
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
OnCheckedChangeListener, OnClickListener {

	private RadioGroup radioGroup;

	public FragmentManagers fragmentManagers;

	private static final long BACK_PRESSED_INTERVAL_MILLIS = 1500;
	private long mLastBackPressedTimeMillis = 0;

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

		ChangeFragment(new MainIndexFragment(), R.id.main_tab_index);

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
			final long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis - mLastBackPressedTimeMillis > BACK_PRESSED_INTERVAL_MILLIS)
				Toast.makeText(getApplicationContext(), "再按一次退出程序！",
						Toast.LENGTH_SHORT).show();
			else
				System.exit(0);
			mLastBackPressedTimeMillis = currentTimeMillis;
		} else {
			fragmentManagers.BackPress(fragmentManagers.fragments
					.get(fragmentManagers.fragments.size() - 1));
			return;
		}

	}

	@Override
	public void onClick(View v) {

		if(CacheUtil.get(CacheKey.HOME_MENUITEM_KEY)==null){
			Toast.makeText(this, "数据异常，请重启，或检查网络", Toast.LENGTH_SHORT).show();
			return ;
		}
		SlideLevelFragment slideLevelFragment = new SlideLevelFragment();
		switch (v.getId()) {

		case R.id.main_tab_index:
			init();
			break;

		case R.id.main_tab_search:
			fragmentManagers.RemoveAllFragment();
			slideLevelFragment
			.setFragmentName(Constants.FragmentName.MAINSEARCH_FRAGMENT);
			fragmentManagers.ChangeFragment(slideLevelFragment);
			
			break;

		case R.id.main_tab_login_reg:// 登录注册
			
			slideLevelFragment
			.setFragmentName(Constants.FragmentName.LOGIN_FRAGMENT);

			fragmentManagers.ChangeFragment(slideLevelFragment);
			
			break;

		case R.id.main_tab_mine:

			if("".equals(SystemUtil.getAccessToken(this))){
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				slideLevelFragment
				.setFragmentName(Constants.FragmentName.LOGIN_FRAGMENT);

				fragmentManagers.ChangeFragment(slideLevelFragment);
			}
			else{
				slideLevelFragment
				.setFragmentName(Constants.FragmentName.MAINMINEFRAGMENT);
				fragmentManagers.ChangeFragment(slideLevelFragment);
			}



			break;

		case R.id.main_tab_more:
			
			slideLevelFragment
			.setFragmentName(Constants.FragmentName.SYSTEMSETF_RAGMENT);
			fragmentManagers.ChangeFragment(slideLevelFragment);
			
			break;

		}

	}
}
