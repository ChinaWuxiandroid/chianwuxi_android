/**
 * 
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
import com.wuxi.domain.MemoirWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 访谈实录数据加载业务类
 * 
 * @author 智佳 罗森
 * @createtime 2013年8月9日 20:01
 * 
 */
public class MemoirService extends Service {

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public MemoirService(Context context) {
		super(context);
	}

	/**
	 * 解析访谈实录结果集
	 * 
	 * @param interViewId
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public MemoirWrapper getMemoirWrapper(String interViewId, int start, int end)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.MEMOIR_CONTENT_URL.replace("{interViewId}",
				interViewId) + "?start=" + start + "&end=" + end;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			MemoirWrapper memoirWrapper = new MemoirWrapper();
			memoirWrapper.setEnd(jresult.getInt("end"));
			memoirWrapper.setStart(jresult.getInt("start"));
			memoirWrapper.setNext(jresult.getBoolean("next"));
			memoirWrapper.setPrevious(jresult.getBoolean("previous"));
			memoirWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				// 解析单条数组
				memoirWrapper.setMemoirs(parseData(jData,
						memoirWrapper.getStart(), memoirWrapper.getEnd()));
			}
			return memoirWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * 解析单条数据
	 * 
	 * @param jData
	 * @param start
	 * @param end
	 * @return
	 * @throws JSONException
	 */
	private List<MemoirWrapper.Memoir> parseData(JSONArray jData, int start,
			int end) throws JSONException {

		if (jData != null) {
			List<MemoirWrapper.Memoir> memoirs = new ArrayList<MemoirWrapper.Memoir>();

			for (int i = 0; i < jData.length(); i++) {
				JSONObject jb = jData.getJSONObject(i);

				MemoirWrapper memoirWrapper = new MemoirWrapper();
				MemoirWrapper.Memoir memoir = memoirWrapper.new Memoir();

				memoir.setId(jb.getString("id"));
				memoir.setContent(jb.getString("content"));
				memoir.setInterViewId(jb.getString("interViewId"));

				if (!jb.isNull("submitTime")) {
					memoir.setSubmitTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("submitTime")),
							TimeFormateUtil.DATE_TIME_PATTERN));
				}

				memoir.setAnswerUser(jb.getString("answerUser"));
				memoir.setAnswerType(jb.getInt("answerType"));

				memoirs.add(memoir);
			}
			return memoirs;
		}
		return null;
	}

}
