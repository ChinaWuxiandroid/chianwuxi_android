package com.wuxi.app.fragment.index.type;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.MenuItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * 
 * @author wanglu 泰得利通 资讯中心
 * 
 */
public class InformationCenterFragment extends BaseSlideFragment implements
		InitializContentLayoutListner, OnClickListener {

	private LayoutInflater inflater;
	private Context context;
	private MenuItem menuItem;
	private List<MenuItem> titleMenuItems;// 头部子菜单
	private TitleScrollLayout mtitleScrollLayout;
	private ImageButton ib_nextItems;

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_chanel_layout, null);
		this.InitBtn();
		this.setFragmentTitle(menuItem.getName());// 设置头部名称
		this.inflater = inflater;
		context = this.getActivity();
		initUI();
		return view;
	}

	private void initUI() {
		mtitleScrollLayout = (TitleScrollLayout) view
				.findViewById(R.id.title_scroll_action);// 头部控件
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器

		ib_nextItems = (ImageButton) view.findViewById(R.id.btn_next_screen);// 头部下一个按钮
		ib_nextItems.setOnClickListener(this);

		loadTitleData();
	}

	/**
	 * 
	 * wanglu 泰得利通 加载头部菜单数据
	 */
	@SuppressWarnings("unchecked")
	private void loadTitleData() {

		if (CacheUtil.get(menuItem.getId()) != null) {// 从缓存中查找子菜单
			titleMenuItems = (List<MenuItem>) CacheUtil.get(menuItem.getId());
			showTitleData();
		}else{//从网络加载获取
			
			
			
		}

	}

	/**
	 * 显示头部数据 wanglu 泰得利通
	 */
	private void showTitleData() {

		
		mtitleScrollLayout
				.initMenuItemScreen(context, inflater, titleMenuItems);// 初始化头部空间
		// initData(titleChannels.get(0));//默认显示第一个channel的子channel页

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void bindContentLayout(Fragment fragment) {

	}

}
