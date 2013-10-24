package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.adapter.GovernmentGeneralizeAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 市政府信息公开目录 里的 可扩展列表
 * 
 * @author 杨宸 智佳
 * */

public class NavigatorContentExpandListFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	private LayoutInflater mInflater;
	private Context context;
	
	private MenuItem parentItem;

	private ListView listview;
	private ProgressBar processBar;

	private List<MenuItem> menuItems;
	private List<Channel> channels;

	private Channel parentChannel;
	private MenuItem parentMenuItem;

	private ContentWrapper contentWrapper;// 内容
	private List<Content> contents;

	private TextView textView_title;
	private ListView govermsg_detail_lv_channel;
	private ListView channelListView;
	private ProgressBar dataProgressBar;
	private Button packup_btn;
	private LinearLayout titleLayout;
	private FrameLayout channleFrameLayout;

	private static final int MENUITEM_DATA_LOAD_SUCESS = 0;
	private static final int CHANNEL_DATA_LOAD_SUCESS = 1;
	private static final int DATA_LOAD_ERROR = 2;
	private static final int LOAD_CHANNEL_DATA = 3;
	private static final int CHANNEL_LOAD_SUCESS = 4;// 子频道获取成功
	private static final int CHANNEL_LOAD_FAIL = 5;// 子频道获取失败
	private static final int DATA_LOAD_SUCESS = 6;

	private int fifterType = 0;

	// @Override
	// public void onHiddenChanged(boolean hidden) {
	//
	// super.onHiddenChanged(hidden);
	// processBar.setVisibility(View.INVISIBLE);
	// }

	public MenuItem getParentMenuItem() {
		return parentMenuItem;
	}

	public void setParentMenuItem(MenuItem menuItem) {
		this.parentMenuItem = menuItem;
	}

	public Channel getParentChannel() {
		return parentChannel;
	}

	public void setParentChannel(Channel parentChannel) {
		this.parentChannel = parentChannel;
	}

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
			// case LOAD_CHANNEL_DATA:
			// showChannelList();
			// break;

			case DATA_LOAD_ERROR:
				processBar.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case CHANNEL_LOAD_FAIL:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case DATA_LOAD_SUCESS:
				showContentData();
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
		mInflater = inflater;
		context = getActivity();
		initUI();
		initLayoutUI();
		return view;
	}

	/**
	 * @方法： initUI
	 * @描述： TODO
	 */
	private void initUI() {

		listview = (ListView) view.findViewById(R.id.expand_channel_listview);
		processBar = (ProgressBar) view
				.findViewById(R.id.expand_channel_progress);
		processBar.setVisibility(View.VISIBLE);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				Object object = (Object) adapterView
						.getItemAtPosition(position);

				MenuItem menuItem = null;
				Channel channel = null;
				if (object instanceof MenuItem) {// 如果是频道
					menuItem = (MenuItem) object;
				} else if (object instanceof Channel) {
					channel = (Channel) object;
				}

				System.out.println("测试listview的跳转");

				if (menuItem != null) {
					setParentMenuItem(menuItem);
					loadData(0, 10);

				} else if (channel != null) {
					setParentChannel(channel);
					loadData(0, 10);
				}

				if (getParentMenuItem() != null) {
					textView_title.setText(getParentMenuItem().getName());
				} else if (parentChannel != null) {
					textView_title.setText(getParentChannel().getChannelName());
				}
			}
		});

		processBar.setVisibility(View.VISIBLE);
		if (parentItem.getType() == MenuItem.CUSTOM_MENU) {
			loadMenuItemData();
		} else if (parentItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelData();
		}

	}

	/**
	 * @方法： initUI
	 * @描述： 初始化视图
	 */
	private void initLayoutUI() {
		channleFrameLayout = (FrameLayout) view
				.findViewById(R.id.expand_channel_prog_layout);
		titleLayout = (LinearLayout) view
				.findViewById(R.id.gpm_detail_tv_title_layout);
		textView_title = (TextView) view.findViewById(R.id.gpm_detail_tv_title);
		govermsg_detail_lv_channel = (ListView) view
				.findViewById(R.id.gpm_detail_listview);
		packup_btn = (Button) view.findViewById(R.id.gpm_detail_btn_packup);

		System.out.println("BBBB:" + getParentMenuItem());

		govermsg_detail_lv_channel
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View arg1, int position, long arg3) {
						Content content = (Content) adapterView
								.getItemAtPosition(position);

						if (parentItem != null) {

							Intent intent = new Intent(getActivity(),
								GoverMsgContentDetailWebActivity.class);
							intent.putExtra("url", content.getWapUrl());
							intent.putExtra("fragmentTitle", parentItem.getName());

							MainTabActivity.instance.addView(intent);

						} else if (parentChannel != null) {

							Intent intent = new Intent(getActivity(),
								GoverMsgContentDetailWebActivity.class);
							intent.putExtra("url", content.getWapUrl());
							intent.putExtra("fragmentTitle", parentChannel.getChannelName());

							Animation animation = AnimationUtils.loadAnimation(
								getActivity(), R.anim.rbm_in_from_right);
							MainTabActivity.instance.addView(intent, animation);

						}
					}
				});
		packup_btn.setOnClickListener(this);

		channelListView = (ListView) view.findViewById(R.id.gpm_detail_channel_listview);
		channelListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		// showContentList();
	}

	/**
	 * @方法： showContentData
	 * @描述： 显示数据
	 */
	private void showContentData() {
		contents = contentWrapper.getContents();
		
		if (contents == null && contents.size() == 0) {
			Toast.makeText(context, "数据为空！", Toast.LENGTH_SHORT).show();
		}
		channleFrameLayout.setVisibility(View.GONE);
		govermsg_detail_lv_channel.setVisibility(View.VISIBLE);
		packup_btn.setVisibility(View.VISIBLE);
		titleLayout.setVisibility(View.VISIBLE);
		channleFrameLayout.setVisibility(View.GONE);
		channelListView.setVisibility(View.GONE);
		
		GovernmentGeneralizeAdapter adapter = new GovernmentGeneralizeAdapter(
				contents, context);
		govermsg_detail_lv_channel.setAdapter(adapter);
	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemData() {
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
					menuItems = menuSevice.getSubMenuItems(parentItem.getId());
					if (menuItems != null) {
						handler.sendEmptyMessage(MENUITEM_DATA_LOAD_SUCESS);

					} else {
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

	private void loadChannelData() {
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
					if (parentItem.getName().equals("业务工作")) {
						handler.sendEmptyMessage(LOAD_CHANNEL_DATA);

					} else {
						channels = channelSevice.getSubChannels(parentItem
								.getChannelId());
						if (channels != null) {
							handler.sendEmptyMessage(CHANNEL_DATA_LOAD_SUCESS);

						} else {
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

	private void showMenuItemList() {
		MenuItemListAdapter adapter = new MenuItemListAdapter();

		listview.setAdapter(adapter);

	}

	private void showChannelList() {
		MenuItemListAdapter adapter = new MenuItemListAdapter();

		listview.setAdapter(adapter);

	}

	public class MenuItemListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (menuItems != null) {
				return menuItems.size();
			} else if (channels != null) {
				return channels.size();
			} else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			if (menuItems != null) {
				return menuItems.get(position);
			} else if (channels != null) {
				return channels.get(position);
			} else
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
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.expand_channel_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.expand_channel_listview_item_title);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (viewHolder.title_text != null) {
				if (menuItems != null) {
					viewHolder.title_text.setText(menuItems.get(position)
							.getName());
				} else if (channels != null) {
					viewHolder.title_text.setText(channels.get(position)
							.getChannelName());
				}
			}
			return convertView;
		}
	}

//	/**
//	 * @方法： loadData
//	 * @描述： 加载数据
//	 * @param startIndex
//	 * @param endIndex
//	 */
//	private void loadData(final int startIndex, final int endIndex) {
//		// if (isFirstLoad || isSwitch) {
//		// list_pb.setVisibility(View.VISIBLE);
//		// } else {
//		// pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
//		// }
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// isLoading = true;// 正在加载数据
//				Message msg = handler.obtainMessage();
//				ContentService contentService = new ContentService(context);
//				try {
//					contentWrapper = contentService.getPageContentsById(
//							parentMenuItem.getChannelId(), startIndex, endIndex);
//					if (contentWrapper != null) {
//						contents = contentWrapper.getContents();
//						msg.what = DATA_LOAD_SUCESS;
//					} else {
//						msg.what = DATA_LOAD_ERROR;
//						msg.obj = "加载办件信息失败";
//					}
//					handler.sendMessage(msg);
//				} catch (JSONException e) {
//					e.printStackTrace();
//					msg.what = DATA_LOAD_ERROR;
//					msg.obj = "数据格式错误";
//					handler.sendMessage(msg);
//				} catch (NetException e) {
//					e.printStackTrace();
//					msg.what = DATA_LOAD_ERROR;
//					msg.obj = e.getMessage();
//					handler.sendMessage(msg);
//				} catch (NODataException e) {
//					e.printStackTrace();
//
//					msg.what = DATA_LOAD_ERROR;
//					msg.obj = e.getMessage();
//					handler.sendMessage(msg);
//				}
//
//			}
//		}).start();
//	}
	
	public void loadData(final int start, final int end) {
//		if (isFirstLoad || isSwitch) {
//			content_list_pb.setVisibility(ProgressBar.VISIBLE);
//		} else {
//			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
//		}
		new Thread(new Runnable() {

			@Override
			public void run() {
//				isLoading = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {

					String channelId = "";
					if (parentChannel != null) {
						channelId = parentChannel.getChannelId();
					} else if (parentItem != null) {
						channelId = parentItem.getChannelId();
					}
					contentWrapper = contentService.getPageContentsById(
							channelId, start, end);
					if (contentWrapper != null) {
						msg.what = DATA_LOAD_SUCESS;
					} else {
						msg.what = DATA_LOAD_ERROR;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gpm_detail_btn_packup:
			listview.setVisibility(View.VISIBLE);
			govermsg_detail_lv_channel.setVisibility(View.GONE);
			packup_btn.setVisibility(View.GONE);
			titleLayout.setVisibility(View.GONE);
			channleFrameLayout.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

}
