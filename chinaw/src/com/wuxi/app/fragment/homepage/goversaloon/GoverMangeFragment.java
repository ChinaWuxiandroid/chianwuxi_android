package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverManageAdapter;
import com.wuxi.domain.Channel;

/**
 * 
 * @author wanglu 泰得利通 组织管理
 * 
 */
public class GoverMangeFragment extends GoverSaloonContentFragment implements
		OnPageChangeListener, OnClickListener {

	private ListView gover_mange_lv;
	private ViewPager gover_viewpagerLayout;
	private ImageView gover_mange_iv_next;
	private List<View> titleGridViews;
	private static final int PAGE_ITEM = 4;
	private static final String TAG = "GoverMange";

	private int checkPositons[];// 选中的坐标
	private int pageNo = 0;
	private String itemString[] = { "1.关于在新区梅园镇建立垃圾回收的问题咨询的问题的咨询(2013-5-3)",
			"2.关于在新区梅园镇建立垃圾回收的问题咨询的问题的咨询(2013-5-3)" };

	public void initUI() {
		super.initUI();
		gover_mange_lv = (ListView) view.findViewById(R.id.gover_mange_lv);
		gover_viewpagerLayout = (ViewPager) view
				.findViewById(R.id.gover_viewpagerLayout);
		gover_mange_iv_next = (ImageView) view
				.findViewById(R.id.gover_mange_iv_next);
		gover_viewpagerLayout.setOnPageChangeListener(this);
		gover_mange_iv_next.setOnClickListener(this);
		showChannelData();

		GoverManageAdapter manageAdapter = new GoverManageAdapter(itemString,
				context);
		gover_mange_lv.setAdapter(manageAdapter);

	}

	private void showChannelData() {

		List<Channel> channles = new ArrayList<Channel>();

		for (int i = 0; i < 10; i++) {
			Channel channel = new Channel();
			channel.setChannelName("专栏新闻");
			channles.add(channel);
		}

		int i = 0;
		List<Channel> onScreenItems = null;
		titleGridViews = new ArrayList<View>();
		int currentScreen = 0;// 当前屏

		int totalScreenNum = channles.size() / PAGE_ITEM;// 屏数
		checkPositons = new int[totalScreenNum + 1];
		for (int j = 0; j < checkPositons.length; j++) {// 初始化头部安选中的下标
			checkPositons[j] = -1;
		}

		checkPositons[0] = 0;// 默认选中第一屏第一个Chanel

		for (Channel item : channles) {

			if (i % PAGE_ITEM == 0) {

				if (onScreenItems != null) {

					titleGridViews.add(bulidGridView(onScreenItems,
							currentScreen));
					currentScreen++;

				}

				onScreenItems = new ArrayList<Channel>();
			}

			// 最后一屏操作
			if (currentScreen > totalScreenNum + 1) {

				onScreenItems = new ArrayList<Channel>();
			}

			onScreenItems.add(item);

			// add last category screen //最后一屏
			if (i == channles.size() - 1) {

				titleGridViews.add(bulidGridView(onScreenItems, currentScreen));

			}

			i++;
		}

		gover_viewpagerLayout.setAdapter(new ChannelItemViewPageAdapter(
				titleGridViews));// 设置ViewPage适配器

	}

	private GridView bulidGridView(List<Channel> items, int screenIndex) {
		GridView gridView = (GridView) mInflater.inflate(
				R.layout.gover_mange_title_gridview_layout, null);
		gridView.setColumnWidth(PAGE_ITEM);

		gridView.setAdapter(new ChannelGridViewAdaptger(items, screenIndex));
		gridView.setOnItemClickListener(GridviewOnclick);
		return gridView;
	}

	static class ViewHolder {
		TextView tv_title;
	}

	private class ChannelGridViewAdaptger extends BaseAdapter {

		public List<Channel> channels;
		public int screenIndex;

		public ChannelGridViewAdaptger(List<Channel> channels, int screenIndex) {
			this.channels = channels;
			this.screenIndex = screenIndex;
		}

		@Override
		public int getCount() {
			return channels.size();
		}

		@Override
		public Object getItem(int position) {
			return channels.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Channel channel = channels.get(position);
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gover_mange_title_gridview_item_layout, null);

				viewHolder = new ViewHolder();

				viewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.gover_manger_tv);

				if (screenIndex == 0 && position == 0) {

					viewHolder.tv_title
							.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);
					viewHolder.tv_title.setTextColor(Color.WHITE);

				}

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.tv_title.setText(channel.getChannelName());
			return convertView;
		}

	}

	private class ChannelItemViewPageAdapter extends PagerAdapter {

		private List<View> mListViews;

		public ChannelItemViewPageAdapter(List<View> mGridViews) {

			this.mListViews = mGridViews;
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View collection, int position) {

			((ViewPager) collection).addView(mListViews.get(position), 0);

			return mListViews.get(position);
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView(mListViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

	}

	/**
	 * 菜单点击
	 */
	private OnItemClickListener GridviewOnclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			/**
			 * 切换选中与未选择的样式
			 */
			if (checkPositons[pageNo] != position) {
				View checkView = parent.getChildAt(position);

				TextView tv_Check = (TextView) checkView
						.findViewById(R.id.gover_manger_tv);
				tv_Check.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);

				tv_Check.setTextColor(Color.WHITE);

				View oldCheckView = parent.getChildAt(checkPositons[pageNo]);
				if (null != oldCheckView) {

					TextView tv_oldCheck = (TextView) oldCheckView
							.findViewById(R.id.gover_manger_tv);
					tv_oldCheck.setBackgroundColor(getResources().getColor(
							R.color.content_background));

					tv_oldCheck.setTextColor(Color.BLACK);

				}

				checkPositons[pageNo] = position;
			}

		}
	};

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		pageNo = position;

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.gover_mange_iv_next:
			// gover_viewpagerLayout.setCurrentItem(pageNo+1);
			gover_viewpagerLayout.setCurrentItem(pageNo + 1, true);
			break;

		}

	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_saloon_mange;
	}

}
