/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MailTypeWrapper.java 
 * @包名： com.wuxi.domain 
 * @描述: 信件类型实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-27 下午5:17:49
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.util.List;

/**
 * @类名： MailTypeWrapper
 * @描述： 信件类型实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-8-27 下午5:17:49
 * @修改时间：
 * @修改描述：
 * 
 */
public class MailTypeWrapper {

	private List<MailType> mailTypes;

	/**
	 * @类名： MailType
	 * @描述： 单个信件类型实体类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-8-27 下午5:19:20
	 * @修改时间： 
	 * @修改描述： 
	 *
	 */
	public class MailType {

		private String isnull;
		private String typename;
		private String typeid;

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

		/**
		 * @return typename
		 */
		public String getTypename() {
			return typename;
		}

		/**
		 * @param typename
		 *            要设置的 typename
		 */
		public void setTypename(String typename) {
			this.typename = typename;
		}

		/**
		 * @return typeid
		 */
		public String getTypeid() {
			return typeid;
		}

		/**
		 * @param typeid
		 *            要设置的 typeid
		 */
		public void setTypeid(String typeid) {
			this.typeid = typeid;
		}

	}

	/**
	 * @return mailTypes
	 */
	public List<MailType> getMailTypes() {
		return mailTypes;
	}

	/**
	 * @param mailTypes
	 *            要设置的 mailTypes
	 */
	public void setMailTypes(List<MailType> mailTypes) {
		this.mailTypes = mailTypes;
	}

}
