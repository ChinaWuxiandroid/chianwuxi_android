package com.wuxi.app.fragment.homepage.goversaloon;

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
import com.wuxi.app.adapter.ContentNavigatorAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅
 * 
 */
public class GoverSaloonFragment extends BaseSlideFragment implements
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

	private MenuItem parentMenuItem; // 父菜单

	private ContentNavigatorAdapter adapter;
	public static final String  SHOWLAYOUTINDEX="showLayout_index";

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
	public void initUI() {

		super.initUI();

		mListView = (ListView) view.findViewById(R.id.lv_left_navigator);
		if (parentMenuItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelData();
		} else if (parentMenuItem.getType() == MenuItem.CUSTOM_MENU) {// 普通菜单
			loadMenuItemData();// 加载子菜单

		}

	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemData(){

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
						CacheUtil.put(parentMenuItem.getId(), menuItems);// 放入缓存
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
		Bundle bundle=this.getArguments();
		int showIndex=0;
		if(bundle!=null){
			showIndex=bundle.getInt(SHOWLAYOUTINDEX);
		}
		adapter = new ContentNavigatorAdapter(mInflater, null, menuItems);
		adapter.setSelectedPosition(showIndex);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(this);

		
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
							CacheUtil.put(parentMenuItem.getChannelId(),
									channels);// 放入缓存
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
		adapter = new ContentNavigatorAdapter(mInflater, channels, null);
		adapter.setSelectedPosition(0);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(this);

		if (channels.size() > 0) {
			showContentFragment(showChannelContentFragment(channels.get(0)));// 显示第一个Channel数据
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
	 * wanglu 泰得利通 显示子菜单的内容
	 * 
	 * @param menuItem
	 */
	private BaseFragment showMenItemContentFragment(MenuItem menuItem) {

		GoverSaloonContentMainFragment goverSaloonContentMainFragment = new GoverSaloonContentMainFragment();

		goverSaloonContentMainFragment.setMenuItem(menuItem);

		Bundle bundle = new Bundle();
		
		goverSaloonContentMainFragment.setArguments(bundle);// 将对象传递过去
		goverSaloonContentMainFragment.setBaseSlideFragment(this);
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
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(DETAIL_ID, fragment);// 替换视图
			fragment.setManagers(managers);// 传递mangers
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

			ft.commit();
		}
	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goversaloon_main_layout;
	}

	@Override
	protected String getTitleText() {
		return parentMenuItem.getName();
	}

}
