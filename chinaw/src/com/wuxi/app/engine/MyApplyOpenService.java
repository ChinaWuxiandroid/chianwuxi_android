/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MyApplyOpenService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 我的依申请公开业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-15 上午11:24:50
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

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.MyApplyOpenWrapper;
import com.wuxi.domain.MyApplyOpenWrapper.MyApplyOpen;
import com.wuxi.domain.MyOpinionOpenWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： MyApplyOpenService
 * @描述： 我的依申请公开业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-15 上午11:24:50
 * @修改时间：
 * @修改描述：
 */
public class MyApplyOpenService extends Service {

	public MyApplyOpenService(Context context) {
		super(context);
	}

	/**
	 * @方法： getMyApplyOpenWrapper
	 * @描述： 解析数据集
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public MyApplyOpenWrapper getMyApplyOpenWrapper(String url)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		// 提交请求并返回结果
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			MyApplyOpenWrapper wrapper = new MyApplyOpenWrapper();

			wrapper.setEnd(jresult.getInt("end"));
			wrapper.setStart(jresult.getInt("start"));
			wrapper.setNext(jresult.getBoolean("next"));
			wrapper.setPrevious(jresult.getBoolean("previous"));
			wrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				wrapper.setMyApplyOpens(getMyApplyOpens(jData));
			}

			return wrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}

	}

	/**
	 * @方法： getMyApplyOpens
	 * @描述： 解析单条数据
	 * @param ja
	 * @return
	 * @throws JSONException
	 */
	private List<MyApplyOpen> getMyApplyOpens(JSONArray ja)
			throws JSONException {
		if (ja != null) {
			List<MyApplyOpen> opens = new ArrayList<MyApplyOpen>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject jb = ja.getJSONObject(i);

				MyApplyOpenWrapper wrapper = new MyApplyOpenWrapper();
				MyApplyOpen open = wrapper.new MyApplyOpen();

				open.setId(jb.getString("id"));
				open.setContent(jb.getString("content"));
				open.setCode(jb.getString("code"));
				open.setTitle(jb.getString("title"));
				if (!jb.isNull("applyDate")) {
					open.setApplyDate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("applyDate")),
							TimeFormateUtil.DATE_PATTERN));
				}
				open.setReadCount(jb.getInt("readCount"));
				open.setAnswerDep(jb.getString("answerDep"));
				if (!jb.isNull("answerDate")) {
					open.setAnswerDate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("answerDate")),
							TimeFormateUtil.DATE_PATTERN));
				}
				open.setAnswerUser(jb.getString("answerUser"));
				open.setAnswerContent(jb.getString("answerContent"));

				opens.add(open);
			}
			return opens;
		}
		return null;
	}

	/**
	 * 数据数据，并解析
	 * 
	 * @方法： getMyOpinionOpenWrapper
	 * @param url
	 */
	public ArrayList<MyOpinionOpenWrapper> getMyOpinionOpenWrapper(String url)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		ArrayList<MyOpinionOpenWrapper> mArrayList = null;
		// 提交请求并返回结果
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		try {
			if (resultStr != null) {
				JSONObject object = new JSONObject(resultStr);
				boolean success = object.getBoolean("success");
				if (success) {
					String result = object.getString("result");
					if (result != null) {

						JSONObject object2 = new JSONObject(result);

						String data = object2.getString("data");

						boolean next = object2.getBoolean("next");

						mArrayList = new MyOpinionOpenWrapper()
								.getMyOpinionOpenWrapper(data, next);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mArrayList;
	}

}
