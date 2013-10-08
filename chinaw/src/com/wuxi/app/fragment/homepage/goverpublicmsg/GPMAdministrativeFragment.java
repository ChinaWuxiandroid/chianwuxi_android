/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GPMAdministrativeLicenseFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.goverpublicmsg 
 * @描述:  行政事项 子菜单内容列表 界面
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-29 上午9:28:14
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailCFActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQTActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQZActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailXKActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailZSActivtiy;
import com.wuxi.app.adapter.AdministrativeAdapter;
import com.wuxi.app.adapter.DeptSpinnerAdapter;
import com.wuxi.app.engine.AdministrativeService;
import com.wuxi.app.engine.DeptService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.AdministrativeCon;
import com.wuxi.domain.AdministrativeWrapper;
import com.wuxi.domain.Dept;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GPMAdministrativeLicenseFragment
 * @描述： 行政事项 子菜单内容列表 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-29 上午9:28:14
 * @修改时间：
 * @修改描述：
 */
public class GPMAdministrativeFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {
	
	private static final int FRAGMENT_ID = R.id.gpm_admini_license_framelayout;

	private Context context;
	private View view;

	private ListView mListView;
	private ProgressBar list_pb;

	private static final int DATA_LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;
	protected static final int LOAD_DEPT_SUCCESS = 2;
	protected static final int LOAD_DEPT_FAIL = 3;

	private String type;

	private AdministrativeWrapper licenseWrapper;
	private List<GoverSaoonItem> licenses;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private AdministrativeAdapter adapter;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private List<Dept> depts;

	private Spinner deptSpinner = null;
	private Spinner yearSpinner = null;
	private Button searchBtn = null;

	private AdministrativeCon con = new AdministrativeCon();

	private String[] years = new String[] { "按年份", "2009", "2010", "2011",
			"2012", "2013" };

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				showPoloticsList();
				break;
			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			case LOAD_DEPT_SUCCESS:// 部门加载成功
				showDept();
				break;
			case LOAD_DEPT_FAIL:
				Toast.makeText(context, "部门信息加载失败！", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gpm_administrative_license_layout,
				null);
		context = getActivity();

		initLayout();

		loadFirstData(0, PAGE_NUM);

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {

		loadDept();

		mListView = (ListView) view
				.findViewById(R.id.gpm_admini_license_listview);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gpm_admini_license_progressbar);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);

		deptSpinner = (Spinner) view
				.findViewById(R.id.gpm_admini_license_dept_spinner);
		deptSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				con.setId(depts.get(position).getId());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		yearSpinner = (Spinner) view
				.findViewById(R.id.gpm_admini_license_year_spinner);
		yearSpinner.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, years));
		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
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

		searchBtn = (Button) view
				.findViewById(R.id.gpm_admini_license_search_btn);
		searchBtn.setOnClickListener(this);
	}

	
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (licenseWrapper != null && licenseWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
			
		case R.id.gpm_admini_license_search_btn:
			search();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long arg3) {
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

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 * @param startIndex
	 * @param endIndex
	 */
	private void loadData(final int startIndex, final int endIndex) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				AdministrativeService service = new AdministrativeService(
						context);
				
				String url = Constants.Urls.GETITEM_QUERY_URL + "?qltype="
						+ getType() + "&start=" + startIndex + "&end="
						+ endIndex;
				try {
					licenseWrapper = service.getLicenseWrapper(url);
					if (licenseWrapper != null) {
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
	 * @方法： loadDept
	 * @描述： 加载部门数据
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
	 * @方法： showPoloticsList
	 * @描述： 显示列表数据
	 */
	private void showPoloticsList() {
		licenses = licenseWrapper.getLicenses();

		if (licenses == null || licenses.size() == 0) {
			Toast.makeText(context, "对不起，暂无行政事项信息", Toast.LENGTH_SHORT).show();
			list_pb.setVisibility(View.GONE);
		} else {
			if (isFirstLoad) {
				adapter = new AdministrativeAdapter(context, licenses);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setAdministratives(licenses);
					list_pb.setVisibility(View.GONE);
				} else {
					for (GoverSaoonItem license : licenses) {
						adapter.addItem(license);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (licenseWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			mListView.removeFooterView(loadMoreView);
		}
	}

	/**
	 * @方法： showDept
	 * @描述： 显示部门数据
	 */
	private void showDept() {

		Dept dept = new Dept("按部门筛选");
		depts.add(0, dept);

		DeptSpinnerAdapter adapter = new DeptSpinnerAdapter(depts, context);

		deptSpinner.setAdapter(adapter);

	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
		}
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @方法： bindFragment
	 * @描述： 替换碎片
	 * @param fragment
	 */
	private void bindFragment(BaseFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAGMENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}
	
	/**
	 * @方法： search
	 * @描述： 搜索
	 */
	private void search(){
		GPMAdministrativeSearchFragment searchFragment = new GPMAdministrativeSearchFragment();
		con.setQltype(getType());
		searchFragment.setCon(con);
		bindFragment(searchFragment);
	}

}
