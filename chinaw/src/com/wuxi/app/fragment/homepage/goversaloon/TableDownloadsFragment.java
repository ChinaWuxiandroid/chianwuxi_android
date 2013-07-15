package com.wuxi.app.fragment.homepage.goversaloon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

/**'
 * 
 * @author wanglu 泰得利通
 * 表格下载Fragment
 *
 */
public class TableDownloadsFragment extends BaseFragment {
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.gover_saloon_tabledownload_layout, null);
		return view;
	}
	

}
