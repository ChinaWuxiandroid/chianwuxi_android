/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: AdministrativeLicenseService.java 
 * @包名： com.wuxi.app.engine 
 * @描述:  行政事项子菜单内容列表业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-29 上午10:31:30
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

import com.wuxi.app.util.Constants;
import com.wuxi.domain.AdministrativeWrapper;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * @类名： AdministrativeLicenseService
 * @描述： 行政事项子菜单内容列表业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-29 上午10:31:30
 * @修改时间：
 * @修改描述：
 */
public class AdministrativeService extends Service {

	public AdministrativeService(Context context) {
		super(context);
	}

	/**
	 * @方法： getLicenseWrapper
	 * @描述： 解析数据集
	 * @param type
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public AdministrativeWrapper getLicenseWrapper(String url) throws NetException, JSONException,
			NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		
		// 提交请求并返回结果
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			AdministrativeWrapper wrapper = new AdministrativeWrapper();

			wrapper.setEnd(jresult.getInt("end"));
			wrapper.setStart(jresult.getInt("start"));
			wrapper.setNext(jresult.getBoolean("next"));
			wrapper.setPrevious(jresult.getBoolean("previous"));
			wrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				wrapper.setLicenses(getAdministrativeLicenses(jData));
			}
			
			return wrapper;
		}else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * @方法： getAdministrativeLicenses
	 * @描述： 解析单条数据
	 * @param jData
	 * @return
	 * @throws JSONException
	 */
	private List<GoverSaoonItem> getAdministrativeLicenses(
			JSONArray jData) throws JSONException {
		if (jData != null) {
			List<GoverSaoonItem> licenses = new ArrayList<GoverSaoonItem>();
			for (int i = 0; i < jData.length(); i++) {
				JSONObject jb = jData.getJSONObject(i);

				GoverSaoonItem license = new GoverSaoonItem();

				license.setType(jb.getString("type"));
//				license.setBm(jb.getString("bm"));
				license.setNum(jb.getInt("num"));
				license.setKindtype(jb.getInt("kindtype"));
				license.setSubkindtype(jb.getInt("subkindtype"));
				license.setKindname(jb.getString("kindname"));
//				license.setSubkindname(jb.getString("subkindname"));
				license.setTypename(jb.getString("typename"));
//				license.setBladdress(jb.getString("bladdress"));
				license.setName(jb.getString("name"));
				license.setId(jb.getString("id"));
				license.setDeptid(jb.getString("deptid"));
				license.setDeptname(jb.getString("deptname"));

				licenses.add(license);
			}

			return licenses;
		}
		return null;
	}

}
