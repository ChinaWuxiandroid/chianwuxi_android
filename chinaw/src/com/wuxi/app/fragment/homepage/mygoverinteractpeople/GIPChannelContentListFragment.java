package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.util.Constants.FragmentName;
import com.wuxi.domain.Content;

public class GIPChannelContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		Content content = (Content) adapterView.getItemAtPosition(position);
		BaseSlideFragment baseSlideFragment = this.baseSlideFragment;
		Bundle bundle = new Bundle();
		bundle.putSerializable("url", content.getWapUrl());
		bundle.putSerializable("fragmentTitle", channel.getChannelName());
		baseSlideFragment.slideLinstener.replaceFragment(null, -1, FragmentName.GIP_CHANNEL_CONTENT_DETAILWEB_FRAGMENT, bundle);

	}

}
