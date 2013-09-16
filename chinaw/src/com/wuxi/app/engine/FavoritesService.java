package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.db.FavoriteItemDao;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 收藏列表
 * 
 */
public class FavoritesService extends Service {

	public FavoritesService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 获取收藏列表
	 * 
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<MenuItem> getFavorites() throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.GET_FAVORITES_URL;
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {
				JSONArray jsArray = (JSONArray) o;
				FavoriteItemDao favoriteItemDao=new FavoriteItemDao(context);
				List<MenuItem> menuItems = new ArrayList<MenuItem>();
				for (int index = 0; index < jsArray.length(); index++) {
					JSONObject jb = jsArray.getJSONObject(index);
					MenuItem menuItem = new MenuItem();
					String id = jb.getString("id");
					String parentMenuId = jb.getString("parentMenuId");
					menuItem.setId(id);
					menuItem.setName(jb.getString("name"));
					menuItem.setType(jb.getInt("type"));
					if (!parentMenuId.equals("null")) {
						menuItem.setParentMenuId(jb.getString("parentMenuId"));
					}
					
					if(favoriteItemDao.findFavoriteItem(id)){
						menuItem.setLocalFavorites(true);
					}else{
						menuItem.setLocalFavorites(false);
					}
					

					menuItem.setChannelId(jb.getString("channelId"));
					menuItem.setChannelName(jb.getString("channelName"));

					menuItems.add(menuItem);
				}

				return menuItems;

			}
		}

		return null;
	}

}
