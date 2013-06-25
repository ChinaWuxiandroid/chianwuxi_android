package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.List;

import com.wuxi.domain.NavigatorItmeAction;

/**
 * 无锡概览
 * 
 * @author wanglu
 * 
 */
public class WuxiIntroFragment extends NavigatorFragment {

	private String[] navigatorNames = new String[] { "历史沿革", "位置面积", "地形地貌",
			"人口简介", "行政区划", "自然资源", "自然环境", "风景名胜", "经济发展", "社会事业" };

	@Override
	protected List<NavigatorItmeAction> getNavigatorItmeActions() {

		List<NavigatorItmeAction> naItems = new ArrayList<NavigatorItmeAction>();

		int i=0;
		for (String navigatorName : navigatorNames) {

			naItems.add(new NavigatorItmeAction(navigatorName, DataFragment
					.Instance(i)));
			i++;
			if(i%4==0){
				i=0;
			}
		}

		return naItems;

	}

}
