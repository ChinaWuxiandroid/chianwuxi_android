package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.List;

import com.wuxi.domain.NavigatorItmeAction;

/**
 * 友好往来 fragment
 * 
 * @author wanglu
 * 
 */
public class FriendlyFragment extends NavigatorChannelFragment {

	private String[] navigatorNames = new String[] { "最近动态", "国籍好友", "荣誉市民" };

	/*@Override
	protected List<NavigatorItmeAction> getNavigatorItmeActions() {

		List<NavigatorItmeAction> naItems = new ArrayList<NavigatorItmeAction>();
		for (String navigatorName : navigatorNames) {
			naItems.add(new NavigatorItmeAction(navigatorName, DataFragment
					.Instance(2)));
		}
		return naItems;
	}*/

}
