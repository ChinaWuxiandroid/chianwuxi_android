package com.wuxi.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我的信息公开意见实体类
 * 
 * @类名： MyOpinionOpenWrapper
 * @作者： 陈彬
 * @创建时间： 2013 2013-11-22 下午4:20:10
 * @修改时间：
 * @修改描述：
 */
public class MyOpinionOpenWrapper {

	private String id;
	private String state;
	private String content;
	private String username;
	private String title;
	private String answer;
	private String deptid;
	private String deptname;
	private String sendTime;
	private String doTime;
	private String useremail;
	private String usertel;
	private String searchkey;
	private boolean next;

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
	 * @return state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            要设置的 state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            要设置的 content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            要设置的 username
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            要设置的 answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return deptid
	 */
	public String getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid
	 *            要设置的 deptid
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * @return deptname
	 */
	public String getDeptname() {
		return deptname;
	}

	/**
	 * @param deptname
	 *            要设置的 deptname
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	/**
	 * @return sendTime
	 */
	public String getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            要设置的 sendTime
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return doTime
	 */
	public String getDoTime() {
		return doTime;
	}

	/**
	 * @param doTime
	 *            要设置的 doTime
	 */
	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}

	/**
	 * @return useremail
	 */
	public String getUseremail() {
		return useremail;
	}

	/**
	 * @param useremail
	 *            要设置的 useremail
	 */
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	/**
	 * @return usertel
	 */
	public String getUsertel() {
		return usertel;
	}

	/**
	 * @param usertel
	 *            要设置的 usertel
	 */
	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	/**
	 * @return searchkey
	 */
	public String getSearchkey() {
		return searchkey;
	}

	/**
	 * @param searchkey
	 *            要设置的 searchkey
	 */
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	/**
	 * 解析数据
	 * 
	 * @方法： getMyOpinionOpenWrapper
	 * @param json
	 *            需要接续的数据
	 * @return
	 */
	public ArrayList<MyOpinionOpenWrapper> getMyOpinionOpenWrapper(String json,
			boolean next) {

		ArrayList<MyOpinionOpenWrapper> mArrayList = new ArrayList<MyOpinionOpenWrapper>();

		JSONArray array = null;
		try {

			array = new JSONArray(json);

			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				MyOpinionOpenWrapper mWrapper = new MyOpinionOpenWrapper();
				mWrapper.setId(object.getString("id"));
				mWrapper.setState(object.getString("state"));
				mWrapper.setContent(object.getString("content"));
				mWrapper.setUsername(object.getString("username"));
				mWrapper.setTitle(object.getString("title"));
				mWrapper.setAnswer(object.getString("answer"));
				mWrapper.setDeptid(object.getString("deptid"));
				mWrapper.setDeptname(object.getString("deptname"));
				mWrapper.setSendTime(object.getString("sendTime"));
				mWrapper.setDoTime(object.getString("doTime"));
				mWrapper.setUseremail(object.getString("useremail"));
				mWrapper.setUsertel(object.getString("usertel"));
				mWrapper.setSearchkey(object.getString("searchkey"));
				mWrapper.setNext(next);
				mArrayList.add(mWrapper);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mArrayList;
	}
}
