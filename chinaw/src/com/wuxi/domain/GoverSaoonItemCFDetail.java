package com.wuxi.domain;


/**
 * 
 * @author wanglu 泰得利通
 * 办件  行政处罚
 *
 */
public class GoverSaoonItemCFDetail {

	private String address;//办公地址
	private String cfbz;//处罚标准
	private String cfyj;//处罚依据
	private String sszt;//实施主体
	private String ssztbm;//实施主体编码
	private  String ssztxz;//实施主体性质
	private String wtjg;
	private String itemcode;//事项编码
	private String supertel;//监督电话
	private String lcfileid;//征收流程
	private String name;
	private String id;
	private String deptid;
	private String deptname;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCfbz() {
		return cfbz;
	}
	public void setCfbz(String cfbz) {
		this.cfbz = cfbz;
	}
	public String getCfyj() {
		return cfyj;
	}
	public void setCfyj(String cfyj) {
		this.cfyj = cfyj;
	}
	public String getSszt() {
		return sszt;
	}
	public void setSszt(String sszt) {
		this.sszt = sszt;
	}
	public String getSsztbm() {
		return ssztbm;
	}
	public void setSsztbm(String ssztbm) {
		this.ssztbm = ssztbm;
	}
	public String getSsztxz() {
		if(ssztxz.equals("1")){
			return "法定";
		}else{
			return ssztxz;
		}
		
	}
	public void setSsztxz(String ssztxz) {
		this.ssztxz = ssztxz;
	}
	public String getWtjg() {
		return wtjg;
	}
	public void setWtjg(String wtjg) {
		this.wtjg = wtjg;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getSupertel() {
		return supertel;
	}
	public void setSupertel(String supertel) {
		this.supertel = supertel;
	}
	public String getLcfileid() {
		return lcfileid;
	}
	public void setLcfileid(String lcfileid) {
		this.lcfileid = lcfileid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	
	
}
