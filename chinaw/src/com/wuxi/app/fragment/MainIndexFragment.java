package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.IndexGridAdapter;
import com.wuxi.app.adapter.IndexNewsListAdapter;
import com.wuxi.app.engine.AnnouncementsService;
import com.wuxi.app.engine.ImportNewsService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Content;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author 主页界面
 * 
 */
public class MainIndexFragment extends BaseFragment implements
		OnCheckedChangeListener {

	private View view;
	private Context context;
	private ProgressBar pb;
	private LayoutInflater mInflater;

	/** —————————ListView——————————— **/
	private ListView listView;
	private IndexNewsListAdapter listAdapter;
	public static final int[] newslist_viewid = { R.id.index_num_text,
			R.id.index_news_title };
	public static final String[] newslist_dataName = { "num_text", "title_text" };

	/** ————Gridview—————— **/

	private IndexGridAdapter gridAdapter;
	public static final int[] Grid_viewid = { R.id.index_gridview_image,
			R.id.index_gridview_texttitle };

	private RadioGroup main_tab_radiogroup;
	private List<MenuItem> menuItems;// 菜单数据
	private static final int MENUITEM_LOAD_SUCESS = 1;// 菜单加载成功标识
	private static final String MENUITEM_CACKE_KEY = "man_menu_item";
	private static final String ANNOUNCE_CACHE_KEY = "announce";
	private static final String NEW_CACHE_KEY = "news";
	private static final int MENUITEM_LOAD_ERROR = 0;// 菜单加载失败标志
	private static final int ANNOUNCE_LOAD_SUCCESS = 2;// 公共列表加载成功
	private static final int ANNOUNCE_LOAD_FAIL = 3;// 公共列表加载失败
	private static final int NEWS_LOAD_SUCCESS = 4;// 新闻加载成功
	private static final int NEWS_LOAD_FAIL = 5;// 新闻加载失败
	private static final int PAGE_ITEM = 6;// 每屏显示菜单数目;
	private List<View> mGridViews;
	private ViewPager viewpagerLayout;
	private List<Content> announcements;// 公告集合
	private List<Content> news;// 新闻集合

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MENUITEM_LOAD_SUCESS:

				pb.setVisibility(ProgressBar.GONE);
				showGridData();

				break;
			case ANNOUNCE_LOAD_SUCCESS:
				showAnnouncements();
				break;
			case NEWS_LOAD_SUCCESS:
				showNews();
				break;
			case ANNOUNCE_LOAD_FAIL:
			case NEWS_LOAD_FAIL:

			case MENUITEM_LOAD_ERROR:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_LONG).show();
				break;

			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.main_index_fragment_layout, null);
		context = this.getActivity();
		mInflater = inflater;
		initUI();
		return view;
	}

	private void initUI() {

		listView = (ListView) view.findViewById(R.id.index_news_list);
		pb = (ProgressBar) view.findViewById(R.id.index_progess);
		viewpagerLayout = (ViewPager) view.findViewById(R.id.viewpagerLayout);
		main_tab_radiogroup = (RadioGroup) view
				.findViewById(R.id.main_tab_radiogroup);
		main_tab_radiogroup.setOnCheckedChangeListener(this);

		LoadGrid();
		loadNews();// 加载新闻数据

	}

	/**
	 * 
	 * wanglu 泰得利通 加载菜单数据
	 */
	@SuppressWarnings("unchecked")
	private void LoadGrid() {

		if (CacheUtil.get(MENUITEM_CACKE_KEY) != null) {// 从缓存加载

			menuItems = (List<MenuItem>) CacheUtil.get(MENUITEM_CACKE_KEY);
			pb.setVisibility(ProgressBar.GONE);
			showGridData();
			return;
		}

		new Thread(new Runnable() {// 加载首页MenuItem数据

					@Override
					public void run() {

						MenuService menuSevice = new MenuService(context);
						try {
							menuItems = menuSevice
									.getHomeMenuItems(Constants.Urls.MENU_URL
											+ "?recursions=0");
							if (menuItems != null) {
								handler.sendEmptyMessage(MENUITEM_LOAD_SUCESS);// 发送消息
								CacheUtil.put(MENUITEM_CACKE_KEY, menuItems);// 将菜单数据放入缓存
							} else {
								Message msg = handler.obtainMessage();
								msg.what = MENUITEM_LOAD_ERROR;
								msg.obj = "加载错误";
								handler.sendMessage(msg);// 加载错误
							}
						} catch (NetException e) {
							e.printStackTrace();
							Message msg = handler.obtainMessage();
							msg.obj = e.getMessage();
							msg.what = MENUITEM_LOAD_ERROR;
							handler.sendMessage(msg);// 加载错误
						} catch (JSONException e) {
							e.printStackTrace();
							Message msg = handler.obtainMessage();
							msg.obj = "网络格式出错";
							msg.what = MENUITEM_LOAD_ERROR;
							handler.sendMessage(msg);// 加载错误
						} catch (NODataException e) {
							e.printStackTrace();
							Message msg = handler.obtainMessage();
							msg.obj = "获取数据异常";
							msg.what = MENUITEM_LOAD_ERROR;
							handler.sendMessage(msg);// 加载错误
						}

					}
				}

		).start();

	}

	/**
	 * 显示菜单数据
	 */
	private void showGridData() {

		int i = 0;
		List<MenuItem> onScreenItems = null;
		mGridViews = new ArrayList<View>();
		int currentScreen = 0;// 当前屏

		int totalScreenNum = menuItems.size() / PAGE_ITEM;// 屏数
		for (MenuItem item : menuItems) {

			if (i % PAGE_ITEM == 0) {

				if (onScreenItems != null) {

					currentScreen++;
					mGridViews.add(bulidGridView(onScreenItems));

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

				mGridViews.add(bulidGridView(onScreenItems));

			}

			i++;
		}

		viewpagerLayout.setAdapter(new MenuItemViewPageAdapter(mGridViews));// 设置ViewPage适配器

	}

	/**
	 * 
	 * wanglu 泰得利通 构建gridview
	 * 
	 * @param items
	 *            数据
	 * @return
	 * 
	 */
	private GridView bulidGridView(List<MenuItem> items) {
		GridView gridView = (GridView) mInflater.inflate(
				R.layout.index_gridview_layout, null);

		gridAdapter = new IndexGridAdapter(context,
				R.layout.index_gridview_item_layout, Grid_viewid, items, null);
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(GridviewOnclick);
		return gridView;
	}

	/**
	 * 
	 * wanglu 泰得利通 显示新闻或推荐信息数据
	 * 
	 * @param list
	 * 
	 */
	private void showNewsOrAnncountData(List<Content> list) {

		listAdapter = new IndexNewsListAdapter(context,
				R.layout.index_newslist_layout, newslist_viewid, list,
				newslist_dataName);
		listView.setAdapter(listAdapter);
		setViewHeight(listView);
	}

	/**
	 * 
	 * wanglu 泰得利通 计算listView高度
	 * 
	 * @param listView
	 */
	public void setViewHeight(ListView listView) {
		IndexNewsListAdapter listAdapter = (IndexNewsListAdapter) listView
				.getAdapter();
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

	/**
	 * 菜单点击
	 */
	private OnItemClickListener GridviewOnclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			MenuItem checkMenuItem = (MenuItem) parent
					.getItemAtPosition(position);
			SlideLevelFragment saveFragment = new SlideLevelFragment();
			saveFragment.setManagers(managers);
			saveFragment.setPosition(position);
			saveFragment.setMenuItem(checkMenuItem);//
			managers.IntentFragment(saveFragment);
		}
	};

	/**
	 * 
	 * @author wanglu 泰得利通 导航分页适配器
	 * 
	 */
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
	 * 
	 * wanglu 泰得利通 推荐新闻和推荐信息点击事件处理
	 * 
	 * @param group
	 * @param checkedId
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.index_rb_announcements:// 推荐公告
			loadAnnouncements();
			break;
		case R.id.index_rb_news:// 新闻
			loadNews();// 加载信息Content
			break;

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 加载新闻数据
	 */
	@SuppressWarnings("unchecked")
	private void loadNews() {

		if (news != null) {
			showNews();
			return;
		} else if ((CacheUtil.get(NEW_CACHE_KEY)) != null) {//看缓存中有没有
			news = (List<Content>) CacheUtil.get(NEW_CACHE_KEY);
			showNews();
			return;

		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				ImportNewsService importNewsService = new ImportNewsService(
						context);
				try {
					news = importNewsService.getImportNews();
					if (news != null) {
						handler.sendEmptyMessage(NEWS_LOAD_SUCCESS);
						CacheUtil.put(NEW_CACHE_KEY, news);// 放入缓存
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = "获取新闻列表失败";
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = "数据格式错误";
					msg.what = NEWS_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = NEWS_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = NEWS_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}

		).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示新闻数据
	 */
	private void showNews() {

		showNewsOrAnncountData(news);
	}

	/**
	 * 
	 * wanglu 泰得利通 加载推荐公告信息
	 */
	@SuppressWarnings("unchecked")
	private void loadAnnouncements() {

		if (announcements != null) {
			showAnnouncements();
			return;
		}else if((CacheUtil.get(ANNOUNCE_CACHE_KEY))!=null){//查看缓存数据
			announcements=(List<Content>) CacheUtil.get(ANNOUNCE_CACHE_KEY);
			showAnnouncements();
			return  ;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				AnnouncementsService announcementsService = new AnnouncementsService(
						context);
				try {
					announcements = announcementsService.getAnnouncements();
					if (announcements != null) {
						handler.sendEmptyMessage(ANNOUNCE_LOAD_SUCCESS);
						CacheUtil.put(ANNOUNCE_CACHE_KEY, announcements);//放入缓存
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = "获取公告列表失败";
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = ANNOUNCE_LOAD_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = ANNOUNCE_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = ANNOUNCE_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}

		).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示推荐信息数据
	 */
	private void showAnnouncements() {
		showNewsOrAnncountData(announcements);
	}
}
