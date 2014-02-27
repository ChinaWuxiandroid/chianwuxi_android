package com.wuxi.app.fragment.commonfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.SimpleListViewFragmentAdapter;

/**
 * simple ListView 作为一个独立的Fragment嵌入到节目中
 * @author 杨宸 智佳
 * */

public class SimpleListViewFragment extends BaseFragment{
	protected View view;
	protected ListView mListView;// ListView
	protected LayoutInflater mInflater;
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.simple_listview_layout, null);
		mListView = (ListView) view.findViewById(R.id.simple_listview_fragment);
		mInflater = inflater;
		context = getActivity();
		
		initView();
		return view;
	}

	public void initView(){
		String[] testData={".[业务工作]绿羊温泉又或殊荣   （2013-05-24）",
				".[业务工作]我省将开展省级创业投资聚集发展示范人   （2013-05-24）",
				".[业务工作]中心组织召开2014年度居民医疗保证征缴方式   （2013-05-22）"};
		mListView.setAdapter(new SimpleListViewFragmentAdapter(mInflater,testData));
	}
}
