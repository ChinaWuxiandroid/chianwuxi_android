package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.GIPContentDetailWebActivity;

import com.wuxi.app.fragment.commonfragment.ContentListFragment;

import com.wuxi.domain.Content;

public class GIPChannelContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Content content = (Content) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(),
				GIPContentDetailWebActivity.class);
		intent.putExtra("url", content.getWapUrl());
		intent.putExtra("fragmentTitle", channel.getChannelName());

		MainTabActivity.instance.addView(intent);

	}

}
