package com.wuxi.app.fragment.homepage.goversaloon;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.domain.Myconsult;

/**
 * 
 * @author wanglu 泰得利通 我的在线咨询
 * 
 */
public class MyOnlineAskFragment extends BaseSlideFragment implements
		android.widget.RadioGroup.OnCheckedChangeListener {

	private RadioGroup gover_btn_rg;
	private LinearLayout gover_myonlineask_detail;// 咨询类容相信信息视图
	private LinearLayout gover_myonline_goonask;// 继续咨询视图
	private RadioButton gover_sallon_my_ask_detail;// 我的咨询内容
	private RadioButton gover_sallon_my_goon_ask;// 咨询咨询
	public static final int MYASK = 1;// 我的咨询内容标识
	public static final int GOASK = 2;// 继续咨询标识
	private int showType = MYASK;
	private Myconsult myconsult;

	public void setMyconsult(Myconsult myconsult) {
		this.myconsult = myconsult;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	@Override
	public void initUI() {
		Myconsult myconsult = (Myconsult) this.getArguments().get(
				"selectMyconsult");
		if (myconsult != null) {
			showType = MYASK;
		} else {
			showType = GOASK;
		}
		super.initUI();
		gover_btn_rg = (RadioGroup) view.findViewById(R.id.gover_btn_rg);
		gover_myonlineask_detail = (LinearLayout) view
				.findViewById(R.id.gover_myonlineask_detail);
		gover_myonline_goonask = (LinearLayout) view
				.findViewById(R.id.gover_myonline_goonask);
		gover_sallon_my_ask_detail = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_ask_detail);
		gover_sallon_my_goon_ask = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_goon_ask);
		gover_btn_rg.setOnCheckedChangeListener(this);

		showUI();

	}

	public void showUI() {
		switch (showType) {
		case MYASK:
			gover_myonlineask_detail.setVisibility(LinearLayout.VISIBLE);
			gover_myonline_goonask.setVisibility(LinearLayout.GONE);
			gover_btn_rg.check(R.id.gover_myonlineask_detail);
			gover_sallon_my_ask_detail.setBackground(getResources()
					.getDrawable(R.drawable.goversaloon_menuitem_bg));
			gover_sallon_my_ask_detail.setTextColor(Color.WHITE);
			break;
		case GOASK:
			gover_myonlineask_detail.setVisibility(LinearLayout.GONE);
			gover_myonline_goonask.setVisibility(LinearLayout.VISIBLE);
			gover_btn_rg.check(R.id.gover_myonline_goonask);
			gover_sallon_my_goon_ask.setBackground(getResources().getDrawable(
					R.drawable.goversaloon_menuitem_bg));
			gover_sallon_my_goon_ask.setTextColor(Color.WHITE);
			break;
		}
	}

	private void showMyConsultData() {

	}

	@Override
	protected int getLayoutId() {
		return R.layout.gover_myonline_ask_layout;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {

		case R.id.gover_sallon_my_ask_detail:
			this.showType = MYASK;
			gover_sallon_my_ask_detail.setTextColor(Color.WHITE);
			gover_sallon_my_goon_ask.setTextColor(Color.BLACK);
			gover_sallon_my_ask_detail.setBackground(getResources()
					.getDrawable(R.drawable.goversaloon_menuitem_bg));
			gover_sallon_my_goon_ask.setBackgroundColor(getResources()
					.getColor(R.color.content_background));
			break;
		case R.id.gover_sallon_my_goon_ask:
			this.showType = GOASK;
			gover_sallon_my_ask_detail.setTextColor(Color.BLACK);
			gover_sallon_my_goon_ask.setTextColor(Color.WHITE);

			gover_sallon_my_goon_ask.setBackground(getResources().getDrawable(
					R.drawable.goversaloon_menuitem_bg));
			gover_sallon_my_ask_detail.setBackgroundColor(getResources()
					.getColor(R.color.content_background));
			break;
		}

		showUI();
	}

	@Override
	protected String getTitleText() {

		return "我的在线咨询";
	}

}
