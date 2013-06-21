package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.TitleItemAction;

/**
 * 魅力无锡
 * 
 * @author wanglu
 * 
 */
public class WuxiCityFragment extends BaseFragment implements OnClickListener,
		InitializContentLayoutListner {
	private View view;

	private TitleScrollLayout mtitleScrollLayout;
	private LayoutInflater inflater;
	private ImageButton ib_nextItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.wuxi_city_layout, null);

		this.inflater = inflater;
		initUI();
		return view;
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		mtitleScrollLayout = (TitleScrollLayout) view
				.findViewById(R.id.title_scroll_action);
		mtitleScrollLayout.setItems(getItems());// 设置头部显示的数据
		mtitleScrollLayout.initScreen(getActivity(), inflater);
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器F

		ib_nextItems = (ImageButton) view.findViewById(R.id.btn_next_screen);
		ib_nextItems.setOnClickListener(this);

		bindFragment(new ContentFragment());

	}

	/**
	 * 获取标题头数据
	 * 
	 * @return
	 */
	private List<TitleItemAction> getItems() {
		List<TitleItemAction> items = new ArrayList<TitleItemAction>();

		items.add(new TitleItemAction("无锡概览", new ContentFragment()));
		items.add(new TitleItemAction("好友往来", new TestSwitchFragment()));
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

	@Override
	public void bindContentLayout(BaseFragment fragment) {

		bindFragment(fragment);
	}

	private void bindFragment(BaseFragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.model_main, fragment);// 替换内容界面

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
}
