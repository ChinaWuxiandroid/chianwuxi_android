package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.GoverItemCount;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅办件统计
 * 
 */
public class GoverItemCountService extends Service {

	public GoverItemCountService(Context context) {
		super(context);

	}

	public GoverItemCount getGoverItemCount() throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.GETGOVEDR_ITEM_COUNT_URL;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {

			JSONObject jreslut = new JSONObject(resultStr);
			JSONObject jb=jreslut.getJSONObject("result");

			try {
				GoverItemCount goverItemCount = JAsonPaserUtil.getBeanByJASSON(
						GoverItemCount.class, jb);
				return goverItemCount;
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
