/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GIPMailInfoWrapper.java 
 * @包名： com.wuxi.domain 
 * @描述: 信件的详细内容实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-22 下午4:02:09
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

/**
 * @类名： GIPMailInfoWrapper
 * @描述： 信件的详细内容实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-8-22 下午4:02:09
 * @修改时间：
 * @修改描述：
 * 
 */
public class GIPMailInfoWrapper {

	// 信件主键
	private String id;
	// 信件类型
	private String type;
	// 信件内容
	private String content;
	// 答复内容
	private String result;
	// 信件编号
	private String code;
	// 标题
	private String title;
	// 提问部门
	private String depname;
	// 评价
	private String appraise;
	// 提问时间
	private String begintime;
	// 答复时间
	private String endtime;
	// 项目主键
	private String doprojectid;
	// 答复部门
	private String dodepname;

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
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            要设置的 result
	 */
	public void setResult(String result) {
		this.result = result;
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
	 * @return depname
	 */
	public String getDepname() {
		return depname;
	}

	/**
	 * @param depname
	 *            要设置的 depname
	 */
	public void setDepname(String depname) {
		this.depname = depname;
	}

	/**
	 * @return appraise
	 */
	public String getAppraise() {
		return appraise;
	}

	/**
	 * @param appraise
	 *            要设置的 appraise
	 */
	public void setAppraise(String appraise) {
		this.appraise = appraise;
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
	 * @return endtime
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            要设置的 endtime
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return doprojectid
	 */
	public String getDoprojectid() {
		return doprojectid;
	}

	/**
	 * @param doprojectid
	 *            要设置的 doprojectid
	 */
	public void setDoprojectid(String doprojectid) {
		this.doprojectid = doprojectid;
	}

	/**
	 * @return dodepname
	 */
	public String getDodepname() {
		return dodepname;
	}

	/**
	 * @param dodepname
	 *            要设置的 dodepname
	 */
	public void setDodepname(String dodepname) {
		this.dodepname = dodepname;
	}

}
