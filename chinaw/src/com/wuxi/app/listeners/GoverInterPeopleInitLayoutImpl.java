package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345AnswerStatisticsFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345CMayorMailBoxFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345ComplaintFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345HotMailFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345IWantMailFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345MayorMaiBoxFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIP12345PartLeaderMailboxFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPMine12345Fragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPMineInfoPublicPlatformFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPMineInternetGoverSaloonFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPMinePublicForumFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPMineSuggestionPlatformFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPSuggestLawSuggestionFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPSuggestPeopleWillFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GIPSuggestSurveyFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeoplePublicSuperviseFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleVideoLiveFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleVideoLiveHomeFragment;
import com.wuxi.domain.MenuItem;

public class GoverInterPeopleInitLayoutImpl implements
		MenuItemInitLayoutListener {

	@Override
	public void bindMenuItemLayout(
			InitializContentLayoutListner initLayoutListner, MenuItem menuItem) {

		Class<? extends Fragment> fragmentClass = menuItem.getContentFragment();

		Fragment fragment;

		if (fragmentClass == null) {
			return;
		}

		try {
			fragment = (Fragment) fragmentClass.newInstance();

			if (fragment == null) {
				return;
			}
			/**
			 * 我的政民互动
			 */
			GIPMine12345Fragment gIPMine12345Fragment = null;
			GIPMineSuggestionPlatformFragment gIPMineSuggestionPlatformFragment = null;
			GIPMineInternetGoverSaloonFragment gIPMineInternetGoverSaloonFragment = null;
			GIPMineInfoPublicPlatformFragment gIPMineInfoPublicPlatformFragment = null;
			GIPMinePublicForumFragment gIPMinePublicForumFragment = null;

			/**
			 * 12345来信办理平台
			 */
			GIP12345MayorMaiBoxFragment GIP12345MayorMaiBoxFragment = null;
			GIP12345ComplaintFragment gIP12345ComplaintFragment = null;
			GIP12345PartLeaderMailboxFragment gIP12345PartLeaderMailboxFragment = null;
			GIP12345CMayorMailBoxFragment gIP12345CMayorMailBoxFragment = null;
			GIP12345HotMailFragment gIP12345HotMail = null;
			GIP12345AnswerStatisticsFragment gIP12345AnswerStatisticsFragment = null;
			GIP12345IWantMailFragment gIP12345IWantMailFragment = null;

			/**
			 * 征求意见平台
			 */
			GIPSuggestLawSuggestionFragment gIPSuggestLawSuggestionFragment = null;
			GIPSuggestSurveyFragment gIPSuggestSurveyFragment = null;
			GIPSuggestPeopleWillFragment gIPSuggestPeopleWill = null;

			/**
			 * 视频直播平台之走进直播间
			 */
			GoverInterPeopleVideoLiveFragment goverInterPeopleVideoLiveFragment = null;

			/**
			 * 走进直播间之栏目首页
			 */
			GoverInterPeopleVideoLiveHomeFragment goverInterPeopleVideoLiveHomeFragment = null;

			/**
			 * 公众监督
			 */
			GoverInterPeoplePublicSuperviseFragment goverInterPeoplePublicSuperviseFragment = null;

			/**
			 * 我的政民互动
			 */
			if (fragment instanceof GIPMine12345Fragment) {
				gIPMine12345Fragment = (GIPMine12345Fragment) fragment;
				gIPMine12345Fragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(gIPMine12345Fragment);
			} else if (fragment instanceof GIPMineSuggestionPlatformFragment) {
				gIPMineSuggestionPlatformFragment = (GIPMineSuggestionPlatformFragment) fragment;
				gIPMineSuggestionPlatformFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIPMineSuggestionPlatformFragment);
			}

			else if (fragment instanceof GIPMineInternetGoverSaloonFragment) {
				gIPMineInternetGoverSaloonFragment = (GIPMineInternetGoverSaloonFragment) fragment;
				gIPMineInternetGoverSaloonFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIPMineInternetGoverSaloonFragment);
			}

			else if (fragment instanceof GIPMineInfoPublicPlatformFragment) {
				gIPMineInfoPublicPlatformFragment = (GIPMineInfoPublicPlatformFragment) fragment;
				gIPMineInfoPublicPlatformFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIPMineInfoPublicPlatformFragment);
			}

			else if (fragment instanceof GIPMinePublicForumFragment) {
				gIPMinePublicForumFragment = (GIPMinePublicForumFragment) fragment;
				gIPMinePublicForumFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(gIPMinePublicForumFragment);
			}

			/**
			 * 12345来信办理平台
			 */
			else if (fragment instanceof GIP12345MayorMaiBoxFragment) {
				GIP12345MayorMaiBoxFragment = (GIP12345MayorMaiBoxFragment) fragment;
				GIP12345MayorMaiBoxFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(GIP12345MayorMaiBoxFragment);
			}

			else if (fragment instanceof GIP12345ComplaintFragment) {
				gIP12345ComplaintFragment = (GIP12345ComplaintFragment) fragment;
				gIP12345ComplaintFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(gIP12345ComplaintFragment);
			}

			else if (fragment instanceof GIP12345PartLeaderMailboxFragment) {
				gIP12345PartLeaderMailboxFragment = (GIP12345PartLeaderMailboxFragment) fragment;
				gIP12345PartLeaderMailboxFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIP12345PartLeaderMailboxFragment);
			} else if (fragment instanceof GIP12345CMayorMailBoxFragment) {
				gIP12345CMayorMailBoxFragment = (GIP12345CMayorMailBoxFragment) fragment;
				gIP12345CMayorMailBoxFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIP12345CMayorMailBoxFragment);
			} else if (fragment instanceof GIP12345HotMailFragment) {
				gIP12345HotMail = (GIP12345HotMailFragment) fragment;
				initLayoutListner.bindContentLayout(gIP12345HotMail);
			} else if (fragment instanceof GIP12345IWantMailFragment) {
				gIP12345IWantMailFragment = (GIP12345IWantMailFragment) fragment;
				gIP12345IWantMailFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(gIP12345IWantMailFragment);
			} else if (fragment instanceof GIP12345AnswerStatisticsFragment) {
				gIP12345AnswerStatisticsFragment = (GIP12345AnswerStatisticsFragment) fragment;
				gIP12345AnswerStatisticsFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIP12345AnswerStatisticsFragment);
			}

			/**
			 * 征求意见平台
			 */
			else if (fragment instanceof GIPSuggestLawSuggestionFragment) {
				gIPSuggestLawSuggestionFragment = (GIPSuggestLawSuggestionFragment) fragment;
				// gIPSuggestLawSuggestionFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(gIPSuggestLawSuggestionFragment);
			} else if (fragment instanceof GIPSuggestSurveyFragment) {
				gIPSuggestSurveyFragment = (GIPSuggestSurveyFragment) fragment;
				gIPSuggestSurveyFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(gIPSuggestSurveyFragment);
			} else if (fragment instanceof GIPSuggestPeopleWillFragment) {
				gIPSuggestPeopleWill = (GIPSuggestPeopleWillFragment) fragment;
				gIPSuggestPeopleWill.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(gIPSuggestPeopleWill);
			}

			/**
			 * 走进直播间
			 */
			else if (fragment instanceof GoverInterPeopleVideoLiveFragment) {
				goverInterPeopleVideoLiveFragment = (GoverInterPeopleVideoLiveFragment) fragment;
				goverInterPeopleVideoLiveFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(goverInterPeopleVideoLiveFragment);
			}

			/**
			 * 走进直播间之栏目主页
			 */
			else if (fragment instanceof GoverInterPeopleVideoLiveHomeFragment) {
				goverInterPeopleVideoLiveHomeFragment = (GoverInterPeopleVideoLiveHomeFragment) fragment;
				goverInterPeopleVideoLiveHomeFragment
						.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(goverInterPeopleVideoLiveHomeFragment);
			}

			/**
			 * 公众监督
			 */
			else if (fragment instanceof GoverInterPeoplePublicSuperviseFragment) {
				goverInterPeoplePublicSuperviseFragment = (GoverInterPeoplePublicSuperviseFragment) fragment;
				initLayoutListner
						.bindContentLayout(goverInterPeoplePublicSuperviseFragment);
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
