package com.wuxi.app.fragment.homepage.goversaloon;

import org.xmlpull.v1.XmlPullParser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

/**
 * 
 * @author wanglu 泰得利通 内容页父类
 * 
 */
public abstract class GoverSaloonContentFragment extends BaseFragment {

	protected View view;
	private RadioGroup goversaloon_title_search;
	private RadioButton search_bything;
	private RadioButton search_bydeparent;
	private RadioButton search_byrange;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(getLayoutId(), null);
		initUI();
		
		
		return view;
	}

	
	private void initUI() {
		goversaloon_title_search=(RadioGroup) view.findViewById(R.id.goversaloon_title_search);
		search_bything=(RadioButton) view.findViewById(R.id.search_bything);
		search_bydeparent=(RadioButton) view.findViewById(R.id.search_bydeparent);
		search_byrange=(RadioButton) view.findViewById(R.id.search_byrange);
	
	}

	/**
	 * 子类实现 wanglu 泰得利通 该视图必须含有有头部和底部的view
	 * 
	 * @return
	 */
	protected abstract int getLayoutId();

}
