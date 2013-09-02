package com.wuxi.app.activity.homepage.more;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.commactivity.MenuItemMainActivity;
import com.wuxi.app.activity.homepage.fantasticwuxi.ChannelActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.PublicGoverMsgActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonActivity;
import com.wuxi.app.activity.homepage.informationcenter.InformationCenterActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.activity.homepage.publicservice.PublicServiceActivity;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 网站地图
 */
public class SiteMapActivity extends BaseSlideActivity {

	private ExpandableListView site_map_elv;

	@Override
	protected void findMainContentViews(View view) {

		site_map_elv = (ExpandableListView) view.findViewById(R.id.site_map_elv);
		site_map_elv.setDivider(null);
		site_map_elv.setGroupIndicator(null);

		initData();
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		if (CacheUtil.get(Constants.CacheKey.HOME_MENUITEM_KEY) == null) {
			Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
			return;
		}
		List<MenuItem> menuItems = (List<MenuItem>) CacheUtil.get(Constants.CacheKey.HOME_MENUITEM_KEY);
		site_map_elv.setAdapter(new SiteMapExpandableAdapter(menuItems));

		// 将所有项设置成默认展开

		int groupCount = site_map_elv.getCount();

		for (int i = 0; i < groupCount; i++) {

			site_map_elv.expandGroup(i);

		}

	}

	@SuppressWarnings({ "unchecked" })
	private class SiteMapExpandableAdapter extends BaseExpandableListAdapter {

		public List<MenuItem> items;

		public SiteMapExpandableAdapter(List<MenuItem> items) {
			this.items = items;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {

			MenuItem item = items.get(groupPosition);

			if (item.getType() == MenuItem.CUSTOM_MENU) { // 普通菜单
				List<MenuItem> subMenuItems = (List<MenuItem>) CacheUtil.get(item.getId());
				return subMenuItems;
			} else if (item.getType() == MenuItem.CHANNEL_MENU) {
				List<Channel> subChannels = (List<Channel>) CacheUtil.get(item.getChannelId());
				return subChannels;
			}

			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View subView = View.inflate(
				SiteMapActivity.this, R.layout.sitemap_gridview_layout, null);
			GridView gv = (GridView) subView.findViewById(R.id.sitemap_gv_menuitem);
			List<MenuItem> subMenuItems = null;
			List<Channel> subChannels = null;
			MenuItem item = items.get(groupPosition);
			if (item.getType() == MenuItem.CUSTOM_MENU) { // 普通菜单
				subMenuItems = (List<MenuItem>) CacheUtil.get(item.getId());

			} else if (item.getType() == MenuItem.CHANNEL_MENU) {
				subChannels = (List<Channel>) CacheUtil.get(item.getChannelId());

			}

			gv.setOnItemClickListener(new SiteMapClickLister(item));// 绑定事件

			if (subMenuItems != null && subChannels == null) {
				gv.setAdapter(new SiteMapAdapter(subMenuItems));
			} else if (subMenuItems == null && subChannels != null) {
				gv.setAdapter(new SiteMapAdapter(subChannels));
			}

			return subView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {

			MenuItem item = items.get(groupPosition);

			if (item.getType() == MenuItem.CUSTOM_MENU) { // 普通菜单
				List<MenuItem> subMenuItems = (List<MenuItem>) CacheUtil.get(item.getId());
				if (subMenuItems == null) {
					return 0;
				}
				return subMenuItems.size() > 0 ? 1 : 0;
			} else if (item.getType() == MenuItem.CHANNEL_MENU) {
				List<Channel> subChannels = (List<Channel>) CacheUtil.get(item.getChannelId());
				if (subChannels == null) {
					return 0;
				}
				return subChannels.size() > 0 ? 1 : 0;
			}
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return items.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return items.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean,
		 * android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			MenuItem menuItem = items.get(groupPosition);
			TextView tv = new TextView(SiteMapActivity.this);
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				200, ViewGroup.LayoutParams.WRAP_CONTENT);

			tv.setBackgroundColor(Color.parseColor("#2890DF"));
			tv.setGravity(Gravity.CENTER);
			tv.setText(menuItem.getName());
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(14);
			tv.setGravity(Gravity.CENTER);
			tv.setLayoutParams(layoutParams);

			return tv;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}

	private class SiteMapAdapter extends BaseAdapter {

		@SuppressWarnings("rawtypes")
		private List items;

		@SuppressWarnings("rawtypes")
		public SiteMapAdapter(List items) {
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
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
			Object object = items.get(position);
			String name = "";
			if (object instanceof MenuItem) {
				name = ((MenuItem) object).getName();
			} else if (object instanceof Channel) {
				name = ((Channel) object).getChannelName();
			}
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(
					SiteMapActivity.this,
					R.layout.left_navigator_list_item_layout, null);
				TextView tv = (TextView) convertView.findViewById(R.id.tv_navigator_name);
				tv.setTextSize(12);
				viewHolder = new ViewHolder();

				viewHolder.title_text = tv;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.title_text.setText(name);
			return convertView;
		}

	}

	@Override
	protected int getLayoutId() {
		return R.layout.sitemap_layout;
	}

	@Override
	protected String getTitleText() {
		return "网站地图";
	}

	private class SiteMapClickLister implements OnItemClickListener {

		private MenuItem menuItem;

		public SiteMapClickLister(MenuItem menuItem) {
			this.menuItem = menuItem;

		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long arg3) {

			switchActivity(menuItem, position);

		}

	}

	protected void switchActivity(MenuItem menuItem, int position) {

		Intent intent = null;
		int selectMenuItemIndex = 0;
		if (menuItem.getType() != MenuItem.CHANNEL_MENU) {
			if (menuItem.getName().equals("资讯中心")) {

				intent = new Intent(SiteMapActivity.this,
					InformationCenterActivity.class);
				selectMenuItemIndex = 2;
				intent.putExtra(
					MenuItemMainActivity.SHOWITEM_LAYOUT_INDEXKEY, position);
			} else if (menuItem.getName().equals("政府信息公开")) {
				intent = new Intent(SiteMapActivity.this,
					PublicGoverMsgActivity.class);
				selectMenuItemIndex = 0;
			} else if (menuItem.getName().equals("公共服务")) {

				intent = new Intent(SiteMapActivity.this,
					PublicServiceActivity.class);
				intent.putExtra(
					MenuItemMainActivity.SHOWITEM_LAYOUT_INDEXKEY, position);
				selectMenuItemIndex = 4;
			} else if (menuItem.getName().equals("政务大厅")) {

				intent = new Intent(SiteMapActivity.this,
					GoverSaloonActivity.class);
				intent.putExtra(GoverSaloonActivity.SHOWLAYOUTINDEX, position);
				selectMenuItemIndex = 3;
			} else if (menuItem.getName().equals("政民互动")) {
				intent = new Intent(SiteMapActivity.this,
					MainMineActivity.class);
				selectMenuItemIndex = 5;
			}

		} else if (menuItem.getType() == MenuItem.CHANNEL_MENU) {// 频道类型菜单
			intent = new Intent(SiteMapActivity.this, ChannelActivity.class);
			intent.putExtra(
				ChannelActivity.SHOWCHANNEL_LAYOUT_INDEXKEY, position);
			selectMenuItemIndex = 1;

		}

		if (intent != null) {
			intent.putExtra(
				BaseSlideActivity.SELECT_MENU_POSITION_KEY, selectMenuItemIndex);
			MainTabActivity.instance.addView(intent);
		}

	}

}
