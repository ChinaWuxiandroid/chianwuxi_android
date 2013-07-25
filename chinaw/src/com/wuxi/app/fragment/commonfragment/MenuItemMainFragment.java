package com.wuxi.app.fragment.commonfragment;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.BaseSlideFragment;
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
public abstract class MenuItemMainFragment extends BaseSlideFragment implements
		InitializContentLayoutListner, OnClickListener, Serializable {

	
	
	protected MenuItem menuItem;
	protected List<MenuItem> titleMenuItems;// 头部子菜单
	protected TitleScrollLayout mtitleScrollLayout;
	protected ImageButton ib_nextItems;
	private static final int MANCOTENT_ID = R.id.model_main;
	private static final int TITLE_LOAD_SUCCESS = 0;// 头部加载成功
	protected static final int TITLE_LOAD_FAIL = 1;// 加载失败
	public static final String ROOTFRAGMENT_KEY="BaseSlideFragment";
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case TITLE_LOAD_FAIL:
				String tip = "";
				if (msg.obj != null) {
					tip = msg.obj.toString();
					Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				}
				break;
			case TITLE_LOAD_SUCCESS:
				showTitleData();
				break;
			}

		};
	};

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	

	/**
	 * 
	 * wanglu 泰得利通 初始化界面
	 */
	public void initUI() {
		super.initUI();
		mtitleScrollLayout = (TitleScrollLayout) view
				.findViewById(R.id.title_scroll_action);// 头部控件
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器
		mtitleScrollLayout
				.setMenuItemInitLayoutListener(getMenuItemInitLayoutListener());// 设置界面监听处理类

		mtitleScrollLayout.setPerscreenCount(getTitlePerScreenItemCount());

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

				MenuService menuSevice = new MenuService(context);
				try {
					titleMenuItems = menuSevice.getSubMenuItems(menuItem
							.getId());
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

		mtitleScrollLayout
				.initMenuItemScreen(context, mInflater, titleMenuItems);// 初始化头部空间

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_next_screen:// 下一屏
			mtitleScrollLayout.goNextScreen();
			break;

		}

	}

	@Override
	public void bindContentLayout(Fragment fragment) {
		bindFragment(fragment);
	}

	private void bindFragment(Fragment fragment) {
		Bundle bundle=new Bundle();
		bundle.putSerializable(ROOTFRAGMENT_KEY, this);
		fragment.setArguments(bundle);//将外层框架传递给子内容框架
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(MANCOTENT_ID, fragment);// 替换内容界面
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	/**
	 * //子类实现MenuItem与其要显示的底部的布局绑定
	 * items 头部的菜单
	 */
	public abstract void initializSubFragmentsLayout(List<MenuItem> items);

}
