package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 内容接口业务类
 * 
 */
public class ContentService extends Service {

	public ContentService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 根据id 获取content集合
	 * 
	 * @param id
	 *            MenuItem Id 或ChannelId
	 * @return content集合
	 * @throws NetExceptions
	 * @throws JSONException
	 * @throws NODataException
	 */
	public List<Content> getContentsById(String id) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.CHANNEL_CONTENT_URL.replace("{id}", id);
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {

			JSONObject jObject = new JSONObject(resultStr);
			JSONArray jData = jObject.getJSONArray("result");
			if (jData != null) {
				return parseData(jData);
			}

		} else {

			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常

		}

		return null;

	}

	/**
	 * 
	 * @param id
	 *            刷新阅读次数
	 * @throws NetException
	 */
	public void flushContentBrowsCount(String id) throws NetException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.CONTENT_BROWSECOUNT_URL.replace("{id}", id);
		httpUtils.executeGetToString(url, TIME_OUT);

	}

	/**
	 * '
	 * 
	 * wanglu 泰得利通 根据URL地址获取内容，列推荐新闻，推荐公告API调用
	 * 
	 * @param url
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public List<Content> getContentsByUrl(String url) throws NetException,
			JSONException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {

			JSONObject jObject = new JSONObject(resultStr);
			JSONArray jData = jObject.getJSONArray("result");
			if (jData != null) {
				return parseData(jData);
			}

		} else {

			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常

		}

		return null;
	}

	/**
	 * 
	 * wanglu 泰得利通 根据开始位置结束位置
	 * 
	 * @param id
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @return ContentWrapper content包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public ContentWrapper getPageContentsById(String id, int start, int end)
			throws NetException, JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.CHANNEL_CONTENT_P_URL.replace("{id}", id)
				.replace("{start}", start + "").replace("{end}", end + "");
		String resultStr = null;
		boolean isHasCacheFile = cacheUtil.isHasCacheFile(url, true);//检查是否有缓存文件
		if (isHasCacheFile) {
			resultStr = cacheUtil.getCacheStr(url,true);// 缓存读取
		} else {
			resultStr = httpUtils.executeGetToString(url, 5000);
		}

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			ContentWrapper contentWrapper = new ContentWrapper();
			contentWrapper.setEnd(jresult.getInt("end"));
			contentWrapper.setStart(jresult.getInt("start"));
			contentWrapper.setNext(jresult.getBoolean("next"));
			contentWrapper.setPrevious(jresult.getBoolean("previous"));
			contentWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				contentWrapper.setContents(parseData(jData));// 解析数组
			}

			if(!isHasCacheFile){
				cacheUtil.cacheFile(url, resultStr, true);//缓存内容列表
			}
			return contentWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * 
	 * 杨宸 智佳 根据开始位置结束位置
	 * 
	 * @param url
	 *            包含id start end 等后面 部门 地区 关键词 年份 可选项
	 * 
	 * @return ContentWrapper content包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public ContentWrapper getPageContentsByUrl(String url) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String resultStr = httpUtils.executeGetToString(url, 5000);
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			ContentWrapper contentWrapper = new ContentWrapper();
			contentWrapper.setEnd(jresult.getInt("end"));
			contentWrapper.setStart(jresult.getInt("start"));
			contentWrapper.setNext(jresult.getBoolean("next"));
			contentWrapper.setPrevious(jresult.getBoolean("previous"));
			contentWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				contentWrapper.setContents(parseData(jData));// 解析数组
			}

			return contentWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * ID wanglu 泰得利通
	 * 
	 * @param jData
	 * @return
	 * @throws JSONException
	 */

	private List<Content> parseData(JSONArray jData) throws JSONException {

		if (jData != null) {
			List<Content> contents = new ArrayList<Content>();

			for (int index = 0; index < jData.length(); index++) {

				JSONObject jb = jData.getJSONObject(index);
				if (!jb.getBoolean("delete")) {// 不是标记删除的
					Content content = new Content();
					content.setStatus(jb.getInt("status"));
					content.setOrganization(jb.getString("organization"));
					content.setContentId(jb.getString("contentId"));
					content.setTitle(jb.getString("title"));
					content.setBrowseCount(jb.getInt("browseCount"));
					content.setOrderId(jb.getInt("orderId"));
					content.setBuildTime(jb.getString("buildTime"));
					content.setHangingParentChannelId(jb
							.getString("hangingParentChannelId"));
					content.setLocalUpdateDate(jb.getString("localUpdateDate"));
					content.setContentSource(jb.getString("contentSource"));
					content.setUpFile(jb.getString("upFile"));
					content.setDocSummary(jb.getString("docSummary"));
					content.setPutTop(jb.getBoolean("putTop"));
					content.setWapUrl(jb.getString("wapUrl"));
					content.setPushWap(jb.getBoolean("pushWap"));
					content.setUpdateTime(jb.getString("updateTime"));
					content.setPublishTime(jb.getString("publishTime"));
					content.setParentId(jb.getString("parentId"));
					content.setDelete(jb.getBoolean("delete"));
					contents.add(content);
				}

			}

			return contents;

		}

		return null;
	}
}
