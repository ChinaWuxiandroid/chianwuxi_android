package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.AdvancedSearchUtil;
import com.wuxi.domain.SearchResultWrapper;
import com.wuxi.domain.SearchResultWrapper.SearchPage;
import com.wuxi.domain.SearchResultWrapper.SearchResult;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 全局搜索 业务类
 * 
 * @author 杨宸 智佳
 * */

public class SearchService extends Service {

	public SearchService(Context context) {
		super(context);
	}

	/**
	 * 普通搜索
	 * 
	 * @param query
	 * @param sitename
	 * @param countperpage
	 * @param pagenum
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 * 
	 * */
	public SearchResultWrapper getNormalSearchResult(String query,
			String sitename, int countperpage, int pagenum)
			throws NetException, JSONException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.SEARCH_URL.replace("{query}", query)
				.replace("{sitename}", sitename)
				.replace("{countperpage}", countperpage + "")
				.replace("{pagenum}", pagenum + "");

		String resultStr = httpUtils.executeGetToStringGBK(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			SearchResultWrapper searchResultWrapper = new SearchResultWrapper();

			// 初始化 page 类
			JSONObject jpage = jsonObject.getJSONObject("page");
			SearchPage page = searchResultWrapper.new SearchPage();
			page.setPagesize(jpage.getString("pagesize"));
			page.setReqstatus(jpage.getString("reqstatus"));
			page.setSitename(jpage.getString("sitename"));
			page.setQuery(jpage.getString("query"));
			page.setHitcount(jpage.getString("hitcount"));
			page.setSearchtime(jpage.getString("searchtime"));
			page.setCurrentpage(jpage.getString("currentpage"));
			searchResultWrapper.setPage(page);

			// 初始化 List<SearchResult>
			JSONArray jresults = jsonObject.getJSONArray("results");
			if (jresults != null) {
				searchResultWrapper.setResults(parseData(jresults));// 解析数组
			}

			return searchResultWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * 高级搜索
	 * 
	 * @param searchUtil
	 *            高级搜索参数实体类
	 * @param sitename
	 * @param countperpage
	 * @param pagenum
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 * 
	 * */
	public SearchResultWrapper getAdvancedSearchResult(
			AdvancedSearchUtil searchUtil, String sitename, int pagenum)
			throws NetException, JSONException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		String url = Constants.Urls.SEARCH_URL
				.replace("{query}", searchUtil.getKeyWord())
				.replace("{sitename}", sitename)
				.replace("{countperpage}", searchUtil.getPageSize() + "")
				.replace("{pagenum}", pagenum + "");

		if (!searchUtil.getInfoType().equals("全部"))
			url = url + "&field.key_kinds=" + searchUtil.getInfoType();
			url = url + "&DateSearchType=" + searchUtil.getDateSearchType()
				+ "&FromDate=" + searchUtil.getBeginDate() + "&ToDate="
				+ searchUtil.getEndDate();

		if (!searchUtil.getContentType().equals("全部"))
			url = url + "&searchType=" + searchUtil.getContentType();

		System.out.println("url:" + url);
		String resultStr = httpUtils.executeGetToStringGBK(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			SearchResultWrapper searchResultWrapper = new SearchResultWrapper();

			// 初始化 page 类
			JSONObject jpage = jsonObject.getJSONObject("page");
			SearchPage page = searchResultWrapper.new SearchPage();
			page.setPagesize(jpage.getString("pagesize"));
			page.setReqstatus(jpage.getString("reqstatus"));
			page.setSitename(jpage.getString("sitename"));
			page.setQuery(jpage.getString("query"));
			page.setHitcount(jpage.getString("hitcount"));
			page.setSearchtime(jpage.getString("searchtime"));
			page.setCurrentpage(jpage.getString("currentpage"));
			searchResultWrapper.setPage(page);

			// 初始化 List<SearchResult>
			JSONArray jresults = jsonObject.getJSONArray("results");
			if (jresults != null) {
				searchResultWrapper.setResults(parseData(jresults));// 解析数组
			}

			return searchResultWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * 
	 * @param jData
	 * @return
	 * @throws JSONException
	 */

	private List<SearchResult> parseData(JSONArray jData) throws JSONException {
		if (jData != null) {
			List<SearchResult> results = new ArrayList<SearchResult>();
			SearchResultWrapper wrapper = new SearchResultWrapper();
			for (int index = 0; index < jData.length(); index++) {
				JSONObject jb = jData.getJSONObject(index);
				SearchResult result = wrapper.new SearchResult();
				result.setDocid(jb.getString("docid"));
				result.setTitle(jb.getString("title"));
				result.setSummarycontent(jb.getString("summarycontent"));
				result.setLink(jb.getString("link"));
				result.setScore(jb.getString("score"));
				result.setModifiedday(jb.getString("modifiedday"));
				result.setSize(jb.getString("size"));
				results.add(result);
			}
			return results;
		}
		return null;
	}
}
