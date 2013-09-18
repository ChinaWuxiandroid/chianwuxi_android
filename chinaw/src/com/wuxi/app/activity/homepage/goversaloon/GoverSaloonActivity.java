package com.wuxi.app.activity.homepage.goversaloon;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.adapter.ContentNavigatorAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonContentMainFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅
 * 
 */
public class GoverSaloonActivity extends BaseSlideActivity implements
		OnItemClickListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DETAIL_ID = R.id.details;// 点击左侧导航时右侧要显示内容区域的ID

	protected static final int LEFT_CHANNEL_DATA__LOAD_SUCCESS = 1;// 左侧频道(菜单)加载

	protected static final int LEFT_MENUITEM_DATA__LOAD_SUCCESS = 2;// 左侧频道(菜单)加载

	protected static final int LEFT_DATA__LOAD_ERROR = 0;// 左侧频道(菜单)加载失败

	protected ListView mListView;// 左侧ListView

	private List<Channel> channels;

	private List<MenuItem> menuItems;

	private ContentNavigatorAdapter adapter;



	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case LEFT_CHANNEL_DATA__LOAD_SUCCESS:
				showLeftChannelData();
				break;
			case LEFT_MENUITEM_DATA__LOAD_SUCCESS:
				showLeftMenuItemData();
				break;

			case LEFT_DATA__LOAD_ERROR:
				Toast.makeText(
					GoverSaloonActivity.this, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@SuppressWarnings("unchecked")
	private void loadMenuItemData() {

		if (null != CacheUtil.get(menuItem.getId())) {
			menuItems = (List<MenuItem>) CacheUtil.get(menuItem.getId());
			showLeftMenuItemData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuService = new MenuService(
					GoverSaloonActivity.this);
				Message msg = handler.obtainMessage();
				try {
					menuItems = menuService.getSubMenuItems(menuItem.getId());
					if (menuItems != null) {
						
						msg.what = LEFT_MENUITEM_DATA__LOAD_SUCCESS;
						handler.sendMessage(msg);

					}

				} catch (NetException e) {
					e.printStackTrace();

					msg.obj = "网络连接错误稍后重试";
					msg.what = LEFT_DATA__LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = LEFT_DATA__LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = LEFT_DATA__LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}

		).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示左侧数据是MenuItem类型的数据
	 */
	private void showLeftMenuItemData() {
		Bundle bundle = getIntent().getExtras();
		int showIndex = 1;
		if (bundle != null && bundle.containsKey(Constants.CheckPositionKey.LEVEL_TWO__KEY)) {
			showIndex = bundle.getInt(Constants.CheckPositionKey.LEVEL_TWO__KEY);
		}
		adapter = new ContentNavigatorAdapter(getLayoutInflater(), null,
			menuItems);
		adapter.setSelectedPosition(showIndex);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(new GoverOnItemClickListenr());

		if (menuItems.size() > 0) {

			showContentFragment(showMenItemContentFragment(menuItems.get(showIndex)));// 默认显示第一个ConentFragment
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 加载左侧数据类型是Channel类型数据
	 */
	@SuppressWarnings("unchecked")
	private void loadChannelData() {

		if (null != CacheUtil.get(menuItem.getChannelId())) {// 从缓存中查找
			channels = (List<Channel>) CacheUtil.get(menuItem.getChannelId());
			showLeftChannelData();
			return;

		} else {// 从网络加载

			new Thread(new Runnable() {

				@Override
				public void run() {

					ChannelService channelService = new ChannelService(
						GoverSaloonActivity.this);

					try {
						channels = channelService.getSubChannels(menuItem.getChannelId());
						if (channels != null) {
							handler.sendEmptyMessage(LEFT_CHANNEL_DATA__LOAD_SUCCESS);
						
						}

					} catch (NetException e) {
						e.printStackTrace();
						Message msg = handler.obtainMessage();
						msg.obj = "网络连接错误稍后重试";
						handler.sendMessage(msg);
					}
				}
			}

			).start();

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示导航视图的左侧是channel类型的数据
	 * 
	 */
	private void showLeftChannelData() {
		adapter = new ContentNavigatorAdapter(getLayoutInflater(), channels,
			null);
		adapter.setSelectedPosition(0);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(new GoverOnItemClickListenr());

		if (channels.size() > 0) {
			showContentFragment(showChannelContentFragment(channels.get(0)));// 显示第一个Channel数据
		}
	}

	private class GoverOnItemClickListenr implements OnItemClickListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
		 * .widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long arg3) {
			Object object = adapterView.getItemAtPosition(position);

			if (object instanceof Channel) {// 如果是频道
				adapter.setSelectedPosition(position); // 刷新左侧导航listView背景
				adapter.notifyDataSetInvalidated();
				showContentFragment(showChannelContentFragment((Channel) object));
			} else if (object instanceof MenuItem) {
				adapter.setSelectedPosition(position); // 刷新左侧导航listView背景
				adapter.notifyDataSetInvalidated();
				showContentFragment(showMenItemContentFragment((MenuItem) object));
			}

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示子菜单的内容
	 * 
	 * @param menuItem
	 */
	private BaseFragment showMenItemContentFragment(MenuItem menuItem) {

		GoverSaloonContentMainFragment goverSaloonContentMainFragment = new GoverSaloonContentMainFragment();

		goverSaloonContentMainFragment.setMenuItem(menuItem);

		return goverSaloonContentMainFragment;
	};

	/**
	 * 
	 * wanglu 泰得利通 显示频道内容信息 具体显示哪个Fragment由子类实现 返回需要展示的ContentFragment
	 */
	private BaseFragment showChannelContentFragment(Channel channel) {
		return null;
	};

	/**
	 * 显示替换主要内容区域
	 * 
	 * @param fragment
	 */
	private void showContentFragment(BaseFragment fragment) {
		if (fragment != null) {
			fragment.setArguments(this.getIntent().getExtras());//传递intent数据
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(DETAIL_ID, fragment);// 替换视图
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commitAllowingStateLoss();
		}
	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.menuItem = parentMenuItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goversaloon_main_layout;
	}

	@Override
	protected String getTitleText() {
		return menuItem.getName();
	}

	@Override
	protected void findMainContentViews(View view) {

		mListView = (ListView) view.findViewById(R.id.lv_left_navigator);
		if (menuItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelData();
		} else if (menuItem.getType() == MenuItem.CUSTOM_MENU) {// 普通菜单
			loadMenuItemData();// 加载子菜单

		}

	}

}
