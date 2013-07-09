package com.wuxi.app.fragment.commonfragment;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 具有左右导航视图  跳转时必须调用方法setParentChannel(Channel parentChannel)或setParentMenuItem(MenuItem parentMenuItem)
 *                          和方法public void setDataType(int type)
 * @author wanglu
 * 
 */

public class NavigatorWithContentFragment extends BaseFragment implements
		OnItemClickListener {
	private static final int DETAIL_ID = R.id.details;// 点击左侧导航时右侧要显示内容区域的ID
	protected static final int LEFT_CHANNEL_DATA__LOAD_SUCCESS = 1;// 左侧频道(菜单)加载
	protected static final int LEFT_MENUITEM_DATA__LOAD_SUCCESS = 2;// 左侧频道(菜单)加载
	protected static final int LEFT_DATA__LOAD_ERROR = 0;// 左侧频道(菜单)加载失败
	
	public static final int DATA_TPYE_MENUITEM=0;    //加载的数据类型为MenuItem
	public static final int DATA_TYPE_CHANNEL=1;     //加载的数据类型为Channel,后续消息类型未加。。。
	
	protected View view;
	protected ListView mListView;// 左侧ListView
	protected LayoutInflater mInflater;
	private Context context;
	
	private Channel parentChannel;// 父频道
	private List<Channel> channels;
	
	private MenuItem parentMenuItem; //父菜单
	private List<MenuItem> menuItems;
	
	private int dataType=1;                 //消息类型，默认为Channel ,因为@wanglu 最先定义用作加载Channel消息类型
	

	@SuppressLint("HandlerLeak")
	private Handler  handler = new Handler() {
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

		Drawable drawable = getResources()
				.getDrawable(R.drawable.navgator_back);
		mListView.setSelector(drawable);
		mInflater = inflater;
		context = getActivity();

		switch(dataType){
		case DATA_TYPE_CHANNEL:
			loadChannelData();
			break;
		case DATA_TPYE_MENUITEM:
			loadMenuItemData();
			break;
		}
		return view;

	}

	@SuppressWarnings("unchecked")
	private void loadChannelData() {

		if (null != CacheUtil.get(parentChannel.getChannelId())) {// 从缓存中查找
			channels = (List<Channel>) CacheUtil.get(parentChannel
					.getChannelId());
			showLeftChannelData();
			return;

		} else {// 从网络加载

			new Thread(new Runnable() {

				@Override
				public void run() {

					ChannelService channelService = new ChannelService(context);

					try {
						channels = channelService.getSubChannels(parentChannel
								.getChannelId());
						if (channels != null) {
							handler.sendEmptyMessage(LEFT_CHANNEL_DATA__LOAD_SUCCESS);
							CacheUtil.put(parentChannel.getChannelId(),
									channels);//放入缓存
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
	
	@SuppressWarnings("unchecked")
	private void loadMenuItemData() {

		if (null != CacheUtil.get(parentMenuItem.getId())) {// 从缓存中查找
			menuItems = (List<MenuItem>) CacheUtil.get(parentMenuItem
					.getId());
			showLeftMenuItemData();
			return;

		} else {// 从网络加载

			new Thread(new Runnable() {

				@Override
				public void run() {

					MenuService menuService = new MenuService(context);

					try {
						menuItems = menuService.getSubMenuItems(parentMenuItem.getId());
						if (menuItems != null) {
							handler.sendEmptyMessage(LEFT_MENUITEM_DATA__LOAD_SUCCESS);
							CacheUtil.put(parentMenuItem.getId(),
									menuItems);//放入缓存
						}

					} catch (NetException e) {
						e.printStackTrace();
						Message msg = handler.obtainMessage();
						msg.obj = "网络连接错误稍后重试";
						handler.sendMessage(msg);
					} catch (NODataException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			).start();
		}
	}
	

	/**
	 * 
	 * wanglu 泰得利通 显示数据
	 */
	private void showLeftChannelData() {
		mListView.setAdapter(new ContentNavigatorAdapter(mInflater,channels,null));// 设置适配器
		mListView.setOnItemClickListener(this);
	}
	
	private void showLeftMenuItemData() {
		mListView.setAdapter(new ContentNavigatorAdapter(mInflater, null,menuItems));// 设置适配器
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		/*
		 * NavigatorItmeAction naItem = (NavigatorItmeAction) parent
		 * .getItemAtPosition(position); DataFragment df = (DataFragment)
		 * naItem.getFrament();
		 * 
		 * showContentFragment(df);
		 */

	}

	/**
	 * 获取左侧导航数据及点击导航显示的右侧数据视图的封装集合
	 * 
	 * @return
	 */
	// protected abstract List<NavigatorItmeAction> getNavigatorItmeActions();

	/**
	 * 显示替换主要内容区域
	 * 
	 * @param fragment
	 */
	protected void showContentFragment(Fragment fragment) {
		if (fragment != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(DETAIL_ID, fragment);// 替换视图

			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}

	}

	public void setParentChannel(Channel parentChannel) {
		this.parentChannel = parentChannel;
	}
	
	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

	public void setDataType(int type){
		dataType=type;
	}
}
