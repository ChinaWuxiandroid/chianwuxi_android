/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: OpenInfoDept.java 
 * @包名： com.wuxi.domain 
 * @描述:  政府信息公开 部门下拉框 数据实体类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-25 下午2:39:48
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.io.Serializable;

/**
 * @类名： OpenInfoDept
 * @描述： 政府信息公开 部门下拉框 数据实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-25 下午2:39:48
 * @修改时间：
 * @修改描述：
 */
public class OpenInfoDept implements Serializable {

	/**
	 * @字段： serialVersionUID
	 * @类型： long
	 * @描述： 序列号
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String id;
	private String isnull;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return isnull
	 */
	public String getIsnull() {
		return isnull;
	}

	/**
	 * @param isnull
	 *            要设置的 isnull
	 */
	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

}
