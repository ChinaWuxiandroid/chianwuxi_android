package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.Kindtype;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅办件分类获取
 * 
 */
public class KindTypeService extends Service {

	public KindTypeService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取分类通过type 获取
	 * 
	 * 
	 * 上级分类编号 01：个人身份 02：个人办事 03：企业行业 04：企业办事 05：港澳台侨、外国人 06：主题服务
	 * 
	 * @param type
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public List<Kindtype> getKindtypesByType(String type) throws NetException,
			JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.KIND_TYPE_URL.replace("{type}", type);
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jresult = (JSONArray) o;

				List<Kindtype> kindtypes = new ArrayList<Kindtype>();
				for (int index = 0; index < jresult.length(); index++) {
					JSONObject jb = jresult.getJSONObject(index);
					Kindtype kindtype = new Kindtype();
					kindtype.setId(jb.getString("id"));
					kindtype.setSubKindType(jb.getString("subKindType"));
					kindtype.setKindName(jb.getString("kindName"));
					kindtype.setKindType(jb.getString("kindType"));
					kindtype.setSubKindName(jb.getString("subKindName"));
					kindtypes.add(kindtype);
				}

				return kindtypes;

			}

		}

		return null;
	}

	public List<Kindtype> getKindtypesByType2(String type) throws NetException,
			JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.KIND_TYPE_URL.replace("{type}", type);
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			Object o = jsonObject.get("result");
			if (!o.toString().equals("[]") && !o.toString().equals("null")) {

				JSONArray jresult = (JSONArray) o;

				List<Kindtype> kindtypes = null;
				try {
					kindtypes = JAsonPaserUtil.getListByJassory(Kindtype.class,
							jresult);
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

				return kindtypes;

			}

		}

		return null;
	}

}
