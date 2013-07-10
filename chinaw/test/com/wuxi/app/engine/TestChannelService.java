package com.wuxi.app.engine;

import java.util.List;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 频道测试
 */
public class TestChannelService extends AndroidTestCase {

	private static final String TAG = "TestChannelService";

	public void getSubChannels() {
		ChannelService channelService = new ChannelService(getContext());
		try {
			List<Channel> channels = channelService
					.getSubChannels("ace3e926-9206-4193-a9da-b99955f0ff4b");
			for (Channel ch : channels) {
				LogUtil.i(TAG, ch.getChannelName());
			}

		} catch (NetException e) {
			e.printStackTrace();
		}

	}
}
