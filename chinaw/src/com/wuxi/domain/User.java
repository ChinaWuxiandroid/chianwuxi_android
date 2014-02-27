package com.wuxi.domain;


public class User {

	/**
	 *wanglu 泰得利通 
	 *用户信息
	 * @param args
	 */
	
	private String userName;//用户名
	private String accessToken;//认证给该用户
	private String refreshToken;
	private  String expireIn;//OAuth Token有效期单位毫秒
	private String userId;//用户Id
	private String trueName;//真实姓名
	private String sex;//性别
	private String birthday;//生日
	private String industry;//从事行业
	private String marriage;//婚姻状态
	private String post;//邮政编码
	private String idcard;//身份证
	private String homeAddress;//家庭住址
	private String userHostAddress;
	private String userHostName;
	private String email;
	private String linkTel;
	private String mobile;
	private String smallTel;
	private String regTime;
	private int loginCount;
	private String lastLogin;
	private String loginIp;
	private String perProjectId;
	private String toeknCreateTime;
	private boolean mobileRegister;//是否是移动平台注册
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getExpireIn() {
		return expireIn;
	}
	public void setExpireIn(String expireIn) {
		this.expireIn = expireIn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getUserHostAddress() {
		return userHostAddress;
	}
	public void setUserHostAddress(String userHostAddress) {
		this.userHostAddress = userHostAddress;
	}
	public String getUserHostName() {
		return userHostName;
	}
	public void setUserHostName(String userHostName) {
		this.userHostName = userHostName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSmallTel() {
		return smallTel;
	}
	public void setSmallTel(String smallTel) {
		this.smallTel = smallTel;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getPerProjectId() {
		return perProjectId;
	}
	public void setPerProjectId(String perProjectId) {
		this.perProjectId = perProjectId;
	}
	public String getToeknCreateTime() {
		return toeknCreateTime;
	}
	public void setToeknCreateTime(String toeknCreateTime) {
		this.toeknCreateTime = toeknCreateTime;
	}
	public boolean isMobileRegister() {
		return mobileRegister;
	}
	public void setMobileRegister(boolean mobileRegister) {
		this.mobileRegister = mobileRegister;
	}
	
	
	
	
}
