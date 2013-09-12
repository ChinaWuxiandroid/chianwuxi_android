package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import com.wuxi.app.listeners.GoverInterPeopleInitLayoutImpl;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.domain.MenuItem;

public class GIPContentFragment extends GoverMenuItemTitleFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public MenuItemInitLayoutListener getMenuItemInitLayoutListener() {
		
		 return new GoverInterPeopleInitLayoutImpl();
	}

	@Override
	public void initializSubFragmentsLayout(List<MenuItem> items) {
		for (final MenuItem menu : items) {

			if(menu.getType() == MenuItem.APP_MENU){
				//我的政民互动  里面的appui
				if(menu.getAppUI().endsWith("Letter12345_C")){
					menu.setContentFragment(GIPMine12345Fragment.class);
				}
				else if(menu.getAppUI().endsWith("CommentsPlatform_C")){
					menu.setContentFragment(GIPMineSuggestionPlatformFragment.class);
				}
				else if(menu.getAppUI().endsWith("OnlineChiefHall_C")){
					menu.setContentFragment(GIPMineInternetGoverSaloonFragment.class);
				}
				else if(menu.getAppUI().endsWith("InformationDisclosurePlatform_C")){
					menu.setContentFragment(GIPMineInfoPublicPlatformFragment.class);
				}
				else if(menu.getAppUI().endsWith("PublicBBS_C")){
					menu.setContentFragment(GIPMinePublicForumFragment.class);
				}
				//12345来信办理平台
				else if(menu.getAppUI().endsWith("市长信箱")){
					menu.setContentFragment(GIP12345MayorMaiBoxFragment.class);
				}
				else if(menu.getAppUI().endsWith("建议咨询投诉")){
					menu.setContentFragment(GIP12345ComplaintFragment.class);
				}
				else if(menu.getAppUI().endsWith("部门领导信箱")){
					menu.setContentFragment(GIP12345PartLeaderMailboxFragment.class);
				}
//				else if(menu.getAppUI().endsWith("区市长信箱")){
//					menu.setContentFragment(GIP12345CMayorMailBoxFragment.class);
//				}
				else if(menu.getAppUI().endsWith("热门信件选登")){
					menu.setContentFragment(GIP12345HotMailFragment.class);
				}
				else if(menu.getAppUI().endsWith("答复率统计")){
					menu.setContentFragment(GIP12345AnswerStatisticsFragment.class);
				}
				else if(menu.getAppUI().endsWith("我要写信")){
					menu.setContentFragment(GIP12345IWantMailFragment.class);
				}
				//征求意见平台
				else if(menu.getAppUI().endsWith("Legislates_to_solicit_the_suggestions")){
					menu.setContentFragment(GIPSuggestLawSuggestionFragment.class);
				}
				else if(menu.getAppUI().endsWith("Online_investigation")){
					menu.setContentFragment(GIPSuggestSurveyFragment.class);
				}
				else if(menu.getAppUI().endsWith("Public_opinion_collection")){
					menu.setContentFragment(GIPSuggestPeopleWillFragment.class);
				}
//				//视频直播平台
//				else if(menu.getAppUI().endsWith("ItemHomePage")){
//					menu.setContentFragment(GoverInterPeopleVideoLiveHomeFragment.class);
//				}
				
				
//				else if(menu.getAppUI().endsWith("行风热线")){
//					menu.setContentFragment(GoverInterPeopleVideoLiveFragment.class);
//				}
				//公众监督
				else if(menu.getAppUI().endsWith("无锡市委组织部12380网上举报")){
					menu.setContentFragment(GoverInterPeoplePublicSuperviseFragment.class);
				}
			}

		}
	}

	@Override
	public void redirectFragment(MenuItem showMenuItem, int showMenuPositon,
			int subMenuPostion) {
	}
}
