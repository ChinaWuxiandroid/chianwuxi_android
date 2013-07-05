package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.IndexGridAdapter;
import com.wuxi.app.adapter.IndexNewsListAdapter;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.index.SlideLevelFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author 主页界面
 * 
 */
public class MainIndexFragment extends BaseFragment {

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
	private GridView gridView;
	private IndexGridAdapter gridAdapter;
	public static final int[] Grid_viewid = { R.id.index_gridview_image,
			R.id.index_gridview_texttitle };
	
	private List<MenuItem> menuItems;// 菜单数据
	private static final int MENUITEM_LOAD_SUCESS = 1;// 菜单加载成功标识
	private static final String MENUITEM_CACKE_KEY = "man_menu_item";
	protected static final int MENUITEM_LOAD_ERROR = 0;// 菜单加载失败标志
	

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MENUITEM_LOAD_SUCESS:

				pb.setVisibility(ProgressBar.GONE);
				showGridData();

				break;
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
		mInflater=inflater;
		initUI();
		return view;
	}

	private void initUI() {
		gridView = (GridView) view.findViewById(R.id.gridview);
		
		listView = (ListView) view.findViewById(R.id.index_news_list);
		pb = (ProgressBar) view.findViewById(R.id.index_progess);
		loadList();
		LoadGrid();
		gridView.setOnItemClickListener(GridviewOnclick);
	}

	/**
	 * 加载菜单数据
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
		
		
		int size = menuItems.size();
		int column;
		if (size % 2 == 0) {
			column = size / 2;

		} else {
			column = size / 2 + 1;
		}
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int allWidth = (int) (110 * (column) * density);
		int itemWidth = (int) (100 * density);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				allWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
		gridView.setLayoutParams(params);
		gridView.setColumnWidth(itemWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		
		gridView.setNumColumns(column);
		gridAdapter = new IndexGridAdapter(context,
				R.layout.index_gridview_item_layout, Grid_viewid, menuItems, null);
		gridView.setAdapter(gridAdapter);
	}

	private void loadList() {
		List<Map<String, Object>> list = getListData();
		listAdapter = new IndexNewsListAdapter(context,
				R.layout.index_newslist_layout, newslist_viewid, list,
				newslist_dataName);
		listView.setAdapter(listAdapter);
		setViewHeight(listView);
	}

	// 新闻 公告的测试数据
	private List<Map<String, Object>> getListData() {
		String[] name = { "黄丽鑫：学习吴仁宝同志 学习华西经验 扎实推进基层党组织建设",
				"抓紧舒楠现代化建设示范区 与时俱进开创“两个率先”新局面" };
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(newslist_dataName[0], i + 1);
			map.put(newslist_dataName[1], name[i]);
			list.add(map);
		}
		return list;
	}

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
}
