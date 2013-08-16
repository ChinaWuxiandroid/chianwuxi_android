package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.EfficaComplain;
import com.wuxi.domain.EfficaComplainWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 效能列表
 * 
 */
public class EfficaComplainService extends Service {

	public EfficaComplainService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取能效分页
	 * 
	 * @param params
	 *            查询条件
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws ResultException
	 */
	public EfficaComplainWrapper getPageEfficaComplains(
			Map<String, String> params, int start, int end)
			throws NetException, JSONException, ResultException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.TOUSU_URL.replace("{start}", start + "")
				.replace("{end}", end + "");

		if (params != null&&params.size()>0) {

			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> paramSet : params.entrySet()) {

				sb.append(paramSet.getKey()).append("=")
						.append(paramSet.getValue()).append("&");

			}

			sb.deleteCharAt(sb.length() - 1);// 删除最后一个字符
			url = url + "&" + sb.toString();

		}

		return getPageEfficaComplainsByURL(url);

	}

	public EfficaComplainWrapper getPageEfficaComplainsByURL(String url)
			throws JSONException, ResultException {
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			if (jresult.has("message") && jresult.get("message") != null) {
				throw new ResultException(jresult.getString("message"));// 有错误存在
			}

			EfficaComplainWrapper efficaComplainWrapper = new EfficaComplainWrapper();
			efficaComplainWrapper.setStart(jresult.getInt("start"));
			efficaComplainWrapper.setEnd(jresult.getInt("end"));
			efficaComplainWrapper.setNext(jresult.getBoolean("next"));
			efficaComplainWrapper.setPrevious(jresult.getBoolean("previous"));
			efficaComplainWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));

			Object o = jresult.get("data");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jdata = jresult.getJSONArray("data");

				List<EfficaComplain> efficaComplains = new ArrayList<EfficaComplain>();
				for (int index = 0; index < jdata.length(); index++) {

					JSONObject jb = jdata.getJSONObject(index);
					EfficaComplain eff = new EfficaComplain();
					eff.setId(jb.getString("id"));
					eff.setEndTime(jb.getString("endTime"));
					eff.setTitle(jb.getString("title"));
					eff.setDoDpetname(jb.getString("doDpetname"));

					efficaComplains.add(eff);

				}

				efficaComplainWrapper.setEfficaComplains(efficaComplains);

			}

			return efficaComplainWrapper;

		}

		return null;
	}
	
	
	
}
