package com.wuxi.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

public class PromoMoreVideoInfo {

	private Context context;

	public PromoMoreVideoInfo(Context context) {
		this.context = context;
	}

	public PromoMoreVideoInfo() {
	}

	private String id;
	private String subject;
	private String guests;
	private String workDate;
	private String subjectContent;
	private String beginTime;
	private String endTime;
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
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            要设置的 subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return guests
	 */
	public String getGuests() {
		return guests;
	}

	/**
	 * @param guests
	 *            要设置的 guests
	 */
	public void setGuests(String guests) {
		this.guests = guests;
	}

	/**
	 * @return workDate
	 */
	public String getWorkDate() {
		return workDate;
	}

	/**
	 * @param workDate
	 *            要设置的 workDate
	 */
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	/**
	 * @return subjectContent
	 */
	public String getSubjectContent() {
		return subjectContent;
	}

	/**
	 * @param subjectContent
	 *            要设置的 subjectContent
	 */
	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
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
	 *            需要解析的数据
	 * @return 返回数据集合
	 */
	public ArrayList<PromoMoreVideoInfo> resolveData(String json) {
		ArrayList<PromoMoreVideoInfo> arrayList = new ArrayList<PromoMoreVideoInfo>();
		try {
			JSONObject object = new JSONObject(json);
			boolean success = object.getBoolean("success");
			if (success) {
				String result = object.getString("result");
				JSONObject jsonObject = new JSONObject(result);
				String data = jsonObject.getString("data");
				JSONArray array = new JSONArray(data);
				for (int i = 0; i < array.length(); i++) {
					JSONObject object2 = (JSONObject) array.get(i);
					PromoMoreVideoInfo mInfo = new PromoMoreVideoInfo();
					mInfo.setBeginTime(object2.getString("beginTime"));
					mInfo.setEndTime(object2.getString("endTime"));
					mInfo.setGuests(object2.getString("guests"));
					mInfo.setId(object2.getString("id"));
					mInfo.setNext(object2.getBoolean("next"));
					mInfo.setSubject(object2.getString("subject"));
					mInfo.setSubjectContent(object2.getString("subjectContent"));
					mInfo.setWorkDate(object2.getString("workDate"));
					arrayList.add(mInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "解析数据错误", Toast.LENGTH_SHORT).show();
			arrayList = null;
		}
		return arrayList;
	}
}
