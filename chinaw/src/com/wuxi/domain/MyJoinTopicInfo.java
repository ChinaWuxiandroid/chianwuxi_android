package com.wuxi.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyJoinTopicInfo {

	private String id;
	private String endTime;
	private String title;
	private String beginTime;
	private String createDate;
	private boolean next;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            要设置的 endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	 * @return beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            要设置的 beginTime
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
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
	 * 解析数据
	 * 
	 * @方法： resolveData
	 * @param json
	 * @param next
	 * @param time
	 * @return
	 */
	public ArrayList<MyJoinTopicInfo> resolveData(String json) {

		ArrayList<MyJoinTopicInfo> arrayList = new ArrayList<MyJoinTopicInfo>();

		try {
			JSONObject object = new JSONObject(json);
			boolean success = object.getBoolean("success");
			if (success) {
				String createDate = object.getString("createDate");
				String result = object.getString("result");

				JSONObject object2 = new JSONObject(result);
				boolean next = object2.getBoolean("next");
				String data = object2.getString("data");

				JSONArray array = new JSONArray(data);
				for (int i = 0; i < array.length(); i++) {
					JSONObject object3 = (JSONObject) array.get(i);
					String id = object3.getString("id");
					String endTime = object3.getString("endTime");
					String title = object3.getString("title");
					String beginTime = object3.getString("beginTime");

					MyJoinTopicInfo mInfo = new MyJoinTopicInfo();
					mInfo.setBeginTime(beginTime);
					mInfo.setCreateDate(createDate);
					mInfo.setEndTime(endTime);
					mInfo.setId(id);
					mInfo.setNext(next);
					mInfo.setTitle(title);

					arrayList.add(mInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
}
