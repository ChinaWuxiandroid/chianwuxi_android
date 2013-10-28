package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.commonfragment.MenuItemNavigatorWithContentFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 政府信息公开 导航带内容类
 * 
 * @author 杨宸 智佳
 * */
public class GoverMsgNaviWithContentFragment extends
		MenuItemNavigatorWithContentFragment {

	@Override
	protected Fragment showMenItemContentFragment(MenuItem menuItem) {

		if (menuItem.getType() == MenuItem.CUSTOM_MENU
				|| menuItem.getType() == MenuItem.CHANNEL_MENU) {
			if (menuItem.getChannelName().equals("业务工作")) {
				GoverMsgSearchContentListFragment searchContentListFragment = new GoverMsgSearchContentListFragment();
				searchContentListFragment.setParentMenuItem(menuItem);
				searchContentListFragment.setFifterType(GoverMsgSearchContentListFragment.DEPT_TYPE);
				return searchContentListFragment;
			}else {
				NavigatorContentExpandListFragment navigatorContentExpandListFragment = new NavigatorContentExpandListFragment();
				navigatorContentExpandListFragment.setParentItem(menuItem);
				return navigatorContentExpandListFragment;
			}
			
		} else if (menuItem.getType() == MenuItem.WAP_MENU) {
			GoverMsgWebFragment wapFragment = new GoverMsgWebFragment();
			wapFragment.setParentItem(menuItem);
			return wapFragment;
		} else if (menuItem.getType() == MenuItem.APP_MENU) {
			// 依申请公开 --各部门依申请公开
			if (menuItem.getAppUI().equals("Applyopen_dept")) {
				GoverMsgApplyDownloadFragment goverMsgDeptApplyOpenFragment = new GoverMsgApplyDownloadFragment();
				goverMsgDeptApplyOpenFragment
						.setType(GoverMsgApplyDownloadFragment.DEPT_TYPE);

				return goverMsgDeptApplyOpenFragment;
			}
			// 依申请公开 --我的依申请公开办件答复
			else if (menuItem.getAppUI().equals("myapplypage")) {
				GoverMsgMyApplyPageFragment goverMsgMyApplyPageFragment = new GoverMsgMyApplyPageFragment();
				return goverMsgMyApplyPageFragment;
			}
			// 依申请公开 --各市(县)区依申请公开
			else if (menuItem.getAppUI().equals("Applyopen_city")) {
				GoverMsgApplyDownloadFragment goverMsgDeptApplyOpenFragment = new GoverMsgApplyDownloadFragment();

				goverMsgDeptApplyOpenFragment
						.setType(GoverMsgApplyDownloadFragment.GOVER_TYPE);
				return goverMsgDeptApplyOpenFragment;
			}
			// 依申请公开 --网上办件统计
			else if (menuItem.getAppUI().equals("Online_Handles_Totals")) {
				GoverMsgInterMailStatisticsFragment goverMsgInterMailStatisticsFragment = new GoverMsgInterMailStatisticsFragment();
				return goverMsgInterMailStatisticsFragment;
			}
		}

		return null;
	}

	@Override
	protected Fragment showChannelContentFragment(Channel channel) {

		if (channel.getChannelName().equals("部门年度报告")) {
			GoverMsgSearchContentListFragment goverMsgSearchContentListFragment = new GoverMsgSearchContentListFragment();
			goverMsgSearchContentListFragment
					.setFifterType(GoverMsgSearchContentListFragment.DEPT_TYPE);

			goverMsgSearchContentListFragment.setChannel(channel);
			return goverMsgSearchContentListFragment;
		} else if (channel.getChannelName().equals("县区政府年度报告")) {

			GoverMsgSearchContentListFragment goverMsgSearchContentListFragment = new GoverMsgSearchContentListFragment();
			goverMsgSearchContentListFragment
					.setFifterType(GoverMsgSearchContentListFragment.ZONE_TYPE);

			goverMsgSearchContentListFragment.setChannel(channel);
			return goverMsgSearchContentListFragment;
		} else {
			GoverMsgContentListFragment goverMsgContentListFragment = new GoverMsgContentListFragment();
			goverMsgContentListFragment.setChannel(channel);

			return goverMsgContentListFragment;
		}
	}
}
