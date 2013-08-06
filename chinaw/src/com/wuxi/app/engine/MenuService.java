package com.wuxi.app.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;

import com.wuxi.app.fragment.index.InitializContentLayout;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 首页导航菜菜业务
 * 
 * @author wanglu
 * 
 */
public class MenuService extends Service {
	// private static final String TAG = "MenuSevice";

	public MenuService(Context context) {
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
	 * @throws NODataException
	 */
	public List<MenuItem> getHomeMenuItems(String url) throws NetException,
			JSONException, NODataException {
		return getMenuItems(url);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取菜单的子菜单
	 * 
	 * @param parentId
	 *            父Id
	 * @return 菜单列表
	 * @throws NetException
	 * @throws NODataException
	 * @throws JSONException
	 */
	public List<MenuItem> getSubMenuItems(String parentId) throws NetException,
			NODataException, JSONException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET); // 检查网络
		}

		String url = Constants.Urls.SUB_MENU_URL.replace("{id}", parentId);

		String reslutStr = httpUtils.executeGetToString(url, 500);

		if (reslutStr != null) {

			JSONObject jsonObject = new JSONObject(reslutStr);
			JSONArray jresult = jsonObject.getJSONArray("result");
			if (null != jresult && jresult.length() > 0) {

				List<MenuItem> menuItems = new ArrayList<MenuItem>();
				for (int i = 0; i < jresult.length(); i++) {

					JSONObject jb = jresult.getJSONObject(i);
					MenuItem menu = new MenuItem();
					menu.setName(jb.getString("name"));
					menu.setId(jb.getString("id"));
					menu.setType(jb.getInt("type"));
					menu.setDisabled(jb.getBoolean("disabled"));
					menu.setDes(jb.getString("desc"));
					menu.setSort(jb.getInt("sort"));
					if ((!jb.getString("childrens").equals("null"))
							|| (!jb.getString("childrens").equals("[]"))) {
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
					menu.setLinkMenuItemName(jb.getString("linkMenuItemName"));

					if (!menu.isDeleted()&&!menu.isDisabled()) {// 已经删除标记和弃用的不显示
						menuItems.add(menu);
					}
				}

				return menuItems;
			}

		} else {
			throw new NODataException(Constants.ExceptionMessage.DATA_FORMATE);// 抛出没有获取到数据异常

		}

		return null;

	}

	/**
	 * 获取菜单数据
	 * 
	 * @param context
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */

	private List<MenuItem> getMenuItems(String url) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {

			throw new NetException(Constants.ExceptionMessage.NO_NET);

		}

		boolean fileFlag = false;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// SDK可用
			File file = new File(Constants.APPFiles.MENU_ICON_PATH);
			fileFlag = true;
			if (!file.exists()) {
				file.mkdirs();// 建立菜单图标存放目录

			}
		}

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
					if ((!jb.getString("childrens").equals("null"))
							|| (!jb.getString("childrens").equals("[]"))) {
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
					menu.setLinkMenuItemName(jb.getString("linkMenuItemName"));

					int type = jb.getInt("type");
					if (type == MenuItem.CHANNEL_MENU&&!menu.isDeleted()) {// 如果是频道菜单，获取子频道，并放入缓存中
						new Thread(new ChannelTask(menu.getChannelId()))
								.start();
					} else if (type == MenuItem.CUSTOM_MENU&&!menu.isDeleted()) {// 如果是普通菜单,将该菜单的子菜单提前获取好，放入缓存

						new Thread(new SubMenuItemsTask(menu)).start();

					}

					// 图标处理
					String iconUrl = jb.getString("icon");
					if (!iconUrl.equals("null") && !iconUrl.equals("")
							&& fileFlag) {
						String iconName = iconUrl.substring(iconUrl
								.lastIndexOf("/") + 1);// 取出图标名称

						File fileIcon = new File(
								Constants.APPFiles.MENU_ICON_PATH + iconName);

						if (!fileIcon.exists()) {// 如果不存在，下载图标并保存到本地

							new Thread(new IconTask(iconUrl, menu, fileIcon))
									.start();

						} else {// 本地SDK已存在图标
							menu.setIcon(iconName);
						}

					}

					// LogUtil.i(TAG, jb.toString());
					if (!menu.isDeleted()) {// 已经删除标记的不显示
						menuItems.add(menu);
					}

				}

			}

			Collections.sort(menuItems);// 排序
			return menuItems;

		} else {
			throw new NODataException(Constants.ExceptionMessage.DATA_FORMATE);// 抛出没有获取到数据异常
		}

	}

	/**
	 * 
	 * @author wanglu 泰得利通 首页导航的直接子菜单获取任务
	 * 
	 */
	private final class SubMenuItemsTask implements Runnable {

		private MenuItem menuItem;

		public SubMenuItemsTask(MenuItem menuItem) {
			this.menuItem = menuItem;
		}

		@Override
		public void run() {

			try {
				List<MenuItem> items = getSubMenuItems(menuItem.getId());

				if (items != null) {

					CacheUtil.put(menuItem.getId(), items);// 将菜单的子菜单放入缓存
					InitializContentLayout.initMenuItemContentLayout(menuItem, items, context);
				}
			} catch (NetException e) {
				e.printStackTrace();
			} catch (NODataException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * @author wanglu 泰得利通 菜单图标下载任务
	 * 
	 */
	private final class IconTask implements Runnable {

		private String iconUrl;// 图标地址
		private MenuItem menuItem;
		private File fileIcon;

		public IconTask(String iconUrl, MenuItem menuItem, File fileIcon) {

			this.iconUrl = iconUrl;
			this.menuItem = menuItem;
			this.fileIcon = fileIcon;
		}

		@Override
		public void run() {

			InputStream is = httpUtils.executeGet(iconUrl, 5000);
			String iconName = iconUrl.substring(iconUrl.lastIndexOf("/") + 1);

			try {
				FileOutputStream fos = new FileOutputStream(fileIcon);
				int len = 0;
				byte buffer[] = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}

				fos.flush();
				fos.close();
				is.close();
				menuItem.setIcon(iconName);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

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
