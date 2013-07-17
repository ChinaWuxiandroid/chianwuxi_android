package com.wuxi.app.util;

public class MyLetter {
	private String access_token;    //OAuth Token
	private String doprojectid;     //提交信箱的项目编号   市长信箱：6b8e124e-1e5c-4a11-8dd3-c6623c809eff 
	//                 建议咨询投诉:bfffa273-086a-47cb-a7a8-7ae8140550db
	private String typeid="1";        //咨询类型 （1:咨询 2:求助 3:建议 4:投诉 5:举报 6:表扬 7:其他）
	private String title; 
	private String content;
	private int openState=1;      //内容是否公开
	private int sentMailBack=0;   //是否邮件回复
	private int msgStatus=0;    //是否短信回复

	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getDoprojectid() {
		return doprojectid;
	}
	public void setDoprojectid(String doprojectid) {
		this.doprojectid = doprojectid;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOpenState() {
		return openState;
	}
	public void setOpenState(int openState) {
		this.openState = openState;
	}
	public int getSentMailBack() {
		return sentMailBack;
	}
	public void setSentMailBack(int sentMailBack) {
		this.sentMailBack = sentMailBack;
	}
	public int getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}
}
