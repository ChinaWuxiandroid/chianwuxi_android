/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GuestPresenceService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 嘉宾风采业务类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-26 上午9:25:55
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
import com.wuxi.domain.GuestPresenceWrapper;
import com.wuxi.domain.GuestPresenceWrapper.GuestPresence;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * @类名： GuestPresenceService
 * @描述： 嘉宾风采业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-26 上午9:25:55
 * @修改时间：
 * @修改描述：
 */
public class GuestPresenceService extends Service {

	/**
	 * @方法： GuestPresenceService
	 * @描述： 构造方法
	 * @param context
	 */
	public GuestPresenceService(Context context) {
		super(context);
	}

	/**
	 * @方法： getGuestPresenceWrapper
	 * @描述： 解析数据集
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public GuestPresenceWrapper getGuestPresenceWrapper(String id, int start,
			int end) throws NetException, JSONException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.CHANNEL_CONTENT_P_URL.replace("{id}", id)
				.replace("{start}", start + "").replace("{end}", end + "");
		String resultStr = httpUtils.executeGetToString(url, 5000);
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			GuestPresenceWrapper wrapper = new GuestPresenceWrapper();

			wrapper.setEnd(jresult.getInt("end"));
			wrapper.setStart(jresult.getInt("start"));
			wrapper.setNext(jresult.getBoolean("next"));
			wrapper.setPrevious(jresult.getBoolean("previous"));
			wrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				wrapper.setGuestPresences(getGuestPresences(jData));// 解析数组
			}

			return wrapper;
		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * @方法： getGuestPresences
	 * @描述： 解析单条数据
	 * @param jData
	 * @return
	 * @throws JSONException
	 */
	private List<GuestPresence> getGuestPresences(JSONArray jData)
			throws JSONException {
		if (jData != null) {
			List<GuestPresence> guestPresences = new ArrayList<GuestPresence>();

			for (int i = 0; i < jData.length(); i++) {
				JSONObject jb = jData.getJSONObject(i);

				if (!jb.getBoolean("delete")) {// 不是标记删除的
					GuestPresenceWrapper wrapper = new GuestPresenceWrapper();
					GuestPresence presence = wrapper.new GuestPresence();

					presence.setStatus(jb.getInt("status"));
					presence.setOrganization(jb.getString("organization"));
					presence.setContentId(jb.getString("contentId"));
					presence.setTitle(jb.getString("title"));
					presence.setBrowseCount(jb.getInt("browseCount"));
					presence.setOrderId(jb.getInt("orderId"));
					presence.setBuildTime(jb.getString("buildTime"));
					presence.setHangingParentChannelId(jb
							.getString("hangingParentChannelId"));
					presence.setLocalUpdateDate(jb.getString("localUpdateDate"));
					presence.setContentSource(jb.getString("contentSource"));
					presence.setUpFile(jb.getString("upFile"));
					presence.setDocSummary(jb.getString("docSummary"));
					presence.setPutTop(jb.getBoolean("putTop"));
					presence.setWapUrl(jb.getString("wapUrl"));
					presence.setPushWap(jb.getBoolean("pushWap"));
					presence.setUpdateTime(jb.getString("updateTime"));
					presence.setPublishTime(jb.getString("publishTime"));
					presence.setParentId(jb.getString("parentId"));
					presence.setDelete(jb.getBoolean("delete"));
					presence.setDocId(jb.getString("docId"));
					presence.setRecommendPictures(jb.getString("recommendPictures"));
					presence.setPicturesTitle(jb.getString("picturesTitle"));

					guestPresences.add(presence);
				}

			}

			return guestPresences;
		}

		return null;
	}

}
