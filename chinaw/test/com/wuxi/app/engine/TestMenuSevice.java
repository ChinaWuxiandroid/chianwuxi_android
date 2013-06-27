package com.wuxi.app.engine;

import java.util.List;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

import android.test.AndroidTestCase;

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
	 */
	public void testGetMenu() throws NetException {
		MenuSevice menuSevice = new MenuSevice(getContext());

		List<MenuItem> items = menuSevice
				.getHomeMenuItems(Constants.Urls.MENU_URL);

		for (MenuItem item : items) {

			LogUtil.i(TAG, item.getName());
			LogUtil.i(TAG, item.getDes());
			LogUtil.i(TAG, "======================");
		}

	}
}
