package com.wuxi.app.fragment.homepage.more;

import android.view.View;
import android.widget.RelativeLayout;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;

/**
 * 
 * @author wanglu 泰得利通 系统设置
 */
public class SystemSetFragment extends HomeBaseSlideLevelFragment {

	private RelativeLayout sys_menu_set, sys_clear_cache, sys_score, sys_share,
			sys_idea, sys_software_update, sys_about_us, sys_site_map,
			sys_use_help, sys_join_twiiter;

	@Override
	protected void initUI() {
		super.initUI();
		sys_menu_set = (RelativeLayout) view.findViewById(R.id.sys_menu_set);
		sys_clear_cache = (RelativeLayout) view
				.findViewById(R.id.sys_clear_cache);
		sys_score = (RelativeLayout) view.findViewById(R.id.sys_score);
		sys_share = (RelativeLayout) view.findViewById(R.id.sys_share);
		sys_idea = (RelativeLayout) view.findViewById(R.id.sys_idea);
		sys_software_update = (RelativeLayout) view
				.findViewById(R.id.sys_software_update);
		sys_about_us = (RelativeLayout) view.findViewById(R.id.sys_about_us);
		sys_site_map = (RelativeLayout) view.findViewById(R.id.sys_site_map);
		sys_use_help = (RelativeLayout) view.findViewById(R.id.sys_use_help);
		sys_join_twiiter = (RelativeLayout) view
				.findViewById(R.id.sys_join_twiiter);

		sys_menu_set.setOnClickListener(this);
		sys_clear_cache.setOnClickListener(this);
		sys_score.setOnClickListener(this);
		sys_share.setOnClickListener(this);
		sys_idea.setOnClickListener(this);
		sys_software_update.setOnClickListener(this);
		sys_about_us.setOnClickListener(this);
		sys_site_map.setOnClickListener(this);
		sys_use_help.setOnClickListener(this);
		sys_join_twiiter.setOnClickListener(this);

	}

	@Override
	protected int getLayoutId() {
		return R.layout.systemset_slide_layout;
	}

	@Override
	protected void onBack() {
		managers.BackPress(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.sys_menu_set:// 常用栏设置
			HomeBaseSlideLevelFragment menusetFragment = new MenuItemSetFragment();
			managers.IntentFragment(menusetFragment);
			break;
		case R.id.sys_clear_cache:// 清楚缓存
			break;
		case R.id.sys_score:// 给系统评分
			break;
		case R.id.sys_share:// 分享
			break;
		case R.id.sys_idea:// 意见反馈
			break;
		case R.id.sys_software_update:// 软件升级
			break;
		case R.id.sys_about_us:// 关于我们
			break;
		case R.id.sys_site_map:// 网站地图
			HomeBaseSlideLevelFragment siteMapFragment = new SiteMapFragment();
			managers.IntentFragment(siteMapFragment);
			break;
		case R.id.sys_use_help:// 使用帮组
			break;
		case R.id.sys_join_twiiter:// 合作推广
			break;

		}
	}

	@Override
	protected String getTtitle() {
		return "系统设置";
	}

}
