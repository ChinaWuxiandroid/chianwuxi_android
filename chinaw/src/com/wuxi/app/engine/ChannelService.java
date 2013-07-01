package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.wuxi.app.fragment.NavigatorChannelFragment;
import com.wuxi.app.fragment.index.type.CityMapFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 频道业务类
 * 
 */
public class ChannelService extends Service {

	public ChannelService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取频道的子频道
	 * 
	 * @param context
	 * @param channelId
	 * @return
	 * @throws NetException
	 */
	public List<Channel> getSubChannels(String channelId) throws NetException {

		if (!checkNet()) {

			throw new NetException(NET_ERROR);

		}

		String url = Constants.Urls.CHANNEL_URL.replace("{channelId}",
				channelId);

		String resultStr = httpUtils.executeGetToString(url, 5000);
		if (resultStr != null) {
			List<Channel> channels = null;
			try {
				JSONObject jobject = new JSONObject(resultStr);
				JSONArray jresult = jobject.getJSONArray("result");
				if (null != jresult) {
					channels = new ArrayList<Channel>();
					for (int index = 0; index < jresult.length(); index++) {
						JSONObject jb = jresult.getJSONObject(index);
						Channel channel = new Channel();
						channel.setIsNull(jb.getBoolean("null"));
						channel.setChannelId(jb.getString("channelId"));
						channel.setChannelName(jb.getString("channelName"));
						channel.setContents(jb.getString("contents"));
						if (channel.getChannelName().equals("城市地图")) {//处理城市题图显示的视图
							channel.setContentFragment(CityMapFragment.class);
						} else {
							channel.setContentFragment(NavigatorChannelFragment.class);
						}

						channels.add(channel);

					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(context, DATA_FORMATE_ERROR, Toast.LENGTH_LONG)
						.show();
			}

			return channels;
		}

		return null;

	}

}
