package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.ContentType;
import com.wuxi.domain.LetterType;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅,信件查询 业务类
 * 
 */
public class LetterQueryService extends Service {

	public LetterQueryService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 获取信息分类
	 * 
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<ContentType> getContentTypes() throws NetException,
			JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.CONTENT_TYPE_URL;
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jdata = (JSONArray) o;
				List<ContentType> contentTypes;
				try {
					contentTypes = JAsonPaserUtil.getListByJassory(
							ContentType.class, jdata);
					return contentTypes;
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

		}
		return null;
	}

	/**
	 * 
	 * wanglu 泰得利通 获取新建分类信息
	 * 
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<LetterType> getLetterTypes() throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.LETTER_TYPE_URL;
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jdata = (JSONArray) o;
				List<LetterType> letterTypes;
				try {
					letterTypes = JAsonPaserUtil.getListByJassory(
							LetterType.class, jdata);
					return letterTypes;
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

		}
		return null;
	}

}
