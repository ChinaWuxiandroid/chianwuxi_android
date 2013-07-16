package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.MyApply;
import com.wuxi.domain.MyApplyWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

import android.content.Context;

/**
 * 
 * @author wanglu 泰得利通
 * 
 *         我的申请接口
 * 
 */
public class MyApplyService extends Service {

	public MyApplyService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取我的申报列表
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
	public MyApplyWrapper getPageMyApplyes(String access_token, int start,
			int end) throws NetException, JSONException, ResultException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.MY_APPLY_URL
				.replace("{access_token}", access_token)
				.replace("{start}", start + "").replace("{end}", end + "");

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			if (jresult.has("message") && jresult.get("message") != null) {
				throw new ResultException(jresult.getString("message"));// 有错误存在
			}

			MyApplyWrapper myApplyWrapper = new MyApplyWrapper();
			myApplyWrapper.setStart(jresult.getInt("start"));
			myApplyWrapper.setEnd(jresult.getInt("end"));
			myApplyWrapper.setPrevious(jresult.getBoolean("previous"));
			myApplyWrapper.setNext(jresult.getBoolean("next"));
			myApplyWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			Object o = jresult.get("data");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jdata = jresult.getJSONArray("data");

				List<MyApply> myApplies = new ArrayList<MyApply>();
				for (int index = 0; index < jdata.length(); index++) {

					JSONObject jb = jdata.getJSONObject(index);
					MyApply myApply = new MyApply();
					myApply.setId(jb.getString("id"));
					myApply.setTitle(jb.getString("title"));
					myApply.setStatue(jb.getString("statue"));
					myApplies.add(myApply);

				}

				myApplyWrapper.setMyApplies(myApplies);

			}

			return myApplyWrapper;

		}

		return null;

	}

}
