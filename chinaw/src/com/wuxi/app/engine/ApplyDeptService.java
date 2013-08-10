package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.ApplyDept;
import com.wuxi.exception.NetException;

/**
 * 依申请展开 回复部门 业务类
 *@author 杨宸 智佳  
 * */

public class ApplyDeptService extends Service {

	public ApplyDeptService(Context context) {
		super(context);
	}

	public List<ApplyDept> getDepts(String url) throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
	
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jresult = (JSONArray) o;

				List<ApplyDept> depts = new ArrayList<ApplyDept>();
				for (int index = 0; index < jresult.length(); index++) {
					JSONObject jb = jresult.getJSONObject(index);
					ApplyDept dept = new ApplyDept();
					dept.setDepId(jb.getString("depId"));
					dept.setDepName(jb.getString("depName"));
					dept.setDoProjectId(jb.getString("doProjectId"));
					dept.setNull(jb.getBoolean("null"));
					dept.setZhinanUrl(jb.getString("zhinanUrl"));
					depts.add(dept);
				}
				return depts;
			}
		}

		return null;
	}

}
