/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 各部门领导信箱数据集实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class PartLeaderMailWrapper {

	private List<PartLeaderMail> partLeaderMails;

	/**
	 * @return the partLeaderMails
	 */
	public List<PartLeaderMail> getPartLeaderMails() {
		return partLeaderMails;
	}

	/**
	 * @param partLeaderMails
	 *            the partLeaderMails to set
	 */
	public void setPartLeaderMails(List<PartLeaderMail> partLeaderMails) {
		this.partLeaderMails = partLeaderMails;
	}

	/**
	 * 各部门领导信箱单挑数据实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class PartLeaderMail {

		private String isnull;
		private String depid;
		private String depname;
		private String doProjectID;

		/**
		 * @return the isnull
		 */
		public String getIsnull() {
			return isnull;
		}

		/**
		 * @param isnull
		 *            the isnull to set
		 */
		public void setIsnull(String isnull) {
			this.isnull = isnull;
		}

		/**
		 * @return the depid
		 */
		public String getDepid() {
			return depid;
		}

		/**
		 * @param depid
		 *            the depid to set
		 */
		public void setDepid(String depid) {
			this.depid = depid;
		}

		/**
		 * @return the depname
		 */
		public String getDepname() {
			return depname;
		}

		/**
		 * @param depname
		 *            the depname to set
		 */
		public void setDepname(String depname) {
			this.depname = depname;
		}

		/**
		 * @return the doProjectID
		 */
		public String getDoProjectID() {
			return doProjectID;
		}

		/**
		 * @param doProjectID
		 *            the doProjectID to set
		 */
		public void setDoProjectID(String doProjectID) {
			this.doProjectID = doProjectID;
		}

	}

}
