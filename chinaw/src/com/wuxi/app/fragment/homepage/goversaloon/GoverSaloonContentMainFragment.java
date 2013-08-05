package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.DeptSpinnerAdapter;
import com.wuxi.app.engine.DeptService;
import com.wuxi.app.engine.GoverItemCountService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Dept;
import com.wuxi.domain.GoverItemCount;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅内容主Fragment
 */
public class GoverSaloonContentMainFragment extends BaseFragment implements
		OnCheckedChangeListener, OnClickListener {

	private MenuItem menuItem;
	private View view;
	private RadioGroup goversaloon_title_search;
	private RadioButton search_bything;
	private RadioButton search_bydeparent;
	private RadioButton search_byrange;
	private Context context;

	private static final int CONTENT_MAIN_ID = R.id.gover_content_main;
	private static final int SEARCH_BYTHING = 0, SEARCH_BYDEPARENT = 1,
			SEARCH_BYRANGE = 2, SEARCH_BYSTATE = 3, COUNTPOP_WINDOW = 4;
	protected static final int LOAD_DEPT_SUCCESS = 1;
	protected static final int LOAD_DEPT_FAIL = 0;
	protected static final int GOVER_ITEMCOUNT_LOAD_SUCCESS = 2;
	protected static final int GOVER_ITEMCOUNT_LOAD_ERROR = 3;

	private Spinner goversaloon_sp_szxk;
	private Button goversaloon_btn_statesearch;
	private Button goversaloon_btn_count;
	private Spinner sp_item_type;
	private Spinner sp_dept;// 部门
	private Spinner sp_dept_range;// 范围
	private Spinner sp_range;// 范围
	private String[] itemType = new String[] { "事项名称", "事项编码" };
	private String[] rangType = new String[] { "全部事项", "当前栏目", "网上审批" };
	private String[] xzType = new String[] { "行政许可", "行政处罚", "行政征收", "行政强制",
			"其它" };
	private String[] xzState = new String[] { "行政许可状态查询", "行政处罚状态查询",
			"行政征收状态查询", "行政强制状态查询", "其它状态查询" };
	private Spinner sp_xzstate;

	private LinearLayout ll_searchbything, ll_searchby_dept, ll_searchby_range,
			ll_itemcount, ll_state;
	private Button btn_searchbything, btn_search_bydept, btn_searchbyrange,
			btn_search_bystate;
	private EditText et_searchbying_content, et_state_itemcode;
	private List<Dept> depts;
	private GoverItemCount goverItemCount;
	private TextView tv_item_count, tv_quick_info, tv_item_count_detail;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case LOAD_DEPT_SUCCESS:
				showDept();
				break;

			case GOVER_ITEMCOUNT_LOAD_SUCCESS:
				showGoverItemCount();
				break;
			case GOVER_ITEMCOUNT_LOAD_ERROR:
			case LOAD_DEPT_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.goversaloon_content_layout, null);

		context = getActivity();

		initUI();

		return view;
	}

	/**
	 * 
	 *wanglu 泰得利通
	 *显示办件统计
	 */
	private void showGoverItemCount() {

		
		
		tv_item_count.setText("累计接件:"+goverItemCount.getLjjj()+"\n累计办件:"+goverItemCount.getLjbj());
		tv_quick_info.setText("昨日受理:"+goverItemCount.getZrsl()+"\n昨日办结:"+goverItemCount.getZrbj());
		tv_item_count_detail.setText("已受理:"+goverItemCount.getYsl()+" 已办结:"+goverItemCount.getYbj()+"\n"
				+"上月受理:"+goverItemCount.getSysl()+" 上月办结:"+goverItemCount.getSybj()+"\n"+
				"本月受理:"+goverItemCount.getBysl()+" 本月办结"+goverItemCount.getBybj());
	}

	private void initUI() {

		goversaloon_title_search = (RadioGroup) view
				.findViewById(R.id.goversaloon_title_search);
		search_bything = (RadioButton) view.findViewById(R.id.search_bything);
		search_bydeparent = (RadioButton) view
				.findViewById(R.id.search_bydeparent);
		search_byrange = (RadioButton) view.findViewById(R.id.search_byrange);
		goversaloon_sp_szxk = (Spinner) view
				.findViewById(R.id.goversaloon_sp_szxk);
		goversaloon_sp_szxk.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, xzType));

		goversaloon_btn_statesearch = (Button) view
				.findViewById(R.id.goversaloon_btn_statesearch);
		goversaloon_btn_count = (Button) view
				.findViewById(R.id.goversaloon_btn_count);
		ll_searchbything = (LinearLayout) view
				.findViewById(R.id.ll_searchbything);
		ll_searchby_dept = (LinearLayout) view
				.findViewById(R.id.ll_searchby_dept);
		ll_searchby_range = (LinearLayout) view
				.findViewById(R.id.ll_searchby_range);
		ll_itemcount = (LinearLayout) view.findViewById(R.id.ll_itemcount);
		ll_state = (LinearLayout) view.findViewById(R.id.ll_state);
		sp_item_type = (Spinner) view.findViewById(R.id.sp_item_type);// 办事事项搜索下拉框

		sp_item_type.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, itemType));
		sp_dept = (Spinner) view.findViewById(R.id.sp_dept);
		loadDeptData();
		sp_dept_range = (Spinner) view.findViewById(R.id.sp_dept_range);
		sp_dept_range.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, rangType));

		sp_range = (Spinner) view.findViewById(R.id.sp_range);
		sp_range.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, rangType));

		sp_xzstate = (Spinner) view.findViewById(R.id.sp_xzstate);
		sp_xzstate.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, xzState));// 行政状态
		btn_searchbything = (Button) view.findViewById(R.id.btn_searchbything);
		et_searchbying_content = (EditText) view
				.findViewById(R.id.et_searchbying_content);
		et_state_itemcode = (EditText) view
				.findViewById(R.id.et_state_itemcode);
		btn_search_bydept = (Button) view.findViewById(R.id.btn_search_bydept);
		btn_searchbyrange = (Button) view.findViewById(R.id.btn_searchbyrange);
		btn_search_bystate = (Button) view
				.findViewById(R.id.btn_search_bystate);
		goversaloon_title_search.setOnCheckedChangeListener(this);
		goversaloon_btn_statesearch.setOnClickListener(this);
		search_bything.setOnClickListener(this);
		search_bydeparent.setOnClickListener(this);
		search_byrange.setOnClickListener(this);
		goversaloon_btn_count.setOnClickListener(this);
		btn_searchbything.setOnClickListener(this);
		btn_search_bydept.setOnClickListener(this);
		btn_searchbyrange.setOnClickListener(this);
		btn_search_bystate.setOnClickListener(this);
		
		
		tv_item_count=(TextView) view.findViewById(R.id.tv_item_count);
		tv_quick_info=(TextView) view.findViewById(R.id.tv_quick_info);
		tv_item_count_detail=(TextView) view.findViewById(R.id.tv_item_count_detail);

		if (menuItem.getType() == MenuItem.CUSTOM_MENU) {

			if (menuItem.getName().equals("我的政务大厅")) {
				onTransaction(new MyGoverSaloonFragment());
			} else if (menuItem.getName().equals("效能投诉")) {
				onTransaction(new EfficacyComplaintFragment());
			} else if (menuItem.getName().equals("行政事项")) {
				this.getArguments().putSerializable("menuItem", menuItem);
				onTransaction(new AdministrativeItemFragment());
			} else if (menuItem.getName().equals("办事指南")) {
				onTransaction(new BusinessGuideFragment());
			}

		} else if (menuItem.getType() == MenuItem.CHANNEL_MENU) {
			GoverMangeFragment goverMangeFragment = new GoverMangeFragment();

			this.getArguments().putSerializable("menuItem", menuItem);

			onTransaction(goverMangeFragment);
		} else if (menuItem.getType() == MenuItem.APP_MENU) {
			if (menuItem.getAppUI().equals("OnlineApproval")) {
				onTransaction(new OnlineApprovalFragment());
			} else if (menuItem.getAppUI().equals("TableDownloads")) {
				onTransaction(new TableDownloadsFragment());
			}
		}

		loadGoverItemCount();//加载办件统计信息
	}

	/**
	 * 
	 * wanglu 泰得利通 加载部门数据
	 */
	private void loadDeptData() {

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
	 * wanglu 泰得利通 获取办件统计数据
	 */
	private void loadGoverItemCount() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();

				GoverItemCountService goverItemCountService = new GoverItemCountService(
						context);
				try {
					goverItemCount = goverItemCountService.getGoverItemCount();
					if (goverItemCount != null) {
						msg.what = GOVER_ITEMCOUNT_LOAD_SUCCESS;
					} else {
						msg.obj = "没有获取到数据";
						msg.what = GOVER_ITEMCOUNT_LOAD_ERROR;
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = GOVER_ITEMCOUNT_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.obj = "数据格式错误";
					msg.what = GOVER_ITEMCOUNT_LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = GOVER_ITEMCOUNT_LOAD_ERROR;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void showDept() {

		sp_dept.setAdapter(new DeptSpinnerAdapter(depts, context));
	};

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	private void onTransaction(BaseFragment fragment) {

		fragment.setArguments(this.getArguments());// 传递主框架对象
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		fragment.setManagers(managers);// 传递managers
		ft.replace(CONTENT_MAIN_ID, fragment);

		ft.commit();

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}

	private void showSearchView(int type) {

		switch (type) {
		case SEARCH_BYTHING:
			ll_searchbything.setVisibility(LinearLayout.VISIBLE);
			ll_searchby_dept.setVisibility(LinearLayout.GONE);
			ll_searchby_range.setVisibility(LinearLayout.GONE);
			ll_itemcount.setVisibility(LinearLayout.GONE);
			ll_state.setVisibility(LinearLayout.GONE);
			break;
		case SEARCH_BYDEPARENT:
			ll_searchbything.setVisibility(LinearLayout.GONE);
			ll_searchby_dept.setVisibility(LinearLayout.VISIBLE);
			ll_searchby_range.setVisibility(LinearLayout.GONE);
			ll_itemcount.setVisibility(LinearLayout.GONE);
			ll_state.setVisibility(LinearLayout.GONE);
			break;
		case SEARCH_BYRANGE:
			ll_searchbything.setVisibility(LinearLayout.GONE);
			ll_searchby_dept.setVisibility(LinearLayout.GONE);
			ll_searchby_range.setVisibility(LinearLayout.VISIBLE);
			ll_itemcount.setVisibility(LinearLayout.GONE);
			ll_state.setVisibility(LinearLayout.GONE);
			break;
		case COUNTPOP_WINDOW:
			ll_searchbything.setVisibility(LinearLayout.GONE);
			ll_searchby_dept.setVisibility(LinearLayout.GONE);
			ll_searchby_range.setVisibility(LinearLayout.GONE);
			ll_itemcount.setVisibility(LinearLayout.VISIBLE);
			ll_state.setVisibility(LinearLayout.GONE);
			break;
		case SEARCH_BYSTATE:
			ll_searchbything.setVisibility(LinearLayout.GONE);
			ll_searchby_dept.setVisibility(LinearLayout.GONE);
			ll_searchby_range.setVisibility(LinearLayout.GONE);
			ll_itemcount.setVisibility(LinearLayout.GONE);
			ll_state.setVisibility(LinearLayout.VISIBLE);
			break;

		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.search_bything:
			if (ll_searchbything.getVisibility() == LinearLayout.GONE) {
				showSearchView(SEARCH_BYTHING);
			} else {
				ll_searchbything.setVisibility(LinearLayout.GONE);
			}

			break;
		case R.id.search_bydeparent:
			if (ll_searchby_dept.getVisibility() == LinearLayout.GONE) {
				showSearchView(SEARCH_BYDEPARENT);
			} else {
				ll_searchby_dept.setVisibility(LinearLayout.GONE);
			}
			break;
		case R.id.search_byrange:
			if (ll_searchby_range.getVisibility() == LinearLayout.GONE) {
				showSearchView(SEARCH_BYRANGE);
			} else {
				ll_searchby_range.setVisibility(LinearLayout.GONE);
			}

			break;

		case R.id.goversaloon_btn_statesearch:
			if (ll_state.getVisibility() == LinearLayout.GONE) {
				showSearchView(SEARCH_BYSTATE);
			} else {
				ll_state.setVisibility(LinearLayout.GONE);
			}
			break;
		case R.id.goversaloon_btn_count:
			if (ll_itemcount.getVisibility() == LinearLayout.GONE) {
				showSearchView(COUNTPOP_WINDOW);
			} else {
				ll_itemcount.setVisibility(LinearLayout.GONE);
			}
			break;

		case R.id.btn_searchbything:// 按事项检索按钮事件

			searchHandler(SEARCH_BYTHING);

			break;
		case R.id.btn_search_bydept:// 按部门筛选
			searchHandler(SEARCH_BYDEPARENT);
			break;
		case R.id.btn_searchbyrange:// 按范围筛选
			searchHandler(SEARCH_BYRANGE);

			break;
		case R.id.btn_search_bystate:// 按行政状态查询
			searchHandler(SEARCH_BYSTATE);
			break;

		}
	}

	/**
	 * 
	 * wanglu 泰得利通 搜索处理
	 * 
	 * @param type
	 */
	private void searchHandler(int type) {
		Bundle bundle = this.getArguments();
		HashMap<String, String> params = new HashMap<String, String>();
		switch (type) {
		case SEARCH_BYTHING:
			String textContent = et_searchbying_content.getText().toString();
			if (textContent.equals("")) {
				Toast.makeText(context, "请输入检索类容", Toast.LENGTH_SHORT).show();
				return;
			}

			if (sp_item_type.getSelectedItemPosition() == 0) {
				params.put("itemname", textContent);
			} else if (sp_item_type.getSelectedItemPosition() == 1) {
				params.put("itemcode", textContent);
			}
			params.put("qltype", getType(SEARCH_BYRANGE));
			break;
		case SEARCH_BYDEPARENT:
			Dept dept = (Dept) sp_dept.getSelectedItem();

			params.put("deptid", dept.getId());
			params.put("qltype", getType(SEARCH_BYRANGE));
			break;
		case SEARCH_BYRANGE:
			params.put("qltype", getType(SEARCH_BYRANGE));
			break;

		case SEARCH_BYSTATE:
			String itemCode = et_state_itemcode.getText().toString();
			if (itemCode.equals("")) {
				Toast.makeText(context, "请输入办件标编号", Toast.LENGTH_SHORT).show();
				return;
			}

			params.put("itemcode", itemCode);
			params.put("qltype", getType(SEARCH_BYSTATE));

			break;
		}

		ll_searchbything.setVisibility(LinearLayout.GONE);
		ll_searchby_dept.setVisibility(LinearLayout.GONE);
		ll_searchby_range.setVisibility(LinearLayout.GONE);

		ll_state.setVisibility(LinearLayout.GONE);

		bundle.putSerializable(SearchResultFragment.PARAMS_KEY, params);
		SearchResultFragment searchResultFragment = new SearchResultFragment();
		searchResultFragment.setArguments(bundle);
		onTransaction(searchResultFragment);

	}

	/**
	 * 
	 * wanglu 泰得利通 行政类型 { "行政许可", "行政处罚", "行政征收", "行政强制", "其它" };
	 * 
	 * @return
	 */
	private String getType(int searchType) {

		int position = 0;
		if (searchType != SEARCH_BYSTATE) {
			position = goversaloon_sp_szxk.getSelectedItemPosition();
		} else {
			position = sp_xzstate.getSelectedItemPosition();
		}
		String type = "QT";
		switch (position) {
		case 0:
			type = "XK";
			break;
		case 1:
			type = "CF";
			break;
		case 2:
			type = "ZS";
			break;
		case 3:
			type = "QZ";
			break;
		case 4:
			type = "QT";
			break;

		}

		return type;
	}
}
