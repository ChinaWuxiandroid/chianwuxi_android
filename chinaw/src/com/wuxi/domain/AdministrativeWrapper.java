/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: AdministrativeLicenseWrapper.java 
 * @包名： com.wuxi.domain 
 * @描述: 行政事项 各个子菜单的详细数据实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-29 上午10:14:22
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.util.List;

/**
 * @类名： AdministrativeLicenseWrapper
 * @描述： 行政事项 各个子菜单的详细数据实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-29 上午10:14:22
 * @修改时间：
 * @修改描述：
 */
public class AdministrativeWrapper extends CommonDataWrapper {

	private List<GoverSaoonItem> licenses;

	/**
	 * @return licenses
	 */
	public List<GoverSaoonItem> getLicenses() {
		return licenses;
	}

	/**
	 * @param licenses
	 *            要设置的 licenses
	 */
	public void setLicenses(List<GoverSaoonItem> licenses) {
		this.licenses = licenses;
	}
}
