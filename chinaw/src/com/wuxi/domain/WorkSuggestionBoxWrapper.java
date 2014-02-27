package com.wuxi.domain;


import java.util.List;

public class WorkSuggestionBoxWrapper {
	private String id;  //主键ID
	private List<MailBoxParameterItem> parameters;  //参数列表
	private String formId;   //项目ID
	private String formTitle;  //表单的标题
	private String formDesc;   //表单的描述
	private int sendType;     //提交方式（0—支持匿名提交，1—登录提交)
	private int linkDept;    //是否关联部门（0—不关联，1—关联）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MailBoxParameterItem> getParameters() {
		return parameters;
	}

	public void setParameters(List<MailBoxParameterItem> parameters) {
		this.parameters = parameters;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}

	public int getLinkDept() {
		return linkDept;
	}

	public void setLinkDept(int linkDept) {
		this.linkDept = linkDept;
	}
}
