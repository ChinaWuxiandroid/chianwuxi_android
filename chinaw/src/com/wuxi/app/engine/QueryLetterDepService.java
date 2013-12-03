/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: QueryLetterDepService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 解析信件查询部门下拉框数据集
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-6 上午10:27:10
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
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： QueryLetterDepService
 * @描述： 我要写信 部门下拉列表 业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-6 上午10:27:10
 * @修改时间： 
 * @修改描述： 
 *
 */
public class QueryLetterDepService extends Service {

	/**
	 * @方法： QueryLetterDepService
	 * @描述： 构造方法
	 * @param context
	 */
	public QueryLetterDepService(Context context) {
		super(context);
	}
	
	/**
	 * @方法： getPartLeaderMailWrapper
	 * @描述： 解析所有数据集
	 * @return PartLeaderMailWrapper
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public PartLeaderMailWrapper getPartLeaderMailWrapper()
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.QUERY_MAIL_DEP_URL;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);

			JSONArray jresult = jsonObject.getJSONArray("result");

			PartLeaderMailWrapper leaderMailWrapper = new PartLeaderMailWrapper();

			if (jresult != null) {
				leaderMailWrapper.setPartLeaderMails(getLeaderMails(jresult));
			}

			return leaderMailWrapper;
		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * 解析单个部门数据
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<PartLeaderMail> getLeaderMails(JSONArray array)
			throws JSONException {
		if (array != null) {
			List<PartLeaderMail> leaderMails = new ArrayList<PartLeaderMail>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				PartLeaderMailWrapper leaderMailWrapper = new PartLeaderMailWrapper();
				PartLeaderMail leaderMail = leaderMailWrapper.new PartLeaderMail();

				leaderMail.setIsnull(jb.getString("null"));
				leaderMail.setDepid(jb.getString("depid"));
				leaderMail.setDepname(jb.getString("depname"));
				leaderMail.setDoProjectID(jb.getString("doProjectID"));

				leaderMails.add(leaderMail);
			}

			return leaderMails;
		}

		return null;
	}


}
