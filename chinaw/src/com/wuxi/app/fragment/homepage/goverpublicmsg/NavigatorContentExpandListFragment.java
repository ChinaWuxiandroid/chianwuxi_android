package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 *市政府信息公开目录 里的 可扩展列表
 *@author 杨宸 智佳
 * */

public class NavigatorContentExpandListFragment extends BaseFragment implements OnItemClickListener{
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub

		super.onHiddenChanged(hidden);
		processBar.setVisibility(View.INVISIBLE);
	}

	private View view;
	private LayoutInflater mInflater;
	private MenuItem parentItem;
	private Context context;

	private ListView listview;
	private ProgressBar processBar;

	protected List<MenuItem> menuItems;
	protected List<Channel> channels;

	private static final int MENUITEM_DATA_LOAD_SUCESS = 0;
	private static final int CHANNEL_DATA_LOAD_SUCESS = 1;
	private static final int DATA_LOAD_ERROR = 2;
	private static final int LOAD_CHANNEL_DATA = 3;
	protected static final int CHANNELCONTENT_ID=R.id.govermsg_custom_content;


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case MENUITEM_DATA_LOAD_SUCESS:
				processBar.setVisibility(View.INVISIBLE);
				showMenuItemList();
				break;
			case CHANNEL_DATA_LOAD_SUCESS:
				processBar.setVisibility(View.INVISIBLE);
				showChannelList();
				break;
			case LOAD_CHANNEL_DATA:
				showChannelData();
				break;

			case DATA_LOAD_ERROR:
				processBar.setVisibility(View.INVISIBLE);
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

		view = inflater.inflate(R.layout.expand_channel_listview_layout, null);
		mInflater=inflater;
		context = getActivity();
		initUI();
		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initUI() {

		listview = (ListView) view.findViewById(R.id.expand_channel_listview);
		processBar = (ProgressBar) view.findViewById(R.id.expand_channel_progress);
		listview.setOnItemClickListener(this);

		processBar.setVisibility(View.VISIBLE);
		if(parentItem.getType()==MenuItem.CUSTOM_MENU){
			loadMenuItemData();
		}
		else if(parentItem.getType()==MenuItem.CHANNEL_MENU){
			loadChannelData();
		}

	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemData(){
		if (CacheUtil.get(parentItem.getId()) != null) {// 从缓存中查找子菜单
			menuItems = (List<MenuItem>) CacheUtil.get(parentItem.getId());
			processBar.setVisibility(View.INVISIBLE);
			showMenuItemList();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuSevice = new MenuService(context);
				try {
					menuItems = menuSevice.getSubMenuItems(parentItem
							.getId());
					if (menuItems != null) {
						handler.sendEmptyMessage(MENUITEM_DATA_LOAD_SUCESS);
						CacheUtil.put(parentItem.getId(), menuItems);// 放入缓存
					}
					else{
						Message msg = handler.obtainMessage();
						msg.obj = "暂无信息";
						msg.what = DATA_LOAD_ERROR;
						handler.sendMessage(msg);
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	@SuppressWarnings("unchecked")
	private void loadChannelData(){
		if (CacheUtil.get(parentItem.getChannelId()) != null) {// 从缓存中查找子菜单
			channels = (List<Channel>) CacheUtil.get(parentItem.getChannelId());
			processBar.setVisibility(View.INVISIBLE);
			showChannelList();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				ChannelService channelSevice = new ChannelService(context);

				try {
					if(parentItem.getName().equals("业务工作")){
						handler.sendEmptyMessage(LOAD_CHANNEL_DATA);

					}
					else{
						channels = channelSevice.getSubChannels(parentItem.getChannelId());
						if (channels != null) {
							handler.sendEmptyMessage(CHANNEL_DATA_LOAD_SUCESS);
							CacheUtil.put(parentItem.getChannelId(), channels);// 放入缓存
						}
						else{
							Message msg = handler.obtainMessage();
							msg.obj = "暂无信息";
							msg.what = DATA_LOAD_ERROR;
							handler.sendMessage(msg);
						}
					}


				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}
	public void showChannelData(){
		//没有子频道列表
		GoverMsgCustomContentDetailFragment goverMsgCustomContentDetailFragment=new GoverMsgCustomContentDetailFragment();
		goverMsgCustomContentDetailFragment.setParentMenuItem(parentItem);
		goverMsgCustomContentDetailFragment.setFifterType(GoverMsgSearchContentListFragment.DEPT_TYPE);
		bindFragment(goverMsgCustomContentDetailFragment);
	}

	private void showMenuItemList(){
		MenuItemListAdapter adapter=new MenuItemListAdapter();

		listview.setAdapter(adapter);

	}

	private void showChannelList(){
		MenuItemListAdapter adapter=new MenuItemListAdapter();

		listview.setAdapter(adapter);

	}

	public class MenuItemListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if(menuItems!=null){
				return menuItems.size();
			}
			else if(channels!=null){
				return channels.size();
			}
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			if(menuItems!=null){
				return menuItems.get(position);
			}
			else if(channels!=null){
				return channels.get(position);
			}
			else
				return null;			

		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView title_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.expand_channel_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.expand_channel_listview_item_title);	
				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if(viewHolder.title_text!=null){
				if(menuItems!=null){
					viewHolder.title_text.setText(menuItems.get(position).getName());
				}
				else if(channels!=null){
					viewHolder.title_text.setText(channels.get(position).getChannelName());
				}
			}
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		Object object=(Object) adapterView.getItemAtPosition(position);

		MenuItem menuItem=null;
		Channel channel=null;
		if (object instanceof MenuItem) {// 如果是频道
			menuItem=(MenuItem)object;
		} else if (object instanceof Channel) {
			channel=(Channel)object;
		}

		GoverMsgCustomContentDetailFragment goverMsgCustomContentDetailFragment=new GoverMsgCustomContentDetailFragment();

		if(menuItem!=null){
			goverMsgCustomContentDetailFragment.setParentMenuItem(menuItem);
			bindFragment(goverMsgCustomContentDetailFragment);
		}		
		else{
			if(channel.getChildrenChannelsCount()>0){
				GoverMsgSubChannelsSearchFragment goverMsgSubChannelsSearchFragment=new GoverMsgSubChannelsSearchFragment();
				goverMsgSubChannelsSearchFragment.setParentChannel(channel);
				bindFragment(goverMsgSubChannelsSearchFragment);
			}
			else{
				goverMsgCustomContentDetailFragment.setParentChannel(channel);
				bindFragment(goverMsgCustomContentDetailFragment);
			}
		}
	}

	public void bindFragment(BaseFragment fragment){
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(CHANNELCONTENT_ID, fragment);

		ft.commit();
	}
}
