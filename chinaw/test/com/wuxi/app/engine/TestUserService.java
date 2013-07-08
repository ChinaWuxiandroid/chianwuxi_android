package com.wuxi.app.engine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.User;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

import android.test.AndroidTestCase;

/**
 * 
 * @author wanglu 泰得利通 用户测试方法
 * 
 */
public class TestUserService extends AndroidTestCase {

	private static final String TAG = "TestUserService";

	/**
	 * 
	 *wanglu 泰得利通 
	 * @throws NetException
	 * @throws NODataException
	 * @throws JSONException
	 */
	public void testLogin() throws NetException, NODataException, JSONException {
		UserService userService = new UserService(getContext());

		User user = userService.login("huhong",
				"30-02-58-AA-28-2A-BB-75-94-9C-BB-A7-B7-43-2F-00");

		if (user != null) {
			LogUtil.i(TAG, user.getTrueName());
		}

	}

	
	/**
	 * 
	 *wanglu 泰得利通
	 *测试登录
	 *username=newhuhong6&password=30-02-58-AA-28-2A-BB-75-94-9C-BB-A7-B7-43-2F-01&realname=胡宏
	 * @throws NODataException 
	 * @throws JSONException 
	 * @throws NetException 
	 * @throws ResultException 
	 */
	public void testRegist() throws NetException, JSONException, NODataException, ResultException{
		UserService userService = new UserService(getContext());

		Map<String, String> params=new HashMap<String, String>();
		params.put("username", "newhuhong8");
		params.put("password", "30-02-58-AA-28-2A-BB-75-94-9C-BB-A7-B7-43-2F-01");
		params.put("realname", "胡宏");
		
		User user=userService.resgistUser(params);
		if(null!=user){
			LogUtil.i(TAG, user.getAccessToken());
		}
		
	}
}
