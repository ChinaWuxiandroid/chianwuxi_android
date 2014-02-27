/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: QueryMailContentType.java 
 * @包名： com.wuxi.domain 
 * @描述: 信件查询内容类型实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-27 下午4:12:27
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.util.List;

/**
 * @类名： QueryMailContentType
 * @描述： 信件查询内容类型实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-8-27 下午4:12:27
 * @修改时间：
 * @修改描述：
 * 
 */
public class QueryMailContentTypeWrapper {

	private List<QueryMailContentType> contentTypes;

	/**
	 * @类名： QueryMailContentType
	 * @描述： 信件查询单个内容类型实体类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-8-27 下午4:23:42
	 * @修改时间： 
	 * @修改描述： 
	 *
	 */
	public class QueryMailContentType {

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
	 * @return contentTypes
	 */
	public List<QueryMailContentType> getContentTypes() {
		return contentTypes;
	}

	/**
	 * @param contentTypes
	 *            要设置的 contentTypes
	 */
	public void setContentTypes(List<QueryMailContentType> contentTypes) {
		this.contentTypes = contentTypes;
	}

}
