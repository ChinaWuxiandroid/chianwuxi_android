/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: SuggestLawSearchCon.java 
 * @包名： com.wuxi.domain 
 * @描述: 政民互动 征求意见平台 立法征求意见 搜索条件实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-8 下午4:49:41
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

/**
 * @类名： SuggestLawSearchCon
 * @描述： 政民互动 征求意见平台 立法征求意见 搜索条件实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-8 下午4:49:41
 * @修改时间：
 * @修改描述：
 */
public class SuggestLawSearchCon {

	private int year;
	private int start;
	private int end;

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
