package com.wuxi.app.fragment.index.type;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wuxi.app.R;
import com.wuxi.app.fragment.ContentNavigatorFragment;
import com.wuxi.app.fragment.TestSwitchFragment1;
import com.wuxi.app.fragment.TestSwitchFragment2;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.TitleItemAction;

public class NiceXCFragment extends BaseSlideFragment implements
		InitializContentLayoutListner, OnClickListener {
	


	private TitleScrollLayout mtitleScrollLayout;
	private LayoutInflater inflater;
	private ImageButton ib_nextItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_nicexc_layout, null);
		this.InitBtn();
		this.setFragmentTitle("魅力无锡");
		this.inflater = inflater;
		initUI();
		return view;
	}

	@Override
	public void bindContentLayout(Fragment fragment) {

		bindFragment(fragment);

	}
	
	/**
	 * 初始化界面
	 */
	private void initUI() {
		mtitleScrollLayout = (TitleScrollLayout) view
				.findViewById(R.id.title_scroll_action);//头部控件
		
		mtitleScrollLayout.setItems(getItems());// 设置头部显示的数据
		mtitleScrollLayout.initScreen(getActivity(), inflater);//初始化头部空间
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器

		ib_nextItems = (ImageButton) view.findViewById(R.id.btn_next_screen);//头部下一个按钮
		ib_nextItems.setOnClickListener(this);

		bindFragment(new ContentNavigatorFragment());

	}

	/**
	 * 获取标题头数据
	 * 
	 * @return
	 */
	private List<TitleItemAction> getItems() {
		List<TitleItemAction> items = new ArrayList<TitleItemAction>();

		items.add(new TitleItemAction("无锡概览", new ContentNavigatorFragment()));
		items.add(new TitleItemAction("好友往来", new TestSwitchFragment1()));
		items.add(new TitleItemAction("好友往来", new TestSwitchFragment2()));
		items.add(new TitleItemAction("无锡人文", null));
		items.add(new TitleItemAction("大事记", null));
		items.add(new TitleItemAction("无锡年鉴", null));
		items.add(new TitleItemAction("城市名片", null));
		items.add(new TitleItemAction("城市地图", null));
		items.add(new TitleItemAction("拉拉拉", null));

		return items;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_screen:// 下一屏
			mtitleScrollLayout.goNextScreen();
			break;

		}

	}

	private void bindFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.model_main, fragment);// 替换内容界面

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}
}
