package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.DeptSpinnerAdapter;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;
import com.wuxi.app.engine.DeptService;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Dept;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 行政事项
 * 
 */
@SuppressLint("HandlerLeak")
public class AdministrativeItemFragment extends GoverSaloonContentFragment
		implements OnPageChangeListener, OnClickListener, OnScrollListener,
		OnItemSelectedListener {

	private ListView gover_mange_lv;
	private ViewPager gover_viewpagerLayout;
	private ImageView gover_mange_iv_next;
	private List<View> titleGridViews;
	private static final int PAGE_ITEM = 4;

	protected static final int TITLE_ITEM_LOADSUCCESS = 0;
	protected static final int TITLE_ITEM_LOADFAIL = 1;
	protected static final int GOVERITEM_LOAD_SUCCESS = 2;
	protected static final int GOVERITEM_LOAD_FIAL = 3;
	private static final int PAGE_SIZE = 10;
	protected static final int LOAD_DEPT_SUCCESS = 4;
	protected static final int LOAD_DEPT_FAIL = 5;
	private int checkPositons[];// 选中的坐标
	private int pageNo = 0;
	private Spinner sp_dept;
	private MenuItem menuItem;
	private List<MenuItem> menuItems;
	private ProgressBar pb_mange;
	private boolean isFirstLoadGoverItem = true;
	private boolean isSwitch = false;
	private GoverSaoonItemWrapper goverSaoonItemWrapper;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private GoverOnlineApproveAdapter goverOnlineApproveAdapter;
	private List<Dept> depts;
	private String qltype = null;// 当前查询类型
	private String deptid = null;// 当前查询的部门
	private String year = null;// 当前的年
	private Spinner sp_dept_year;
	private ImageButton govver_admintrative_ib_search;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TITLE_ITEM_LOADSUCCESS:
				showTitleData();

				break;
			case LOAD_DEPT_SUCCESS:// 部门加载成功
				showDept();
				break;
			case GOVERITEM_LOAD_SUCCESS:
				showItemList();// 显示办件列表
				break;
			case LOAD_DEPT_FAIL:
			case GOVERITEM_LOAD_FIAL:
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
		pb_mange = (ProgressBar) view.findViewById(R.id.pb_mange);
		gover_mange_iv_next = (ImageView) view
				.findViewById(R.id.gover_mange_iv_next);
		sp_dept = (Spinner) view.findViewById(R.id.sp_dept);// 时间
		sp_dept_year = (Spinner) view.findViewById(R.id.sp_dept_year);// 年
		govver_admintrative_ib_search = (ImageButton) view
				.findViewById(R.id.govver_admintrative_ib_search);
		sp_dept.setOnItemSelectedListener(this);
		gover_viewpagerLayout.setOnPageChangeListener(this);
		gover_mange_iv_next.setOnClickListener(this);
		govver_admintrative_ib_search.setOnClickListener(this);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		gover_mange_lv.addFooterView(loadMoreView);
		gover_mange_lv.setOnScrollListener(this);
		menuItem = (MenuItem) getArguments().get("menuItem");

		loadTitleItems(menuItem.getId());// 加载滑动菜单
		loadDept();// 加载部门
		initYear();

	}

	private void initYear() {

		List<String> years = TimeFormateUtil
				.getYears(TimeFormateUtil.START_YEAR);
		years.add(0, "按年份");
		sp_dept_year.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, years));
		sp_dept_year.setOnItemSelectedListener(this);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取部门信息
	 */
	@SuppressWarnings("unchecked")
	private void loadDept() {

		/*
		 * if (CacheUtil.get(Constants.CacheKey.DEPT_KEY) != null) { depts =
		 * (List<Dept>) CacheUtil.get(Constants.CacheKey.DEPT_KEY); showDept();
		 * return; }
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				DeptService deptService = new DeptService(context);
				try {
					depts = deptService.getDepts();
					if (depts != null) {
						msg.what = LOAD_DEPT_SUCCESS;
						CacheUtil.put(Constants.CacheKey.DEPT_KEY, depts);// 放入缓存
					} else {
						msg.what = LOAD_DEPT_FAIL;
						msg.obj = "没有获取到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_SUCCESS;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示部门信息
	 */
	private void showDept() {

		if (isFirstLoadGoverItem) {
			Dept dept = new Dept("按部门赛选");
			depts.add(0, dept);
		}

		sp_dept.setAdapter(new DeptSpinnerAdapter(depts, context));

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

		// 显示第一个数据
		this.qltype = getType(menuItems.get(0));
		Map<String, String> parmsMap = buildParams(null, qltype, deptid, year,
				0, PAGE_SIZE);
		loadItem(parmsMap);

	}

	/**
	 * 获取办件信息
	 */
	private void loadItem(final Map<String, String> params) {

		if (isFirstLoadGoverItem || isSwitch) {// 首次加载时或切换部门时显示进度条

			pb_mange.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				GoverSaoonItemService goverSaoonItemService = new GoverSaoonItemService(
						context);
				try {
					goverSaoonItemWrapper = goverSaoonItemService
							.getGoverSaoonItemsByParas(params);
					if (goverSaoonItemWrapper != null) {
						msg.what = GOVERITEM_LOAD_SUCCESS;
					} else {
						msg.what = GOVERITEM_LOAD_FIAL;
						msg.obj = "加载办件信息失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();

					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示办件事项详细信息
	 */
	protected void showItemList() {

		if (goverSaoonItemWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {
			loadMoreButton.setText("没有数据了...");
		}

		List<GoverSaoonItem> goverSaoonItems = goverSaoonItemWrapper
				.getGoverSaoonItems();
		if (goverSaoonItems != null && goverSaoonItems.size() > 0) {
			if (isFirstLoadGoverItem) {// 首次加载
				goverOnlineApproveAdapter = new GoverOnlineApproveAdapter(
						goverSaoonItems, context);
				isFirstLoadGoverItem = false;
				gover_mange_lv.setAdapter(goverOnlineApproveAdapter);
				pb_mange.setVisibility(ProgressBar.GONE);

			} else {

				if (isSwitch) {// 切换部门
					goverOnlineApproveAdapter
							.setGoverSaoonItems(goverSaoonItems);
					pb_mange.setVisibility(ProgressBar.GONE);
				} else {
					for (GoverSaoonItem item : goverSaoonItems) {
						goverOnlineApproveAdapter.addItem(item);
					}
				}

				goverOnlineApproveAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_mange_lv.setSelection(visibleLastIndex - visibleItemCount
						+ 1); // 设置选中项

			}

		}

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

			MenuItem menuItem = (MenuItem) parent.getItemAtPosition(position);
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

			isSwitch = true;
			qltype = getType(menuItem);// 记录当前选中的type
			Map<String, String> params = buildParams(null, qltype, deptid,
					year, 0, PAGE_SIZE);
			loadItem(params);

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
		case R.id.govver_admintrative_ib_search:
			isSwitch = true;
			Map<String, String> paMap = buildParams(null, qltype, deptid, year,
					0, PAGE_SIZE);
			loadItem(paMap);
			break;

		}

	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_saloon_administrative;
	}

	/**
	 * temname false String 办件名称，模糊查询 qltype false String 办件类型 deptid false
	 * String 部门id year true Int 年份 start true Int 分页开始 end true Int 分页结束 wanglu
	 * 泰得利通
	 * 
	 * @return
	 */
	public Map<String, String> buildParams(String itemname, String qltype,
			String deptid, String year, int start, int end) {

		Map<String, String> params = new HashMap<String, String>();
		if (itemname != null) {
			params.put("itemname", itemname);
		}

		if (qltype != null) {
			params.put("qltype", qltype);
		}

		if (deptid != null) {
			params.put("deptid", deptid);
		}

		if (year != null) {
			params.put("year", year);
		}

		params.put("start", start + "");
		params.put("end", end + "");

		return params;
	}

	/**
	 * 
	 * wanglu 泰得利通 获取分类
	 * 
	 * @param menuItem
	 * @return
	 */
	public String getType(MenuItem menuItem) {

		String name = menuItem.getName();
		if (name.contains("许可")) {
			return "XK";
		} else if (name.contains("处罚")) {
			return "CF";
		} else if (name.contains("验收")) {
			return "YS";
		} else if (name.contains("强制")) {
			return "QZ";
		} else if (name.contains("其它")) {
			return "QT";
		}

		return "";

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		int itemsLastIndex = goverOnlineApproveAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (goverSaoonItemWrapper != null && goverSaoonItemWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitch = false;

				Map<String, String> parms = buildParams(null, qltype, deptid,
						year, visibleLastIndex + 1, visibleLastIndex + 1
								+ PAGE_SIZE);

				loadItem(parms);

			}

		}
	}

	/**
	 * 部门选择事件 wanglu 泰得利通
	 * 
	 * @param parent
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {

		Object o = parent.getItemAtPosition(position);

		if (o instanceof Dept) {
			Dept dept = (Dept) o;
			this.deptid = dept.getId();
		} else {

			String year = (String) o;
			if (!year.equals("按年份")) {
				this.year = year;
			}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}
