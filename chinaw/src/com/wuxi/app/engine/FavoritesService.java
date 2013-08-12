package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

import android.content.Context;

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

				JSONArray jsArray=(JSONArray)o;
				
				try {
					List<MenuItem> menuItems=JAsonPaserUtil.getListByJassory(MenuItem.class, jsArray);
					return menuItems;
				} catch (IllegalArgumentException e) {
					
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

}
