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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 类似了MenuItemMainFragment，但是脱离了BaseSlideFragment，根据ParentMenuitems的个数动态加载按钮个数
 * 
 * @author 杨宸 智佳
 * */

public abstract class GoverMenuItemTitleFragment extends BaseFragment implements
		InitializContentLayoutListner, OnClickListener, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private View view;
	private LayoutInflater mInflater;
	private MenuItem parentItem;
	private List<MenuItem> titleMenuItems;
	private List<Channel> titleChannels;
	private Context context;

	private GoverMenuItemTitleFragment titleFragment;

	/**
	 * @param titleFragment
	 *            要设置的 titleFragment
	 */
	public void setTitleFragment(GoverMenuItemTitleFragment titleFragment) {
		this.titleFragment = titleFragment;
	}

	private static final int MENUITEM_TITLEDATA__LOAD_SUCESS = 0;
	private static final int CHANNEL_TITLEDATA__LOAD_SUCESS = 1;
	private static final int TITLEDATA_LOAD_ERROR = 2;

	private static final int RIGHT_CONTENT_ID = R.id.gip_menuitem_content_fragmentlayout;

	private ProgressBar titlePb;
	private GridView Titles_gridView;
	private int checkPoint = 0;

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
				showMenuItemTitle(0);
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
		mInflater = inflater;
		context = getActivity();
		initTitleUI();
		return view;
	}

	/**
	 * @方法： initTitleUI
	 * @描述： 初始化界面控件
	 */
	private void initTitleUI() {
		titlePb = (ProgressBar) view
				.findViewById(R.id.gip_menuitem_progressbar);
		titlePb.setVisibility(View.VISIBLE);
		if (parentItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelTitleData();
		} else if (parentItem.getType() == MenuItem.CUSTOM_MENU) {// 普通菜单
			loadMenuItemTitleData();// 加载子菜单
		}
	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemTitleData() {
		if (CacheUtil.get(parentItem.getId()) != null) {// 从缓存中查找子菜单
			titleMenuItems = (List<MenuItem>) CacheUtil.get(parentItem.getId());
			titlePb.setVisibility(View.INVISIBLE);
			showMenuItemTitle(0);
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

					} else {
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

	// 显示普通菜单类型标题条
	private void showMenuItemTitle(int key) {
		checkPoint = 0; // 默认选中第一个
		Bundle bundle = getArguments();
		if (bundle != null) {
			checkPoint = bundle
					.getInt(Constants.CheckPositionKey.LEVEL_THREE_KEY);

			bundle.putInt(Constants.CheckPositionKey.LEVEL_THREE_KEY, key);// 回复现场
			getActivity().getIntent().putExtras(bundle);// 回复现场
		}
		Titles_gridView = (GridView) view
				.findViewById(R.id.gip_menuitem_gridview_title);
		int titleSize = titleMenuItems.size();
		if (titleSize < 2)
			titleSize = 2;
		Titles_gridView.setNumColumns(titleSize);

		Titles_gridView.setAdapter(new GridViewAdaptger());
		Titles_gridView.setOnItemClickListener(GridviewOnclick);

		initializSubFragmentsLayout(titleMenuItems);// 绑定子界面
		loadMenuItemListLayout(titleMenuItems.get(checkPoint));
	}

	@SuppressWarnings("unchecked")
	private void loadChannelTitleData() {
		if (CacheUtil.get(parentItem.getChannelId()) != null) {// 从缓存中查找子菜单
			titleChannels = (List<Channel>) CacheUtil.get(parentItem
					.getChannelId());
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

	// 显示频道菜单类型标题条
	private void showChannelTitle() {
		checkPoint = 0; // 默认选中第一个
		Bundle bundle = getArguments();
		if (bundle != null) {
			checkPoint = bundle
					.getInt(Constants.CheckPositionKey.LEVEL_THREE_KEY);
			bundle.putInt(Constants.CheckPositionKey.LEVEL_THREE_KEY, 0);// 回复现场
			getActivity().getIntent().putExtras(bundle);// 回复现场
		}
		Titles_gridView = (GridView) view
				.findViewById(R.id.gip_menuitem_gridview_title);
		int titleSize = titleChannels.size();
		if (titleSize < 2)
			titleSize = 2;
		Titles_gridView.setNumColumns(titleSize);

		Titles_gridView.setAdapter(new GridViewAdaptger());
		Titles_gridView.setOnItemClickListener(GridviewOnclick);

		loadChannelContentList(titleChannels.get(checkPoint));

	}

	public abstract MenuItemInitLayoutListener getMenuItemInitLayoutListener();

	public abstract void initializSubFragmentsLayout(List<MenuItem> items);

	@Override
	public void onClick(View v) {

	}

	@Override
	public void bindContentLayout(BaseFragment fragment) {
		bindFragment(fragment);
	}

	/**
	 * @方法： bindFragment
	 * @描述： 绑定碎片视图
	 * @param fragment
	 */
	private void bindFragment(BaseFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(RIGHT_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * @类名： GridViewAdaptger
	 * @描述： 适配器
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-11 下午5:12:20
	 * @修改时间：
	 * @修改描述：
	 */
	private class GridViewAdaptger extends BaseAdapter {

		@Override
		public int getCount() {
			if (titleMenuItems != null) {
				return titleMenuItems.size();
			} else if (titleChannels != null) {
				return titleChannels.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (titleMenuItems != null) {
				return titleMenuItems.get(position);
			} else if (titleChannels != null) {
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
			MenuItem menuItem = null;
			Channel channel = null;
			if (titleMenuItems != null) {
				menuItem = titleMenuItems.get(position);
			} else if (titleChannels != null) {
				channel = titleChannels.get(position);
			}

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gip_menuitem_gridview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.gip_menu_tv_title);

				if (position == checkPoint) {

					viewHolder.tv_title
							.setBackgroundResource(R.drawable.title_item_select_bg);
					viewHolder.tv_title.setTextColor(Color.WHITE);

				} else {
					viewHolder.tv_title
							.setBackgroundResource(R.drawable.title_item_bg);
					viewHolder.tv_title.setTextColor(Color
							.parseColor("#177CCA"));
				}

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (menuItem != null) {
				viewHolder.tv_title.setText(menuItem.getName());
			} else if (channel != null) {
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
			MenuItem menuItem = null;
			Channel channel = null;

			Object object = (Object) parent.getItemAtPosition(position);
			if (object instanceof MenuItem) {
				menuItem = (MenuItem) object;
			} else if (object instanceof Channel) {
				channel = (Channel) object;
			}

			/**
			 * 切换选中与未选择的样式
			 */
			if (checkPoint != position) {
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

			if (menuItem != null && getMenuItemInitLayoutListener() != null) {
				loadMenuItemListLayout(menuItem);
			} else if (channel != null) {
				loadChannelContentList(channel);
			}
		}
	};

	/**
	 * @方法： loadMenuItemListLayout
	 * @描述： 加载菜单列表布局
	 * @param menuItem
	 */
	private void loadMenuItemListLayout(MenuItem menuItem) {
		getMenuItemInitLayoutListener().bindMenuItemLayout(this, menuItem);
	}

	/**
	 * @方法： loadChannelContentList
	 * @描述： 加载频道列表
	 * @param channel
	 */
	private void loadChannelContentList(Channel channel) {
		GIPChannelContentListFragment gIPContentListFragment = new GIPChannelContentListFragment();
		gIPContentListFragment.setChannel(channel);
		bindContentLayout(gIPContentListFragment);
	}
}
