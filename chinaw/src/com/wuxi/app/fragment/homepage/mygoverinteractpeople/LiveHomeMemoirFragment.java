/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

/**
 * 栏目首页中访谈实录碎片布局
 * 
 * @author 智佳 罗森
 * @time 2013年8月8日 20:42
 * 
 */
public class LiveHomeMemoirFragment extends BaseFragment {

	private View view = null;

	private Context context = null;
	
	private ProgressBar memoirBar = null;
	
	private ListView memoirListView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.gip_live_home_memoir_layout, null);
		context = getActivity();
		
		initLayout();
		
		return view;
	}
	
	/**
	 * 加载布局文件
	 */
	private void initLayout(){
		memoirListView = (ListView) view.findViewById(R.id.gip_live_home_memoir_listview);
		
		memoirBar = (ProgressBar) view.findViewById(R.id.gip_live_home_memoir_progressbar);
	}

}
