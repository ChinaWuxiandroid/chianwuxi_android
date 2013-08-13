package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 类似了MenuItemMainFragment，但是脱离了BaseSlideFragment，根据ParentMenuitems的个数动态加载按钮个数
 * @author 杨宸 智佳
 * */

public abstract class GoverMenuItemTitleFragment extends BaseFragment implements
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

	private ProgressBar titlePb;
	private GridView Titles_gridView;	
	private int checkPoint=0;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {

			case MENUITEM_TITLEDATA__LOAD_SUCESS:
				titlePb.setVisibility(View.INVISIBLE);
				showMenuItemTitle();
				break;
			case CHANNEL_TITLEDATA__LOAD_SUCESS:
				titlePb.setVisibility(View.INVISIBLE);
				showChannelTitle();
				break;
			case TITLEDATA_LOAD_ERROR:
				titlePb.setVisibility(View.INVISIBLE);
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

		view = inflater.inflate(R.layout.gip_meuitem_fragment, null);
		mInflater=inflater;
		context = getActivity();
		initTitleUI();
		return view;
	}

	public void initTitleUI(){
		titlePb=(ProgressBar)view.findViewById(R.id.gip_menuitem_progressbar);
		titlePb.setVisibility(View.VISIBLE);
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
			titlePb.setVisibility(View.INVISIBLE);
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
		checkPoint=0;  //默认选中第一个
		Titles_gridView=(GridView) view.findViewById(R.id.gip_menuitem_gridview_title);
		int titleSize=titleMenuItems.size();
		if(titleSize<2)
			titleSize=2;
		Titles_gridView.setNumColumns(titleSize);

		Titles_gridView.setAdapter(new GridViewAdaptger());
		Titles_gridView.setOnItemClickListener(GridviewOnclick);

		initializSubFragmentsLayout(titleMenuItems);// 绑定子界面
		loadMenuItemListLayout(titleMenuItems.get(checkPoint));
	}

	@SuppressWarnings("unchecked")
	private void loadChannelTitleData(){
		if (CacheUtil.get(parentItem.getChannelId()) != null) {// 从缓存中查找子菜单
			titleChannels = (List<Channel>) CacheUtil.get(parentItem.getChannelId());
			if (titleChannels != null) {
				titlePb.setVisibility(View.INVISIBLE);
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
		checkPoint=0;  //默认选中第一个
		Titles_gridView=(GridView) view.findViewById(R.id.gip_menuitem_gridview_title);
		int titleSize=titleChannels.size();
		if(titleSize<2)
			titleSize=2;
		Titles_gridView.setNumColumns(titleSize);

		Titles_gridView.setAdapter(new GridViewAdaptger());
		Titles_gridView.setOnItemClickListener(GridviewOnclick);

		loadChannelContentList(titleChannels.get(checkPoint));

	}
	public  abstract MenuItemInitLayoutListener getMenuItemInitLayoutListener() ;

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

	private class GridViewAdaptger extends BaseAdapter {


		@Override
		public int getCount() {
			if(titleMenuItems!=null){
				return titleMenuItems.size();
			}
			else if(titleChannels!=null){
				return titleChannels.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(titleMenuItems!=null){
				return titleMenuItems.get(position);
			}
			else if(titleChannels!=null){
				return titleChannels.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public class ViewHolder {
			TextView tv_title;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MenuItem menuItem=null;
			Channel channel=null ;
			if(titleMenuItems!=null){
				menuItem=titleMenuItems.get(position);
			}
			else if(titleChannels!=null){
				channel=titleChannels.get(position);
			}


			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gip_menuitem_gridview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.gip_menu_tv_title);

				if ( position == checkPoint) {

					viewHolder.tv_title
					.setBackgroundResource(R.drawable.title_item_select_bg);
					viewHolder.tv_title.setTextColor(Color.WHITE);

				}
				else{
					viewHolder.tv_title
					.setBackgroundResource(R.drawable.title_item_bg);
					viewHolder.tv_title.setTextColor(Color.parseColor("#177CCA"));
				}

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if(menuItem!=null){
				viewHolder.tv_title.setText(menuItem.getName());
			}
			else if(channel!=null){
				viewHolder.tv_title.setText(channel.getChannelName());
			}

			return convertView;
		}
	}

	/**
	 * 菜单点击
	 */
	private OnItemClickListener GridviewOnclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			MenuItem menuItem=null;
			Channel channel =null;

			Object object= (Object) parent.getItemAtPosition(position);
			if(object instanceof MenuItem){
				menuItem=(MenuItem)object;
			}
			else if (object instanceof Channel) {
				channel=(Channel)object;
			}


			/**
			 * 切换选中与未选择的样式
			 */
			if (checkPoint!= position) {
				View checkView = parent.getChildAt(position);

				TextView tv_Check = (TextView) checkView
						.findViewById(R.id.gip_menu_tv_title);
				tv_Check.setBackgroundResource(R.drawable.title_item_select_bg);

				tv_Check.setTextColor(Color.WHITE);

				View oldCheckView = parent.getChildAt(checkPoint);
				if (null != oldCheckView) {

					TextView tv_oldCheck = (TextView) oldCheckView
							.findViewById(R.id.gip_menu_tv_title);
					tv_oldCheck.setBackgroundResource(R.drawable.title_item_bg);

					tv_oldCheck.setTextColor(Color.parseColor("#177CCA"));

				}

				checkPoint = position;
			}

			if(menuItem!=null&&getMenuItemInitLayoutListener()!=null){
				loadMenuItemListLayout(menuItem);
			}
			else if(channel!=null){
				loadChannelContentList(channel);
			}
		}
	};

	public void loadMenuItemListLayout(MenuItem menuItem){
		getMenuItemInitLayoutListener().bindMenuItemLayout(
				this, menuItem);
	}

	public void loadChannelContentList(Channel channel){
		GIPChannelContentListFragment gIPContentListFragment = new GIPChannelContentListFragment();	
		gIPContentListFragment.setArguments(this.getArguments());
		gIPContentListFragment.setChannel(channel);
		bindContentLayout(gIPContentListFragment);
	}
}
