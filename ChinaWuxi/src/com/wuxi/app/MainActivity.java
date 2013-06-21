package com.wuxi.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.fragment.MainCollectFragment;
import com.wuxi.app.fragment.MainIndexFragment;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.MainMoreFragment;
import com.wuxi.app.fragment.MainSearchFragment;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private RadioGroup radioGroup;
	private int mCurrentFragmentId = 0;
	public FragmentManagers fragmentManagers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_fragment_layout);
		init();
		radioGroup = (RadioGroup) findViewById(R.id.main_tab_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
	}

	public FragmentActivity getContext() {
		return MainActivity.this;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {

		case R.id.main_tab_index:
			init();
			break;

		case R.id.main_tab_search:
			ChangeFragment(new MainSearchFragment(), checkedId);
			break;

		case R.id.main_tab_collect:
			ChangeFragment(new MainCollectFragment(), checkedId);
			break;

		case R.id.main_tab_mine:
			ChangeFragment(new MainMineFragment(), checkedId);
			break;

		case R.id.main_tab_more:
			ChangeFragment(new MainMoreFragment(), checkedId);
			break;
		}
	}

	private void init() {
		fragmentManagers = FragmentManagers.getInstance();
		fragmentManagers.setFragmentActivity(getContext());
		fragmentManagers.ChangeFragment(new MainIndexFragment());
		ChangeFragment(new MainIndexFragment(), R.id.main_tab_index);
	}

	public void ChangeFragment(BaseFragment saveFragment, int id) {
		if (id == mCurrentFragmentId)
			return;
		mCurrentFragmentId = id;
		fragmentManagers.ChangeFragment(saveFragment);
	}

	private static final long BACK_PRESSED_INTERVAL_MILLIS = 1500;
	private long mLastBackPressedTimeMillis = 0;

	@Override
	public void onBackPressed() {

		if (fragmentManagers.fragments == null || fragmentManagers.fragments.size() <= 0) {
			final long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis - mLastBackPressedTimeMillis > BACK_PRESSED_INTERVAL_MILLIS)
				Toast.makeText(getApplicationContext(), "再按一次退出程序！",
						Toast.LENGTH_SHORT).show();
			else
				System.exit(0);
			mLastBackPressedTimeMillis = currentTimeMillis;
		} else {
			fragmentManagers.BackPress(fragmentManagers.fragments.get(fragmentManagers.fragments.size() - 1));
			return;
		}

	}
}
