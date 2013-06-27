package com.wuxi.app.fragment;

import java.util.List;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentNavigatorAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.NavigatorItmeAction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 具有左右导航视图父类
 * 
 * @author wanglu
 * 
 */
public abstract class NavigatorFragment extends BaseFragment implements
		OnItemClickListener {
	private static final int DETAIL_ID = R.id.details;// 点击左侧导航时右侧要显示内容区域的ID
	protected View view;
	protected ListView mListView;// 左侧ListView
	protected LayoutInflater mInflater;
	private Channel parentChannel;// 父频道
	private Context context;

	private List<Channel> channels;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.content_navigator_layout, null);
		mListView = (ListView) view.findViewById(R.id.lv_left_navigator);

		Drawable drawable = getResources()
				.getDrawable(R.drawable.navgator_back);
		mListView.setSelector(drawable);
		mInflater = inflater;
		context=getActivity();
		initData();

		return view;

	}

	@SuppressWarnings("unchecked")
	private void loadData() {

		if (null != CacheUtil.get(parentChannel.getChannelId())) {// 从缓存中查找
			channels = (List<Channel>) CacheUtil.get(parentChannel
					.getChannelId());

		} else {// 从网络加载

			new Thread(new Runnable() {

				@Override
				public void run() {

					ChannelService channelService=new ChannelService(context);
					
				}
			}

			).start();

		}

	}

	/**
	 * 初始化显示的数据
	 */
	protected void initData() {

		List<NavigatorItmeAction> naItmes = getNavigatorItmeActions();
		if (naItmes != null && naItmes.size() > 0) {

			mListView
					.setAdapter(new ContentNavigatorAdapter(mInflater, naItmes));// 设置适配器
			mListView.setOnItemClickListener(this);
			showContentFragment(naItmes.get(0).getFrament());// 默认显示第一个

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		NavigatorItmeAction naItem = (NavigatorItmeAction) parent
				.getItemAtPosition(position);
		DataFragment df = (DataFragment) naItem.getFrament();

		showContentFragment(df);

	}

	/**
	 * 获取左侧导航数据及点击导航显示的右侧数据视图的封装集合
	 * 
	 * @return
	 */
	protected abstract List<NavigatorItmeAction> getNavigatorItmeActions();

	/**
	 * 显示替换主要内容区域
	 * 
	 * @param fragment
	 */
	protected void showContentFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(DETAIL_ID, fragment);// 替换视图

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();

	}

	public void setParentChannel(Channel parentChannel) {
		this.parentChannel = parentChannel;
	}
}
