package com.wuxi.app.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.wuxi.app.R;
import com.wuxi.app.adapter.SimpleListViewFragmentAdapter;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;

import com.wuxi.app.fragment.search.AdvancedSearchFragment;



/**
 * 全站搜索模块  主Fragment
 * @author 杨宸 智佳
 * */

public class MainSearchFragment extends HomeBaseSlideLevelFragment{

	private EditText wordKey_Edt;        //搜索关键字输入框
	private ImageButton normalSearch_Btn,advancedSearch_Btn;   //普通搜索  和    跳转到高级搜索 按钮
	protected ListView resultListView;						// 搜索结果ListView 

	@Override
	protected void initUI() {
		super.initUI();
		findView();
	}
	
	public void findView(){
		wordKey_Edt=(EditText)view.findViewById(R.id.search_editText_keyword);
		normalSearch_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_normal_search);
		advancedSearch_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_to_advanced_search);
		resultListView=(ListView)view.findViewById(R.id.search_listView_result);
		
		normalSearch_Btn.setOnClickListener(this);
		advancedSearch_Btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.search_imageButton_normal_search:
			String keyWord=wordKey_Edt.getText().toString();
			inflateResultListView();

			break;
		case R.id.search_imageButton_to_advanced_search:
			HomeBaseSlideLevelFragment searchAdvancedFragment = new AdvancedSearchFragment();
			managers.IntentFragment(searchAdvancedFragment);
			break;
		}
	}

	/*
	 * 显示搜索结果
	 * */
	public void inflateResultListView(){
		//测试数据
		String[] testData={"无锡锡山山无锡 (关联度: 100%)无锡得名于山，传说战国末年，秦始皇派大将王翦大军打楚国时，军队常驻锡山，士兵发现刻有“有锡兵，天下争”，“无锡宁，天下清”等字句古碑，王翦经问百姓知http://www.wuxi.gov.cn/mlxc/wxrw/wxcs/6190478.shtml - 4.93 kB",
				"无锡景（无锡民歌） (关联度: 99%)《无锡景》是江浙一带流行的典型曲调。主要叙述了在旧时无锡的一个茶楼里,游客面对著万顷碧波,憩歇品茶,一位歌女在一把二胡的伴下唱著“我有一段情,唱拨拉诸公听……”幽雅清脆的歌声为游客们助兴http://www.wuxi.gov.cn/mlxc/csmp/mqu/5895589.sht",
		"宣传无锡 推介无锡 (关联度: 98%)大连软交会”登录处无锡指网生物识别科技有限公司展台人头攒动无锡全真通科技有限公司成展会亮点无锡物...数量800家。无锡展区以新颖的设计、先进的展品、高精的技术在整个展区脱颖而出。我市具有"};
		resultListView.setAdapter(new SimpleListViewFragmentAdapter(inflater,testData));
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.search_slide_layout;
	}

	@Override
	protected void onBack() {
		// TODO Auto-generated method stub	
	}

	@Override
	protected String getTtitle() {
		return "全站搜索";
	}
}
