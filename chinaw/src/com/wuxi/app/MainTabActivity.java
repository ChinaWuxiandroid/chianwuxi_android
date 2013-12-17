/*
 * (#)MainTabActivity.java 1.0 2013-8-29 2013-8-29 GMT+08:00
 */
package com.wuxi.app;

import java.util.Stack;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wuxi.app.activity.MainIndexActivity;
import com.wuxi.app.activity.homepage.logorregister.LoginActivity;
import com.wuxi.app.activity.homepage.more.SystemSetActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.activity.search.MainSearchActivity;
import com.wuxi.app.engine.InitService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants.CacheKey;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.StackElement;

/**
 * @author wanglu 泰得利通
 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00
 * 
 */

@SuppressWarnings("deprecation")
public class MainTabActivity extends ActivityGroup implements
		OnCheckedChangeListener, OnClickListener {

	private static final int REMOVE_VIEW = 0;

	private RadioGroup radioGroup;

	public Stack<StackElement> stack;

	public static MainTabActivity instance;
	private LinearLayout main_tab;
	private InitService initService;

	private RelativeLayout llMain;

	private RadioButton main_tab_index, main_tab_search, main_tab_login_reg,
			main_tab_mine, main_tab_more;

	public boolean fistLoadAPP = true;
	private long lastExitTime = 0;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REMOVE_VIEW:
				removeView();
				break;

			}

		};
	};

	/**
	 * 
	 * wanglu 泰得利通 隐藏tab
	 */
	public void hideOrShowTab() {
		if (main_tab.getVisibility() == LinearLayout.GONE) {
			main_tab.setVisibility(LinearLayout.VISIBLE);
		} else {
			main_tab.setVisibility(LinearLayout.GONE);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		instance = this;
		stack = new Stack<StackElement>();
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main_tab_layout);
		main_tab = (LinearLayout) findViewById(R.id.main_tab);
		llMain = (RelativeLayout) findViewById(R.id.main_content);
		radioGroup = (RadioGroup) findViewById(R.id.main_tab_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);

		main_tab_index = (RadioButton) findViewById(R.id.main_tab_index);
		main_tab_search = (RadioButton) findViewById(R.id.main_tab_search);
		main_tab_login_reg = (RadioButton) findViewById(R.id.main_tab_login_reg);
		main_tab_mine = (RadioButton) findViewById(R.id.main_tab_mine);
		main_tab_more = (RadioButton) findViewById(R.id.main_tab_more);

		main_tab_index.setOnClickListener(this);
		main_tab_search.setOnClickListener(this);
		main_tab_login_reg.setOnClickListener(this);
		main_tab_mine.setOnClickListener(this);
		main_tab_more.setOnClickListener(this);

		init();

		initService = new InitService(this);
		initService.init();// 初始化APP操作

	}

	public void addView(Intent intent, Animation animation) {

		String str = UUID.randomUUID().toString();

		View view = getLocalActivityManager().startActivity(str,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		view.startAnimation(animation);
		llMain.addView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		llMain.requestFocus();

		StackElement component = new StackElement(str, view);
		stack.add(component);

		if (llMain.getChildCount() > 1) {
			handler.sendEmptyMessageDelayed(REMOVE_VIEW,
					animation.getDuration() + 1000L);// 移除view
		}
	}

	/**
	 * 
	 * @param intent
	 *            期望获得的activity
	 */
	public void addView(Intent intent) {
		String str = UUID.randomUUID().toString();

		View view = getLocalActivityManager().startActivity(str,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

		llMain.addView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		llMain.requestFocus();

		StackElement component = new StackElement(str, view);
		stack.add(component);

		if (llMain.getChildCount() > 1) {
			handler.sendEmptyMessageDelayed(REMOVE_VIEW, 1000);// 移除view
		}

	}

	private void removeView() {
		for (int index = 0; index < llMain.getChildCount() - 1; index++) {// 移除下面的view保留最上面的view
			llMain.removeViewAt(index);
		}
	}

	/** 退出栈顶元素,显示下一层元素 */
	public int pop() {
		int size = stack.size();
		StackElement element = null;
		if (size > 1) {
			element = stack.pop();
			getLocalActivityManager().destroyActivity(element.getTag(), true);
			element = stack.peek();

			llMain.removeAllViews();
			llMain.addView(element.getView());
			llMain.requestFocus();
		} else if (size == 1) {
			element = stack.peek();

			exit();
		}

		return stack.size();
	}

	/**
	 * 
	 *wanglu 泰得利通
	 *APP退出
	 */
	private void exit() {

		if ((System.currentTimeMillis() - lastExitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出中国无锡",
					Toast.LENGTH_SHORT).show();
			lastExitTime = System.currentTimeMillis();
		} else {
			AppManager.getInstance(this).clearCacheFile(true);//清楚内容列表缓存文件
			System.exit(0);

		}

	}

	/**
	 * 清空Stack，仅保留最底层的view
	 */
	public void clearStack() {
		int size = stack.size();
		if (size > 1) {
			for (int i = 0; i < size - 1; i++) {
				stack.pop();

			}
		}
	}

	public void clearAll() {
		int size = stack.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				StackElement element = stack.pop();
				getLocalActivityManager().destroyActivity(element.getTag(),
						true);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/*
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		try {
			super.onConfigurationChanged(newConfig);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			}
		} catch (Exception ex) {
		}
	}*/

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

		Intent intent = new Intent(MainTabActivity.this,
				MainIndexActivity.class);

		instance.addView(intent);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

		if (CacheUtil.get(CacheKey.MAIN_MENUITEM_KEY) == null) {
			Toast.makeText(this, "数据异常，请重启，或检查网络", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = null;

		switch (v.getId()) {

		case R.id.main_tab_index:// 首页

			clearAll();

			intent = new Intent(MainTabActivity.this, MainIndexActivity.class);

			break;

		case R.id.main_tab_search:// 搜索
			clearAll();
			intent = new Intent(MainTabActivity.this, MainSearchActivity.class);
			break;

		case R.id.main_tab_login_reg:// 登录注册
			clearAll();
			intent = new Intent(MainTabActivity.this, LoginActivity.class);

			break;

		case R.id.main_tab_mine:// 我的政民互动

			if ("".equals(SystemUtil.getAccessToken(this))) {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				clearAll();
				intent = new Intent(MainTabActivity.this, LoginActivity.class);

			} else {
				intent = new Intent(MainTabActivity.this,
						MainMineActivity.class);
			}

			break;

		case R.id.main_tab_more:// 设置

			clearAll();
			intent = new Intent(MainTabActivity.this, SystemSetActivity.class);
			break;

		}

		if (intent != null) {
			addView(intent);
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (getCurrentActivity() != null) {
			return getCurrentActivity().dispatchKeyEvent(event);
		} else {
			return super.dispatchKeyEvent(event);
		}

	}
}
