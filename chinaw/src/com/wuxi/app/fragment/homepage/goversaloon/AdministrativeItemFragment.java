package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailCFActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQTActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQZActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailXKActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailZSActivtiy;
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
		implements OnClickListener, OnScrollListener, OnItemSelectedListener,
		OnItemClickListener, OnCheckedChangeListener {

	private ListView gover_mange_lv;

	private ImageView gover_mange_iv_next;

	private RadioGroup administrative_rg_channel;
	protected static final int TITLE_ITEM_LOADSUCCESS = 0;
	protected static final int TITLE_ITEM_LOADFAIL = 1;
	protected static final int GOVERITEM_LOAD_SUCCESS = 2;
	protected static final int GOVERITEM_LOAD_FIAL = 3;
	private static final int PAGE_SIZE = 10;
	protected static final int LOAD_DEPT_SUCCESS = 4;
	protected static final int LOAD_DEPT_FAIL = 5;

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
	private ProgressBar pb_loadmoore;
	private boolean isFirstChange = true;
	private int showIndex=0;//要显示的序号
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
		administrative_rg_channel = (RadioGroup) view
				.findViewById(R.id.administrative_rg_channel);
		administrative_rg_channel.setOnCheckedChangeListener(this);
		pb_mange = (ProgressBar) view.findViewById(R.id.pb_mange);
		gover_mange_iv_next = (ImageView) view
				.findViewById(R.id.gover_mange_iv_next);
		sp_dept = (Spinner) view.findViewById(R.id.sp_dept);// 时间
		sp_dept_year = (Spinner) view.findViewById(R.id.sp_dept_year);// 年
		govver_admintrative_ib_search = (ImageButton) view
				.findViewById(R.id.govver_admintrative_ib_search);
		sp_dept.setOnItemSelectedListener(this);

		gover_mange_iv_next.setOnClickListener(this);
		govver_admintrative_ib_search.setOnClickListener(this);

		gover_mange_lv.addFooterView(getFootView());
		gover_mange_lv.setOnScrollListener(this);

		menuItem = (MenuItem) getArguments().get("menuItem");
		gover_mange_lv.setOnItemClickListener(this);
		loadTitleItems(menuItem.getId());// 加载滑动菜单
		loadDept();// 加载部门
		initYear();

	}

	private View getFootView() {
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		loadMoreButton.setOnClickListener(this);
		return loadMoreView;

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

	private void loadDept() {

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

		int index = 0;
		
		
		Bundle bundle=getArguments();
		if(bundle!=null&&bundle.containsKey(Constants.CheckPositionKey.LEVEL_THREE_KEY)){
			showIndex=bundle.getInt(Constants.CheckPositionKey.LEVEL_THREE_KEY);
		}
		for (MenuItem menuItem : menuItems) {

			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			RadioButton radioButton = new RadioButton(context);
			if (index == showIndex) {

				radioButton.setTextColor(Color.WHITE);

				radioButton
						.setBackgroundResource(R.drawable.wuxi_content_channelselect_);

				this.qltype = getType(menuItem);
				Map<String, String> parmsMap = buildParams(null, qltype,
						deptid, year, 0, PAGE_SIZE);
				loadItem(parmsMap);

			}

			radioButton.setText(menuItem.getName());
			radioButton.setTextSize(14);
			radioButton.setPadding(5, 5, 5, 5);
			radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

			administrative_rg_channel.addView(radioButton, params);
			index++;

		}

	}

	/**
	 * 获取办件信息
	 */
	private void loadItem(final Map<String, String> params) {

		if (isFirstLoadGoverItem || isSwitch) {// 首次加载时或切换部门时显示进度条

			pb_mange.setVisibility(ProgressBar.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
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

		if (goverSaoonItemWrapper.isNext()) {

			if (gover_mange_lv.getFooterViewsCount() != 0) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {

				gover_mange_lv.addFooterView(getFootView());
			}

		} else {

			gover_mange_lv.removeFooterView(loadMoreView);
		}

	}

	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.gover_mange_iv_next:

			break;
		case R.id.govver_admintrative_ib_search:
			isSwitch = true;
			Map<String, String> paMap = buildParams(null, qltype, deptid, year,
					0, PAGE_SIZE);
			loadItem(paMap);
			break;
		case R.id.loadMoreButton:
			if (goverSaoonItemWrapper != null && goverSaoonItemWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitch = false;

				Map<String, String> parms = buildParams(null, qltype, deptid,
						year, visibleLastIndex + 1, visibleLastIndex + 1
								+ PAGE_SIZE);

				loadItem(parms);

			}

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
		} else if (name.contains("收")) {
			return "ZS";
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		GoverSaoonItem goverSaoonItem = (GoverSaoonItem) adapterView
				.getItemAtPosition(position);
		
		Intent intent=null;
		if (goverSaoonItem.getType().equals("XK")) {
			intent=new Intent(getActivity(),GoverSaloonDetailXKActivity.class);
		} else if (goverSaoonItem.getType().equals("QT")) {
			
			intent=new Intent(getActivity(),GoverSaloonDetailQTActivity.class);
		} else if (goverSaoonItem.getType().equals("ZS")) {
			
			intent=new Intent(getActivity(),GoverSaloonDetailZSActivtiy.class);
		} else if (goverSaoonItem.getType().equals("QZ")) {
			
			intent=new Intent(getActivity(),GoverSaloonDetailQZActivity.class);
		} else if (goverSaoonItem.getType().equals("CF")) {
			intent=new Intent(getActivity(),GoverSaloonDetailCFActivity.class);
		}
		
		
		if(intent!=null){
			intent.putExtra("goverSaoonItem", goverSaoonItem);
			MainTabActivity.instance.addView(intent);
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		for (int i = 0; i < group.getChildCount(); i++) {

			RadioButton r = (RadioButton) group.getChildAt(i);

			if (isFirstChange && i == showIndex) {
				r.setBackgroundColor(Color.TRANSPARENT);
				r.setTextColor(Color.BLACK);
			}

			if (r.isChecked()) {

				r.setBackgroundResource(R.drawable.wuxicity_content_channel_item_selector);

				r.setTextColor(Color.WHITE);

				isSwitch = true;
				qltype = getType(menuItems.get(i));// 记录当前选中的type
				Map<String, String> params = buildParams(null, qltype, deptid,
						year, 0, PAGE_SIZE);
				loadItem(params);
			} else {

				r.setTextColor(Color.BLACK);
			}

		}

		isFirstChange = false;

	}
}
