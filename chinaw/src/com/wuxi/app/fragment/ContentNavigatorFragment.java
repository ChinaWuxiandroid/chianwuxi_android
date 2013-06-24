package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentNavigatorAdapter;
import com.wuxi.domain.NavigatorItmeAction;

/**
 * 有足有导航的fragment
 * 
 * @author wanglu
 * 
 */
public class ContentNavigatorFragment extends BaseFragment implements
		OnItemClickListener {

	private View view;
	private ListView mListView;
	private LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.content_navigator_layout, null);
		mListView = (ListView) view.findViewById(R.id.lv_left_navigator);
		mInflater = inflater;
		initData();

		return view;

	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	private List<NavigatorItmeAction> getNavigatorItmeActions() {
		List<NavigatorItmeAction> naItems = new ArrayList<NavigatorItmeAction>();
		for (int i = 0; i < 5; i++) {
			naItems.add(new NavigatorItmeAction("标题" + i, DataFragment
					.Instance(i)));
		}

		return naItems;

	}

	/**
	 * 初始化左侧导航数据，及初始化右侧主要显示区域数据
	 */
	private void initData() {
		mListView.setAdapter(new ContentNavigatorAdapter(mInflater,
				getNavigatorItmeActions()));//
		mListView.setOnItemClickListener(this);
		
		showContentFragment(DataFragment
				.Instance(0));//默认显示第一个
	}

	/**
	 * 显示替换主要内容区域
	 * @param fragment
	 */
	private void showContentFragment(Fragment fragment){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(com.wuxi.app.R.id.details, fragment);// 替换视图

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		NavigatorItmeAction naItem = (NavigatorItmeAction) parent
				.getItemAtPosition(position);
		DataFragment df = (DataFragment) naItem.getFrament();

		showContentFragment(df);

	}

}
