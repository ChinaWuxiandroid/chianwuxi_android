package com.wuxi.domain;

import java.util.Date;

/**
 * 
 * 政民互动---视频直播平台----节目预告
 * 
 * @类名： VideoPreviewInfo
 * @描述： TODO
 * @作者： 罗森
 * @创建时间： 2013 2013-11-14 下午4:38:08
 * @修改时间：
 * @修改描述：
 */
public class VideoPreviewInfo {

	private String id;// 直播编号
	private String subject;// 主题
	private String guests;// 嘉宾
	private String workDate;// 访谈时间
	private String subjectContent;// 主要内容
	private Date beginTime;// 开始时间
	private Date endTime;// 结束时间
	private int end;// 最后行数
	private int start;// 开始行数
	private boolean previous;// 前面是否还有内容
	private long totalRowsAmount;// 总行数
	private boolean next;// 是否有下一行

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
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            要设置的 beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            要设置的 endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            要设置的 end
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @return start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start
	 *            要设置的 start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return previous
	 */
	public boolean isPrevious() {
		return previous;
	}

	/**
	 * @param previous
	 *            要设置的 previous
	 */
	public void setPrevious(boolean previous) {
		this.previous = previous;
	}

	/**
	 * @return totalRowsAmount
	 */
	public long getTotalRowsAmount() {
		return totalRowsAmount;
	}

	/**
	 * @param totalRowsAmount
	 *            要设置的 totalRowsAmount
	 */
	public void setTotalRowsAmount(long totalRowsAmount) {
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

}
