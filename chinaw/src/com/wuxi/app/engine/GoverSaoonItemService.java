package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

public class GoverSaoonItemService extends Service {

	public GoverSaoonItemService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 通过部门获取办件信息 分页接口
	 * 
	 * @param deptId
	 * @param start
	 * @param end
	 * @return
	 * @throws JSONException
	 * @throws NetException
	 * @throws NODataException
	 */
	public GoverSaoonItemWrapper getGoverSaoonItemsByDeptId(String deptId,
			int start, int end) throws JSONException, NetException,
			NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.GETITEM_BYDEPT_URL
				.replace("{deptid}", deptId).replace("{start}", start + "")
				.replace("{end}", end + "");
		String resultStr = httpUtils.executeGetToString(url, 5000);
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			GoverSaoonItemWrapper goverSaoonItemWrapper = new GoverSaoonItemWrapper();
			goverSaoonItemWrapper.setEnd(jresult.getInt("end"));
			goverSaoonItemWrapper.setStart(jresult.getInt("start"));
			goverSaoonItemWrapper.setNext(jresult.getBoolean("next"));
			goverSaoonItemWrapper.setPrevious(jresult.getBoolean("previous"));
			goverSaoonItemWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));
			Object o = jresult.get("data");
			if (!o.toString().equals("[]") && !o.equals("null")) {
				JSONArray jData = (JSONArray) o;
				try {
					goverSaoonItemWrapper.setGoverSaoonItems(JAsonPaserUtil
							.getListByJassory(GoverSaoonItem.class, jData));
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

			return goverSaoonItemWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

}
