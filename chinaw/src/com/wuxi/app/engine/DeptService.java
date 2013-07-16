package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.Dept;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 部门后获取业务类
 * 
 */
public class DeptService extends Service {

	public DeptService(Context context) {
		super(context);
	}

	public List<Dept> getDepts() throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.DEPT_URL;
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jresult = (JSONArray) o;

				List<Dept> depts = new ArrayList<Dept>();
				for (int index = 0; index < jresult.length(); index++) {
					JSONObject jb = jresult.getJSONObject(index);
					Dept dept = new Dept();
					dept.setId(jb.getString("id"));
					dept.setName(jb.getString("name"));
					dept.setNull(jb.getBoolean("null"));
					depts.add(dept);

				}

				return depts;

			}

		}

		return null;
	}

}
