package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu
 * 
 */
public class TestMenuSevice extends AndroidTestCase {

	private static final String TAG = "TestMenuSevice";

	/**
	 * 菜单获取测试
	 * 
	 * @throws NetException
	 * @throws JSONException 
	 * @throws NODataException 
	 */
	public void testGetMenu() throws NetException, JSONException, NODataException {
		MenuService menuSevice = new MenuService(getContext());

		List<MenuItem> items = menuSevice
				.getHomeMenuItems(Constants.Urls.MENU_URL);

		for (MenuItem item : items) {

			LogUtil.i(TAG, item.getName());
			LogUtil.i(TAG, item.getDes());
			LogUtil.i(TAG, "======================");
		}

	}
	
	public void testGetSubMenu() throws NetException, JSONException, NODataException {
		MenuService menuSevice = new MenuService(getContext());

		List<MenuItem> items = menuSevice
				.getSubMenuItems("402881de3f758726013f75873bce00d9");

		for (MenuItem item : items) {

			LogUtil.i(TAG, item.getName());
			LogUtil.i(TAG, item.getDes());
			LogUtil.i(TAG, "======================");
		}

	}
}
