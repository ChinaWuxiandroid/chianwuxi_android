package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.adapter.OpenInfoDeptAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.engine.OpenInfoDeptService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.FifterContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.domain.OpenInfoDept;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政府信息公开 里 带有部门/区县 时间 的 内容列表搜索Fragment
 * 
 * @author 杨宸 智佳
 * */

public class GoverMsgSearchContentListFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	private Context context;

	// 部门数据加载成功
	private static final int LOAD_DEPT_SUCCESS = 1;
	// 部门数据加载失败
	private static final int LOAD_DEPT_FAILED = 2;
	// 年份数据加载成功
	private static final int LOAD_YEAR_SUCCESS = 3;
	// 年份数据加载失败
	private static final int LOAD_YEAR_FAILED = 4;
	// 搜索成功
	private static final int CONTENT_LOAD_SUCCESS = 5;
	// 搜搜失败
	private static final int CONTENT_LOAD_FAIL = 6;
	// 加载列表数据成功
	private static final int INFO_LOAD_SUCCESS = 7;
	// 加载列表数据失败
	private static final int INFO_LOAD_FAIL = 8;
	// 每页显示数据条数
	private static final int PAGE_SIZE = 10;

	// 列表控件对象
	private ListView content_list_lv;
	// 进度条对象
	private ProgressBar content_list_pb;
	// 内容
	private ContentWrapper contentWrapper;
	// 加载更多视图
	private View loadMoreView;
	// 加载更多按钮
	private Button loadMoreButton;
	// 最后一条索引
	private int visibleLastIndex;
	// 当前显示的总条数
	private int visibleItemNum;
	// 列表适配器
	private ContentListAdapter adapter;
	// 是不是加载更多
	private boolean isSwitch = false;
	// 是不是首次加载数据
	private boolean isFirstLoad = true;
	// 是否正在加载
	private boolean isLoading = false;
	// 加载更多进度条
	private ProgressBar pb_loadmoore;

	// 过滤包装类
	private FifterContentWrapper fifter;
	
	

	// 子菜单项对象
	private MenuItem parentItem;
	// 子频道对象
	private Channel subchannel;

	// 菜单对象
	private MenuItem parentMenuItem;
	// 频道对象
	private Channel channel;

	// 部门数据列表
	private List<OpenInfoDept> depts;

	// 搜索相关控件
	private Spinner partment_sp;
	private Spinner year_sp;
	private Button search_imbtn;

	// 搜索相关字段
	private static String DEFAULT_DEPT_FIFTER = "按部门筛选";
	private static String DEFAULT_ZONE_FIFTER = "按县区筛选";
	private String deptStrFifter = DEFAULT_DEPT_FIFTER;
	private String zoneStrFifter = DEFAULT_ZONE_FIFTER;
	// 年份默认今年
	private int DEFAULT_YEAR_FIFTER = 2013;
	private int yearFifter = DEFAULT_YEAR_FIFTER; // 2013

	public static final int DEPT_TYPE = 1;
	public static final int ZONE_TYPE = 2;
	private int filterType = DEPT_TYPE; // 检索过滤 类型 1-部门时间型(缺省类型) 2-区县时间型

	private int buttonCount = 0;

	private boolean isNull = false;

	private boolean isfirstsearch = true;

	public void setFifterType(int type) {
		this.filterType = type;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case LOAD_DEPT_SUCCESS:
				showDept();
				break;
			case LOAD_DEPT_FAILED:
				Toast.makeText(context, "部门信息加载失败", Toast.LENGTH_SHORT).show();
				break;
			case LOAD_YEAR_SUCCESS:
				Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
				break;
			case LOAD_YEAR_FAILED:
				Toast.makeText(context, "下载出错，稍后再试", Toast.LENGTH_SHORT).show();
				break;

			case INFO_LOAD_SUCCESS:

			case CONTENT_LOAD_SUCCESS:
				showContentData();
				break;

			case INFO_LOAD_FAIL:
			case CONTENT_LOAD_FAIL:
				String tip1 = msg.obj.toString();
				content_list_pb.setVisibility(ProgressBar.GONE);
				Toast.makeText(context, tip1, Toast.LENGTH_SHORT).show();
				break;

			}

		}
	};

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.govermsg_search_contentlist_layout,
				null);
		context = getActivity();

		buttonCount = 0;

		initView();
		initSearchUI();
		loadContentList();
		return view;
	}

	/**
	 * @方法： initView
	 * @描述： 初始化布局控件
	 */
	private void initView() {
		partment_sp = (Spinner) view
				.findViewById(R.id.govermsg_search_spinner_partment);
		year_sp = (Spinner) view
				.findViewById(R.id.govermsg_search_spinner_year);
		search_imbtn = (Button) view
				.findViewById(R.id.govermsg_search_button_search);
		search_imbtn.setOnClickListener(this);

		initFilter(filterType);
	}

	/**
	 * @方法： initSearchUI
	 * @描述： 初始化搜索布局
	 */
	private void initSearchUI() {
		String id = null;
		if (channel != null) {
			id = channel.getChannelId();
		} else if (parentMenuItem != null) {
			id = parentMenuItem.getChannelId();
		}
		fifter = new FifterContentWrapper(id);

		content_list_lv = (ListView) view
				.findViewById(R.id.gpm_content_list_lv);
		content_list_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				Content content = (Content) adapterView
						.getItemAtPosition(position);

				if (parentItem != null) {

					Intent intent = new Intent(context,
							GoverMsgContentDetailWebActivity.class);
					intent.putExtra("url", content.getWapUrl());
					intent.putExtra("fragmentTitle", parentItem.getName());
					MainTabActivity.instance.addView(intent);

				} else if (subchannel != null) {

					Intent intent = new Intent(context,
							GoverMsgContentDetailWebActivity.class);
					intent.putExtra("url", content.getWapUrl());
					intent.putExtra("fragmentTitle",
							subchannel.getChannelName());

					MainTabActivity.instance.addView(intent);
				}
			}
		});

		content_list_pb = (ProgressBar) view
				.findViewById(R.id.gpm_content_list_pb);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);

		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		isNull = false;
		buttonCount = 0;
		isfirstsearch = true;

		content_list_lv.addFooterView(loadMoreView);// 为listView添加底部视图
		content_list_lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
				int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				visibleItemNum = visibleItemCount;
				visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
			}
		});// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	/**
	 * @方法： changeChannelOrMenItem
	 * @描述： 切换频道和菜单
	 * @param channel
	 * @param menuItem
	 */
	public void changeChannelOrMenItem(Channel channel, MenuItem menuItem) {
		this.isSwitch = true;
		this.channel = channel;
		this.parentItem = menuItem;
		loadData(0, PAGE_SIZE);
	}

	/**
	 * @方法： initData
	 * @描述： 初次加载数据
	 * @param start
	 * @param end
	 */
	private void initData(final int start, final int end) {
		loadData(start, end);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 * @param start
	 * @param end
	 */
	private void loadData(final int start, final int end) {
		if (isFirstLoad || isSwitch) {
			content_list_pb.setVisibility(ProgressBar.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
					fifter.setStart(start);
					fifter.setEnd(end);
					contentWrapper = contentService
							.getPageContentsByUrl(getURL(fifter));
					if (contentWrapper != null) {
						msg.what = CONTENT_LOAD_SUCCESS;

					} else {
						msg.what = CONTENT_LOAD_FAIL;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = CONTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = CONTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = CONTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * @方法： loadMore
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMore(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_SIZE);
		}
	}

	/**
	 * @方法： showContentData
	 * @描述： 显示列表数据
	 */
	private void showContentData() {

		List<Content> contents = contentWrapper.getContents();

		if (contents != null && contents.size() > 0) {

			if (isFirstLoad) {
				adapter = new ContentListAdapter(contents, context);
				isFirstLoad = false;
				content_list_lv.setAdapter(adapter);
				content_list_pb.setVisibility(ProgressBar.GONE);
				isLoading = false;
			} else {

				if (isSwitch) {

					adapter.setContents(contents);
					content_list_pb.setVisibility(ProgressBar.GONE);
				} else {
					for (Content content : contents) {
						adapter.addItem(content);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				content_list_lv.setSelection(visibleLastIndex - visibleItemNum
						+ 1); // 设置选中项
				isLoading = false;
			}
			
			isNull = false;
		} else {
			isNull = true;
			
			content_list_pb.setVisibility(ProgressBar.GONE);
			Toast.makeText(context, "根据您的条件，检索的数据为空，请重新选择条件。",
					Toast.LENGTH_SHORT).show();
			adapter.setContents(contents);
			adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
			
		}

	
		if (contentWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
			
			if(content_list_lv.getFooterViewsCount()==0){
				content_list_lv.addFooterView(loadMoreView);
			}
		} else {
			
			if(adapter!=null){
				content_list_lv.removeFooterView(loadMoreView);
			}
			
			
			/*
			
			if (isfirstsearch) {
				if (buttonCount == 0 && isNull == false
						&& !contentWrapper.isNext()) {
					content_list_lv.removeFooterView(loadMoreView);
					System.out.println("4444444444");

				}
			}
			if (!isfirstsearch) {
				if (buttonCount < 0 && isNull == true) {
					content_list_lv.removeFooterView(loadMoreView);

				}
				if (buttonCount == 0 && isNull == false) {
					content_list_lv.removeFooterView(loadMoreView);

				}
				if (buttonCount > 0 && isNull == true
						&& !contentWrapper.isNext()) {
					content_list_lv.removeFooterView(loadMoreView);

				}
			}
			if (buttonCount == 0 && isNull == false && isfirstsearch == true
					&& !contentWrapper.isNext()) {
				content_list_lv.removeFooterView(loadMoreView);

			}*/
		}
	}

	/**
	 * @方法： initFilter
	 * @描述： 初始化筛选控件数据
	 * @param Type
	 */
	private void initFilter(int Type) {
		Calendar c = Calendar.getInstance();
		DEFAULT_YEAR_FIFTER = c.get(Calendar.YEAR); // 2013
		yearFifter = DEFAULT_YEAR_FIFTER;
		switch (Type) {
		case DEPT_TYPE:
			loadDeptData();
			initYearSpinner();
			break;
		case ZONE_TYPE:
			initCountrySpinner();
			initYearSpinner();
			break;
		}
	}

	/**
	 * 初始化区县过滤信息 （已屏蔽）
	 * */
	private void initCountrySpinner() {

		// ArrayAdapter<String> country_Spinner_adapter = new
		// ArrayAdapter<String>(context,
		// R.layout.my_simple_spinner_item_layout, zoneArr);
		// country_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_medium_dropdown_item);
		// partment_sp.setAdapter(country_Spinner_adapter);
		// partment_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View view,
		// int arg2, long arg3) {
		// zoneStrFifter=((TextView)view).getText().toString();
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });
		// partment_sp.setVisibility(View.VISIBLE);
		partment_sp.setVisibility(View.GONE);
	}

	/**
	 * @方法： loadDeptData
	 * @描述： 加载部门信息
	 */
	private void loadDeptData() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				OpenInfoDeptService infoDeptService = new OpenInfoDeptService(
						context);
				try {
					depts = infoDeptService
							.getOpenInfoDepts(Constants.Urls.OPEN_INFO_DEPT_URL);
					if (depts != null) {
						msg.what = LOAD_DEPT_SUCCESS;
						CacheUtil.put(Constants.CacheKey.DEPT_KEY, depts);// 放入缓存
					} else {
						msg.what = LOAD_DEPT_FAILED;
						msg.obj = "没有获取到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_FAILED;
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
	 * @方法： showDept
	 * @描述： 显示部门数据
	 */
	private void showDept() {
		OpenInfoDept deptDefault = new OpenInfoDept();
		deptDefault.setId("0");
		deptDefault.setName(DEFAULT_DEPT_FIFTER);
		depts.add(0, deptDefault);

		OpenInfoDeptAdapter partment_Spinner_adapter = new OpenInfoDeptAdapter(
				context, depts);
		if (partment_sp.getVisibility() == View.VISIBLE) {
			partment_sp.setAdapter(partment_Spinner_adapter);
			partment_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					deptStrFifter = depts.get(position).getName();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			partment_sp.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * @方法： initYearSpinner
	 * @描述： 初始化年份下拉框
	 */
	private void initYearSpinner() {
		List<String> years = TimeFormateUtil
				.getYears(TimeFormateUtil.START_YEAR);

		years.add(years.size(), "选择年份");

		int size = years.size();

		String[] yearStr = new String[size];

		for (int i = size - 1; i >= 0; i--) {
			yearStr[size - 1 - i] = years.get(i);
		}

		MyAryAdapter year_Spinner_adapter = new MyAryAdapter(context,
				android.R.layout.simple_spinner_item, yearStr);
		year_Spinner_adapter
				.setDropDownViewResource(R.layout.my_spinner_medium_dropdown_item);
		year_sp.setAdapter(year_Spinner_adapter);
		year_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				String buffYearStr = ((TextView) view).getText().toString();
				if (position != 0) {
					yearFifter = Integer.valueOf(buffYearStr);
				} else {
					yearFifter = DEFAULT_YEAR_FIFTER; // 表示按年份来
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		year_sp.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.govermsg_search_button_search:
			isSwitch = true;

			isNull = false;
			// buttonCount = 0;
			isfirstsearch = false;
			search();
			break;

		case R.id.loadMoreButton:
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录
				isSwitch=false;
				loadMoreButton.setText("loading.....");
				buttonCount += 1;
				loadMore(v);
			}
			break;
		}
	}

	/**
	 * @类名： MyAryAdapter
	 * @描述： 年份下拉框适配器
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-11 下午5:45:16
	 * @修改时间：
	 * @修改描述：
	 */
	public class MyAryAdapter extends ArrayAdapter<String> {

		Context context;
		String[] items = new String[] {};

		public MyAryAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);

			this.items = objects;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(16);
			tv.setPadding(3, 5, 5, 5);

			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(12);

			return convertView;
		}

	}

	/**
	 * @方法： search
	 * @描述： 搜索
	 */
	private void search() {
		switch (filterType) {
		// 按部门 时间 检索
		case DEPT_TYPE:
			if (DEFAULT_DEPT_FIFTER.equals(deptStrFifter))
				deptStrFifter = null;
			if (yearFifter == DEFAULT_YEAR_FIFTER)
				yearFifter = -1;
			fifter.setDept(deptStrFifter);
			fifter.setYear(yearFifter);
			
			
			System.out.println("按部门 时间 检索");

			break;
		// 按区县 时间 检索
		case ZONE_TYPE:
			if (DEFAULT_ZONE_FIFTER.equals(zoneStrFifter))
				zoneStrFifter = null;
			if (yearFifter == DEFAULT_YEAR_FIFTER)
				yearFifter = -1;
			fifter.setZone(zoneStrFifter);
			fifter.setYear(yearFifter);
			
			break;
		}

		if (parentMenuItem != null) {
			this.setParentItem(parentMenuItem);
		} else if (channel != null) {
			this.setSubChannel(channel);
		}
		initData(0, PAGE_SIZE);
	}

	/**
	 * @方法： loadContentList
	 * @描述： 第一次加载数据
	 */
	private void loadContentList() {

		if (parentMenuItem != null) {
			this.setParentItem(parentMenuItem);
		} else if (channel != null) {
			this.setSubChannel(channel);
		}

		loadData(0, PAGE_SIZE);
	}

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	public void setSubChannel(Channel channel) {
		this.subchannel = channel;
	}

	/**
	 * @方法： getURL
	 * @描述： 构建URL
	 * @param fifter
	 * @return
	 */
	private String getURL(FifterContentWrapper fifter) {
		String url = Constants.Urls.CHANNEL_CONTENT_P_URL
				.replace("{id}", fifter.getId())
				.replace("{start}", String.valueOf(fifter.getStart()))
				.replace("{end}", String.valueOf(fifter.getEnd()));
		String dept = fifter.getDept();
		String zone = fifter.getZone();
		String typeword = fifter.getTypeword();
		int year = fifter.getYear();

		if (dept != null && !"".equals(dept)) {
			url = url + "&dept=" + dept;
		}
		if (year != -1) {
			url = url + "&year=" + year;
		}
		if (typeword != null && !"".equals(typeword)) {
			url = url + "&typeword=" + typeword;
		}
		if (zone != null && !"".equals(zone)) {
			url = url + "&zone=" + zone;
		}

		return url;
	}
}
