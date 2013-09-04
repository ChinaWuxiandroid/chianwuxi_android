package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.StatisticsLetter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 回复统计业务类  包括答复率总数统计 和 各信箱答复率统计
 * @author 杨宸  智佳
 * */
public class ReplyStatisticsService extends Service{

	public ReplyStatisticsService(Context context) {
		super(context);
	}

	/**
	 * 获取答复率总数统计
	 * @throws NODataException 
	 * @throws JSONException 
	 * @throws NetException 
	 * */
	public List<AllCount> getAllCount(String url) 
			throws NetException, JSONException, NODataException{
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String reslutStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (reslutStr != null) {
			JSONObject jsonObject = new JSONObject(reslutStr);
			JSONArray jresult = jsonObject.getJSONArray("result");
			if (null != jresult && jresult.length() > 0) {
				List<AllCount> allCounts = new ArrayList<AllCount>();
				for (int i = 0; i < jresult.length(); i++) {
					JSONObject jb = jresult.getJSONObject(i);
					AllCount allCount = new AllCount();
					allCount.setName(jb.getString("name"));	
					allCount.setCount(jb.getInt("count"));
					allCount.setNull(jb.getBoolean("null"));
					allCounts.add(allCount);	
				}
				return allCounts;

			} else {
				throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
			}
		}
		return null;
	}

	/**
	 * 获取各信箱部门答复率总数统计
	 * @throws NODataException 
	 * @throws JSONException 
	 * @throws NetException 
	 * */
	public List<StatisticsLetter> getLettersStatistics(int letter_type,int year,int month) 
			throws JSONException, NetException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url=Constants.Urls.LETTERS_STATISTICS_URL+"?type="+letter_type+"&year="+year+"&month="+month;	
		
		System.out.println("--->"+url);
		
		String reslutStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (reslutStr != null) {
			JSONObject jsonObject = new JSONObject(reslutStr);
			JSONArray jresult = jsonObject.getJSONArray("result");
			if (null != jresult && jresult.length() > 0) {
				List<StatisticsLetter> letters = new ArrayList<StatisticsLetter>();
				for (int i = 0; i < jresult.length(); i++) {
					JSONObject jb = jresult.getJSONObject(i);
					StatisticsLetter letter = new StatisticsLetter();
	
					letter.setDepname(jb.getString("depname"));	
					letter.setAcceptedNum(jb.getInt("acceptedNum"));
					letter.setReplyNum(jb.getInt("replyNum"));
					letter.setReplyRate(jb.getString("replyRate"));	
					letter.setReplyDay(jb.getString("replyDay"));	
					letter.setNull(jb.getBoolean("null"));
					letters.add(letter);	
				}
				return letters;
			}
		}
		return null;
	}
	
	/**
	 * 政府信息公开  网上办件统计 获取所有列表
	 * @author 杨宸 智佳
	 * */
	
	public List<StatisticsLetter> getAllLettersStatistics(String url) 
			throws JSONException, NetException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String reslutStr = httpUtils.executeGetToString(url, 500);

		if (reslutStr != null) {
			JSONObject jsonObject = new JSONObject(reslutStr);
			JSONArray jresult = jsonObject.getJSONArray("result");
			if (null != jresult && jresult.length() > 0) {
				List<StatisticsLetter> letters = new ArrayList<StatisticsLetter>();
				for (int i = 0; i < jresult.length(); i++) {
					JSONObject jb = jresult.getJSONObject(i);
					StatisticsLetter letter = new StatisticsLetter();
	
					letter.setDepname(jb.getString("depname"));	
					letter.setAcceptedNum(jb.getInt("acceptedNum"));
					letter.setReplyNum(jb.getInt("replyNum"));
					letter.setReplyRate(jb.getString("replyRate"));	
					letter.setReplyDay(jb.getString("replyDay"));	
					letter.setNull(jb.getBoolean("null"));
					letters.add(letter);	
				}
				return letters;

			} else {
				throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
			}
		}
		return null;
	}
}
