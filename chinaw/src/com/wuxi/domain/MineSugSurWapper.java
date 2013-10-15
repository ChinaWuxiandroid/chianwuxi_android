/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MineSugSurWapper.java 
 * @包名： com.wuxi.domain 
 * @描述: 我的网上调查数据集实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-14 下午4:52:54
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @类名： MineSugSurWapper
 * @描述： 我的网上调查数据集实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-14 下午4:52:54
 * @修改时间：
 * @修改描述：
 */
public class MineSugSurWapper extends CommonDataWrapper {

	private List<MineSugSur> mineSugSurs;

	/**
	 * @return mineSugSurs
	 */
	public List<MineSugSur> getMineSugSurs() {
		return mineSugSurs;
	}

	/**
	 * @param mineSugSurs
	 *            要设置的 mineSugSurs
	 */
	public void setMineSugSurs(List<MineSugSur> mineSugSurs) {
		this.mineSugSurs = mineSugSurs;
	}

	/**
	 * @类名： MineSugSur
	 * @描述： 我的网上调查实体类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-14 下午4:59:11
	 * @修改时间： 
	 * @修改描述：
	 */
	public class MineSugSur implements Serializable {

		/**
		 * @字段： serialVersionUID
		 * @类型： long
		 * @描述： 序列号
		 */
		private static final long serialVersionUID = 1L;

		private String id;
		private String title;
		private String starttime;
		private String endtime;
		private String replydept;
		private String surveryID;

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
		 * @return starttime
		 */
		public String getStarttime() {
			return starttime;
		}

		/**
		 * @param starttime
		 *            要设置的 starttime
		 */
		public void setStarttime(String starttime) {
			this.starttime = starttime;
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
		 * @return replydept
		 */
		public String getReplydept() {
			return replydept;
		}

		/**
		 * @param replydept
		 *            要设置的 replydept
		 */
		public void setReplydept(String replydept) {
			this.replydept = replydept;
		}

		/**
		 * @return surveryID
		 */
		public String getSurveryID() {
			return surveryID;
		}

		/**
		 * @param surveryID
		 *            要设置的 surveryID
		 */
		public void setSurveryID(String surveryID) {
			this.surveryID = surveryID;
		}

	}

}
