package com.wuxi.domain;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * 
 * @author wanglu 泰得利通 内容
 */
public class Content implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String organization;// 发布机构
	private String keyword;// 关键词
	private boolean isNull;
	private int status;// 状态
	private int browseCount;// 浏览次数
	private String title;// 标题
	private int orderId;// 顺序
	private boolean delete;// 是否逻辑删除
	private String buildTime;// 生成日期
	private String hangingParentChannelId;
	private String localUpdateDate;// 本地更新时间
	private String contentSource;// 文章来源
	private String indexId;// 索引编号
	private String openTime;// 公开日期
	private String fileNum;// 文件编号
	private String openTimeLimit;// 公开时限
	private StringTokenizer timeLimit;// 时限
	private String openForm;// 公开形式
	private String openWay;// 公开方式
	private String openLimits;// 公开范围
	private String validDate;// 文件有效期
	private String openProgram;// 公开程序
	private String fist_topicword;// 主题词 （一级）
	private String second_topicword;// 主题词(二级)
	private String genre;// 体裁
	private String typeWord;// 分类词
	private String upFile;// 文件上传 URL
	private String docSummary;// 文章摘要
	private String contentId;// 内容Id
	private String longTitle;// 长标题
	private boolean putTop;// 是否置顶
	private String wapUrl;// 绑定的WAP URL
	private boolean pushWap;// 是否发布到wap
	private String updateTime;// 更新时间
	private String parentId;// 父几点 ID
	private String publishTime;// 发布时间

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(int browseCount) {
		this.browseCount = browseCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getHangingParentChannelId() {
		return hangingParentChannelId;
	}

	public void setHangingParentChannelId(String hangingParentChannelId) {
		this.hangingParentChannelId = hangingParentChannelId;
	}

	public String getLocalUpdateDate() {
		return localUpdateDate;
	}

	public void setLocalUpdateDate(String localUpdateDate) {
		this.localUpdateDate = localUpdateDate;
	}

	public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getOpenTimeLimit() {
		return openTimeLimit;
	}

	public void setOpenTimeLimit(String openTimeLimit) {
		this.openTimeLimit = openTimeLimit;
	}

	public StringTokenizer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(StringTokenizer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getOpenForm() {
		return openForm;
	}

	public void setOpenForm(String openForm) {
		this.openForm = openForm;
	}

	public String getOpenWay() {
		return openWay;
	}

	public void setOpenWay(String openWay) {
		this.openWay = openWay;
	}

	public String getOpenLimits() {
		return openLimits;
	}

	public void setOpenLimits(String openLimits) {
		this.openLimits = openLimits;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getOpenProgram() {
		return openProgram;
	}

	public void setOpenProgram(String openProgram) {
		this.openProgram = openProgram;
	}

	public String getFist_topicword() {
		return fist_topicword;
	}

	public void setFist_topicword(String fist_topicword) {
		this.fist_topicword = fist_topicword;
	}

	public String getSecond_topicword() {
		return second_topicword;
	}

	public void setSecond_topicword(String second_topicword) {
		this.second_topicword = second_topicword;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getTypeWord() {
		return typeWord;
	}

	public void setTypeWord(String typeWord) {
		this.typeWord = typeWord;
	}

	public String getUpFile() {
		return upFile;
	}

	public void setUpFile(String upFile) {
		this.upFile = upFile;
	}

	public String getDocSummary() {
		return docSummary;
	}

	public void setDocSummary(String docSummary) {
		this.docSummary = docSummary;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getLongTitle() {
		return longTitle;
	}

	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
	}

	public boolean isPutTop() {
		return putTop;
	}

	public void setPutTop(boolean putTop) {
		this.putTop = putTop;
	}

	public String getWapUrl() {
		return wapUrl;
	}

	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

	public boolean isPushWap() {
		return pushWap;
	}

	public void setPushWap(boolean pushWap) {
		this.pushWap = pushWap;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
