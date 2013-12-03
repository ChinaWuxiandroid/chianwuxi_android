/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GIPMailInfoService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 解析信件详细信息数据 
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-22 下午4:08:19
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.GIPMailInfoWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GIPMailInfoService
 * @描述： 解析信件详细信息数据
 * @作者： 罗森
 * @创建时间： 2013 2013-8-22 下午4:08:19
 * @修改时间：
 * @修改描述：
 * 
 */
public class GIPMailInfoService extends Service {

	/**
	 * @方法： GIPMailInfoService
	 * @描述： 构造方法
	 * @param context
	 */
	public GIPMailInfoService(Context context) {
		super(context);
	}

	/**
	 * @方法： getGipMailInfoWrapper
	 * @描述： 解析数据
	 * @param id
	 * @return GIPMailInfoWrapper
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public GIPMailInfoWrapper getGipMailInfoWrapper(String id)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.MAIL_INFO_URL.replace("{id}", id);

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			GIPMailInfoWrapper wrapper = new GIPMailInfoWrapper();

			wrapper.setId(jresult.getString("id"));
			wrapper.setType(jresult.getString("type"));
			wrapper.setContent(jresult.getString("content"));
			wrapper.setResult(jresult.getString("result"));
			wrapper.setCode(jresult.getString("code"));
			wrapper.setTitle(jresult.getString("title"));
			wrapper.setDepname(jresult.getString("depname"));
			wrapper.setAppraise(jresult.getString("appraise"));

			if (!jresult.isNull("begintime")) {
				wrapper.setBegintime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("begintime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			if (!jresult.isNull("endtime")) {
				wrapper.setEndtime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("endtime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			wrapper.setDoprojectid(jresult.getString("doprojectid"));
			wrapper.setDodepname(jresult.getString("dodepname"));

			return wrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

}
