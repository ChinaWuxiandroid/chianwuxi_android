package com.wuxi.app.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.FourTopicActivity;
import com.wuxi.app.activity.homepage.LearActivity;
import com.wuxi.app.activity.homepage.NewsAnnAcountActivitiy;
import com.wuxi.app.activity.homepage.informationcenter.InformationCenterActivity;
import com.wuxi.app.adapter.IndexGridAdapter;
import com.wuxi.app.adapter.IndexNewsListAdapter;
import com.wuxi.app.engine.AnnouncementsService;
import com.wuxi.app.engine.DownLoadTask;
import com.wuxi.app.engine.ImportNewsService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.engine.UpdateInfoService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.Constants.CacheKey;
import com.wuxi.app.util.MenuItemChanelUtil;
import com.wuxi.domain.Content;
import com.wuxi.domain.MenuItem;
import com.wuxi.domain.UpdateInfo;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 首页
 * 
 */
public class MainIndexActivity extends Activity implements
		OnCheckedChangeListener, OnPageChangeListener, OnClickListener {

	private ProgressBar pb;

	/** —————————ListView——————————— **/
	// private ListView listView;
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

	private static final String MENUITEM_CACKE_KEY = Constants.CacheKey.HOME_MENUITEM_KEY;

	private static final String ANNOUNCE_CACHE_KEY = "announce";

	private static final String NEW_CACHE_KEY = "news";

	private static final int MENUITEM_LOAD_ERROR = 0;// 菜单加载失败标志

	private static final int ANNOUNCE_LOAD_SUCCESS = 2;// 公共列表加载成功

	private static final int ANNOUNCE_LOAD_FAIL = 3;// 公共列表加载失败

	private static final int NEWS_LOAD_SUCCESS = 4;// 新闻加载成功

	private static final int NEWS_LOAD_FAIL = 5;// 新闻加载失败

	private static final int PAGE_ITEM = 6;// 每屏显示菜单数目;

	protected static final int UPDATE_APK = 7;

	protected static final int NO_UPDATE_APK = 8;

	public static final int DOWLOAD_ERROR = 9;

	private List<View> mGridViews;

	private List<View> mListViews = new ArrayList<View>();

	private ViewPager viewpagerLayout;

	private ViewPager index_title_news_page;

	private List<Content> announcements;// 公告集合

	private List<Content> news;// 新闻集合

	private RadioButton index_rb_news, index_rb_announcements;

	private ImageView iv_index_ldhd;// 领导活动集锦

	private ImageView iv_index_zt;// 四个专题

	private UpdateInfo updateInfo;

	private ProgressDialog pd;

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
			case UPDATE_APK:
				showUpdatDialog();
				break;
			case DOWLOAD_ERROR:
				Toast.makeText(MainIndexActivity.this, "下载出错",
						Toast.LENGTH_LONG).show();
				break;
			case ANNOUNCE_LOAD_FAIL:
			case NEWS_LOAD_FAIL:

			case MENUITEM_LOAD_ERROR:
				String tip = msg.obj.toString();
				Toast.makeText(MainIndexActivity.this, tip, Toast.LENGTH_LONG)
						.show();
				break;

			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_index_fragment_layout);

		initUI();
	}

	private void initUI() {

		pb = (ProgressBar) findViewById(R.id.index_progess);
		viewpagerLayout = (ViewPager) findViewById(R.id.viewpagerLayout);
		index_title_news_page = (ViewPager) findViewById(R.id.index_title_news_page);// 首页分页

		for (int i = 0; i < 2; i++) {
			mListViews.add(bulidListView(i));
		}
		index_title_news_page
				.setAdapter(new MainDataViewPageAdapter(mListViews));// 设置适配器
		index_title_news_page.setOnPageChangeListener(this);
		main_tab_radiogroup = (RadioGroup) findViewById(R.id.main_tab_radiogroup);
		main_tab_radiogroup.setOnCheckedChangeListener(this);

		index_rb_news = (RadioButton) findViewById(R.id.index_rb_news);
		index_rb_announcements = (RadioButton) findViewById(R.id.index_rb_announcements);
		index_rb_news.setOnClickListener(this);
		index_rb_announcements.setOnClickListener(this);

		index_rb_news.setBackgroundResource(R.drawable.index_news_pre);
		index_rb_announcements.setBackgroundResource(R.drawable.index_news);
		iv_index_ldhd = (ImageView) findViewById(R.id.iv_index_ldhd);
		iv_index_zt = (ImageView) findViewById(R.id.iv_index_zt);
		iv_index_ldhd.setOnClickListener(this);
		iv_index_zt.setOnClickListener(this);
		LoadGrid();
		loadNews();// 加载新闻数据
		loadAnnouncements();

		pd = new ProgressDialog(this);

		pd.setMessage("正在下载");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

		if (MainTabActivity.instance.fistLoadAPP) {
			checkUpdate();// 监测软件是否有更新
			MainTabActivity.instance.fistLoadAPP = false;
		}

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

						MenuService menuSevice = new MenuService(
								MainIndexActivity.this);
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

		viewpagerLayout.setAdapter(new MainDataViewPageAdapter(mGridViews));// 设置ViewPage适配器

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
		GridView gridView = (GridView) getLayoutInflater().inflate(
				R.layout.index_gridview_layout, null);

		gridAdapter = new IndexGridAdapter(this,
				R.layout.index_gridview_item_layout, Grid_viewid, items, null);
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(GridviewOnclick);
		return gridView;
	}

	/**
	 * 
	 * @author wanglu 泰得利通
	 * @return
	 */
	private ListView bulidListView(int type) {

		View layoutView = getLayoutInflater().inflate(
				R.layout.index_news_announts_list_layout, null);
		ListView listView = (ListView) layoutView
				.findViewById(R.id.index_news_lv);
		listView.setOnItemClickListener(new NewsOrAnncountClick(type));
		return listView;

	}

	/**
	 * 
	 * @author wanglu 泰得利通
	 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00 公告和新闻点击事件
	 * 
	 */
	private final class NewsOrAnncountClick implements OnItemClickListener {

		private int type;

		public NewsOrAnncountClick(int type) {
			this.type = type;
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View arg1,
				int position, long arg3) {

			Content content = (Content) adapterView.getItemAtPosition(position);

			Intent intent = new Intent(MainIndexActivity.this,
					NewsAnnAcountActivitiy.class);

			intent.putExtra("content", content);
			intent.putExtra(NewsAnnAcountActivitiy.SHOWTYPE_KEY, type);// 显示

			MainTabActivity.instance.addView(intent);

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示新闻或推荐信息数据
	 * 
	 * @param list
	 * 
	 */
	private void showNewsOrAnncountData(ListView listView, List<Content> list) {
		if (list.size() == 3) {
			list.remove(2);
		}
		listAdapter = new IndexNewsListAdapter(this,
				R.layout.index_newslist_layout, newslist_viewid, list,
				newslist_dataName);
		listView.setAdapter(listAdapter);

	}

	/**
	 * 
	 * wanglu 泰得利通 计算listView高度
	 * 
	 * @param listView
	 */
	public int getListViewHeight(ListView listView) {
		IndexNewsListAdapter listAdapter = (IndexNewsListAdapter) listView
				.getAdapter();

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		return params.height;

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

			Class<?> acClass = MenuItemChanelUtil
					.getActivityClassByName(checkMenuItem);

			if (acClass != null) {
				Intent intent = new Intent(MainIndexActivity.this, acClass);
				intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY,
						position);

				MainTabActivity.instance.addView(intent);
			}

		}
	};

	/**
	 * 
	 * @author wanglu 泰得利通 导航分页适配器
	 * 
	 */
	private class MainDataViewPageAdapter extends PagerAdapter {

		private List<View> mListViews;

		public MainDataViewPageAdapter(List<View> mGridViews) {

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
		} else if ((CacheUtil.get(NEW_CACHE_KEY)) != null) {// 看缓存中有没有
			news = (List<Content>) CacheUtil.get(NEW_CACHE_KEY);
			showNews();
			return;

		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				ImportNewsService importNewsService = new ImportNewsService(
						MainIndexActivity.this);
				try {
					news = importNewsService.getImportNews();
					if (news != null) {
						handler.sendEmptyMessage(NEWS_LOAD_SUCCESS);
						CacheUtil.put(NEW_CACHE_KEY, news);// 放入缓存
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = "获取要闻列表失败";
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
		ListView listView = (ListView) mListViews.get(0);
		showNewsOrAnncountData(listView, news);

		index_title_news_page.getLayoutParams().height = getListViewHeight(listView);
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
		} else if ((CacheUtil.get(ANNOUNCE_CACHE_KEY)) != null) {// 查看缓存数据
			announcements = (List<Content>) CacheUtil.get(ANNOUNCE_CACHE_KEY);
			showAnnouncements();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				AnnouncementsService announcementsService = new AnnouncementsService(
						MainIndexActivity.this);
				try {
					announcements = announcementsService.getAnnouncements();
					if (announcements != null) {
						handler.sendEmptyMessage(ANNOUNCE_LOAD_SUCCESS);
						CacheUtil.put(ANNOUNCE_CACHE_KEY, announcements);// 放入缓存
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
		ListView listView = (ListView) mListViews.get(1);
		showNewsOrAnncountData(listView, announcements);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:

			index_rb_news.setBackgroundResource(R.drawable.index_news_pre);
			index_rb_announcements.setBackgroundResource(R.drawable.index_news);
			loadNews();
			break;
		case 1:

			index_rb_news.setBackgroundResource(R.drawable.index_news);
			index_rb_announcements
					.setBackgroundResource(R.drawable.index_news_pre);
			loadAnnouncements();
			break;
		}

	}

	@Override
	public void onClick(View v) {
		if (CacheUtil.get(CacheKey.HOME_MENUITEM_KEY) == null) {
			Toast.makeText(this, "数据异常，请重启，或检查网络", Toast.LENGTH_SHORT).show();
			return;
		}

		Intent intent = null;
		switch (v.getId()) {
		case R.id.index_rb_news:// 无锡要闻

			intent = new Intent(MainIndexActivity.this,
					InformationCenterActivity.class);
			intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 2);
			intent.putExtra(Constants.CheckPositionKey.LEVEL_ONE_KEY, 1);
			break;
		case R.id.index_rb_announcements:// 推荐公告
			intent = new Intent(MainIndexActivity.this,
					InformationCenterActivity.class);
			intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 2);
			intent.putExtra(Constants.CheckPositionKey.LEVEL_ONE_KEY, 2);
			break;
		case R.id.iv_index_ldhd:

			intent = new Intent(MainIndexActivity.this, LearActivity.class);
			break;
		case R.id.iv_index_zt:

			intent = new Intent(MainIndexActivity.this, FourTopicActivity.class);
			break;

		}

		if (intent != null) {
			// IndexActivity.instance.addView(intent);
			MainTabActivity.instance.addView(intent);
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 获取软件版本
	 * 
	 * @return
	 */
	private String getVersion() {
		PackageManager pm = this.getPackageManager();
		try {
			PackageInfo paInfo = pm.getPackageInfo(this.getPackageName(), 0);
			return paInfo.versionName;
		} catch (NameNotFoundException e) {

			e.printStackTrace();
			return "未知版本";
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 安装APK
	 * 
	 * @param file
	 */
	private void install(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		this.finish();
		startActivity(intent);

	}

	/**
	 * 
	 * wanglu 泰得利通 是否要更新
	 * 
	 * @return
	 */
	public boolean isUpdate() {

		String oldVerson = getVersion();
		UpdateInfoService updateInfoService = new UpdateInfoService(this);
		try {
			updateInfo = updateInfoService.getUpdateInfo(R.string.updateurl);
			if (!updateInfo.getVersion().equals(oldVerson)) {

				return true;
			} else {

				return false;
			}

		} catch (Exception e) {

			// Toast.makeText(context, "监测更新出错", Toast.LENGTH_SHORT).show();

			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 监测更新 wanglu 泰得利通
	 */
	private void checkUpdate() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				boolean update = isUpdate();
				if (update) {
					handler.sendEmptyMessage(UPDATE_APK);
				} else {
					handler.sendEmptyMessage(NO_UPDATE_APK);
				}
			}
		}).start();
	}

	/**
	 * 更新对话框 wanglu 泰得利通
	 */
	private void showUpdatDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.logo);
		builder.setTitle("更新消息");
		builder.setMessage("版本" + updateInfo.getVersion() + " 更新信息:"
				+ updateInfo.getDescription());
		builder.setCancelable(false);

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {

							File file = new File(
									Constants.APPFiles.DOWNLOAF_FILE_PATH);
							if (!file.exists()) {
								file.mkdirs();
							}
							DownLoadThreadTask dowTask = new DownLoadThreadTask(
									updateInfo.getUrl(),
									Constants.APPFiles.DOWNLOAF_FILE_PATH
											+ "chinawuxi.apk");

							new Thread(dowTask).start();
							pd.show();

						} else {
							Toast.makeText(MainIndexActivity.this, "SDK不存在",
									Toast.LENGTH_SHORT).show();

						}

					}
				});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				});

		builder.create().show();

	}

	private class DownLoadThreadTask implements Runnable {

		private String path;

		private String filePath;

		public DownLoadThreadTask(String path, String filePath) {

			this.path = path;
			this.filePath = filePath;
		}

		@Override
		public void run() {

			try {
				File file = DownLoadTask.dowLoadNewSoft(path, filePath, pd);
				pd.dismiss();
				install(file);// 安装
			} catch (Exception e) {

				e.printStackTrace();
				handler.sendEmptyMessage(DOWLOAD_ERROR);
				pd.dismiss();

			}

		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {

				MainTabActivity.instance.pop();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
