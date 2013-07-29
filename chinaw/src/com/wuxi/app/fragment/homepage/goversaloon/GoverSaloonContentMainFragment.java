package com.wuxi.app.fragment.homepage.goversaloon;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

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
	private PopupWindow bythingPopWindow, bydeptPopWindow, byRangPopWindow,
			byStatePopupWindow;
	private PopWindowManager popWindowManager;
	private static final int CONTENT_MAIN_ID = R.id.gover_content_main;
	private static final int SEARCH_BYTHING = 0, SEARCH_BYDEPARENT = 1,
			SEARCH_BYRANGE = 2, SEARCH_BYSTATE = 3;
	private Spinner goversaloon_sp_szxk;
	private Button goversaloon_btn_statesearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.goversaloon_content_layout, null);
		context = getActivity();
		initUI();
		popWindowManager = PopWindowManager.getInstance();
		return view;
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
		goversaloon_btn_statesearch = (Button) view
				.findViewById(R.id.goversaloon_btn_statesearch);
		goversaloon_title_search.setOnCheckedChangeListener(this);
		goversaloon_btn_statesearch.setOnClickListener(this);
		search_bything.setOnClickListener(this);
		search_bydeparent.setOnClickListener(this);
		search_byrange.setOnClickListener(this);

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

	}

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

	/**
	 * 
	 * wanglu 泰得利通 按办事事项搜索
	 */
	private void showSearchPopWindow(int type) {
		int location[] = new int[2];

		search_bything.getLocationInWindow(location);
		int showX = location[0];
		int showY = location[1] + search_bything.getHeight();
		int endlocation[] = new int[2];
		search_byrange.getLocationInWindow(endlocation);
		int width = (endlocation[0] + search_byrange.getWidth()) - showX - 30;
		View contentView = null;

		int bottomLocation[] = new int[2];
		goversaloon_sp_szxk.getLocationInWindow(bottomLocation);
		int bottomSowY= bottomLocation[1]-75;
		switch (type) {
		case SEARCH_BYTHING:// 按事项搜索
			contentView = View.inflate(context,
					R.layout.goversaloon_searchby_thing_layout, null);
			bythingPopWindow = new PopupWindow(contentView, width,
					LayoutParams.WRAP_CONTENT);

			bythingPopWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP,
					showX, showY);

			popWindowManager.addPopWindow(bythingPopWindow);
			break;
		case SEARCH_BYDEPARENT:// 按部门搜索

			contentView = View.inflate(context,
					R.layout.goversaloon_searchby_department_layout, null);
			bydeptPopWindow = new PopupWindow(contentView, width,
					LayoutParams.WRAP_CONTENT);

			bydeptPopWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP,
					showX, showY);

			popWindowManager.addPopWindow(bydeptPopWindow);

			break;
		case SEARCH_BYRANGE:// 按范围搜索

			contentView = View.inflate(context,
					R.layout.goversaloon_searchby_range_layout, null);
			byRangPopWindow = new PopupWindow(contentView, width,
					LayoutParams.WRAP_CONTENT);

			byRangPopWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP,
					showX, showY);

			popWindowManager.addPopWindow(byRangPopWindow);
			break;
		case SEARCH_BYSTATE:// 状态查询
			contentView = View.inflate(context,
					R.layout.goversaloon_searchby_state_layout, null);
			byStatePopupWindow = new PopupWindow(contentView, width,
					LayoutParams.WRAP_CONTENT);

			byStatePopupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP,
					showX, bottomSowY);

			popWindowManager.addPopWindow(byStatePopupWindow);
			break;

		}

	}

	private void dismissPopWindow(int type) {

		switch (type) {
		case SEARCH_BYTHING:

			popWindowManager.dissMissPopWindow(bydeptPopWindow);
			bydeptPopWindow = null;

			popWindowManager.dissMissPopWindow(byRangPopWindow);
			byRangPopWindow = null;

			popWindowManager.dissMissPopWindow(byStatePopupWindow);
			byStatePopupWindow = null;
			break;
		case SEARCH_BYDEPARENT:

			popWindowManager.dissMissPopWindow(bythingPopWindow);
			bythingPopWindow = null;

			popWindowManager.dissMissPopWindow(byRangPopWindow);
			byRangPopWindow = null;

			popWindowManager.dissMissPopWindow(byStatePopupWindow);
			byStatePopupWindow = null;
			break;
		case SEARCH_BYRANGE:

			popWindowManager.dissMissPopWindow(bythingPopWindow);
			bythingPopWindow = null;

			popWindowManager.dissMissPopWindow(bydeptPopWindow);
			bydeptPopWindow = null;

			popWindowManager.dissMissPopWindow(byStatePopupWindow);
			byStatePopupWindow = null;

			break;
		case SEARCH_BYSTATE:

			popWindowManager.dissMissPopWindow(bythingPopWindow);
			bythingPopWindow = null;

			popWindowManager.dissMissPopWindow(bydeptPopWindow);
			bydeptPopWindow = null;

			popWindowManager.dissMissPopWindow(byRangPopWindow);
			byRangPopWindow = null;
			break;

		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.search_bything:
			if (popWindowManager.getPopupWindow(bythingPopWindow) != null) {
				popWindowManager.dissMissPopWindow(bythingPopWindow);
				bythingPopWindow = null;

			} else {
				showSearchPopWindow(SEARCH_BYTHING);
				dismissPopWindow(SEARCH_BYTHING);
			}

			break;
		case R.id.search_bydeparent:
			if (popWindowManager.getPopupWindow(bydeptPopWindow) != null) {
				popWindowManager.dissMissPopWindow(bydeptPopWindow);
				bydeptPopWindow = null;

			} else {
				showSearchPopWindow(SEARCH_BYDEPARENT);
				dismissPopWindow(SEARCH_BYDEPARENT);
			}

			break;
		case R.id.search_byrange:
			if (popWindowManager.getPopupWindow(byRangPopWindow) != null) {
				popWindowManager.dissMissPopWindow(byRangPopWindow);
				byRangPopWindow = null;

			} else {
				showSearchPopWindow(SEARCH_BYRANGE);
				dismissPopWindow(SEARCH_BYRANGE);
			}

			break;

		case R.id.goversaloon_btn_statesearch:
			if (popWindowManager.getPopupWindow(byStatePopupWindow) != null) {
				popWindowManager.dissMissPopWindow(byStatePopupWindow);
				byStatePopupWindow = null;

			} else {
				showSearchPopWindow(SEARCH_BYSTATE);
				dismissPopWindow(SEARCH_BYSTATE);
			}
			break;

		}
	}

}
