package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.BBSWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公众论坛列表业务类
 * 
 * @author 杨宸 智佳
 * */

public class BBSService extends Service {

	public BBSService(Context context) {
		super(context);
	}

	/**
	 * 
	 * 杨宸 智佳
	 * 
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @param previous
	 *            是否可以上一页
	 * @param totalRowsAmount
	 *            ; 数据列表中元素个数
	 * @param next
	 *            ; 是否可以下一页
	 * @return HotReviewWrapper OpenTel包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public BBSWrapper getBBSWrapper(String url, int startIndex, int endIndex)
			throws NetException, JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		url = url + "?start=" + startIndex + "&end=" + endIndex;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			BBSWrapper bBSWrapper = new BBSWrapper();
			bBSWrapper.setEnd(jresult.getInt("end"));
			bBSWrapper.setStart(jresult.getInt("start"));
			bBSWrapper.setNext(jresult.getBoolean("next"));
			bBSWrapper.setPrevious(jresult.getBoolean("previous"));
			bBSWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				bBSWrapper.setData(parseData(jData));// 解析数组
			}

			return bBSWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * 
	 * 杨宸 智佳
	 * 
	 * @param jData
	 * @return 从 索引start 到 end-1 的 List<HotReview>
	 * @throws JSONException
	 */

	private List<BBSWrapper.BBS> parseData(JSONArray jData)
			throws JSONException {

		if (jData != null) {
			List<BBSWrapper.BBS> bbsList = new ArrayList<BBSWrapper.BBS>();

			for (int index = 0; index < jData.length(); index++) {

				JSONObject jb = jData.getJSONObject(index);
				BBSWrapper h = new BBSWrapper();
				BBSWrapper.BBS bbs = h.new BBS();
				bbs.setPoliticsMainID(jb.getString("politicsMainID"));
				bbs.setTitle(jb.getString("title"));
				bbs.setReadCount(jb.getInt("readCount"));
				bbs.setTop(jb.getBoolean("top"));
				bbs.setSentuser(jb.getString("sentUser"));
				bbs.setDoProjectID(jb.getString("doProjectID"));
				bbs.setDataNO(jb.getInt("title"));
				bbs.setTitle(jb.getString("dataNo"));
				
				if (!jb.isNull("beginTime")) {
					bbs.setBeginTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("beginTime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				bbs.setViewpath(jb.getString("viewpath"));
				bbs.setResultCount(jb.getInt("resultCount"));
				bbsList.add(bbs);
			}
			return bbsList;
		}
		return null;
	}

}
