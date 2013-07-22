package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.MenuService;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 行政事项
 * 
 */
public class AdministrativeItemFragment extends GoverSaloonContentFragment
		implements OnPageChangeListener, OnClickListener {

	private ListView gover_mange_lv;
	private ViewPager gover_viewpagerLayout;
	private ImageView gover_mange_iv_next;
	private List<View> titleGridViews;
	private static final int PAGE_ITEM = 4;
	private static final String TAG = "AdministrativeItemFragment";
	protected static final int TITLE_ITEM_LOADSUCCESS = 0;
	protected static final int TITLE_ITEM_LOADFAIL = 1;

	private int checkPositons[];// 选中的坐标
	private int pageNo = 0;
	private String itemString[] = { "1.关于在新区梅园镇建立垃圾回收的问题咨询的问题的咨询(2013-5-3)",
			"2.关于在新区梅园镇建立垃圾回收的问题咨询的问题的咨询(2013-5-3)" };
	private MenuItem menuItem;
	private List<MenuItem> menuItems;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TITLE_ITEM_LOADSUCCESS:
				showTitleData();

				break;
			case TITLE_ITEM_LOADFAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	public void initUI() {
		super.initUI();
		gover_mange_lv = (ListView) view.findViewById(R.id.gover_mange_lv);
		gover_viewpagerLayout = (ViewPager) view
				.findViewById(R.id.gover_viewpagerLayout);
		gover_mange_iv_next = (ImageView) view
				.findViewById(R.id.gover_mange_iv_next);
		gover_viewpagerLayout.setOnPageChangeListener(this);
		gover_mange_iv_next.setOnClickListener(this);

		menuItem = (MenuItem) getArguments().get("menuItem");
		loadTitleItems(menuItem.getId());
		// showChannelData();

		/*
		 * GoverManageAdapter manageAdapter = new GoverManageAdapter(itemString,
		 * context);
		 */
		// gover_mange_lv.setAdapter(manageAdapter);

	}

	/**
	 * 
	 * wanglu 泰得利通 加载头部信息
	 * 
	 * @param id
	 */
	private void loadTitleItems(final String id) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();

				MenuService menuService = new MenuService(context);
				try {
					menuItems = menuService.getSubMenuItems(id);
					if (menuItems != null) {
						msg.what = TITLE_ITEM_LOADSUCCESS;
					} else {
						msg.what = TITLE_ITEM_LOADFAIL;
						msg.obj = "加载数据失败";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = TITLE_ITEM_LOADFAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = TITLE_ITEM_LOADFAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = TITLE_ITEM_LOADFAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void showTitleData() {

		int i = 0;
		List<MenuItem> onScreenItems = null;
		titleGridViews = new ArrayList<View>();
		int currentScreen = 0;// 当前屏

		int totalScreenNum = menuItems.size() / PAGE_ITEM;// 屏数
		checkPositons = new int[totalScreenNum + 1];
		for (int j = 0; j < checkPositons.length; j++) {// 初始化头部安选中的下标
			checkPositons[j] = -1;
		}

		checkPositons[0] = 0;// 默认选中第一屏第一个Chanel

		for (MenuItem item : menuItems) {

			if (i % PAGE_ITEM == 0) {

				if (onScreenItems != null) {

					titleGridViews.add(bulidGridView(onScreenItems,
							currentScreen));
					currentScreen++;

				}

				onScreenItems = new ArrayList<MenuItem>();
			}

			// 最后一屏操作
			if (currentScreen > totalScreenNum + 1) {

				onScreenItems = new ArrayList<MenuItem>();
			}

			onScreenItems.add(item);

			// add last category screen //最后一屏
			if (i == menuItems.size() - 1) {

				titleGridViews.add(bulidGridView(onScreenItems, currentScreen));

			}

			i++;
		}

		gover_viewpagerLayout.setAdapter(new MenuItemViewPageAdapter(
				titleGridViews));// 设置ViewPage适配器

	}

	private GridView bulidGridView(List<MenuItem> items, int screenIndex) {
		GridView gridView = (GridView) mInflater.inflate(
				R.layout.gover_mange_title_gridview_layout, null);
		gridView.setColumnWidth(PAGE_ITEM);

		gridView.setAdapter(new MenuItemGridViewAdaptger(items, screenIndex));
		gridView.setOnItemClickListener(GridviewOnclick);
		return gridView;
	}

	static class ViewHolder {
		TextView tv_title;
	}

	private class MenuItemGridViewAdaptger extends BaseAdapter {

		public List<MenuItem> menuItems;
		public int screenIndex;

		public MenuItemGridViewAdaptger(List<MenuItem> menuItems,
				int screenIndex) {
			this.menuItems = menuItems;
			this.screenIndex = screenIndex;
		}

		@Override
		public int getCount() {
			return menuItems.size();
		}

		@Override
		public Object getItem(int position) {
			return menuItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MenuItem menuItem = menuItems.get(position);
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

			viewHolder.tv_title.setText(menuItem.getName());
			return convertView;
		}

	}

	private class MenuItemViewPageAdapter extends PagerAdapter {

		private List<View> mListViews;

		public MenuItemViewPageAdapter(List<View> mGridViews) {

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

		return R.layout.gover_saloon_administrative;
	}

}
