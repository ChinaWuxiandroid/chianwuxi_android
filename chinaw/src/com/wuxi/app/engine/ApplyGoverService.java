package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.ApplyGover;
import com.wuxi.exception.NetException;

/**
 *各市县区依申请公开 业务类
 *@author 杨宸 智佳  
 * */

public class ApplyGoverService extends Service{
	
	public ApplyGoverService(Context context) {
		super(context);
	}

	public List<ApplyGover> getGovers(String url) throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
	
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jresult = (JSONArray) o;

				List<ApplyGover> goverList = new ArrayList<ApplyGover>();
				for (int index = 0; index < jresult.length(); index++) {
					JSONObject jb = jresult.getJSONObject(index);
					ApplyGover gover = new ApplyGover();
					gover.setisNull(jb.getBoolean("null"));
					gover.setDepId(jb.getString("depId"));
					gover.setDepName(jb.getString("depName"));
					gover.setDoProjectId(jb.getString("doProjectId"));
					gover.setZhinanUrl(jb.getString("zhinanUrl"));
					gover.setApplyUrl(jb.getString("applyUrl"));
					goverList.add(gover);
				}
				return goverList;
			}
		}

		return null;
	}
}
