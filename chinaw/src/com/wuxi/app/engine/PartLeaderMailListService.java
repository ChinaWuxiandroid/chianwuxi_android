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
import com.wuxi.domain.PartLeaderLetterWrapper;
import com.wuxi.domain.PartLeaderLetterWrapper.PartLeaderLetter;
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
	public PartLeaderLetterWrapper getLeaderLetterWrapper(int start, int end,
			String doid) throws NetException, JSONException, NODataException {
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

			PartLeaderLetterWrapper leaderLetterWrapper = new PartLeaderLetterWrapper();

			leaderLetterWrapper.setEnd(jresult.getInt("end"));
			leaderLetterWrapper.setStart(jresult.getInt("start"));
			leaderLetterWrapper.setNext(jresult.getBoolean("next"));
			leaderLetterWrapper.setPrevious(jresult.getBoolean("previous"));
			leaderLetterWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				leaderLetterWrapper.setLeaderLetters(parseData(jData));// 解析数组
			}

			return leaderLetterWrapper;

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
	private List<PartLeaderLetter> parseData(JSONArray jData)
			throws JSONException {

		if (jData != null) {
			List<PartLeaderLetter> letterList = new ArrayList<PartLeaderLetter>();

			for (int index = 0; index < jData.length(); index++) {
				JSONObject jb = jData.getJSONObject(index);

				PartLeaderLetterWrapper leaderLetterWrapper = new PartLeaderLetterWrapper();

				PartLeaderLetter leaderLetter = leaderLetterWrapper.new PartLeaderLetter();

				leaderLetter.setId(jb.getString("id"));
				leaderLetter.setType(jb.getString("type"));
				leaderLetter.setTitle(jb.getString("title"));
				leaderLetter.setCode(jb.getString("code"));
				leaderLetter.setAppraise(jb.getString("appraise"));
				leaderLetter.setDepname(jb.getString("depname"));

				if (!jb.isNull("answerdate")) {
					leaderLetter.setAnswerdate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("answerdate")),
							TimeFormateUtil.DATE_PATTERN));
				}

				leaderLetter.setReadcount(jb.getString("readcount"));

				letterList.add(leaderLetter);
			}

			return letterList;
		}
		return null;
	}

}
