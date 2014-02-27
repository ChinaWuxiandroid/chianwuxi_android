package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.MenuItemChannelIndexUtil;
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

			throw new NetException(Constants.ExceptionMessage.NO_NET);

		}

		String url = Constants.Urls.CHANNEL_URL.replace("{channelId}",
				channelId);

		String resultStr = null;
		boolean isHasCacheFile = cacheUtil.isHasCacheFile(url, false);
		if (isHasCacheFile) {
			resultStr = cacheUtil.getCacheStr(url, false);// 缓存读取
		} else {
			resultStr = httpUtils.executeGetToString(url, 5000);
		}

		if (resultStr != null) {

			List<Channel> channels = null;
			try {
				JSONObject jobject = new JSONObject(resultStr);
				Object o = jobject.get("result");
				if (o.toString().equals("[]")) {
					return null;
				}
				JSONArray jresult = (JSONArray) o;
				if (null != jresult) {
					channels = new ArrayList<Channel>();
					for (int index = 0; index < jresult.length(); index++) {
						JSONObject jb = jresult.getJSONObject(index);
						Channel channel = new Channel();
						channel.setIsNull(jb.getBoolean("null"));
						channel.setChannelId(jb.getString("channelId"));
						channel.setChannelName(jb.getString("channelName"));
						channel.setContents(jb.getString("contents"));
						channel.setChildrenChannelsCount(jb
								.getInt("childrenChannelsCount"));
						channel.setChildrenContentsCount(jb
								.getInt("childrenContentsCount"));

						CacheUtil.put(
								Channel.CHANNEL_KEY + channel.getChannelId(),
								channel);// 将该屏道菜单单放入缓存
						channels.add(channel);

					}

					if (!isHasCacheFile) {
						cacheUtil.cacheFile(url, resultStr, false);// 缓存文件
					}

				}

			} catch (JSONException e) {
				e.printStackTrace();

			}

			CacheUtil.put(channelId, channels);// 将频道菜单的子菜单放入缓存
			MenuItemChannelIndexUtil.getInstance().addChannelIndex(channelId,
					channels);// 建立字频道索引

			return channels;
		}

		return null;

	}

}
