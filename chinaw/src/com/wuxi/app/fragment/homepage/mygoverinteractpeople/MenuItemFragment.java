package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.view.DynamicTitleLayout;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 相当于继承了MenuItemMainFragment，但是脱离了BaseSlideFragment，根据ParentMenuitems的个数动态加载按钮个数
 * @author 杨宸 智佳
 * */

public abstract  class MenuItemFragment extends BaseFragment implements
InitializContentLayoutListner, OnClickListener, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private View view;
	private LayoutInflater mInflater;
	private MenuItem parentItem;
	private List<MenuItem> titleMenuItems;
	private List<Channel> titleChannels;
	private Context context;

	private static final int MENUITEM_TITLEDATA__LOAD_SUCESS = 0;
	private static final int CHANNEL_TITLEDATA__LOAD_SUCESS = 1;
	private static final int TITLEDATA_LOAD_ERROR = 2;	
	protected static final int RIGHT_CONTENT_ID=R.id.gip_menuitem_content_fragmentlayout;

	//	private ProgressBar titlePb;
	protected DynamicTitleLayout mtitleLayout;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {

			case MENUITEM_TITLEDATA__LOAD_SUCESS:
				//				titlePb.setVisibility(View.INVISIBLE);
				showMenuItemTitle();
				break;
			case CHANNEL_TITLEDATA__LOAD_SUCESS:
				//				titlePb.setVisibility(View.INVISIBLE);
				showChannelTitle();
				break;
			case TITLEDATA_LOAD_ERROR:
				//				titlePb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.gip_content_layout, null);
		mInflater=inflater;
		context = getActivity();
		initTitleUI();
		return view;
	}

	public void initTitleUI(){
		//		titlePb=(ProgressBar)view.findViewById(R.id.gip_menuitem_progressbar);


		//		titlePb.setVisibility(View.VISIBLE);

		if (parentItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelTitleData();
		}
		else if (parentItem.getType() == MenuItem.CUSTOM_MENU) {// 普通菜单
			loadMenuItemTitleData();// 加载子菜单
		}
	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemTitleData(){
		if (CacheUtil.get(parentItem.getId()) != null) {// 从缓存中查找子菜单
			titleMenuItems = (List<MenuItem>) CacheUtil.get(parentItem.getId());
			//			titlePb.setVisibility(View.INVISIBLE);
			showMenuItemTitle();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuSevice = new MenuService(context);
				try {
					titleMenuItems = menuSevice.getSubMenuItems(parentItem
							.getId());
					if (titleMenuItems != null) {
						handler.sendEmptyMessage(MENUITEM_TITLEDATA__LOAD_SUCESS);
						CacheUtil.put(parentItem.getId(), titleMenuItems);// 放入缓存
					}
					else{
						Message msg = handler.obtainMessage();
						msg.obj = "暂无信息";
						msg.what = TITLEDATA_LOAD_ERROR;
						handler.sendMessage(msg);
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLEDATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLEDATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLEDATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	//显示普通菜单类型标题条
	public void showMenuItemTitle(){
		mtitleLayout = (DynamicTitleLayout) view.findViewById(R.id.gip_menuitem_title_layout);// 头部控件

		mtitleLayout.setMenuItemInitLayoutListener(getMenuItemInitLayoutListener());// 设置界面监听处理类
		mtitleLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器

		mtitleLayout.setPerscreenCount(titleMenuItems.size());
		initializSubFragmentsLayout(titleMenuItems);// 绑定子界面
		mtitleLayout.initMenuItemScreen(context, mInflater, titleMenuItems);// 初始化头部空间
	}

	@SuppressWarnings("unchecked")
	private void loadChannelTitleData(){
		if (CacheUtil.get(parentItem.getChannelId()) != null) {// 从缓存中查找子菜单
			titleChannels = (List<Channel>) CacheUtil.get(parentItem.getChannelId());
			if (titleChannels != null) {
				//				titlePb.setVisibility(View.INVISIBLE);
				showChannelTitle();
				return;
			}	
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				ChannelService channelService = new ChannelService(context);
				try {
					titleChannels = channelService.getSubChannels(parentItem
							.getChannelId());
					if (titleChannels != null) {
						handler.sendEmptyMessage(CHANNEL_TITLEDATA__LOAD_SUCESS);
						CacheUtil.put(parentItem.getChannelId(),
								titleChannels);// 放入缓存
					}
				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLEDATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	//显示频道菜单类型标题条
	public void showChannelTitle(){
		mtitleLayout.setPerscreenCount(titleChannels.size());
		//		initializSubFragmentsLayout(titleChannels);// 绑定子界面
		mtitleLayout.initChannelScreen(context, mInflater, titleChannels);// 初始化头部空间
		mtitleLayout.postInvalidate();

	}

	protected abstract MenuItemInitLayoutListener getMenuItemInitLayoutListener();

	public abstract void initializSubFragmentsLayout(List<MenuItem> items);

	@Override
	public void onClick(View v) {

	}

	@Override
	public void bindContentLayout(Fragment fragment) {
		bindFragment(fragment);
	}

	private void bindFragment(Fragment fragment) {		
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(RIGHT_CONTENT_ID, fragment);
		ft.commit();	
	}
}
