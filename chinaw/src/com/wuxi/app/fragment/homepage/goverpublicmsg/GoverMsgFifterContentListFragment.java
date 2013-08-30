package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.FifterContentListFragment;
import com.wuxi.app.util.Constants.FragmentName;
import com.wuxi.domain.Content;

public class GoverMsgFifterContentListFragment extends
		FifterContentListFragment {

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
