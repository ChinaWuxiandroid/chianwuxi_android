package com.wuxi.app.fragment.homepage.publicservice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.activity.commactivity.ContentDetailActivity;
import com.wuxi.app.activity.homepage.publicservice.PublicServiceContentDetailActivity;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 公共服务内容列表
 * 
 */
public class PublicServiceContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		Content content = (Content) adapterView.getItemAtPosition(position);

		Intent intent = null;
		intent = new Intent(getActivity(),
			PublicServiceContentDetailActivity.class);
		intent.putExtra(ContentDetailActivity.CONTENT_KEY, content);
		if (super.parentItem != null) {

			intent.putExtra(ContentDetailActivity.MENUITEM_KEY, parentItem);

			MainTabActivity.instance.addView(intent);

		} else if (super.channel != null) {
			intent.putExtra(ContentDetailActivity.CHANNEL_KEY, channel);
			MainTabActivity.instance.addView(intent);

		}

	}

}
