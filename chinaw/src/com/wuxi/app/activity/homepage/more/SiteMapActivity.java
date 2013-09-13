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
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.MenuItemChanelUtil;
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

		site_map_elv = (ExpandableListView) view
				.findViewById(R.id.site_map_elv);
		site_map_elv.setDivider(null);
		site_map_elv.setGroupIndicator(null);

		initData();
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		if (CacheUtil.get(Constants.CacheKey.MAIN_MENUITEM_KEY) == null) {
			Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
			return;
		}
		List<MenuItem> menuItems = (List<MenuItem>) CacheUtil
				.get(Constants.CacheKey.MAIN_MENUITEM_KEY);
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
				List<MenuItem> subMenuItems = (List<MenuItem>) CacheUtil
						.get(item.getId());
				return subMenuItems;
			} else if (item.getType() == MenuItem.CHANNEL_MENU) {
				List<Channel> subChannels = (List<Channel>) CacheUtil.get(item
						.getChannelId());
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
			View subView = View.inflate(SiteMapActivity.this,
					R.layout.sitemap_gridview_layout, null);
			GridView gv = (GridView) subView
					.findViewById(R.id.sitemap_gv_menuitem);
			List<MenuItem> subMenuItems = null;
			List<Channel> subChannels = null;
			MenuItem item = items.get(groupPosition);
			if (item.getType() == MenuItem.CUSTOM_MENU) { // 普通菜单
				subMenuItems = (List<MenuItem>) CacheUtil.get(item.getId());

			} else if (item.getType() == MenuItem.CHANNEL_MENU) {
				subChannels = (List<Channel>) CacheUtil
						.get(item.getChannelId());

			}

			gv.setOnItemClickListener(new SiteMapClickLister(item,
					groupPosition));// 绑定事件

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
				List<MenuItem> subMenuItems = (List<MenuItem>) CacheUtil
						.get(item.getId());
				if (subMenuItems == null) {
					return 0;
				}
				return subMenuItems.size() > 0 ? 1 : 0;
			} else if (item.getType() == MenuItem.CHANNEL_MENU) {
				List<Channel> subChannels = (List<Channel>) CacheUtil.get(item
						.getChannelId());
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
			tv.setTextSize(16);
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
				convertView = View.inflate(SiteMapActivity.this,
						R.layout.site_map_item, null);
				TextView tv = (TextView) convertView
						.findViewById(R.id.site_map_tv);

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

		private int parentPosition;

		public SiteMapClickLister(MenuItem menuItem, int parentPosition) {
			this.menuItem = menuItem;
			this.parentPosition = parentPosition;

		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long arg3) {

			switchActivity(menuItem, position, parentPosition);

		}

	}

	protected void switchActivity(MenuItem menuItem, int position,
			int parentPosition) {

		Intent intent = null;

		Class<?> acClass = MenuItemChanelUtil.getActivityClassByName(menuItem);

		if (acClass != null) {
			intent = new Intent(SiteMapActivity.this, acClass);
			intent.putExtra(Constants.CheckPositionKey.LEVEL_ONE_KEY, position);// 设置选中的一级菜单的序号
			intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY,
					parentPosition);// 设置左侧选中的选菜单序号
			MainTabActivity.instance.addView(intent);

		}

	}

}
