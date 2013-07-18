package com.wuxi.app.fragment.homepage.more;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.listeners.OnChangedListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.view.SlipButton;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 首页常用栏设置
 * 
 */
public class MenuItemSetFragment extends BaseSlideFragment {
	private LinearLayout menuset_ll;
	private List<MenuItem> items;
	private View subView;

	@SuppressWarnings("unchecked")
	@Override
	public  void initUI() {
		super.initUI();
		menuset_ll = (LinearLayout) view.findViewById(R.id.menuset_ll);
		items = (List<MenuItem>) CacheUtil
				.get(Constants.CacheKey.HOME_MENUITEM_KEY);
		if (items == null) {
			Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
			return;
		}

		initViewData();

	}

	/**
	 * 
	 *wanglu 泰得利通
	 *初始化界面及数据
	 */
	private void initViewData() {

		for (MenuItem item : items) {

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			TextView tv = new TextView(context);
			layoutParams.topMargin = 5;
			layoutParams.bottomMargin = 5;
			layoutParams.leftMargin = 15;
			tv.setText(item.getName());
			tv.setTextSize(16);
			tv.setTextColor(Color.BLACK);

			menuset_ll.addView(tv, layoutParams);

			addSubMenuItemView(item);

		}

	}

	@SuppressWarnings("unchecked")
	private void addSubMenuItemView(MenuItem item) {

		List<MenuItem> menuItems = null;
		List<Channel> channels = null;
		if (item.getType() == MenuItem.CUSTOM_MENU) {
			menuItems = (List<MenuItem>) CacheUtil.get(item.getId());
		} else if (item.getType() == MenuItem.CHANNEL_MENU) {
			channels = (List<Channel>) CacheUtil.get(item.getChannelId());
		}

		subView = View.inflate(context, R.layout.menuset_listview_layout, null);

		ListView lv = (ListView) subView.findViewById(R.id.menuset_lv);

		if (menuItems != null && channels == null) {
			lv.setAdapter(new MenuSetAdapter(menuItems));

		} else if (menuItems == null && channels != null) {
			lv.setAdapter(new MenuSetAdapter(channels));

		}
		setViewHeight(lv);// 动态设置listView高度

		menuset_ll.addView(subView);

	}

	public void setViewHeight(ListView listView) {
		MenuSetAdapter listAdapter = (MenuSetAdapter) listView.getAdapter();
		if (listAdapter == null)
			return;
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	static class ViewHolder {

		public TextView title_text;
		public SlipButton slipButton;
	}

	private class MenuSetAdapter extends BaseAdapter implements
			OnChangedListener {

		@SuppressWarnings("rawtypes")
		private List items;

		public MenuSetAdapter(@SuppressWarnings("rawtypes") List items) {
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			String name = "";
			Object o = items.get(position);
			if (o instanceof MenuItem) {
				name = ((MenuItem) o).getName();
			} else if (o instanceof Channel) {
				name = ((Channel) o).getChannelName();
			}

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.menuset_item_layout, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.menuset_tv_name);
				SlipButton slipButton = (SlipButton) convertView
						.findViewById(R.id.slipButton);

				viewHolder.slipButton = slipButton;

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.title_text.setText(name);
			viewHolder.slipButton.setChecked(true);
			viewHolder.slipButton.SetOnChangedListener("", this);
			return convertView;

		}

		@Override
		public void OnChanged(String strName, boolean CheckState) {

		}

	}

	@Override
	protected int getLayoutId() {

		return R.layout.index_menuitem_set_layout;
	}

	@Override
	protected String getTitleText() {
		
		return "首页常用栏设置";
	}

	
}
