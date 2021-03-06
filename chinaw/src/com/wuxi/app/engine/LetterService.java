package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.domain.LetterWrapper.Letter;
import com.wuxi.domain.MyLetter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @author 杨宸 智佳
 * */
public class LetterService extends Service {

	public LetterService(Context context) {
		super(context);
	}

	/**
	 * 获取市长信箱列表
	 * 
	 * @throws NODataException
	 * @throws JSONException
	 * @throws NetException
	 * */
	public LetterWrapper getLetterLitstWrapper(String url) throws NetException,
			JSONException, NODataException {
		return getLettersWrapper(url);
	}

	/**
	 * 获取我的信箱列表
	 * 
	 * @throws NODataException
	 * @throws JSONException
	 * @throws NetException
	 * */
	public LetterWrapper getMyLettersList(String url, String access_token,
			int startIndex, int endIndex) throws NetException, JSONException,
			NODataException {
		url = url + "?access_token=" + access_token + "&start=" + startIndex
				+ "&end=" + endIndex;
		return getLettersWrapper(url);
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
	 * @return PoliticsWrapper Politics包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public LetterWrapper getLettersWrapper(String url) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {

			JSONObject jsonObject = new JSONObject(resultStr);
			boolean success = jsonObject.getBoolean("success");

			LetterWrapper letterWrapper = new LetterWrapper();

			// 判断是否读取到了数据
			if (success) {

				JSONObject jresult = jsonObject.getJSONObject("result");

				letterWrapper.setEnd(jresult.getInt("end"));
				letterWrapper.setStart(jresult.getInt("start"));
				letterWrapper.setNext(jresult.getBoolean("next"));
				letterWrapper.setPrevious(jresult.getBoolean("previous"));
				letterWrapper.setTotalRowsAmount(jresult
						.getInt("totalRowsAmount"));

				JSONArray jData = jresult.getJSONArray("data");
				if (jData != null) {
					letterWrapper.setData(parseData(jData));// 解析数组
				}

			} else if (!success) {
				letterWrapper.setData(success);
			}
			return letterWrapper;

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

	private List<LetterWrapper.Letter> parseData(JSONArray jData)
			throws JSONException {

		if (jData != null) {
			List<Letter> letterList = new ArrayList<Letter>();

			for (int index = 0; index < jData.length(); index++) {
				JSONObject jb = jData.getJSONObject(index);

				LetterWrapper h = new LetterWrapper();
				LetterWrapper.Letter letters = h.new Letter();

				letters.setId(jb.getString("id"));
				letters.setType(jb.getString("type"));
				letters.setTitle(jb.getString("title"));
				letters.setCode(jb.getString("code"));
				letters.setAppraise(jb.getString("appraise"));
				letters.setDepname(jb.getString("depname"));

				if (!jb.isNull("answerdate")) {

					letters.setAnswerdate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("answerdate")),
							TimeFormateUtil.DATE_PATTERN));
				}

				letters.setReadcount(jb.getInt("readcount"));

				letterList.add(letters);
			}
			return letterList;
		}
		return null;
	}

	/**
	 * 提交我要写信
	 * 
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 * */
	public boolean submitMyLetter(MyLetter myLetter) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET); // 检查网络
		}

		String url = Constants.Urls.IWANTMAIL_URL + "?access_token="
				+ myLetter.getAccess_token() + "&doprojectid="
				+ myLetter.getDoprojectid() + "&typeid=" + myLetter.getTypeid()
				+ "&title=" + myLetter.getTitle() + "&content="
				+ myLetter.getContent() + "&openstate="
				+ myLetter.getOpenState() + "&sentmailback="
				+ myLetter.getSentMailBack() + "&msgstatus="
				+ myLetter.getMsgStatus();

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			return jsonObject.getBoolean("success");
		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}

	}

}
