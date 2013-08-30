package com.wuxi.app.fragment.homepage.informationcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.activity.commactivity.ContentDetailActivity;
import com.wuxi.app.activity.homepage.informationcenter.HotTopicContentActivity;
import com.wuxi.app.activity.homepage.informationcenter.InfoCenterContentDetailActivity;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
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
		
		Intent intent = null;
		
		if (super.parentItem != null) {

			if (parentItem.getName().equals("热点专题")) {

				intent = new Intent(getActivity(),
					HotTopicContentActivity.class);
				intent.putExtra(ContentDetailActivity.MENUITEM_KEY, parentItem);
				intent.putExtra(ContentDetailActivity.CONTENT_KEY, content);
				MainTabActivity.instance.addView(intent);

			} else {

				intent = new Intent(getActivity(),
					InfoCenterContentDetailActivity.class);
				intent.putExtra(ContentDetailActivity.CONTENT_KEY, content);
				intent.putExtra(ContentDetailActivity.MENUITEM_KEY, parentItem);
				MainTabActivity.instance.addView(intent);

			}

		} else if (super.channel != null) {

			intent = new Intent(getActivity(),
				InfoCenterContentDetailActivity.class);
			intent.putExtra(ContentDetailActivity.CONTENT_KEY, content);
			intent.putExtra(ContentDetailActivity.CHANNEL_KEY, channel);
			MainTabActivity.instance.addView(intent);

		}

	}
}
