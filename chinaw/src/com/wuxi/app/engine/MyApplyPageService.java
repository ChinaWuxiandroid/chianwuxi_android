package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.MyApplyPageWrapper;
import com.wuxi.domain.MyApplyPageWrapper.MyApplyPage;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

public class MyApplyPageService extends Service{

	public MyApplyPageService(Context context) {
		super(context);
	}

	/**
	 * 
	 * 杨宸  智佳  获取我的依申请公开列表 支持分页
	 * 
	 * @param access_token
	 *            验证token
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws ResultException
	 */
	public MyApplyPageWrapper getMyApplyPages(String access_token, int start,
			int end) throws NetException, JSONException, ResultException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		
		String url = Constants.Urls.MY_APPLYPAGE_URL
				.replace("{access_token}", access_token)
				.replace("{start}", start + "").replace("{end}", end + "");
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (null != resultStr) {

			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			if (jresult.has("message") && jresult.get("message") != null) {
				throw new ResultException(jresult.getString("message"));// 有错误存在
			}

			MyApplyPageWrapper myApplyWrapper = new MyApplyPageWrapper();
			myApplyWrapper.setStart(jresult.getInt("start"));
			myApplyWrapper.setEnd(jresult.getInt("end"));
			myApplyWrapper.setPrevious(jresult.getBoolean("previous"));
			myApplyWrapper.setNext(jresult.getBoolean("next"));
			myApplyWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				myApplyWrapper.setData(parseData(jData));// 解析数组
			}
			return myApplyWrapper;
		}

		return null;
	}
	
	/**
	 *
	 * 杨宸 智佳
	 * @param jData
	 * @return   从 索引start 到  end-1   的  List<HotReview>
	 * @throws JSONException
	 */

	private List<MyApplyPageWrapper.MyApplyPage> parseData(JSONArray jData) throws JSONException {

		if (jData != null) {
			List<MyApplyPageWrapper.MyApplyPage> ApplyPages = new ArrayList<MyApplyPageWrapper.MyApplyPage>();

			for (int index = 0; index < jData.length(); index++) {
				
				JSONObject jb = jData.getJSONObject(index);
				MyApplyPageWrapper w=new MyApplyPageWrapper();
				MyApplyPage apply=w.new MyApplyPage();
				apply.setId(jb.getString("id"));
				apply.setContent(jb.getString("content"));	
				apply.setCode(jb.getLong("code"));	
				apply.setTitle(jb.getString("title"));
				if(null!=jb.getString("applyDate")&&!"null".equals(jb.getString("applyDate"))){
					System.out.println("date:"+jb.getString("applyDate"));
					apply.setApplyDate(TimeFormateUtil.formateTime
							(String.valueOf(jb.getLong("applyDate")), TimeFormateUtil.DATE_PATTERN));	
				}
				else{
					apply.setApplyDate("");
				}
				
				apply.setReadCount(jb.getInt("readCount"));
				apply.setAnswerContent(jb.getString("answerContent"));	
				apply.setAnswerDep(jb.getString("answerDep"));	
				apply.setAnswerUser(jb.getString("answerUser"));	
				apply.setAnswerDate(TimeFormateUtil.formateTime
						(String.valueOf(jb.getLong("answerDate")), TimeFormateUtil.DATE_PATTERN));	
				ApplyPages.add(apply);
			}
			return ApplyPages;
		}
		return null;
	}
}
