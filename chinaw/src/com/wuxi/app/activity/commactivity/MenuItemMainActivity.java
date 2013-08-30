package com.wuxi.app.activity.commactivity;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.PublicGoverMsgActivity;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 导航菜单主菜单父类 适用与上下结构的 ,头部是一排MenuItem,下面是一个布局的结构的导航菜单
 *         用法:1、直接继承该类，实现initializSubFragmentsLayout方法。该方法是实现头部MenuItem与底部布局进行绑定
 * 
 *         2、 子类需实现getMenuItemInitLayoutListener该方法， 返回头部menItem与子界面绑定的处理实现类
 *         3、子类需实现头部的Item每屏的个数
 * 
 *         具体使用 可参考实例可才考信息中心InformationCenterFragment 类的实现
 * 
 */
public abstract class MenuItemMainActivity extends BaseSlideActivity implements
		InitializContentLayoutListner, OnClickListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 548858849836548351L;

	/**
	 * 
	 */

	protected List<MenuItem> titleMenuItems;// 头部子菜单

	protected TitleScrollLayout mtitleScrollLayout;

	protected ImageButton ib_nextItems;

	private static final int MANCOTENT_ID = R.id.model_main;

	private static final int TITLE_LOAD_SUCCESS = 0;// 头部加载成功

	protected static final int TITLE_LOAD_FAIL = 1;// 加载失败

	// public static final String ROOTFRAGMENT_KEY="BaseSlideFragment";
	public static final String SHOWITEM_LAYOUT_INDEXKEY = "showitem_layout_index";

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case TITLE_LOAD_FAIL:
				String tip = "";
				if (msg.obj != null) {
					tip = msg.obj.toString();
					Toast.makeText(
						MenuItemMainActivity.this, tip, Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case TITLE_LOAD_SUCCESS:
				showTitleData();
				break;
			}
		};
	};

	@Override
	protected void findMainContentViews(View view) {

		Bundle bundle = this.getIntent().getExtras();
		int showIndex = 0;
		if (bundle != null) {
			showIndex = bundle.getInt(SHOWITEM_LAYOUT_INDEXKEY);//
		}
		mtitleScrollLayout = (TitleScrollLayout) view.findViewById(R.id.title_scroll_action);// 头部控件
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器
		mtitleScrollLayout.setMenuItemInitLayoutListener(getMenuItemInitLayoutListener());// 设置界面监听处理类
		int perScreenItemCount = getTitlePerScreenItemCount();
		mtitleScrollLayout.setPerscreenCount(perScreenItemCount);// 美屏显示的菜单数量
		int screenIndex = showIndex / perScreenItemCount;// 第几屏
		int showScreenIndex = showIndex % perScreenItemCount;// 屏的序号

		mtitleScrollLayout.setShowItemIndex(showScreenIndex);// 设置显示的默认布局
		mtitleScrollLayout.setmCurScreen(screenIndex);
		ib_nextItems = (ImageButton) view.findViewById(R.id.btn_next_screen);// 头部下一个按钮
		ib_nextItems.setOnClickListener(this);

		loadTitleData();
	}

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @return 每一个头部的个数 子类实现
	 */
	protected abstract int getTitlePerScreenItemCount();

	/**
	 * 
	 * wanglu 泰得利通 返回布局的处理类 子类实现
	 * 
	 * @return
	 */
	protected abstract MenuItemInitLayoutListener getMenuItemInitLayoutListener();

	/**
	 * 
	 * wanglu 泰得利通 加载头部菜单数据
	 */
	@SuppressWarnings("unchecked")
	private void loadTitleData() {

		if (CacheUtil.get(menuItem.getId()) != null) {// 从缓存中查找子菜单
			titleMenuItems = (List<MenuItem>) CacheUtil.get(menuItem.getId());
			showTitleData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuSevice = new MenuService(
					MenuItemMainActivity.this);
				try {
					titleMenuItems = menuSevice.getSubMenuItems(menuItem.getId());
					if (titleMenuItems != null) {
						handler.sendEmptyMessage(TITLE_LOAD_SUCCESS);
						CacheUtil.put(menuItem.getId(), titleMenuItems);// 放入缓存
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLE_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLE_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLE_LOAD_FAIL;
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	/**
	 * 显示头部数据 wanglu 泰得利通
	 */
	private void showTitleData() {
		initializSubFragmentsLayout(titleMenuItems);// 绑定子界面

		mtitleScrollLayout.initMenuItemScreen(
			this, getLayoutInflater(), titleMenuItems);// 初始化头部空间

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_next_screen:// 下一屏
			mtitleScrollLayout.goNextScreen();
			break;

		}

	}

	@Override
	public void bindContentLayout(BaseFragment fragment) {
		bindFragment(fragment);
	}

	private void bindFragment(BaseFragment fragment) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(MANCOTENT_ID, fragment);// 替换内容界面
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commitAllowingStateLoss();
	}

	/**
	 * //子类实现MenuItem与其要显示的底部的布局绑定 items 头部的菜单
	 */
	public abstract void initializSubFragmentsLayout(List<MenuItem> items);

	/**
	 * 页面跳转切换问题
	 */
	@Override
	public void redirectFragment(MenuItem showMenuItem, int showMenuPositon,
			int subMenuPostion) {
		Bundle bundle = new Bundle();
		if (showMenuItem.getName().equals("政府信息公开")) {
			Intent intent = new Intent(MenuItemMainActivity.this,
				PublicGoverMsgActivity.class);
			intent.putExtra(SHOWITEM_LAYOUT_INDEXKEY, subMenuPostion);
			intent.putExtra(
				BaseSlideActivity.SELECT_MENU_POSITION_KEY, showMenuPositon);
			MainTabActivity.instance.addView(intent);

		}

	}

}
