package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.OpenTel;
import com.wuxi.domain.OpenTelWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公开电话业务类
 * @author 杨宸 智佳
 * */

public class OpenTelService extends Service{

	public OpenTelService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * 杨宸 智佳 
	 * @param start 开始索引
	 * @param end    结束索引
	 * @param previous		是否可以上一页
	 * @param totalRowsAmount; 	数据列表中元素个数
	 * @param next;			是否可以下一页
	 * @return OpenTelWrapper OpenTel包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public OpenTelWrapper getOpenTelWrapper(String url)
			throws NetException, JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			OpenTelWrapper openTelWrapper = new OpenTelWrapper();
			openTelWrapper.setEnd(jresult.getInt("end"));
			openTelWrapper.setStart(jresult.getInt("start"));
			openTelWrapper.setNext(jresult.getBoolean("next"));
			openTelWrapper.setPrevious(jresult.getBoolean("previous"));
			openTelWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				openTelWrapper.setData(parseData(jData,openTelWrapper.getStart(),openTelWrapper.getEnd()));// 解析数组
			}

			return openTelWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}


	/**
	 *
	 * 杨宸 智佳
	 * @param jData
	 * @return   从 索引start 到  end-1   的  List<OpenTel>
	 * @throws JSONException
	 */

	private List<OpenTel> parseData(JSONArray jData,int start,int end) throws JSONException {

		if (jData != null) {
			List<OpenTel> tels = new ArrayList<OpenTel>();

			for (int index = 0; index < jData.length(); index++) {

				JSONObject jb = jData.getJSONObject(index);

				OpenTel tel = new OpenTel();
				tel.setDepname(jb.getString("depname"));
				tel.setTel(jb.getString("tel"));				
				tels.add(tel);
			}
			return tels;
		}
		return null;
	}

}
