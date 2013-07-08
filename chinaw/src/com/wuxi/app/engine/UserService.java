package com.wuxi.app.engine;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.User;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 用户业务类
 */
public class UserService extends Service {

	private static final String ERROR_CODE = "3002";
	private static final String TAG = "UserService";

	public UserService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws NetException
	 * @throws NODataException
	 * @throws JSONException
	 */
	public User login(String userName, String password) throws NetException,
			NODataException, JSONException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET); // 检查网络
		}

		String url = Constants.Urls.LOGIN_URL.replace("{username}", userName)
				.replace("{pwd}", password);

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);
		if (resultStr != null) {

			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jb = jsonObject.getJSONObject("result");

			return paserUser(jb);
		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 用户注册 params 表单参数
	 * 
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 * @throws ResultException
	 */
	public User resgistUser(Map<String, String> params) throws NetException,
			JSONException, NODataException, ResultException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET); // 检查网络
		}

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> paramSet : params.entrySet()) {

			sb.append(paramSet.getKey()).append("=")
					.append(paramSet.getValue()).append("&");

		}

		sb.deleteCharAt(sb.length() - 1);// 删除最后一个字符

		String url = Constants.Urls.REGIST_URL + "?" + sb.toString();
		LogUtil.i(TAG, url);
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {

			JSONObject jsonObject = new JSONObject(resultStr);

			JSONObject jb = jsonObject.getJSONObject("result");

			if (jb.has("message") && jb.getString("message") != null
					&& jb.getString("errorCode").equals(ERROR_CODE)) {
				throw new ResultException(jb.getString("message"));//处理注册错误信息

			}

			return paserUser(jb);

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}

	}

	private User paserUser(JSONObject jb) throws JSONException {
		if (jb != null) {

			User user = new User();
			user.setUserName(jb.getString("userName"));
			user.setAccessToken(jb.getString("accessToken"));
			user.setRefreshToken(jb.getString("refreshToken"));
			user.setExpireIn(jb.getString("expiresIn"));
			user.setMobile(jb.getString("mobile"));
			user.setPerProjectId(jb.getString("perProjectId"));
			user.setUserId(jb.getString("userId"));
			user.setTrueName(jb.getString("trueName"));
			user.setSex(jb.getString("sex"));
			user.setBirthday(jb.getString("birthday"));
			user.setIdcard(jb.getString("idcard"));
			user.setEmail(jb.getString("email"));
			user.setToeknCreateTime(jb.getString("tokenCreateTime"));

			return user;
		}
		return null;
	}
}
