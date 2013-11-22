package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.ArrayList;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailCFActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQTActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQZActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailXKActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailZSActivtiy;
import com.wuxi.app.adapter.AdministrativeAdapter;
import com.wuxi.app.adapter.CityGoverInfoContentListAdapter;
import com.wuxi.app.adapter.DeptSpinnerAdapter;
import com.wuxi.app.adapter.GovernmentGeneralizeAdapter;
import com.wuxi.app.adapter.OpenInfoDeptAdapter;
import com.wuxi.app.adapter.PolicieRegulationAdapter;
import com.wuxi.app.engine.AdministrativeService;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.engine.DeptService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.engine.OpenInfoDeptService;
import com.wuxi.app.listeners.GoverMsgInitInfoOpenListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.AdministrativeCon;
import com.wuxi.domain.AdministrativeWrapper;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.Dept;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.MenuItem;
import com.wuxi.domain.OpenInfoDept;
import com.wuxi.domain.OpenInfoSearchCondition;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 市政府信息公开目录 里的 可扩展列表
 * 
 * @author 杨宸 智佳
 * */

@SuppressLint("HandlerLeak")
public class NavigatorContentExpandListFragment extends BaseFragment implements
		OnClickListener {

	// 视图对象
	private View view;
	private LayoutInflater mInflater;
	private Context context;

	// 菜单对象
	private MenuItem parentItem;

	// 父菜单列表
	private ListView listview;
	// 父菜单进度条
	private ProgressBar processBar;

	// 菜单列表
	private List<MenuItem> menuItems;
	// 频道列表
	private List<Channel> channels;

	// 子频道对象
	private Channel parentChannel;
	// 子菜单对象
	private MenuItem parentMenuItem;

	// 内容
	private ContentWrapper contentWrapper;
	// 内容列表
	private List<Content> contents;

	// 父菜单题目
	private TextView textView_title;
	// 子菜单列表对象
	private ListView govermsg_detail_lv_channel;
	// 子频道列表对象
	private ListView channelListView;
	private ListView xinzhengListView;
	// 子菜单进度条
	private ProgressBar subProgressBar;
	// 收起按钮
	private Button packup_btn;
	// 子列表题目
	private LinearLayout titleLayout;
	// 父菜单布局
	private FrameLayout channleFrameLayout;

	/**
	 * 搜索相关控件变量及字段
	 */
	private LinearLayout searchLayout;
	private Spinner deptSpinner;
	private Spinner yearSpinner;
	private Button searchBtn;
	// 搜索相关字段
	private static String DEFAULT_DEPT_FIFTER = "按部门筛选";
	// 部门数据加载成功
	private static final int LOAD_DEPT_SUCCESS = 9;
	// 部门数据加载失败
	private static final int LOAD_DEPT_FAILED = 10;
	private String deptStrFifter = DEFAULT_DEPT_FIFTER;
	// 年份默认今年
	private String DEFAULT_YEAR_FIFTER = "2013";
	private String yearFifter = DEFAULT_YEAR_FIFTER; // 2013
	private List<OpenInfoDept> depts;
	private OpenInfoSearchCondition searchCondition = new OpenInfoSearchCondition();

	// 加载菜单数据成功
	private static final int MENUITEM_DATA_LOAD_SUCESS = 0;
	// 加载频道数据成功
	private static final int CHANNEL_DATA_LOAD_SUCESS = 1;
	// 加载数据失败
	private static final int DATA_LOAD_ERROR = 2;
	private static final int LOAD_CHANNEL_DATA = 3;
	private static final int CHANNEL_LOAD_SUCESS = 4;// 子频道获取成功
	private static final int CHANNEL_LOAD_FAIL = 5;// 子频道获取失败
	private static final int DATA_LOAD_SUCESS = 6;
	private static final int XINGZHENG_LOAD_SUCESS = 7;
	private static final int XINGZHENG_LOAD_ERROR = 8;

	// 子列表类型
	private int fifterType = 0;

	private final static int PAGE_NUM = 10;

	/**
	 * 除政府概括和政策法规意外的列表分页加载相关变量
	 */
	private CityGoverInfoContentListAdapter contentListAdapter;
	private int ggVisibleLastIndex;
	private int ggVisibleItemCount;
	private boolean isFirstLoadgg = true;// 是不是首次加载数据
	private boolean isSwitchgg = false;// 切换
	private boolean isLoadinggg = false;
	private View loadMoreViewgg;// 加载更多视图
	private Button loadMoreButtongg;
	private ProgressBar pb_loadmooregg;

	private PolicieRegulationAdapter regulationAdapter;

	private GovernmentGeneralizeAdapter generalizeAdapter;

	/**
	 * 行政事项相关变量和字段
	 */
	private String xingzhengtype;
	private AdministrativeWrapper licenseWrapper;
	private List<GoverSaoonItem> licenses;
	private int xingzhengLastIndex;
	private int xingzhengItemCount;// 当前显示的总条数
	private AdministrativeAdapter administrativeAdapter;
	private boolean isFirstLoadXZ = true;// 是不是首次加载数据
	private boolean isSwitchXZ = false;// 切换
	private boolean isLoadingXZ = false;
	private View loadMoreViewXZ;// 加载更多视图
	private Button loadMoreButtonXZ;
	private ProgressBar pb_loadmoorexz;
	private LinearLayout xingzhengsearchLayout;
	private Spinner xingzhengDeptSpinner;
	private Spinner xingzhengYearSpinner;
	private Button xingzhengsearchbtn;
	private static final int XZ_SEARCH_DEPT_SUCESS = 13;
	private static final int XZ_SEARCH_DEPT_FAILED = 14;
	private AdministrativeCon con = new AdministrativeCon();
	private String[] years = new String[] { "按年份", "2013", "2012", "2011",
			"2010", "2009" };
	private List<Dept> deptList;

	// 加载更多
	private ProgressBar moreProgressBar;
	private Button mButtonLoadMore;
	private View loadListMoreView;
	private int moreIndex = 10;
	private boolean isFirstLoad = true;
	private ProgressBar morePrBar;
	private Button mBLoadMore;
	private View loadListViewMoreView;

	/**
	 * @return type
	 */
	public String getXinzhengType() {
		return xingzhengtype;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setXinzhengType(String type) {
		this.xingzhengtype = type;
	}

	public MenuItem getParentMenuItem() {
		return parentMenuItem;
	}

	public void setParentMenuItem(MenuItem menuItem) {
		this.parentMenuItem = menuItem;
	}

	public Channel getParentChannel() {
		return parentChannel;
	}

	public void setParentChannel(Channel parentChannel) {
		this.parentChannel = parentChannel;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case MENUITEM_DATA_LOAD_SUCESS:
				processBar.setVisibility(View.INVISIBLE);
				showMenuItemList();
				break;
			case CHANNEL_DATA_LOAD_SUCESS:
				processBar.setVisibility(View.INVISIBLE);
				showChannelList();
				break;
			case DATA_LOAD_ERROR:
				processBar.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case CHANNEL_LOAD_SUCESS:
				showChannelContentData();
				break;

			case CHANNEL_LOAD_FAIL:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case DATA_LOAD_SUCESS:
				subProgressBar.setVisibility(View.GONE);
				showMenuContentData();
				break;

			case XINGZHENG_LOAD_SUCESS:
				showXingzhengList();
				break;
			case XINGZHENG_LOAD_ERROR:
				subProgressBar.setVisibility(View.GONE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			case LOAD_DEPT_SUCCESS:
				showSearchDept();
				break;
			case LOAD_DEPT_FAILED:
				Toast.makeText(context, "加载部门数据失败！", Toast.LENGTH_SHORT).show();
				break;

			case XZ_SEARCH_DEPT_SUCESS:
				showXZDept();
				break;

			case XZ_SEARCH_DEPT_FAILED:
				Toast.makeText(context, "加载行政事项部门数据失败！", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.expand_channel_listview_layout, null);
		mInflater = inflater;
		context = getActivity();
		initUI();
		getFootListView();
		initSubLayoutUI();
		initXingzhengLayout();
		initSearchLayout();

		// govermsg_detail_lv_channel.addFooterView(getFootListView());
		// govermsg_detail_lv_channel.removeFooterView(loadListMoreView);

		return view;
	}

	/**
	 * @方法： initUI
	 * @描述： 初始化父列表布局控件
	 */
	private void initUI() {

		listview = (ListView) view.findViewById(R.id.expand_channel_listview);
		processBar = (ProgressBar) view
				.findViewById(R.id.expand_channel_progress);
		processBar.setVisibility(View.VISIBLE);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {

				System.out.println("列表的点击");

				Object object = (Object) adapterView
						.getItemAtPosition(position);

				MenuItem menuItem = null;
				Channel channel = null;
				if (object instanceof MenuItem) {// 如果是频道
					menuItem = (MenuItem) object;
				} else if (object instanceof Channel) {
					channel = (Channel) object;
				}

				if (menuItem != null) {
					System.out.println("是否为空");
					setParentMenuItem(menuItem);

					if (getType() == 5) {
						System.out.println("===========5==========");
						channleFrameLayout.setVisibility(View.GONE);
						titleLayout.setVisibility(View.VISIBLE);
						xingzhengsearchLayout.setVisibility(View.VISIBLE);

						if (getParentMenuItem().getName().equals("行政许可")) {
							setXinzhengType("XK");
						} else if (getParentMenuItem().getName().equals("行政处罚")) {
							setXinzhengType("CF");
						} else if (getParentMenuItem().getName().equals("行政征收")) {
							setXinzhengType("ZS");
						} else if (getParentMenuItem().getName().equals("行政强制")) {
							setXinzhengType("QZ");
						} else if (getParentMenuItem().getName().equals("其它")) {
							setXinzhengType("QT");
						}
						isSwitchXZ = true;
						loadXingzhengData(0, PAGE_NUM);
					} else {
						System.out.println("读取四");
						channleFrameLayout.setVisibility(View.GONE);
						subProgressBar.setVisibility(View.VISIBLE);
						titleLayout.setVisibility(View.VISIBLE);
						xingzhengsearchLayout.setVisibility(View.GONE);
						loadMenuListData(0, 10);
					}

				} else if (channel != null) {
					setParentChannel(channel);

					if (getType() == 1) {
						searchLayout.setVisibility(View.VISIBLE);
					} else {
						searchLayout.setVisibility(View.GONE);
					}

					channleFrameLayout.setVisibility(View.GONE);
					titleLayout.setVisibility(View.VISIBLE);
					xingzhengsearchLayout.setVisibility(View.GONE);

					isSwitchgg = true;
					System.out.println("读取333333333");
					loadChannelData(0, PAGE_NUM);
				}

				if (getParentMenuItem() != null) {
					textView_title.setText(getParentMenuItem().getName());
				} else if (parentChannel != null) {
					textView_title.setText(getParentChannel().getChannelName());
				}
			}
		});

		processBar.setVisibility(View.VISIBLE);
		if (parentItem.getType() == MenuItem.CUSTOM_MENU) {
			System.out.println("读取1111111111111");
			loadMenuItemData();
		} else if (parentItem.getType() == MenuItem.CHANNEL_MENU) {
			System.out.println("读取2222222222222");
			loadChannelData();
		}

		loadListMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		moreProgressBar = (ProgressBar) loadListMoreView
				.findViewById(R.id.pb_loadmoore);
		mButtonLoadMore = (Button) loadListMoreView
				.findViewById(R.id.loadMoreButton);
		mButtonLoadMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (contentWrapper.isNext()) {
					mButtonLoadMore.setText("loading.....");
					moreProgressBar.setVisibility(View.VISIBLE);
					loadMoreData();
				}
			}
		});

		getFootListView();
	}

	private View getFootListView() {
		System.out.println("初始化控件");
		loadListViewMoreView = View.inflate(context,
				R.layout.list_loadmore_layout, null);
		morePrBar = (ProgressBar) loadListViewMoreView
				.findViewById(R.id.pb_loadmoore);
		morePrBar.setVisibility(View.GONE);
		mBLoadMore = (Button) loadListViewMoreView
				.findViewById(R.id.loadMoreButton);
		System.out.println("点击加载更多");
		mBLoadMore.setText("点击加载更多");
		mBLoadMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (contentWrapper.isNext()) {
					mBLoadMore.setText("loading.....");
					morePrBar.setVisibility(View.VISIBLE);
					loadMoreData();
				}
			}
		});
		return loadListViewMoreView;
	}

	/**
	 * @方法： initLayoutUI
	 * @描述： 初始化子列表视图
	 */
	private void initSubLayoutUI() {
		channleFrameLayout = (FrameLayout) view
				.findViewById(R.id.expand_channel_prog_layout);
		titleLayout = (LinearLayout) view
				.findViewById(R.id.gpm_detail_tv_title_layout);
		textView_title = (TextView) view.findViewById(R.id.gpm_detail_tv_title);
		govermsg_detail_lv_channel = (ListView) view
				.findViewById(R.id.gpm_detail_listview);
		packup_btn = (Button) view.findViewById(R.id.gpm_detail_btn_packup);

		govermsg_detail_lv_channel
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View arg1, int position, long arg3) {
						Content content = (Content) adapterView
								.getItemAtPosition(position);

						if (parentItem != null) {

							Intent intent = new Intent(getActivity(),
									GoverMsgContentDetailWebActivity.class);
							intent.putExtra("url", content.getWapUrl());
							intent.putExtra("fragmentTitle",
									parentItem.getName());

							MainTabActivity.instance.addView(intent);

						} else if (parentChannel != null) {

							Intent intent = new Intent(getActivity(),
									GoverMsgContentDetailWebActivity.class);
							intent.putExtra("url", content.getWapUrl());
							intent.putExtra("fragmentTitle",
									parentChannel.getChannelName());

							Animation animation = AnimationUtils.loadAnimation(
									context, R.anim.rbm_in_from_right);
							MainTabActivity.instance.addView(intent, animation);

						}
					}
				});

		packup_btn.setOnClickListener(this);

		subProgressBar = (ProgressBar) view
				.findViewById(R.id.gmp_detail_progressbar);

		channelListView = (ListView) view
				.findViewById(R.id.gpm_detail_channel_listview);
		channelListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {

				Content content = (Content) adapterView
						.getItemAtPosition(position);

				if (parentItem != null) {

					Intent intent = new Intent(getActivity(),
							GoverMsgContentDetailWebActivity.class);
					intent.putExtra("url", content.getWapUrl());
					intent.putExtra("fragmentTitle", parentItem.getName());

					MainTabActivity.instance.addView(intent);

				} else if (parentChannel != null) {

					Intent intent = new Intent(getActivity(),
							GoverMsgContentDetailWebActivity.class);
					intent.putExtra("url", content.getWapUrl());
					intent.putExtra("fragmentTitle",
							parentChannel.getChannelName());

					Animation animation = AnimationUtils.loadAnimation(
							getActivity(), R.anim.rbm_in_from_right);
					MainTabActivity.instance.addView(intent, animation);

				}
			}
		});
		channelListView.setOnScrollListener(new OnScrollListener() {

			@SuppressWarnings("unused")
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				int itemsLastIndex = contentListAdapter.getCount() - 1; // 数据集最后一项的索引
				int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				ggVisibleItemCount = visibleItemCount;
				ggVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
			}
		});

		loadMoreViewgg = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButtongg = (Button) loadMoreViewgg
				.findViewById(R.id.loadMoreButton);
		pb_loadmooregg = (ProgressBar) loadMoreViewgg
				.findViewById(R.id.pb_loadmoore);

		channelListView.addFooterView(loadMoreViewgg);// 为listView添加底部视图
		loadMoreButtongg.setOnClickListener(this);
	}

	/**
	 * @方法： initSearchLayout
	 * @描述： 初始化搜索相关控件
	 */
	private void initSearchLayout() {
		searchLayout = (LinearLayout) view.findViewById(R.id.gmp_search_layout);

		deptSpinner = (Spinner) view.findViewById(R.id.gmp_search_dept_spinner);

		yearSpinner = (Spinner) view.findViewById(R.id.gmp_search_year_spinner);

		deptSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position != 0) {
					deptStrFifter = depts.get(position).getName();
					searchCondition.setDept(deptStrFifter);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				String buffYearStr = ((TextView) view).getText().toString();
				if (position != 0) {
					yearFifter = buffYearStr;
					searchCondition.setYear(yearFifter);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		searchBtn = (Button) view.findViewById(R.id.gmp_search_btn);
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isSwitchgg = true;
				loadChannelData(0, PAGE_NUM);
				channelListView.setVisibility(View.GONE);

			}
		});

		loadDeptData();
		initYearSpinner();
	}

	/**
	 * @方法： initXingzhengLayout
	 * @描述： 初始化行政事项相关布局控件
	 */
	private void initXingzhengLayout() {
		xingzhengsearchLayout = (LinearLayout) view
				.findViewById(R.id.gmp_xingzheng_search_layout);

		xingzhengDeptSpinner = (Spinner) view
				.findViewById(R.id.gmp_xingzheng_search_dept_spinner);
		xingzhengYearSpinner = (Spinner) view
				.findViewById(R.id.gmp_xingzheng_search_year_spinner);
		xingzhengYearSpinner.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, years));

		xingzhengsearchbtn = (Button) view
				.findViewById(R.id.gmp_xingzheng_search_btn);

		xingzhengDeptSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						if (position != 0) {
							con.setId(deptList.get(position).getId());
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		xingzhengYearSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,
							int position, long arg3) {
						if (position == 0) {
							con.setYear(-1);
						} else {
							con.setYear(Integer.valueOf(years[position]));
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		xingzhengsearchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isSwitchXZ = true;
				loadXingzhengData(0, PAGE_NUM);
				xinzhengListView.setVisibility(View.GONE);
			}
		});

		xinzhengListView = (ListView) view
				.findViewById(R.id.gpm_detail_xingzheng_listview);
		xinzhengListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				GoverSaoonItem goverSaoonItem = (GoverSaoonItem) adapterView
						.getItemAtPosition(position);

				Intent intent = null;
				if (goverSaoonItem.getType().equals("XK")) {
					intent = new Intent(getActivity(),
							GoverSaloonDetailXKActivity.class);
				} else if (goverSaoonItem.getType().equals("QT")) {

					intent = new Intent(getActivity(),
							GoverSaloonDetailQTActivity.class);
				} else if (goverSaoonItem.getType().equals("ZS")) {

					intent = new Intent(getActivity(),
							GoverSaloonDetailZSActivtiy.class);
				} else if (goverSaoonItem.getType().equals("QZ")) {

					intent = new Intent(getActivity(),
							GoverSaloonDetailQZActivity.class);
				} else if (goverSaoonItem.getType().equals("CF")) {
					intent = new Intent(getActivity(),
							GoverSaloonDetailCFActivity.class);
				}

				if (intent != null) {
					intent.putExtra("goverSaoonItem", goverSaoonItem);
					MainTabActivity.instance.addView(intent);
				}
			}

		});

		loadMoreViewXZ = View.inflate(context,
				R.layout.myapply_list_loadmore_layout, null);
		loadMoreButtonXZ = (Button) loadMoreViewXZ
				.findViewById(R.id.loadapply_MoreButton);
		pb_loadmoorexz = (ProgressBar) loadMoreViewXZ
				.findViewById(R.id.pb_applyloadmoore);

		xinzhengListView.addFooterView(loadMoreViewXZ);// 为listView添加底部视图
		xinzhengListView.setOnScrollListener(new OnScrollListener() {

			@SuppressWarnings("unused")
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				int itemsLastIndex = administrativeAdapter.getCount() - 1; // 数据集最后一项的索引
				int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				xingzhengItemCount = visibleItemCount;
				xingzhengLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
			}
		});// 增加滑动监听

		loadMoreButtonXZ.setOnClickListener(this);

		loadXZDept();
	}

	/**
	 * @方法： showXZDept
	 * @描述： 显示行政事项搜索部门下拉框数据
	 */
	private void showXZDept() {

		Dept dept = new Dept("按部门筛选");
		deptList.add(0, dept);

		DeptSpinnerAdapter adapter = new DeptSpinnerAdapter(deptList, context);

		xingzhengDeptSpinner.setAdapter(adapter);

	}

	/**
	 * @方法： loadData
	 * @描述： 加载行政事项数据
	 * @param startIndex
	 * @param endIndex
	 */
	private void loadXingzhengData(final int startIndex, final int endIndex) {

		System.out.println("进来没有loadXingzhengData");

		if (isFirstLoadXZ || isSwitchXZ) {
			subProgressBar.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoorexz.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoadingXZ = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				AdministrativeService service = new AdministrativeService(
						context);

				con.setStart(startIndex);
				con.setEnd(endIndex);

				try {
					licenseWrapper = service.getLicenseWrapper(getXZUrl(con));
					if (licenseWrapper != null) {
						msg.what = XINGZHENG_LOAD_SUCESS;
						licenses = licenseWrapper.getLicenses();
					} else {
						msg.what = XINGZHENG_LOAD_ERROR;
						msg.obj = "加载办件信息失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = XINGZHENG_LOAD_ERROR;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = XINGZHENG_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = XINGZHENG_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	/**
	 * @方法： loadDept
	 * @描述： 加载行政事项部门下拉框数据
	 */
	private void loadXZDept() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				DeptService deptService = new DeptService(context);
				try {
					deptList = deptService.getDepts();
					if (deptList != null) {
						msg.what = XZ_SEARCH_DEPT_SUCESS;
					} else {
						msg.what = XZ_SEARCH_DEPT_FAILED;
						msg.obj = "没有获取到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = XZ_SEARCH_DEPT_FAILED;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = XZ_SEARCH_DEPT_FAILED;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

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
	private void showSearchDept() {
		OpenInfoDept deptDefault = new OpenInfoDept();
		deptDefault.setId("0");
		deptDefault.setName(DEFAULT_DEPT_FIFTER);
		depts.add(0, deptDefault);

		OpenInfoDeptAdapter partment_Spinner_adapter = new OpenInfoDeptAdapter(
				context, depts);
		if (deptSpinner.getVisibility() == View.VISIBLE) {
			deptSpinner.setAdapter(partment_Spinner_adapter);
			deptSpinner.setVisibility(View.VISIBLE);
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
		if (yearSpinner.getVisibility() == View.VISIBLE) {
			yearSpinner.setAdapter(year_Spinner_adapter);
			yearSpinner.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * @方法： getType
	 * @描述： 获取菜单类型
	 * @return
	 */
	private int getType() {
		if (getParentMenuItem() != null) {
			fifterType = GoverMsgInitInfoOpenListener.getMenuItemFragmentType(
					getParentMenuItem(), fifterType);
		}
		if (getParentChannel() != null) {
			fifterType = GoverMsgInitInfoOpenListener.getChannelFragmentType(
					parentChannel, fifterType);
		}

		return fifterType;

	}

	/**
	 * @方法： getURL
	 * @描述： 构建搜索URL
	 * @param fifter
	 * @return
	 */
	private String getURL(String id) {
		String url = Constants.Urls.CHANNEL_CONTENT_P_URL.replace("{id}", id)
				.replace("{start}", String.valueOf(searchCondition.getStart()))
				.replace("{end}", String.valueOf(searchCondition.getEnd()));
		String dept = searchCondition.getDept();
		String year = searchCondition.getYear();

		if (dept != null && !"".equals(dept)) {
			url = url + "&dept=" + dept;
		}
		if (year != null && !"".equals(year)) {
			url = url + "&year=" + year;
		}

		return url;
	}

	/**
	 * @方法： getXZUrl
	 * @描述： 获取行政事项URL
	 * @param con
	 * @return
	 */
	private String getXZUrl(AdministrativeCon con) {

		String url = Constants.Urls.GETITEM_QUERY_URL + "?qltype="
				+ getXinzhengType() + "&start=" + con.getStart() + "&end="
				+ con.getEnd();

		String id = con.getId();
		int year = con.getYear();

		if (id != null && !"".equals(id)) {
			url = url + "&deptid=" + id;
		}
		if (year != -1) {
			url = url + "&year=" + year;
		}

		return url;
	}

	/**
	 * @方法： showContentData
	 * @描述： 显示子菜单列表数据
	 */
	List<Content> list = new ArrayList<Content>();

	private void showMenuContentData() {
		contents = contentWrapper.getContents();

		System.out.println("放入集合前的长度：" + contents.size());

		govermsg_detail_lv_channel.setVisibility(View.VISIBLE);
		channleFrameLayout.setVisibility(View.GONE);
		channelListView.setVisibility(View.GONE);
		xinzhengListView.setVisibility(View.GONE);
		packup_btn.setVisibility(View.VISIBLE);
		if (contents == null && contents.size() == 0) {
			Toast.makeText(context, "数据为空！", Toast.LENGTH_SHORT).show();
			subProgressBar.setVisibility(ProgressBar.GONE);
		} else {
			if (isFirstLoad) {
				isFirstLoad = false;
				if (contentWrapper.isNext()) {
					if (govermsg_detail_lv_channel.getFooterViewsCount() != 0) {
						morePrBar.setVisibility(View.GONE);
						mBLoadMore.setText("点击加载更多");
					} else {
						govermsg_detail_lv_channel
								.addFooterView(getFootListView());
					}
					govermsg_detail_lv_channel
							.removeFooterView(loadListMoreView);
				}
			}

			if (getType() == 3) {
				if (generalizeAdapter == null) {
					generalizeAdapter = new GovernmentGeneralizeAdapter(
							contents, context);
				}
				govermsg_detail_lv_channel.setAdapter(generalizeAdapter);
			} else if (getType() == 4) {

				list.addAll(contents);

				System.out.println("放入集合后的长度：" + list.size());

				if (regulationAdapter == null) {
					regulationAdapter = new PolicieRegulationAdapter(list,
							context);
					govermsg_detail_lv_channel.setAdapter(regulationAdapter);
				} else {
					regulationAdapter.setContents(list);
					regulationAdapter.notifyDataSetChanged();
				}
				regulationAdapter.setMenuItem(getParentMenuItem());

			}
		}

		if (contentWrapper.isNext()) {
			if (govermsg_detail_lv_channel.getFooterViewsCount() != 0) {
				morePrBar.setVisibility(View.GONE);
				mBLoadMore.setText("点击加载更多");
			} else {
				govermsg_detail_lv_channel.addFooterView(getFootListView());
			}
		} else {
			govermsg_detail_lv_channel.removeFooterView(loadListMoreView);
		}

	}

	/**
	 * @方法： showChannelContentData
	 * @描述： 显示子频道列表菜单
	 */
	private void showChannelContentData() {
		contents = contentWrapper.getContents();

		govermsg_detail_lv_channel.setVisibility(View.GONE);
		packup_btn.setVisibility(View.VISIBLE);
		channleFrameLayout.setVisibility(View.GONE);
		channelListView.setVisibility(View.VISIBLE);
		xinzhengListView.setVisibility(View.GONE);
		if (contents == null && contents.size() == 0
				&& contents.get(0).equals("[") && contents.get(0).equals("]")) {
			Toast.makeText(context, "数据为空！", Toast.LENGTH_SHORT).show();
		} else {
			if (isFirstLoadgg) {
				contentListAdapter = new CityGoverInfoContentListAdapter(
						contents, context);
				contentListAdapter.setChannel(getParentChannel());
				channelListView.setAdapter(contentListAdapter);
				isFirstLoadgg = false;
				subProgressBar.setVisibility(ProgressBar.GONE);
				isLoadinggg = false;
			} else {
				if (isSwitchgg) {
					contentListAdapter.setContents(contents);
					subProgressBar.setVisibility(ProgressBar.GONE);
				} else {
					for (Content content : contents) {
						contentListAdapter.addItem(content);
					}
				}
				contentListAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				channelListView.setSelection(ggVisibleLastIndex
						- ggVisibleItemCount + 1); // 设置选中项
				isLoadinggg = false;
			}
		}
		if (contentWrapper.isNext()) {
			pb_loadmooregg.setVisibility(ProgressBar.GONE);
			loadMoreButtongg.setText("点击加载更多");

		} else {
			if (contentListAdapter != null) {
				channelListView.removeFooterView(loadMoreViewgg);
			}

		}

	}

	/**
	 * @方法： showPoloticsList
	 * @描述： 显示行政事项列表数据
	 */
	private void showXingzhengList() {

		govermsg_detail_lv_channel.setVisibility(View.GONE);
		packup_btn.setVisibility(View.VISIBLE);
		channleFrameLayout.setVisibility(View.GONE);
		channelListView.setVisibility(View.GONE);
		xinzhengListView.setVisibility(View.VISIBLE);
		if (licenses == null || licenses.size() == 0) {
			Toast.makeText(context, "对不起，暂无行政事项信息", Toast.LENGTH_SHORT).show();
			subProgressBar.setVisibility(View.GONE);
		} else {
			if (isFirstLoadXZ) {
				administrativeAdapter = new AdministrativeAdapter(context,
						licenses);
				isFirstLoadXZ = false;
				xinzhengListView.setAdapter(administrativeAdapter);
				subProgressBar.setVisibility(View.GONE);
				isLoadingXZ = false;
			} else {
				if (isSwitchXZ) {
					administrativeAdapter.setAdministratives(licenses);
					subProgressBar.setVisibility(View.GONE);
				} else {
					for (GoverSaoonItem license : licenses) {
						administrativeAdapter.addItem(license);
					}
				}

				administrativeAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				xinzhengListView.setSelection(xingzhengLastIndex
						- xingzhengItemCount + 1); // 设置选中项
				isLoadingXZ = false;
			}
		}

		if (licenseWrapper.isNext()) {
			pb_loadmoorexz.setVisibility(ProgressBar.GONE);
			loadMoreButtonXZ.setText("点击加载更多");
		} else {
			if (administrativeAdapter != null) {
				xinzhengListView.removeFooterView(loadMoreViewXZ);
			}

		}
	}

	/**
	 * @方法： loadMenuItemData
	 * @描述： 加载父菜单数据
	 */
	@SuppressWarnings("unchecked")
	private void loadMenuItemData() {
		if (CacheUtil.get(parentItem.getId()) != null) {// 从缓存中查找子菜单
			menuItems = (List<MenuItem>) CacheUtil.get(parentItem.getId());
			processBar.setVisibility(View.INVISIBLE);
			showMenuItemList();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuSevice = new MenuService(context);
				try {
					menuItems = menuSevice.getSubMenuItems(parentItem.getId());
					if (menuItems != null) {
						handler.sendEmptyMessage(MENUITEM_DATA_LOAD_SUCESS);

					} else {
						Message msg = handler.obtainMessage();
						msg.obj = "暂无信息";
						msg.what = DATA_LOAD_ERROR;
						handler.sendMessage(msg);
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * @方法： loadChannelData
	 * @描述： 加载频道菜单
	 */
	@SuppressWarnings("unchecked")
	private void loadChannelData() {
		if (CacheUtil.get(parentItem.getChannelId()) != null) {// 从缓存中查找子菜单
			channels = (List<Channel>) CacheUtil.get(parentItem.getChannelId());
			processBar.setVisibility(View.INVISIBLE);
			showChannelList();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				ChannelService channelSevice = new ChannelService(context);

				try {
					if (parentItem.getName().equals("业务工作")) {
						handler.sendEmptyMessage(LOAD_CHANNEL_DATA);

					} else {
						channels = channelSevice.getSubChannels(parentItem
								.getChannelId());
						if (channels != null) {
							handler.sendEmptyMessage(CHANNEL_DATA_LOAD_SUCESS);

						} else {
							Message msg = handler.obtainMessage();
							msg.obj = "暂无信息";
							msg.what = DATA_LOAD_ERROR;
							handler.sendMessage(msg);
						}
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = DATA_LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * @方法： showMenuItemList
	 * @描述： 显示菜单项列表
	 */
	private void showMenuItemList() {
		MenuItemListAdapter adapter = new MenuItemListAdapter();
		listview.setAdapter(adapter);

	}

	/**
	 * @方法： showChannelList
	 * @描述： 显示频道项列表
	 */
	private void showChannelList() {
		MenuItemListAdapter adapter = new MenuItemListAdapter();

		listview.setAdapter(adapter);

	}

	/**
	 * @类名： MenuItemListAdapter
	 * @描述： 菜单项列表适配器类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-24 下午3:47:53
	 * @修改时间：
	 * @修改描述：
	 */
	private class MenuItemListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (menuItems != null) {
				return menuItems.size();
			} else if (channels != null) {
				return channels.size();
			} else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			if (menuItems != null) {
				return menuItems.get(position);
			} else if (channels != null) {
				return channels.get(position);
			} else
				return null;

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
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.expand_channel_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.expand_channel_listview_item_title);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (viewHolder.title_text != null) {
				if (menuItems != null) {
					viewHolder.title_text.setText(menuItems.get(position)
							.getName());
				} else if (channels != null) {
					viewHolder.title_text.setText(channels.get(position)
							.getChannelName());
				}
			}
			return convertView;
		}
	}

	/**
	 * 加载更多
	 * 
	 * @方法： loadMoreData
	 * @描述： TODO
	 */
	private void loadMoreData() {
		loadMenuListData(moreIndex, moreIndex + 10);
		moreIndex += 10;
		if (!isFirstLoad) {
			govermsg_detail_lv_channel.removeFooterView(loadListMoreView);
		}
	}

	/**
	 * @方法： loadData
	 * @描述： 加载子菜单数据
	 * @param startIndex
	 * @param endIndex
	 */
	private void loadMenuListData(final int startIndex, final int endIndex) {
		// if (isFirstLoadgg || isSwitchgg) {
		// subProgressBar.setVisibility(View.VISIBLE);
		// } else {
		// pb_loadmooregg.setVisibility(ProgressBar.VISIBLE);
		// }

		new Thread(new Runnable() {

			@Override
			public void run() {
				// isLoading = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
					contentWrapper = contentService.getPageContentsById(
							parentMenuItem.getChannelId(), startIndex, endIndex);

					if (contentWrapper != null) {
						contents = contentWrapper.getContents();
						msg.what = DATA_LOAD_SUCESS;
					} else {
						msg.what = DATA_LOAD_ERROR;
						msg.obj = "加载办件信息失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();

					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	/**
	 * @方法： loadMoreMenuListData
	 * @描述： 加载更多子频道数据
	 * @param view
	 */
	private void loadMoreChannelListData(View view) {
		if (isLoadinggg) {
			return;
		} else {
			loadChannelData(ggVisibleLastIndex + 1, ggVisibleLastIndex + 1
					+ PAGE_NUM);
		}
	}

	/**
	 * @方法： loadChannelData
	 * @描述： 加载子频道数据
	 * @param start
	 * @param end
	 */
	private void loadChannelData(final int start, final int end) {
		if (isFirstLoadgg || isSwitchgg) {
			subProgressBar.setVisibility(ProgressBar.VISIBLE);
		} else {
			pb_loadmooregg.setVisibility(ProgressBar.VISIBLE);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoadinggg = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
					String channelId = "";
					if (parentChannel != null) {
						channelId = parentChannel.getChannelId();
					} else if (parentItem != null) {
						channelId = parentItem.getChannelId();
					}

					searchCondition.setStart(start);
					searchCondition.setEnd(end);

					System.out.println("起始和结束：" + searchCondition.getStart()
							+ ";" + searchCondition.getEnd());

					contentWrapper = contentService
							.getPageContentsByUrl(getURL(channelId));
					if (contentWrapper != null) {
						msg.what = CHANNEL_LOAD_SUCESS;
					} else {
						msg.what = CHANNEL_LOAD_FAIL;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = CHANNEL_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = CHANNEL_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = CHANNEL_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 收起按钮事件监听
		case R.id.gpm_detail_btn_packup:
			isFirstLoad = true;
			moreIndex = 10;
			listview.setVisibility(View.VISIBLE);
			govermsg_detail_lv_channel.setVisibility(View.GONE);
			packup_btn.setVisibility(View.GONE);
			titleLayout.setVisibility(View.GONE);
			channleFrameLayout.setVisibility(View.VISIBLE);
			channelListView.setVisibility(View.GONE);
			xinzhengListView.setVisibility(View.GONE);
			searchLayout.setVisibility(View.GONE);
			xingzhengsearchLayout.setVisibility(View.GONE);
			searchCondition.setDept("");
			searchCondition.setYear("");
			con.setId(null);
			con.setYear(-1);
			list.clear();
			break;

		case R.id.loadMoreButton:
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录
				isSwitchgg = false;
				loadMoreButtongg.setText("loading.....");
				loadMoreChannelListData(v);
			}
			break;

		case R.id.loadapply_MoreButton:
			if (licenseWrapper != null && licenseWrapper.isNext()) {// 还有下一条记录
				isSwitchXZ = false;
				loadMoreButtonXZ.setText("loading.....");
				loadMoreXingzhengData(v);
			}
			break;
		}
	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多行政事项数据
	 * @param view
	 */
	private void loadMoreXingzhengData(View view) {
		if (isLoadingXZ) {
			return;
		} else {
			loadXingzhengData(xingzhengLastIndex + 1, xingzhengLastIndex + 1
					+ PAGE_NUM);
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

}
