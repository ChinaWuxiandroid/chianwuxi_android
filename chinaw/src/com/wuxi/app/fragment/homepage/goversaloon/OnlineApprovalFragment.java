package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Dept;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 网上审批
 * 
 */
public class OnlineApprovalFragment extends GoverSaloonContentFragment
		implements OnScrollListener, OnItemSelectedListener,
		OnItemClickListener, OnClickListener {

	protected static final int LOAD_DEPT_SUCCESS = 0;
	protected static final int LOAD_DEPT_FAIL = 1;
	protected static final int GOVERITEM_LOAD_SUCCESS = 2;
	protected static final int GOVERITEM_LOAD_FIAL = 3;
	private static final int PAGE_SIZE = 10;
	private Spinner gover_oline_filter;
	private ListView gover_online_approval_lv;
	private ProgressBar pb_approval;// 进度条
	private GoverSaoonItemWrapper goverSaoonItemWrapper;
	private boolean isFirstLoadGoverItem = true;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private String currentDeptId = "";
	private GoverOnlineApproveAdapter goverOnlineApproveAdapter;
	private boolean isSwitchDept = false;

	private List<Dept> depts;// 部门
	private ProgressBar pb_loadmoore;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case LOAD_DEPT_SUCCESS:
				showDept();
				break;
			case GOVERITEM_LOAD_SUCCESS:
				showItemList();
				break;
			case GOVERITEM_LOAD_FIAL:
			case LOAD_DEPT_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}

		}
	};

	/**
	 * 获取办件信息
	 */
	private void loadItem(final String deptId, final int start, final int end) {

		if (isFirstLoadGoverItem || isSwitchDept) {// 首次加载时或切换部门时显示进度条

			pb_approval.setVisibility(ProgressBar.VISIBLE);
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
							.getGoverSaoonItemsByDeptId(deptId, start, end);
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
				gover_online_approval_lv.setAdapter(goverOnlineApproveAdapter);
				pb_approval.setVisibility(ProgressBar.GONE);

			} else {

				if (isSwitchDept) {// 切换部门
					goverOnlineApproveAdapter
							.setGoverSaoonItems(goverSaoonItems);
					pb_approval.setVisibility(ProgressBar.GONE);
				} else {
					for (GoverSaoonItem item : goverSaoonItems) {
						goverOnlineApproveAdapter.addItem(item);
					}
				}

				goverOnlineApproveAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_online_approval_lv.setSelection(visibleLastIndex
						- visibleItemCount + 1); // 设置选中项

			}

		}

		if (goverSaoonItemWrapper.isNext()) {
			if (gover_online_approval_lv.getFooterViewsCount() != 0) {
				loadMoreButton.setText("点击加载更多 ");
				pb_loadmoore.setVisibility(ProgressBar.GONE);
			} else {
				gover_online_approval_lv.addFooterView(getFootView());
			}

		} else {

			gover_online_approval_lv.removeFooterView(loadMoreView);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonContentFragment
	 * #initUI()
	 */
	public void initUI() {
		super.initUI();
		gover_oline_filter = (Spinner) view
				.findViewById(R.id.gover_oline_filter);
		gover_oline_filter.setOnItemSelectedListener(this);
		gover_online_approval_lv = (ListView) view
				.findViewById(R.id.gover_online_approval_lv);
		gover_online_approval_lv.setOnItemClickListener(this);

		pb_approval = (ProgressBar) view.findViewById(R.id.pb_approval);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		gover_online_approval_lv.addFooterView(getFootView());
		gover_online_approval_lv.setOnScrollListener(this);

		loadDept();// 加载部门
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

		gover_oline_filter.setAdapter(new DeptSpinnerAdapter(depts, context));

		gover_oline_filter.setSelection(0);
		currentDeptId = depts.get(0).getId();
		loadItem(currentDeptId, 0, PAGE_SIZE);// 加载第一个部门数据

	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_onlineapproval;
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int postion,
			long arg3) {

		Dept dept = (Dept) parent.getItemAtPosition(postion);
		if (dept.getId() != null) {
			this.currentDeptId = dept.getId();
			loadItem(currentDeptId, 0, PAGE_SIZE);
			isSwitchDept = true;
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
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.loadMoreButton:
			if (goverSaoonItemWrapper != null && goverSaoonItemWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitchDept = false;
				loadItem(currentDeptId, visibleLastIndex + 1, visibleLastIndex
						+ 1 + PAGE_SIZE);

			}

			break;

		}

	}

}
