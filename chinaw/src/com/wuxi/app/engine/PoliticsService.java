package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.HotReviewWrapper;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 立法征求或者民意征集 业务类
 * @author 杨宸 智佳
 * */


public class PoliticsService extends Service{

	public PoliticsService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 分页获取立法征求或者民意征集的列表
	 * @throws NODataException 
	 * @throws JSONException 
	 * @throws NetException 
	 * */
	public PoliticsWrapper getPoliticsWrapper(String url,int type,int startIndex,int endIndex)
			throws NetException, JSONException, NODataException{
		url=url+"?type="+type+"&start="+startIndex+"&end="+endIndex;
		return getPoliticsWrapper(url);
	}
	
	/**
	 * 获取当前用户参与的立法征求或民意征集
	 * */
	public PoliticsWrapper getMyPoliticsWrapper(String url,String accessToken,
			int type,int startIndex,int endIndex) 
					throws NetException, JSONException, NODataException{
		url=url+"?access_token="+accessToken+"&type="+type+"&start="+startIndex+"&end="+endIndex;
		return getPoliticsWrapper(url);
	}
	
	/**
	 * 
	 * 杨宸 智佳 
	 * @param start 开始索引
	 * @param end    结束索引
	 * @param previous		是否可以上一页
	 * @param totalRowsAmount; 	数据列表中元素个数
	 * @param next;			是否可以下一页
	 * @return PoliticsWrapper Politics包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public PoliticsWrapper getPoliticsWrapper(String url)
			throws NetException, JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		
		
		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			PoliticsWrapper politicsWrapper = new PoliticsWrapper();
			politicsWrapper.setEnd(jresult.getInt("end"));
			politicsWrapper.setStart(jresult.getInt("start"));
			politicsWrapper.setNext(jresult.getBoolean("next"));
			politicsWrapper.setPrevious(jresult.getBoolean("previous"));
			politicsWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				politicsWrapper.setData(parseData(jData));// 解析数组
			}

			return politicsWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}


	/**
	 *
	 * 杨宸 智佳
	 * @param jData
	 * @return   从 索引start 到  end-1   的  List<HotReview>
	 * @throws JSONException
	 */

	private List<PoliticsWrapper.Politics> parseData(JSONArray jData) throws JSONException {

		if (jData != null) {
			List<PoliticsWrapper.Politics> politicsList = new ArrayList<PoliticsWrapper.Politics>();

			for (int index = 0; index < jData.length(); index++) {

				JSONObject jb = jData.getJSONObject(index);
				PoliticsWrapper h=new PoliticsWrapper();
				PoliticsWrapper.Politics politics = h.new Politics();
				politics.setId(jb.getString("id"));
				politics.setTitle(jb.getString("title"));	
				politics.setDoprojectid(jb.getString("doprojectid"));
//				hotReview.setStartTime(jb.getString("beginTime"));		
//				hotReview.setEndTime(jb.getString("endTime"));		
				politicsList.add(politics);
			}
			return politicsList;
		}
		return null;
	}

	/**
	 * 
	 * 杨宸 智佳 
	 * @param start 开始索引
	 * @param end    结束索引
	 * @param previous		是否可以上一页
	 * @param totalRowsAmount; 	数据列表中元素个数
	 * @param next;			是否可以下一页
	 * @return HotReviewWrapper OpenTel包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public HotReviewWrapper getHotReviewWrapper(String url,int type,int startIndex,int endIndex)
			throws NetException, JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		url=url+"?type="+type+"&start="+startIndex+"&end="+endIndex;
		
		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			HotReviewWrapper hotReviewWrapper = new HotReviewWrapper();
			hotReviewWrapper.setEnd(jresult.getInt("end"));
			hotReviewWrapper.setStart(jresult.getInt("start"));
			hotReviewWrapper.setNext(jresult.getBoolean("next"));
			hotReviewWrapper.setPrevious(jresult.getBoolean("previous"));
			hotReviewWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				hotReviewWrapper.setData(parseData(jData,hotReviewWrapper.getStart(),hotReviewWrapper.getEnd()));// 解析数组
			}

			return hotReviewWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}


	/**
	 *
	 * 杨宸 智佳
	 * @param jData
	 * @return   从 索引start 到  end-1   的  List<HotReview>
	 * @throws JSONException
	 */

	private List<HotReviewWrapper.HotReview> parseData(JSONArray jData,int start,int end) throws JSONException {

		if (jData != null) {
			List<HotReviewWrapper.HotReview> totReviews = new ArrayList<HotReviewWrapper.HotReview>();

			for (int index = 0; index < jData.length(); index++) {

				JSONObject jb = jData.getJSONObject(index);
				HotReviewWrapper h=new HotReviewWrapper();
				HotReviewWrapper.HotReview hotReview = h.new HotReview();
				hotReview.setId(jb.getString("id"));
				hotReview.setTitle(jb.getString("title"));			
//				hotReview.setStartTime(jb.getString("beginTime"));		
//				hotReview.setEndTime(jb.getString("endTime"));		
				totReviews.add(hotReview);
			}
			return totReviews;
		}
		return null;
	}

}
