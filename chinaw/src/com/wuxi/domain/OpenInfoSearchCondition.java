/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: OpenInfoSearchCondition.java 
 * @包名： com.wuxi.domain 
 * @描述: 政府信息公开 市政府信息公开目录 频道搜索列表条件实体类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-28 上午9:23:29
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

/**
 * @类名： OpenInfoSearchCondition
 * @描述： 政府信息公开 市政府信息公开目录 频道搜索列表条件实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-28 上午9:23:29
 * @修改时间：
 * @修改描述：
 */
public class OpenInfoSearchCondition {

	private int start;
	private int end;
	private String dept;
	private String year;

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
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            要设置的 year
	 */
	public void setYear(String year) {
		this.year = year;
	}

}
