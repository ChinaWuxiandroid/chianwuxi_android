package com.wuxi.domain;

public class ReplyMyLetter {
	private String id;   //信件主键
	private String type;  //信件类型  包括 咨询  求助 投诉 。。。
	private String title;  //标题
	private String content;
	private String code; //信件编号
	private String appraise;  // 评价 
	private String depname;  //部门名称
	private String answerdate;  //回复时间
	private int readcount;  //阅读次数
}
