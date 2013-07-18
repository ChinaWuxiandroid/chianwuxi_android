package com.wuxi.app.fragment.homepage.goversaloon;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 政务大厅内容主Fragment
 */
public class GoverSaloonContentMainFragment extends BaseFragment {

	private MenuItem menuItem;
	private View view;
	

	private static final int CONTENT_MAIN_ID = R.id.gover_content_main;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	
		view = inflater.inflate(R.layout.goversaloon_content_layout, null);
		initUI();
		return view;
	}

	private void initUI() {
		
		if (menuItem.getType() == MenuItem.CUSTOM_MENU) {

			if (menuItem.getName().equals("我的政务大厅")) {
				onTransaction(new MyGoverSaloonFragment());
			} else if (menuItem.getName().equals("效能投诉")) {
				onTransaction(new EfficacyComplaintFragment());
			} else if (menuItem.getName().equals("行政事项")) {
				onTransaction(new AdministrativeItemFragment());
			}

		} else if (menuItem.getType() == MenuItem.CHANNEL_MENU) {
			onTransaction(new GoverMangeFragment());
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
		
		fragment.setArguments(this.getArguments());//传递主框架对象
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		fragment.setManagers(managers);// 传递managers
		ft.replace(CONTENT_MAIN_ID, fragment);

		ft.commit();

	}

}
