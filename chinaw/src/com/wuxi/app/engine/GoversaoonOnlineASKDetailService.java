package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.GoversaoonOnlineASKDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅，办件咨询信息详情
 * 
 */
public class GoversaoonOnlineASKDetailService extends Service {

	public GoversaoonOnlineASKDetailService(Context context) {
		super(context);

	}

	/**
	 * 
	 *wanglu 泰得利通 
	 *获取咨询详情信息
	 * @param id
	 * @param access_token
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public GoversaoonOnlineASKDetail getGoversaoonOnlineASKDetail(String id,String access_token) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.GETGOVER_ONLINEASK_DETAIL_URL
				.replace("{id}", id)
				.replace("{access_token}", access_token);

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {

			JSONObject jreslut = new JSONObject(resultStr);
			JSONObject jb = jreslut.getJSONObject("result");

			try {
				GoversaoonOnlineASKDetail goversaoonOnlineASKDetail = JAsonPaserUtil.getBeanByJASSON(
						GoversaoonOnlineASKDetail.class, jb);
				return goversaoonOnlineASKDetail;
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

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}

		return null;
	}

}
