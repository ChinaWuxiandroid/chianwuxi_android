package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.commonfragment.MenuItemNavigatorWithContentFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 政府信息公开  导航带内容类
 * @author 杨宸 智佳
 * */
public class GoverMsgNaviWithContentFragment extends MenuItemNavigatorWithContentFragment{
	@Override
	protected Fragment showMenItemContentFragment(MenuItem menuItem) {

		if(menuItem.getType() == MenuItem.CUSTOM_MENU||menuItem.getType() == MenuItem.CHANNEL_MENU){
			NavigatorContentExpandListFragment navigatorContentExpandListFragment=new NavigatorContentExpandListFragment();
			navigatorContentExpandListFragment.setParentItem(menuItem);
			return navigatorContentExpandListFragment;
		}
		else if (menuItem.getType() == MenuItem.WAP_MENU) {
			GoverMsgWebFragment wapFragment = new GoverMsgWebFragment();
			wapFragment.setParentItem(menuItem);
			return wapFragment;
		}
		else if(menuItem.getType() == MenuItem.APP_MENU){
			//依申请公开   --各部门依申请公开
			if(menuItem.getAppUI().equals("Applyopen_dept")){
				GoverMsgDeptApplyOpenFragment goverMsgDeptApplyOpenFragment=new GoverMsgDeptApplyOpenFragment();
				return goverMsgDeptApplyOpenFragment;
			}
			//依申请公开   --我的依申请公开办件答复
			else if(menuItem.getAppUI().equals("myapplypage")){
				
			}
		}

		return null;
	}

	@Override
	protected Fragment showChannelContentFragment(Channel channel) {

		if(channel.getChannelName().equals("部门年度报告")){
			GoverMsgSearchContentListFragment goverMsgSearchContentListFragment=new GoverMsgSearchContentListFragment();
			goverMsgSearchContentListFragment.setChannel(channel);
			return goverMsgSearchContentListFragment;
		}
		else if(channel.getChannelName().equals("县区政府年度报告")){
			GoverMsgSearchContentListFragment goverMsgSearchContentListFragment=new GoverMsgSearchContentListFragment();
			goverMsgSearchContentListFragment.setChannel(channel);
			return goverMsgSearchContentListFragment;
		}
		else{
			GoverMsgContentListFragment goverMsgContentListFragment=new GoverMsgContentListFragment();
			goverMsgContentListFragment.setChannel(channel);
			return goverMsgContentListFragment;
		}


	}
}
