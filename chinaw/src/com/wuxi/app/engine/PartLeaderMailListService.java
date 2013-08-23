/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PartLearderMailListService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 解析某部门信件数据 
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-23 下午2:07:45
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

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.domain.LetterWrapper.Letter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * @类名： PartLearderMailListService
 * @描述： 解析某部门信件数据
 * @作者： 罗森
 * @创建时间： 2013 2013-8-23 下午2:07:45
 * @修改时间：
 * @修改描述：
 * 
 */
public class PartLeaderMailListService extends Service {

	/**
	 * @方法： PartLeaderMailListService
	 * @描述： 构造方法
	 * @param context
	 */
	public PartLeaderMailListService(Context context) {
		super(context);
	}

	/**
	 * @方法： getLettersWrapper
	 * @描述： 解析信件数据集
	 * @param start
	 * @param end
	 * @param doid
	 * @return LetterWrapper
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public LetterWrapper getLettersWrapper(int start, int end, String doid)
			throws NetException, JSONException, NODataException {
		// 检查网络链接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		// 拼接访问地址
		String url = Constants.Urls.PART_LEADER_MAIL_LIST_URL + "?start="
				+ start + "&end=" + end + "&doprojectid=" + doid;

		// 访问数据
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			LetterWrapper letterWrapper = new LetterWrapper();

			letterWrapper.setEnd(jresult.getInt("end"));
			letterWrapper.setStart(jresult.getInt("start"));
			letterWrapper.setNext(jresult.getBoolean("next"));
			letterWrapper.setPrevious(jresult.getBoolean("previous"));
			letterWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				letterWrapper.setData(parseData(jData));// 解析数组
			}

			return letterWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * @方法： parseData
	 * @描述： 解析单封信件数据
	 * @param jData
	 * @return List<Letter>
	 * @throws JSONException
	 */
	private List<Letter> parseData(JSONArray jData) throws JSONException {

		if (jData != null) {
			List<Letter> letterList = new ArrayList<Letter>();

			for (int index = 0; index < jData.length(); index++) {
				JSONObject jb = jData.getJSONObject(index);
				
				LetterWrapper h = new LetterWrapper();
				
				Letter letters = h.new Letter();
				letters.setId(jb.getString("id"));
				letters.setType(jb.getString("type"));
				letters.setTitle(jb.getString("title"));
				letters.setCode(jb.getString("code"));
				letters.setAppraise(jb.getString("appraise"));
				letters.setDepname(jb.getString("depname"));
//				letters.setAnswerdate(TimeFormateUtil.formateTime(
//						String.valueOf(jb.getLong("answerdate")),
//						TimeFormateUtil.DATE_PATTERN));
				letters.setReadcount(jb.getInt("readcount"));
				
				letterList.add(letters);
			}
			return letterList;
		}
		return null;
	}

}
