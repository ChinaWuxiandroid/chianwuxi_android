/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: AdministrativeCon.java 
 * @包名： com.wuxi.domain 
 * @描述: 市政府信息公开目录 行政事项 搜索条件实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-8 下午2:01:29
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

/**
 * @类名： AdministrativeCon
 * @描述： 市政府信息公开目录 行政事项 搜索条件实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-8 下午2:01:29
 * @修改时间：
 * @修改描述：
 */
public class AdministrativeCon {

	// 部门ID
	private String id;
	// 年份
	private int year;
	// 开始检索数
	private int start;
	// 结束检索数
	private int end;
	// 类型
	private String qltype;

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

	/**
	 * @return qltype
	 */
	public String getQltype() {
		return qltype;
	}

	/**
	 * @param qltype
	 *            要设置的 qltype
	 */
	public void setQltype(String qltype) {
		this.qltype = qltype;
	}

}
