/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: OpenInfoDeptService.java 
 * @包名： com.wuxi.app.engine 
 * @描述:  政府信息公开 部门下拉框 业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-25 下午2:43:18
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.OpenInfoDept;
import com.wuxi.exception.NetException;

/**
 * @类名： OpenInfoDeptService
 * @描述： 政府信息公开 部门下拉框 业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-25 下午2:43:18
 * @修改时间：
 * @修改描述：
 */
public class OpenInfoDeptService extends Service {

	public OpenInfoDeptService(Context context) {
		super(context);
	}

	/**
	 * @方法： getOpenInfoDepts
	 * @描述： 解析数据
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<OpenInfoDept> getOpenInfoDepts(String url) throws NetException,
			JSONException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {
			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jresult = (JSONArray) o;

				List<OpenInfoDept> depts = new ArrayList<OpenInfoDept>();

				for (int index = 0; index < jresult.length(); index++) {
					JSONObject jb = jresult.getJSONObject(index);

					OpenInfoDept dept = new OpenInfoDept();
					dept.setId(jb.getString("id"));
					dept.setName(jb.getString("name"));
					dept.setIsnull(jb.getString("null"));

					depts.add(dept);
				}
				return depts;
			}
		}

		return null;
	}

}
