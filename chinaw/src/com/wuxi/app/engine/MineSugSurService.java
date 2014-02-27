/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MineSugSurService.java 
 * @包名： com.wuxi.app.engine 
 * @描述:  我参与的网上调查业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-14 下午5:12:18
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
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.MineSugSurWapper;
import com.wuxi.domain.MineSugSurWapper.MineSugSur;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： MineSugSurService
 * @描述： 我参与的网上调查业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-14 下午5:12:18
 * @修改时间：
 * @修改描述：
 */
public class MineSugSurService extends Service {

	public MineSugSurService(Context context) {
		super(context);
	}

	/**
	 * @方法： getMineSugSurWapper
	 * @描述： 解析我参与的网上调查数据集
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public MineSugSurWapper getMineSugSurWapper(String url)
			throws NetException, JSONException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			MineSugSurWapper wapper = new MineSugSurWapper();

			wapper.setEnd(jresult.getInt("end"));
			wapper.setStart(jresult.getInt("start"));
			wapper.setNext(jresult.getBoolean("next"));
			wapper.setPrevious(jresult.getBoolean("previous"));
			wapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				wapper.setMineSugSurs(getMineSugSurs(jData));// 解析数组
			}
			return wapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * @方法： getMineSugSurs
	 * @描述： 解析我参与的网上调查数据
	 * @param jData
	 * @return
	 * @throws JSONException
	 */
	private List<MineSugSur> getMineSugSurs(JSONArray jData)
			throws JSONException {
		if (jData != null) {
			List<MineSugSur> surs = new ArrayList<MineSugSur>();

			for (int index = 0; index < jData.length(); index++) {
				JSONObject jb = jData.getJSONObject(index);

				MineSugSurWapper wapper = new MineSugSurWapper();
				MineSugSur sur = wapper.new MineSugSur();

				sur.setId(jb.getString("id"));
				sur.setTitle(jb.getString("title"));
				sur.setReplydept(jb.getString("replydept"));

				if (!jb.isNull("starttime")) {
					sur.setStarttime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("starttime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				if (!jb.isNull("endtime")) {
					sur.setEndtime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("endtime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				sur.setSurveryID(jb.getString("surveryID"));

				surs.add(sur);
			}
			return surs;
		}
		return null;
	}

}
