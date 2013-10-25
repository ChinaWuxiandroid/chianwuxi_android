package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.adapter.GovernmentGeneralizeAdapter;
import com.wuxi.app.adapter.PolicieRegulationAdapter;
import com.wuxi.app.engine.AdministrativeService;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.listeners.GoverMsgInitInfoOpenListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.AdministrativeWrapper;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 市政府信息公开目录 里的 可扩展列表
 * 
 * @author 杨宸 智佳
 * */

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

	private ContentListAdapter contentListAdapter;
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
		initSubLayoutUI();
		initXingzhengLayout();
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
					setParentMenuItem(menuItem);

					if (getType() == 5) {
						channleFrameLayout.setVisibility(View.GONE);
						titleLayout.setVisibility(View.VISIBLE);

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
						channleFrameLayout.setVisibility(View.GONE);
						subProgressBar.setVisibility(View.VISIBLE);
						titleLayout.setVisibility(View.VISIBLE);
						loadMenuListData(0, 10);
					}

				} else if (channel != null) {
					setParentChannel(channel);
					
					channleFrameLayout.setVisibility(View.GONE);
					titleLayout.setVisibility(View.VISIBLE);
					
					isSwitchgg = true;
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
			loadMenuItemData();
		} else if (parentItem.getType() == MenuItem.CHANNEL_MENU) {
			loadChannelData();
		}

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
									getActivity(), R.anim.rbm_in_from_right);
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
	 * @方法： initXingzhengLayout
	 * @描述： TODO
	 */
	private void initXingzhengLayout() {
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
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 * @param startIndex
	 * @param endIndex
	 */
	private void loadXingzhengData(final int startIndex, final int endIndex) {
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

				String url = Constants.Urls.GETITEM_QUERY_URL + "?qltype="
						+ getXinzhengType() + "&start=" + startIndex + "&end="
						+ endIndex;
				try {
					licenseWrapper = service.getLicenseWrapper(url);
					if (licenseWrapper != null) {
						msg.what = XINGZHENG_LOAD_SUCESS;
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
	 * @方法： showContentData
	 * @描述： 显示子菜单列表数据
	 */
	private void showMenuContentData() {
		contents = contentWrapper.getContents();
		govermsg_detail_lv_channel.setVisibility(View.VISIBLE);
		channleFrameLayout.setVisibility(View.GONE);
		channelListView.setVisibility(View.GONE);
		xinzhengListView.setVisibility(View.GONE);
		packup_btn.setVisibility(View.VISIBLE);
		if (contents == null && contents.size() == 0) {
			Toast.makeText(context, "数据为空！", Toast.LENGTH_SHORT).show();
			subProgressBar.setVisibility(ProgressBar.GONE);
		} else {
			if (getType() == 3) {
				generalizeAdapter = new GovernmentGeneralizeAdapter(contents,
						context);
				govermsg_detail_lv_channel.setAdapter(generalizeAdapter);

			} else if (getType() == 4) {
				regulationAdapter = new PolicieRegulationAdapter(contents,
						context);
				regulationAdapter.setMenuItem(getParentMenuItem());
				govermsg_detail_lv_channel.setAdapter(regulationAdapter);
			}
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
		if (contents == null && contents.size() == 0) {
			Toast.makeText(context, "数据为空！", Toast.LENGTH_SHORT).show();
		} else {
			if (isFirstLoadgg) {
				contentListAdapter = new ContentListAdapter(contents, context);
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
	 * @描述： 显示列表数据
	 */
	private void showXingzhengList() {
		licenses = licenseWrapper.getLicenses();
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
			xinzhengListView.removeFooterView(loadMoreViewXZ);
		}
	}

	/**
	 * @方法： loadMenuItemData
	 * @描述： 加载父菜单数据
	 */
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
	 * @描述： 加载更多子菜单数据
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
					contentWrapper = contentService.getPageContentsById(
							channelId, start, end);
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
			listview.setVisibility(View.VISIBLE);
			govermsg_detail_lv_channel.setVisibility(View.GONE);
			packup_btn.setVisibility(View.GONE);
			titleLayout.setVisibility(View.GONE);
			channleFrameLayout.setVisibility(View.VISIBLE);
			channelListView.setVisibility(View.GONE);
			xinzhengListView.setVisibility(View.GONE);
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
	 * @描述： 加载更多数据
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

}
