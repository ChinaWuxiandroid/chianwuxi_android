package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.domain.Content;

/**
 * 政府信息公开 内容列表实现类
 * 
 * @author 杨宸 智佳
 * */
public class GoverMsgContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Content content = (Content) adapterView.getItemAtPosition(position);
		if (super.parentItem != null) {

			Intent intent = new Intent(getActivity(),
					GoverMsgContentDetailWebActivity.class);
			intent.putExtra("url", content.getWapUrl());
			intent.putExtra("fragmentTitle", parentItem.getName());

			MainTabActivity.instance.addView(intent);

		} else if (super.channel != null) {
			Intent intent = new Intent(getActivity(),
					GoverMsgContentDetailWebActivity.class);
			intent.putExtra("url", content.getWapUrl());
			intent.putExtra("fragmentTitle", channel.getChannelName());

			MainTabActivity.instance.addView(intent);
		}
	}

}
