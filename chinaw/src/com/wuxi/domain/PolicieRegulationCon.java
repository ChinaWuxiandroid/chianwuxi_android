package com.wuxi.domain;

/**
 * @类名： PolicieRegulationCon
 * @描述： 市政府信息公开目录 政策法规 搜索条件实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-8 下午4:42:45
 * @修改时间： 
 * @修改描述：
 */
public class PolicieRegulationCon {

	private String dept;
	private int year;
	private int start;
	private int end;

	/**
	 * @return dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            要设置的 dept
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            要设置的 year
	 */
	public void setYear(int year) {
		this.year = year;
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

}
