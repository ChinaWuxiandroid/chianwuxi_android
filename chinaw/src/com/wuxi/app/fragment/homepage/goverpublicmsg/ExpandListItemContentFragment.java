package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 *市政府信息公开目录 里的 可扩展列表的列表内容
 *@author 杨宸 智佳
 * */

public class ExpandListItemContentFragment extends BaseFragment{
	
	private View view;
	private LayoutInflater mInflater;
	private MenuItem parentItem;
	private Context context;

	private ListView listview;
	private ProgressBar processBar;

	protected List<MenuItem> MenuItems;
	protected List<Channel> Channels;

	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;
	protected static final int CHANNELCONTENT_ID=R.id.govermsg_custom_content;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				processBar.setVisibility(View.INVISIBLE);
				showChannelList();
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
		

		processBar.setVisibility(View.VISIBLE);
		loadData();
	}

	@SuppressWarnings("unchecked")
	private void loadData(){
		if (CacheUtil.get(parentItem.getId()) != null) {// 从缓存中查找子菜单
			MenuItems = (List<MenuItem>) CacheUtil.get(parentItem.getId());
			processBar.setVisibility(View.INVISIBLE);
			showChannelList();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuSevice = new MenuService(context);
				try {
					MenuItems = menuSevice.getSubMenuItems(parentItem
							.getId());
					if (MenuItems != null) {
						handler.sendEmptyMessage(DATA__LOAD_SUCESS);
					
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

	private void showChannelList(){
		ChannelListAdapter adapter=new ChannelListAdapter();

		listview.setAdapter(adapter);
		
	}

	public class ChannelListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return MenuItems.size();
		}

		@Override
		public Object getItem(int position) {
			return MenuItems.get(position);
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
				viewHolder.title_text.setText(MenuItems.get(position).getName());
				viewHolder.title_text.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					}
				});	
			}
			return convertView;
		}

	}
}
