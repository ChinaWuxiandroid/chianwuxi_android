/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GIPMineSugMySurveyFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述: TODO(用一句话描述该文件做什么) 
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-14 下午4:43:37
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import android.content.Context;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

/**
 * @类名： GIPMineSugMySurveyFragment
 * @描述： TODO
 * @作者： 罗森
 * @创建时间： 2013 2013-10-14 下午4:43:37
 * @修改时间： 
 * @修改描述： 
 */
public class GIPMineSugMySurveyFragment extends BaseFragment implements OnItemClickListener, OnClickListener, OnScrollListener{
	
	private Context context;
	private View view;
	
	private ListView mListView = null;
	private ProgressBar list_pb = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_main_12345_my_reply_letter_layout,
				null);
		context = getActivity();
		
		initLayout();
		
		return view;
	}
	
	private void initLayout(){
		mListView = (ListView) view
				.findViewById(R.id.goverinterpeople_mine_12345_listview);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.goverinterpeople_mine_12345_progressbar);
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
}
