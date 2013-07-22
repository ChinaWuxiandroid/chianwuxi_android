package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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

		String url = Constants.Urls.GETITEM_BYDEPT_URL
				.replace("{deptid}", deptId).replace("{start}", start + "")
				.replace("{end}", end + "");

		return getGoverSaoonItemsByURL(url);
	}

	/**
	 * 
	 * 
	 * 获取办件信息多条件信息 wanglu 泰得利通
	 * 
	 * @param params
	 *            参数
	 * @return
	 * @throws JSONException
	 * @throws NetException
	 * @throws NODataException
	 */
	public GoverSaoonItemWrapper getGoverSaoonItemsByParas(
			Map<String, String> params) throws JSONException, NetException,
			NODataException {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> paramSet : params.entrySet()) {

			sb.append(paramSet.getKey()).append("=")
					.append(paramSet.getValue()).append("&");

		}

		sb.deleteCharAt(sb.length() - 1);// 删除最后一个字符

		String url = Constants.Urls.GETITEM_QUERY_URL + "?" + sb.toString();

		return getGoverSaoonItemsByURL(url);

	}

	/**
	 * 
	 * wanglu 泰得利通 根据URL获取办件列表
	 * 
	 * @param url
	 * @return
	 * @throws JSONException
	 * @throws NetException
	 * @throws NODataException
	 */
	public GoverSaoonItemWrapper getGoverSaoonItemsByURL(String url)
			throws JSONException, NetException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

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

	/**
	 * 
	 * wanglu 泰得利通 根据kindType获取办件信息
	 * 
	 * @return
	 * @throws NODataException
	 * @throws NetException
	 * @throws JSONException
	 */
	public GoverSaoonItemWrapper getGoverSaoonItemsByKindType(String type,
			String kindType, int start, int end) throws JSONException,
			NetException, NODataException {
		String url = Constants.Urls.GETITEM_BYKINDTYPE_URL
				.replace("{type}", type).replace("{kindtype}", kindType)
				.replace("{start}", start + "").replace("{end}", end + "");

		return getGoverSaoonItemsByURL(url);
	}

}
