/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 普通帖子详情数据集实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class OrdinaryPostWrapper {

	// 普通帖子ID
	private String politicsMainId;
	// 普通帖子内容
	private String content;
	// 普通帖子标题
	private String title;
	// 普通帖子状态
	private String status;
	// 普通帖子发帖人
	private String sentUser;
	// 普通帖子发帖IP
	private String sentIp;
	// 普通帖子红色标题
	private String redTitle;
	// 普通帖子修改时间
	private String modifyTime;
	// 普通帖子置顶状态
	private String isTop;
	// 普通帖子排序号
	private String orderId;
	// 普通帖子发帖时间
	private String beginTime;
	// 普通帖子截止时间
	private String endTime;
	// 普通帖子点击数
	private String readCount;
	private String doProjectId;
	// 回复列表
	private OrdinaryPostRaplyWrapper raplyWrapper;

	/**
	 * @return the raplyWrapper
	 */
	public OrdinaryPostRaplyWrapper getRaplyWrapper() {
		return raplyWrapper;
	}

	/**
	 * @param raplyWrapper
	 *            the raplyWrapper to set
	 */
	public void setRaplyWrapper(OrdinaryPostRaplyWrapper raplyWrapper) {
		this.raplyWrapper = raplyWrapper;
	}

	/**
	 * @return the politicsMainId
	 */
	public String getPoliticsMainId() {
		return politicsMainId;
	}

	/**
	 * @param politicsMainId
	 *            the politicsMainId to set
	 */
	public void setPoliticsMainId(String politicsMainId) {
		this.politicsMainId = politicsMainId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the sentUser
	 */
	public String getSentUser() {
		return sentUser;
	}

	/**
	 * @param sentUser
	 *            the sentUser to set
	 */
	public void setSentUser(String sentUser) {
		this.sentUser = sentUser;
	}

	/**
	 * @return the sentIp
	 */
	public String getSentIp() {
		return sentIp;
	}

	/**
	 * @param sentIp
	 *            the sentIp to set
	 */
	public void setSentIp(String sentIp) {
		this.sentIp = sentIp;
	}

	/**
	 * @return the redTitle
	 */
	public String getRedTitle() {
		return redTitle;
	}

	/**
	 * @param redTitle
	 *            the redTitle to set
	 */
	public void setRedTitle(String redTitle) {
		this.redTitle = redTitle;
	}

	/**
	 * @return the modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the isTop
	 */
	public String getIsTop() {
		return isTop;
	}

	/**
	 * @param isTop
	 *            the isTop to set
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the readCount
	 */
	public String getReadCount() {
		return readCount;
	}

	/**
	 * @param readCount
	 *            the readCount to set
	 */
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	/**
	 * @return the doProjectId
	 */
	public String getDoProjectId() {
		return doProjectId;
	}

	/**
	 * @param doProjectId
	 *            the doProjectId to set
	 */
	public void setDoProjectId(String doProjectId) {
		this.doProjectId = doProjectId;
	}

	/**
	 * 普通帖子回复数据集实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class OrdinaryPostRaplyWrapper extends CommonDataWrapper {

		private List<OrdinaryPostRaply> postRaplies;

		/**
		 * @return the postRaplies
		 */
		public List<OrdinaryPostRaply> getPostRaplies() {
			return postRaplies;
		}

		/**
		 * @param postRaplies
		 *            the postRaplies to set
		 */
		public void setPostRaplies(List<OrdinaryPostRaply> postRaplies) {
			this.postRaplies = postRaplies;
		}

		/**
		 * 普通帖子单条回复数据实体类
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		public class OrdinaryPostRaply {

			// 回复内容
			private String content;
			// 回复状态
			private String status;
			// 回复人
			private String userName;
			// 回复帖子ID
			private String politicsMainId;
			// 回复IP
			private String sentIp;
			// 回复时间
			private String sentTime;
			private String actorInfoId;

			/**
			 * @return the content
			 */
			public String getContent() {
				return content;
			}

			/**
			 * @param content
			 *            the content to set
			 */
			public void setContent(String content) {
				this.content = content;
			}

			/**
			 * @return the status
			 */
			public String getStatus() {
				return status;
			}

			/**
			 * @param status
			 *            the status to set
			 */
			public void setStatus(String status) {
				this.status = status;
			}

			/**
			 * @return the userName
			 */
			public String getUserName() {
				return userName;
			}

			/**
			 * @param userName
			 *            the userName to set
			 */
			public void setUserName(String userName) {
				this.userName = userName;
			}

			/**
			 * @return the politicsMainId
			 */
			public String getPoliticsMainId() {
				return politicsMainId;
			}

			/**
			 * @param politicsMainId
			 *            the politicsMainId to set
			 */
			public void setPoliticsMainId(String politicsMainId) {
				this.politicsMainId = politicsMainId;
			}

			/**
			 * @return the sentIp
			 */
			public String getSentIp() {
				return sentIp;
			}

			/**
			 * @param sentIp
			 *            the sentIp to set
			 */
			public void setSentIp(String sentIp) {
				this.sentIp = sentIp;
			}

			/**
			 * @return the sentTime
			 */
			public String getSentTime() {
				return sentTime;
			}

			/**
			 * @param sentTime
			 *            the sentTime to set
			 */
			public void setSentTime(String sentTime) {
				this.sentTime = sentTime;
			}

			/**
			 * @return the actorInfoId
			 */
			public String getActorInfoId() {
				return actorInfoId;
			}

			/**
			 * @param actorInfoId
			 *            the actorInfoId to set
			 */
			public void setActorInfoId(String actorInfoId) {
				this.actorInfoId = actorInfoId;
			}

		}
	}

}
