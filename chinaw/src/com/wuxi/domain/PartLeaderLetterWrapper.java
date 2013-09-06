/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PartLeaderLetterWrapper.java 
 * @包名： com.wuxi.domain 
 * @描述: TODO(用一句话描述该文件做什么) 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-5 上午10:03:53
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.util.List;

/**
 * @类名： PartLeaderLetterWrapper
 * @描述： TODO
 * @作者： 罗森
 * @创建时间： 2013 2013-9-5 上午10:03:53
 * @修改时间：
 * @修改描述：
 * 
 */
public class PartLeaderLetterWrapper extends CommonDataWrapper{

	private List<PartLeaderLetter> leaderLetters;

	/**
	 * @return leaderLetters
	 */
	public List<PartLeaderLetter> getLeaderLetters() {
		return leaderLetters;
	}

	/**
	 * @param leaderLetters
	 *            要设置的 leaderLetters
	 */
	public void setLeaderLetters(List<PartLeaderLetter> leaderLetters) {
		this.leaderLetters = leaderLetters;
	}

	public class PartLeaderLetter {

		private String id;
		private String type;
		private String code;
		private String title;
		private String depname;
		private String appraise;
		private String answerdate;
		private String readcount;

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
		 * @return answerdate
		 */
		public String getAnswerdate() {
			return answerdate;
		}

		/**
		 * @param answerdate
		 *            要设置的 answerdate
		 */
		public void setAnswerdate(String answerdate) {
			this.answerdate = answerdate;
		}

		/**
		 * @return readcount
		 */
		public String getReadcount() {
			return readcount;
		}

		/**
		 * @param readcount
		 *            要设置的 readcount
		 */
		public void setReadcount(String readcount) {
			this.readcount = readcount;
		}

	}

}
