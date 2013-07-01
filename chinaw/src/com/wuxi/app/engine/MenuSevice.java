package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 首页导航菜菜业务
 * 
 * @author wanglu
 * 
 */
public class MenuSevice extends Service {
	// private static final String TAG = "MenuSevice";

	public MenuSevice(Context context) {
		super(context);
	}

	/**
	 * 获取首页菜单项
	 * 
	 * @param context
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<MenuItem> getHomeMenuItems(String url) throws NetException,
			JSONException {
		return getMenuItems(url);
	}

	/**
	 * 获取子菜单
	 * 
	 * @param context
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<MenuItem> getSubMenuItems(String url) throws NetException,
			JSONException {
		return getMenuItems(url);
	}

	/**
	 * 获取菜单数据
	 * 
	 * @param context
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */

	private List<MenuItem> getMenuItems(String url) throws NetException,
			JSONException {

		if (!checkNet()) {
			Toast.makeText(context, NET_ERROR, Toast.LENGTH_LONG).show();
			throw new NetException(NET_ERROR);
		} else {

			String reslutStr = httpUtils.executeGetToString(url, 500);
			List<MenuItem> menuItems;
			// LogUtil.i(TAG, reslutStr);
			if (null != reslutStr) {
				menuItems = new ArrayList<MenuItem>();
				JSONObject jsonObject = new JSONObject(reslutStr);
				JSONArray jresult = jsonObject.getJSONArray("result");

				if (jresult != null && jresult.length() > 0) {

					for (int i = 0; i < jresult.length(); i++) {

						JSONObject jb = jresult.getJSONObject(i);
						MenuItem menu = new MenuItem();
						menu.setName(jb.getString("name"));
						menu.setId(jb.getString("id"));
						menu.setType(jb.getInt("type"));
						menu.setDisabled(jb.getBoolean("disabled"));
						menu.setDes(jb.getString("desc"));
						menu.setSort(jb.getInt("sort"));
						if (jb.getJSONArray("childrens") != null) {
							menu.setHasChildern(true);// 有子菜单存在
						}

						menu.setCreateDate(jb.getString("createDate"));
						menu.setChannelId(jb.getString("channelId"));
						menu.setChannelName(jb.getString("channelName"));
						menu.setFavorites(jb.getBoolean("favorites"));
						menu.setContentId(jb.getString("contentId"));
						menu.setDeleted(jb.getBoolean("deleted"));
						menu.setAppUI(jb.getString("appUI"));
						menu.setWapURI(jb.getString("wapURI"));
						menu.setParentMenuId(jb.getString("parentMenuId"));
						menu.setLinkMenuItemId(jb.getString("linkMenuItemID"));
						menu.setContentName(jb.getString("contentName"));
						menu.setLinkMenuItemName(jb
								.getString("linkMenuItemName"));

						menuItems.add(menu);

						if (jb.getInt("type") == MenuItem.CHANNEL_MENU) {// 如果是频道菜单，获取子频道，并放入缓存中
							new Thread(new ChannelTask(menu.getChannelId()))
									.start();
						}
						// LogUtil.i(TAG, jb.toString());

					}

				}

				Collections.sort(menuItems);// 排序
				return menuItems;

			}

		}

		return null;

	}

	/**
	 * 
	 * @author wanglu 泰得利通 频道菜单的子频道获取任务
	 * 
	 */
	private final class ChannelTask implements Runnable {
		private String channelId;

		public ChannelTask(String channelId) {
			this.channelId = channelId;

		}

		@Override
		public void run() {
			ChannelService channelService = new ChannelService(context);
			try {
				List<Channel> channels = channelService
						.getSubChannels(channelId);

				if (channels != null) {
					CacheUtil.put(channelId, channels);// 将频道菜单的子菜单放入缓存
				}

			} catch (NetException e) {
				e.printStackTrace();
			}

		}

	}
}
