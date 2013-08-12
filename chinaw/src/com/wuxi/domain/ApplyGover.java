package com.wuxi.domain;


/**
 * 政府信息公开  依申请公开 中  各市县区依申请公开 实体类
 * @author 杨宸  智佳
 * */
public class ApplyGover {
	private boolean isNull;
	private String depId;
	private String depName;
	private String doProjectId;
	private String zhinanUrl;
	private String applyUrl;
	public boolean isNull() {
		return isNull;
	}
	public void setisNull(boolean isNull) {
		this.isNull = isNull;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDoProjectId() {
		return doProjectId;
	}
	public void setDoProjectId(String doProjectId) {
		this.doProjectId = doProjectId;
	}
	public String getZhinanUrl() {
		return zhinanUrl;
	}
	public void setZhinanUrl(String zhinanUrl) {
		this.zhinanUrl = zhinanUrl;
	}
	public String getApplyUrl() {
		return applyUrl;
	}
	public void setApplyUrl(String applyUrl) {
		this.applyUrl = applyUrl;
	}
}
