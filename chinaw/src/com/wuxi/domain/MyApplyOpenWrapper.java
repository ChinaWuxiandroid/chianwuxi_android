/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MyApplyOpenWrapper.java 
 * @包名： com.wuxi.domain 
 * @描述: 我的依申请公开数据集实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-15 上午11:17:59
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @类名： MyApplyOpenWrapper
 * @描述： 我的依申请公开数据集实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-10-15 上午11:17:59
 * @修改时间：
 * @修改描述：
 */
public class MyApplyOpenWrapper extends CommonDataWrapper {

	private List<MyApplyOpen> myApplyOpens;

	/**
	 * @return myApplyOpens
	 */
	public List<MyApplyOpen> getMyApplyOpens() {
		return myApplyOpens;
	}

	/**
	 * @param myApplyOpens
	 *            要设置的 myApplyOpens
	 */
	public void setMyApplyOpens(List<MyApplyOpen> myApplyOpens) {
		this.myApplyOpens = myApplyOpens;
	}

	/**
	 * @类名： MyApplyOpen
	 * @描述： 我的依申请公开实体类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-15 上午11:20:57
	 * @修改时间：
	 * @修改描述：
	 */
	public class MyApplyOpen implements Serializable {

		/**
		 * @字段： serialVersionUID
		 * @类型： long
		 * @描述： 序列号
		 */
		private static final long serialVersionUID = 1L;

		private String id;
		private String content;
		private String code;
		private String title;
		private String applyDate;
		private int readCount;
		private String answerDep;
		private String answerDate;
		private String answerUser;
		private String answerContent;

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
		 * @return applyDate
		 */
		public String getApplyDate() {
			return applyDate;
		}

		/**
		 * @param applyDate
		 *            要设置的 applyDate
		 */
		public void setApplyDate(String applyDate) {
			this.applyDate = applyDate;
		}

		/**
		 * @return readCount
		 */
		public int getReadCount() {
			return readCount;
		}

		/**
		 * @param readCount
		 *            要设置的 readCount
		 */
		public void setReadCount(int readCount) {
			this.readCount = readCount;
		}

		/**
		 * @return answerDep
		 */
		public String getAnswerDep() {
			return answerDep;
		}

		/**
		 * @param answerDep
		 *            要设置的 answerDep
		 */
		public void setAnswerDep(String answerDep) {
			this.answerDep = answerDep;
		}

		/**
		 * @return answerDate
		 */
		public String getAnswerDate() {
			return answerDate;
		}

		/**
		 * @param answerDate
		 *            要设置的 answerDate
		 */
		public void setAnswerDate(String answerDate) {
			this.answerDate = answerDate;
		}

		/**
		 * @return answerUser
		 */
		public String getAnswerUser() {
			return answerUser;
		}

		/**
		 * @param answerUser
		 *            要设置的 answerUser
		 */
		public void setAnswerUser(String answerUser) {
			this.answerUser = answerUser;
		}

		/**
		 * @return answerContent
		 */
		public String getAnswerContent() {
			return answerContent;
		}

		/**
		 * @param answerContent
		 *            要设置的 answerContent
		 */
		public void setAnswerContent(String answerContent) {
			this.answerContent = answerContent;
		}

	}

}
