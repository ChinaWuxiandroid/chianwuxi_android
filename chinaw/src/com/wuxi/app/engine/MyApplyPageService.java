package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.MyApply;
import com.wuxi.domain.MyApplyPageWrapper;
import com.wuxi.domain.MyApplyPageWrapper.MyApplyPage;
import com.wuxi.domain.MyApplyWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

import android.content.Context;

public class MyApplyPageService extends Service{

	public MyApplyPageService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * 杨宸  智佳  获取我的依申请公开列表 支持分页
	 * 
	 * @param access_token
	 *            验证token
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws ResultException
	 */
	public MyApplyPageWrapper getMyApplyPages(String access_token, int start,
			int end) throws NetException, JSONException, ResultException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		
		String url = Constants.Urls.MY_APPLYPAGE_URL
				.replace("{access_token}", access_token)
				.replace("{start}", start + "").replace("{end}", end + "");
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			if (jresult.has("message") && jresult.get("message") != null) {
				throw new ResultException(jresult.getString("message"));// 有错误存在
			}

			MyApplyPageWrapper myApplyWrapper = new MyApplyPageWrapper();
			myApplyWrapper.setStart(jresult.getInt("start"));
			myApplyWrapper.setEnd(jresult.getInt("end"));
			myApplyWrapper.setPrevious(jresult.getBoolean("previous"));
			myApplyWrapper.setNext(jresult.getBoolean("next"));
			myApplyWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			
			Object o = jresult.get("data");

			if (!o.toString().equals("[]") && !o.equals("null")) {
				JSONArray jData = (JSONArray) o;
				try {
					myApplyWrapper.setData(JAsonPaserUtil
							.getListByJassory(MyApplyPage.class, jData));
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
			return myApplyWrapper;
		}

		return null;

	}
}
