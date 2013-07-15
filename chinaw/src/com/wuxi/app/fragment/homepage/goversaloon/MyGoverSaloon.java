package com.wuxi.app.fragment.homepage.goversaloon;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.MyOnlineAskAdapter;

/**
 * 
 * @author wanglu 泰得利通 我的政务大厅
 * 
 */
public class MyGoverSaloon extends BaseFragment implements
		OnCheckedChangeListener {

	private View view;
	private RadioGroup gover_btn_rg;
	private RadioButton gover_sallon_my_ask;
	private RadioButton gover_sallon_my_apply;
	private ListView gover_saloon_lvmygover;
	private Context context;
	private String itemString[] = { "1.关于在新区梅园镇建立垃圾回收的问题咨询的问题的咨询(2013-5-3)",
			"2.关于在新区梅园镇建立垃圾回收的问题咨询的问题的咨询(2013-5-3)" };
	private MyOnlineAskAdapter myOnlineAskAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.my_goversaloon_layout, null);
		context=getActivity();
		initUI();
		initData();
		return view;
	}

	private void initUI() {
		gover_btn_rg = (RadioGroup) view.findViewById(R.id.gover_btn_rg);
		gover_btn_rg.setOnCheckedChangeListener(this);
		gover_sallon_my_ask = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_ask);
		gover_sallon_my_apply = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_apply);
		gover_saloon_lvmygover = (ListView) view
				.findViewById(R.id.gover_saloon_lvmygover);

	}

	private void initData() {
		RadioButton rb = (RadioButton) gover_btn_rg.getChildAt(0);
		rb.setChecked(true);
		myOnlineAskAdapter = new MyOnlineAskAdapter(itemString, context);

		gover_saloon_lvmygover.setAdapter(myOnlineAskAdapter);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.gover_sallon_my_ask:// 我的咨询列表
			gover_saloon_lvmygover.setAdapter(myOnlineAskAdapter);
			break;
		case R.id.gover_sallon_my_apply:// 我的申报列表

			break;
		}

		for (int i = 0; i < group.getChildCount(); i++) {
			RadioButton rb = (RadioButton) group.getChildAt(i);
			if (rb.isChecked()) {
				rb.setTextColor(Color.WHITE);
				rb.setBackground(getResources().getDrawable(
						R.drawable.goversaloon_menuitem_bg));
			} else {
				rb.setTextColor(Color.BLACK);
				rb.setBackgroundColor(getResources().getColor(
						R.color.content_background));
			}

		}
	}

}
