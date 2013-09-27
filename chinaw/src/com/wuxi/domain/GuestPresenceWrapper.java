/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GuestPresenceWrapper.java 
 * @包名： com.wuxi.domain 
 * @描述: 嘉宾风采实体类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-26 上午9:22:14
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.domain;

import java.util.List;

/**
 * @类名： GuestPresenceWrapper
 * @描述： 嘉宾风采实体类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-26 上午9:22:14
 * @修改时间：
 * @修改描述：
 */
public class GuestPresenceWrapper extends ContentWrapper {

	private List<GuestPresence> guestPresences;

	/**
	 * @return guestPresences
	 */
	public List<GuestPresence> getGuestPresences() {
		return guestPresences;
	}

	/**
	 * @param guestPresences
	 *            要设置的 guestPresences
	 */
	public void setGuestPresences(List<GuestPresence> guestPresences) {
		this.guestPresences = guestPresences;
	}

	/**
	 * @类名： GuestPresence
	 * @描述： 单条数据实体类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-26 上午9:24:41
	 * @修改时间： 
	 * @修改描述：
	 */
	public class GuestPresence extends Content {

		/**
		 * @字段： serialVersionUID
		 * @类型： long
		 * @描述： 序列化序号
		 */
		private static final long serialVersionUID = 1L;

		private String docId;
		private String recommendPictures;
		private String picturesTitle;

		/**
		 * @return docId
		 */
		public String getDocId() {
			return docId;
		}

		/**
		 * @param docId
		 *            要设置的 docId
		 */
		public void setDocId(String docId) {
			this.docId = docId;
		}

		/**
		 * @return recommendPictures
		 */
		public String getRecommendPictures() {
			return recommendPictures;
		}

		/**
		 * @param recommendPictures
		 *            要设置的 recommendPictures
		 */
		public void setRecommendPictures(String recommendPictures) {
			this.recommendPictures = recommendPictures;
		}

		/**
		 * @return picturesTitle
		 */
		public String getPicturesTitle() {
			return picturesTitle;
		}

		/**
		 * @param picturesTitle
		 *            要设置的 picturesTitle
		 */
		public void setPicturesTitle(String picturesTitle) {
			this.picturesTitle = picturesTitle;
		}

	}

}
