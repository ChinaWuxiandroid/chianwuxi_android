package com.wuxi.app.engine;

import java.sql.Wrapper;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 内容接口业务类
 * 
 */
public class ContentService extends Service {

	/**
	 * 
	 * wanglu 泰得利通 根据id 获取content集合
	 * 
	 * @param id
	 *            MenuItem Id 或ChannelId
	 * @return content集合
	 * @throws NetException
	 */
	public List<Content> getContentsById(String id) throws NetException {

		String url = Constants.Urls.CHANNEL_CONTENT_URL.replace("{id}", id);
		return null;
	}

	/**
	 * 
	 * wanglu 泰得利通 根据开始位置结束位置
	 * 
	 * @param id
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @return ContentWrapper content包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException 
	 */
	public ContentWrapper getPageContentsById(String id, int start, int end)
			throws NetException, JSONException, NODataException {
		String url = Constants.Urls.CHANNEL_CONTENT_P_URL.replace("{id}", id)
				.replace("{start}", start + "").replace("{end}", end + "");

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, 5000);
		if (resultStr != null) {
			JSONObject jresult = new JSONObject("result");
			ContentWrapper contentWrapper = new ContentWrapper();
			contentWrapper.setEnd(jresult.getInt("end"));
			contentWrapper.setStart(jresult.getInt("start"));
			contentWrapper.setNext(jresult.getBoolean("next"));
			contentWrapper.setPrevious(jresult.getBoolean("previous"));
			contentWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				contentWrapper.setContents(parseData(jData));// 解析数组
			}

			return contentWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);//没有获取到数据异常
		}

	}

	private List<Content> parseData(JSONArray jData) {

		
		
		
		
		
		
		return null;
	}
}
