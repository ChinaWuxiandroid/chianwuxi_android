package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.domain.Content;

public class GoverMsgCustomContentListFragment extends ContentListFragment {

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

			Animation animation = AnimationUtils.loadAnimation(
				getActivity(), R.anim.rbm_in_from_right);
			MainTabActivity.instance.addView(intent, animation);

		}

	}
}
