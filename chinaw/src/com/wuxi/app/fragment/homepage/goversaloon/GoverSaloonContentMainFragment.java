package com.wuxi.app.fragment.homepage.goversaloon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 政务大厅内容主Fragment
 */
public class GoverSaloonContentMainFragment extends BaseFragment implements
		OnCheckedChangeListener {

	private MenuItem menuItem;
	private View view;
	private RadioGroup goversaloon_title_search;
	private static final int SEARCH_BYTHING=1;//按事项赛选
	private static final int SEARCH_BYDEPART=2;//按部门赛选标识
	private static final int SEARCH_BYRANG=3;//按范围赛选标识

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.goversaloon_content_layout, null);
		initUI();
		return view;
	}

	private void initUI() {
		goversaloon_title_search = (RadioGroup) view
				.findViewById(R.id.goversaloon_title_search);
		goversaloon_title_search.setOnCheckedChangeListener(this);

	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch (checkedId) {

		case R.id.search_bything:// 按事项搜索
			
			//goversaloon_title_search.getChildAt(0).
			break;
		case R.id.search_bydeparent:// 按部门搜索
			break;
		case R.id.search_byrange:// 范围搜索
			break;
		}
	}

}
