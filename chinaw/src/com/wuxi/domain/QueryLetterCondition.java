/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: QueryLetterCondition.java 
 * @包名： com.wuxi.domain 
 * @描述: 信件查询条件实体类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-16 上午8:59:04
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

/**
 * @类名： QueryLetterCondition
 * @描述： 信件查询条件实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-16 上午8:59:04
 * @修改时间：
 * @修改描述：
 */
public class QueryLetterCondition {

	private String keyword;
	private String contenttype;
	private String lettertype;
	private long starttime;
	private long endtime;
	private String code;
	private int common; // 常见问题：-1->选中；1->未选中
	private String depid;
	private String dodepid;

	/**
	 * @return depid
	 */
	public String getDepid() {
		return depid;
	}

	/**
	 * @param depid
	 *            要设置的 depid
	 */
	public void setDepid(String depid) {
		this.depid = depid;
	}

	/**
	 * @return dodepid
	 */
	public String getDodepid() {
		return dodepid;
	}

	/**
	 * @param dodepid
	 *            要设置的 dodepid
	 */
	public void setDodepid(String dodepid) {
		this.dodepid = dodepid;
	}

	/**
	 * @return keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            要设置的 keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return contenttype
	 */
	public String getContenttype() {
		return contenttype;
	}

	/**
	 * @param contenttype
	 *            要设置的 contenttype
	 */
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	/**
	 * @return lettertype
	 */
	public String getLettertype() {
		return lettertype;
	}

	/**
	 * @param lettertype
	 *            要设置的 lettertype
	 */
	public void setLettertype(String lettertype) {
		this.lettertype = lettertype;
	}

	/**
	 * @return starttime
	 */
	public long getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *            要设置的 starttime
	 */
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return endtime
	 */
	public long getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            要设置的 endtime
	 */
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            要设置的 code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return common
	 */
	public int getCommon() {
		return common;
	}

	/**
	 * @param common
	 *            要设置的 common
	 */
	public void setCommon(int common) {
		this.common = common;
	}

}
