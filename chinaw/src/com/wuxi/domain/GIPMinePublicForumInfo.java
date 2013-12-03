package com.wuxi.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GIPMinePublicForumInfo {

	private String politicsMainID;
	private String title;
	private String readCount;
	private String top;
	private String sentUser;
	private String doProjectID;
	private String dataNo;
	private String begintime;
	private String viewpath;
	private String resultCount;
	private String end;
	private String start;
	private String previous;
	private String totalRowsAmount;
	private boolean next;
	private String createDate;

	/**
	 * @return politicsMainID
	 */
	public String getPoliticsMainID() {
		return politicsMainID;
	}

	/**
	 * @param politicsMainID
	 *            要设置的 politicsMainID
	 */
	public void setPoliticsMainID(String politicsMainID) {
		this.politicsMainID = politicsMainID;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            要设置的 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return readCount
	 */
	public String getReadCount() {
		return readCount;
	}

	/**
	 * @param readCount
	 *            要设置的 readCount
	 */
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	/**
	 * @return top
	 */
	public String getTop() {
		return top;
	}

	/**
	 * @param top
	 *            要设置的 top
	 */
	public void setTop(String top) {
		this.top = top;
	}

	/**
	 * @return sentUser
	 */
	public String getSentUser() {
		return sentUser;
	}

	/**
	 * @param sentUser
	 *            要设置的 sentUser
	 */
	public void setSentUser(String sentUser) {
		this.sentUser = sentUser;
	}

	/**
	 * @return doProjectID
	 */
	public String getDoProjectID() {
		return doProjectID;
	}

	/**
	 * @param doProjectID
	 *            要设置的 doProjectID
	 */
	public void setDoProjectID(String doProjectID) {
		this.doProjectID = doProjectID;
	}

	/**
	 * @return dataNo
	 */
	public String getDataNo() {
		return dataNo;
	}

	/**
	 * @param dataNo
	 *            要设置的 dataNo
	 */
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}

	/**
	 * @return begintime
	 */
	public String getBegintime() {
		return begintime;
	}

	/**
	 * @param begintime
	 *            要设置的 begintime
	 */
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	/**
	 * @return viewpath
	 */
	public String getViewpath() {
		return viewpath;
	}

	/**
	 * @param viewpath
	 *            要设置的 viewpath
	 */
	public void setViewpath(String viewpath) {
		this.viewpath = viewpath;
	}

	/**
	 * @return resultCount
	 */
	public String getResultCount() {
		return resultCount;
	}

	/**
	 * @param resultCount
	 *            要设置的 resultCount
	 */
	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}

	/**
	 * @return end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            要设置的 end
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            要设置的 start
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return previous
	 */
	public String getPrevious() {
		return previous;
	}

	/**
	 * @param previous
	 *            要设置的 previous
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	/**
	 * @return totalRowsAmount
	 */
	public String getTotalRowsAmount() {
		return totalRowsAmount;
	}

	/**
	 * @param totalRowsAmount
	 *            要设置的 totalRowsAmount
	 */
	public void setTotalRowsAmount(String totalRowsAmount) {
		this.totalRowsAmount = totalRowsAmount;
	}

	/**
	 * @return next
	 */
	public boolean isNext() {
		return next;
	}

	/**
	 * @param next
	 *            要设置的 next
	 */
	public void setNext(boolean next) {
		this.next = next;
	}

	/**
	 * @return createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            要设置的 createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 解析数据
	 * 
	 * @方法： resolveData
	 * @param 需要解析的数据
	 * @return
	 */
	public ArrayList<GIPMinePublicForumInfo> resolveData(String json) {

		ArrayList<GIPMinePublicForumInfo> arrayList = new ArrayList<GIPMinePublicForumInfo>();

		try {
			JSONObject object = new JSONObject(json);
			boolean success = object.getBoolean("success");
			if (success) {

				String createDate = object.getString("createDate");
				String result = object.getString("result");

				JSONObject object2 = new JSONObject(result);

				String end = object2.getString("end");
				String start = object2.getString("start");
				String previous = object2.getString("previous");
				String totalRowsAmount = object2.getString("totalRowsAmount");
				boolean next = object2.getBoolean("next");
				String data = object2.getString("data");

				JSONArray array = new JSONArray(data);
				for (int i = 0; i < array.length(); i++) {
					JSONObject object3 = (JSONObject) array.get(i);

					String title = object3.getString("title");
					String politicsMainID = object3.getString("politicsMainID");
					String readCount = object3.getString("readCount");
					String sentUser = object3.getString("sentUser");
					String top = object3.getString("top");
					String doProjectID = object3.getString("doProjectID");
					String dataNo = object3.getString("dataNo");
					String begintime = object3.getString("begintime");
					String viewpath = object3.getString("viewpath");
					String resultCount = object3.getString("resultCount");

					GIPMinePublicForumInfo mInfo = new GIPMinePublicForumInfo();

					mInfo.setBegintime(begintime);
					mInfo.setCreateDate(createDate);
					mInfo.setDataNo(dataNo);
					mInfo.setDoProjectID(doProjectID);
					mInfo.setEnd(end);
					mInfo.setNext(next);
					mInfo.setPoliticsMainID(politicsMainID);
					mInfo.setPrevious(previous);
					mInfo.setReadCount(readCount);
					mInfo.setResultCount(resultCount);
					mInfo.setSentUser(sentUser);
					mInfo.setStart(start);
					mInfo.setTitle(title);
					mInfo.setTop(top);
					mInfo.setTotalRowsAmount(totalRowsAmount);
					mInfo.setViewpath(viewpath);

					arrayList.add(mInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
}
