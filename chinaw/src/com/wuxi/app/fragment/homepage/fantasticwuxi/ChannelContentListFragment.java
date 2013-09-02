package com.wuxi.app.fragment.homepage.fantasticwuxi;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.activity.commactivity.ContentDetailActivity;
import com.wuxi.app.activity.homepage.fantasticwuxi.WuxiChannelContentDetailActivity;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 内容列表界面
 */
public class ChannelContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Content content = (Content) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(),
			WuxiChannelContentDetailActivity.class);
		intent.putExtra(WuxiChannelContentDetailActivity.CONTENT_KEY, content);

		if (this.channel != null) {
			intent.putExtra(ContentDetailActivity.CHANNEL_KEY, channel);
		}

		MainTabActivity.instance.addView(intent);

	}

}
