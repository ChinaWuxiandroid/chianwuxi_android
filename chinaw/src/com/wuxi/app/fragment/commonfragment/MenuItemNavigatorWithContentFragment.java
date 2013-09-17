package com.wuxi.app.fragment.commonfragment;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentNavigatorAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * 
 * @author wanglu 适用于头部数据类型是 MenuItem的左右导航视图,导航视图的左侧数据可能是Channel，也可能是MenuItem
 *         具体左侧是什么类型，根据头部的MenuItem的的菜单类型判读
 * 
 *         使用方法:1、子类继承该类 2、如果左侧数据是MenuItem类型数据 ，子类需实现showMenItemContentFragment
 *         该方法用途是将用户点击的左侧的MenuItem传入进方法，并由子类实现，返回相应的 content区域的Fragment
 *         3、如果左侧数据是Channel类型数据 ，子类需实现showChannelContentFragment
 *         该方法用途是将用户点击的左侧的Channel传入进方法，并由子类实现，返回相应的 content区域的Fragment
 * 
 *         具体是使用方法可信息咨询中心左右的导航数据的视图InfoNavigatorWithContentFragment 类的实现
 * 
 * 
 * 
 */

public abstract class MenuItemNavigatorWithContentFragment extends BaseFragment
		implements OnItemClickListener {
	private static final int DETAIL_ID = R.id.details;// 点击左侧导航时右侧要显示内容区域的ID

	protected static final int LEFT_CHANNEL_DATA__LOAD_SUCCESS = 1;// 左侧频道(菜单)加载

	protected static final int LEFT_MENUITEM_DATA__LOAD_SUCCESS = 2;// 左侧频道(菜单)加载

	protected static final int LEFT_DATA__LOAD_ERROR = 0;// 左侧频道(菜单)加载失败

	protected View view;

	protected ListView mListView;// 左侧ListView

	protected LayoutInflater mInflater;

	private Context context;

	private List<Channel> channels;

	private List<MenuItem> menuItems;

	private MenuItem parentMenuItem; // 父菜单

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
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.content_navigator_layout, null);
		mListView = (ListView) view.findViewById(R.id.lv_left_navigator);

		mInflater = inflater;
		context = getActivity();
		if (parentMenuItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelData();
		} else if (parentMenuItem.getType() == MenuItem.CUSTOM_MENU) {// 普通菜单
			loadMenuItemData();// 加载子菜单

		}

		return view;

	}

	@Override
	public void onResume() {

		super.onResume();

	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemData() {

		if (null != CacheUtil.get(parentMenuItem.getId())) {
			menuItems = (List<MenuItem>) CacheUtil.get(parentMenuItem.getId());
			showLeftMenuItemData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuService = new MenuService(context);
				Message msg = handler.obtainMessage();
				try {
					menuItems = menuService.getSubMenuItems(parentMenuItem
							.getId());
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

		int showIndex = 0;

		Bundle bundle = getArguments();
		if (bundle != null
				&& bundle
						.containsKey(Constants.CheckPositionKey.LEVEL_THREE_KEY)) {

			showIndex = bundle
					.getInt(Constants.CheckPositionKey.LEVEL_THREE_KEY);
		}
		adapter = new ContentNavigatorAdapter(mInflater, null, menuItems);
		adapter.setSelectedPosition(showIndex);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(this);

		if (menuItems.size() > 0) {

			showContentFragment(showMenItemContentFragment(menuItems
					.get(showIndex)));// 默认显示第一个ConentFragment

		}
	}

	/**
	 * 
	 * wanglu 泰得利通 加载左侧数据类型是Channel类型数据
	 */
	@SuppressWarnings("unchecked")
	private void loadChannelData() {

		if (null != CacheUtil.get(parentMenuItem.getChannelId())) {// 从缓存中查找
			channels = (List<Channel>) CacheUtil.get(parentMenuItem
					.getChannelId());
			showLeftChannelData();
			return;

		} else {// 从网络加载

			new Thread(new Runnable() {

				@Override
				public void run() {

					ChannelService channelService = new ChannelService(context);

					try {
						channels = channelService.getSubChannels(parentMenuItem
								.getChannelId());
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
		
		int showIndex = 0;

		Bundle bundle = getArguments();
		if (bundle != null
				&& bundle
						.containsKey(Constants.CheckPositionKey.LEVEL_THREE_KEY)) {

			showIndex = bundle
					.getInt(Constants.CheckPositionKey.LEVEL_THREE_KEY);
		}
		adapter = new ContentNavigatorAdapter(mInflater, channels, null);
		adapter.setSelectedPosition(showIndex);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(this);

		if (channels.size() > 0) {
			showContentFragment(showChannelContentFragment(channels.get(showIndex)));// 显示第一个Channel数据
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		Object object = parent.getItemAtPosition(position);

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

	/**
	 * 
	 * wanglu 泰得利通 显示子菜单的内容fragment 具体显示哪个Fragment由子类实现 返回需要展示的ContentFragment
	 * 
	 * @param menuItem
	 */
	protected abstract Fragment showMenItemContentFragment(MenuItem menuItem);

	/**
	 * 
	 * wanglu 泰得利通 显示频道内容信息 具体显示哪个Fragment由子类实现 返回需要展示的ContentFragment
	 */
	protected abstract Fragment showChannelContentFragment(Channel channel);

	/**
	 * 显示替换主要内容区域
	 * 
	 * @param fragment
	 */
	private void showContentFragment(Fragment fragment) {
		if (fragment != null) {
			FragmentTransaction ft = getActivity().getSupportFragmentManager()
					.beginTransaction();
			ft.replace(DETAIL_ID, fragment);// 替换视图
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commitAllowingStateLoss();

		}

	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

}
