package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.Myconsult;
import com.wuxi.domain.MyconsultWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 我的在线咨询业务类
 * 
 */
public class MyconsultService extends Service {

	public MyconsultService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 我的在线咨询列表
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
	public MyconsultWrapper getPageMyconsults(String access_token, int start,
			int end) throws NetException, JSONException, ResultException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.MYCONSULT_URL
				.replace("{access_token}", access_token)
				.replace("{start}", start + "").replace("{end}", end + "");

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			if (jresult.has("message") && jresult.get("message") != null) {
				throw new ResultException(jresult.getString("message"));// 有错误存在
			}

			MyconsultWrapper myconsultWrapper = new MyconsultWrapper();
			myconsultWrapper.setStart(jresult.getInt("start"));
			myconsultWrapper.setEnd(jresult.getInt("end"));
			myconsultWrapper.setPrevious(jresult.getBoolean("previous"));
			myconsultWrapper.setNext(jresult.getBoolean("next"));
			myconsultWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));
			Object o = jresult.get("data");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jdata = jresult.getJSONArray("data");

				List<Myconsult> myconsults = new ArrayList<Myconsult>();
				for (int index = 0; index < jdata.length(); index++) {

					JSONObject jb = jdata.getJSONObject(index);
					Myconsult myconsult = new Myconsult();
					myconsult.setId(jb.getString("id"));
					myconsult.setTitle(jb.getString("title"));
					myconsult.setStatue(jb.getString("statue"));
					myconsult.setSendDate(jb.getString("sendDate"));
					myconsults.add(myconsult);

				}

				myconsultWrapper.setMyconsults(myconsults);

			}

			return myconsultWrapper;

		}

		return null;

	}

}
