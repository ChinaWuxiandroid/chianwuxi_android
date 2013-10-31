package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.commonfragment.SimpleListViewFragment;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.domain.MenuItem;

/**
 * 政府公开信息 之 市政府信息公开目录
 * 
 * @author 杨宸 智佳
 * */

public class MunicipalGovInfoPublicDirecFragment extends BaseFragment implements
		InitializContentLayoutListner {

	private View view;

	private MenuItem menuItem;// 菜单项
	private List<MenuItem> titleMenus;// 头部菜单选项

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(
						R.layout.fragment_publicgovermsg_municipalgovinfopublicdirec_layout,
						null);

		initUI();
		return view;
	}

	/**
	 * @方法： initUI
	 * @描述： 初始化视图
	 */
	private void initUI() {
		NavigatorWithContentFragment navigatorWithContentFragment = new NavigatorWithContentFragment();
		navigatorWithContentFragment.setParentMenuItem(menuItem);
		navigatorWithContentFragment
				.setDataType(NavigatorWithContentFragment.DATA_TPYE_MENUITEM);

		onTransaction(navigatorWithContentFragment);
	}

	/**
	 * @方法： onTransaction
	 * @描述： 切换视图
	 * @param fragment
	 */
	private void onTransaction(NavigatorWithContentFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.goverpublic_titlebelow_content_layout, fragment);
		ft.addToBackStack(null);
		ft.commitAllowingStateLoss();
	}

	@Override
	public void bindContentLayout(BaseFragment fragment) {

	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public void initializSubFragmentsLayout() {

		for (MenuItem menu : titleMenus) {
			if (menu.getName().equals("最新信息公开")
					|| menu.getName().equals("信息公开指南")
					|| menu.getName().equals("信息公开制度"))
				menu.setContentFragment(SimpleListViewFragment.class);
			else if (menu.getName().equals("工作意见箱"))
				menu.setContentFragment(WorkSuggestionBoxFragment.class);
			else
				menu.setContentFragment(NavigatorWithContentFragment.class);
		}
	}

	@Override
	public void redirectFragment(MenuItem showMenuItem, int showMenuPositon,
			int subMenuPostion) {

	}

}
