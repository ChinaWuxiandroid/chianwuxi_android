package com.wuxi.app.fragment.homepage.informationcenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.fragment.commonfragment.ContentDetailFragment;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.app.util.Constants.FragmentName;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 内容列表菜单
 * 
 */
@SuppressLint("HandlerLeak")
public class InforContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		Content content = (Content) adapterView.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable(ContentDetailFragment.CONTENT_KEY, content);
		if (super.parentItem != null) {

			bundle.putSerializable(ContentDetailFragment.MENUITEM_KEY,
					parentItem);
			if (parentItem.getName().equals("热点专题")) {

				this.baseSlideFragment.slideLinstener.replaceFragment(null, -1,
						FragmentName.HOTTOPICCONTENTFRAGMENT, bundle);// 跳转

			} else {

				this.baseSlideFragment.slideLinstener.replaceFragment(null, -1,
						FragmentName.INFOCENTER_FRAGMENT, bundle);// 跳转

			}

		} else if (super.channel != null) {

			bundle.putSerializable(ContentDetailFragment.CHANNEL_KEY, channel);
			baseSlideFragment.slideLinstener.replaceFragment(null, -1,
					FragmentName.INFOCENTER_FRAGMENT, bundle);// 跳转
		}

	}
}
