/**
 * 
 */
package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 政民互动 12345来信办理平台 部门领导信箱 各部门领导信箱业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class PartLeaderMailService extends Service {

	public PartLeaderMailService(Context context) {
		super(context);
	}

	/**
	 * 解析整个数据集
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public PartLeaderMailWrapper getPartLeaderMailWrapper()
			throws NetException, JSONException,NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.PART_LEADER_MAIL_URL;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);

			JSONArray jresult = jsonObject.getJSONArray("result");

			PartLeaderMailWrapper leaderMailWrapper = new PartLeaderMailWrapper();

			if (jresult != null) {
				leaderMailWrapper.setPartLeaderMails(getLeaderMails(jresult));
			}

			return leaderMailWrapper;
		}

		return null;
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
