package com.wuxi.domain;

import java.util.List;

/**
 * 
 * @author wanglu 泰得利通 办件类行其它类型
 * 
 */
public class GoverSaoonItemQTDetail {

	private String bbh;
	private String bm;// 事项编码
	private String jc;// 简称
	private String ljdz;
	private String sfsbssp;
	private String sszt;// 实施主体名称
	private String ssztbm;// 实施主体编码
	private String ssztxz;// 实施主体性质
	private String wtjg;
	private String xzqh;
	private String bldate;
	private String bslc;// 公示流程
	private String cbm;
	private String cnsj;// 承诺时限
	private String cnsjms;
	private String fdcnsj;
	private String flfg;// 法律法规
	private String fwzn;// 服务指南
	private String isfz;// 是否发证
	private String issf;// 是否收费
	private String iswssb;
	private String jdjg;// 决定机关
	private String lxdh;// 联系电话
	private String qtbldd;// 其他办理地点
	private String sfbz;
	private String sfjbj;// 是否即办件
	private String sltj;// 受理条件
	private String sqsljg;// 受理机关
	private String xzfwzxbl;// 行政服务中心办理
	private List<GoverMaterials> goverMaterials;// 申请材料
	private String name;// 名称，ID
	private String id;
	private String deptid;
	private String deptname;

	public String getBbh() {
		return bbh;
	}

	public void setBbh(String bbh) {
		this.bbh = bbh;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getLjdz() {
		return ljdz;
	}

	public void setLjdz(String ljdz) {
		this.ljdz = ljdz;
	}

	public String getSfsbssp() {
		return sfsbssp;
	}

	public void setSfsbssp(String sfsbssp) {
		this.sfsbssp = sfsbssp;
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
		if (ssztxz.equals("1")) {
			return "法定";
		} else {
			return ssztxz;
		}

	}

	public void setSsztxz(String ssztxz) {
		this.ssztxz = ssztxz;
	}

	public String getWtjg() {
		if((wtjg!=null&&wtjg.trim().equals("null"))||wtjg==null){
			return "空";
		}
		return wtjg;
	}

	public void setWtjg(String wtjg) {
		
		this.wtjg = wtjg;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getBldate() {
		return bldate;
	}

	public void setBldate(String bldate) {
		this.bldate = bldate;
	}

	public String getBslc() {
		return bslc;
	}

	public void setBslc(String bslc) {
		this.bslc = bslc;
	}

	public String getCbm() {
		return cbm;
	}

	public void setCbm(String cbm) {
		this.cbm = cbm;
	}

	public String getCnsj() {
		return cnsj;
	}

	public void setCnsj(String cnsj) {
		this.cnsj = cnsj;
	}

	public String getCnsjms() {
		return cnsjms;
	}

	public void setCnsjms(String cnsjms) {
		this.cnsjms = cnsjms;
	}

	public String getFdcnsj() {
		return fdcnsj;
	}

	public void setFdcnsj(String fdcnsj) {
		this.fdcnsj = fdcnsj;
	}

	public String getFlfg() {
		return flfg;
	}

	public void setFlfg(String flfg) {
		this.flfg = flfg;
	}

	public String getFwzn() {
		return fwzn;
	}

	public void setFwzn(String fwzn) {
		this.fwzn = fwzn;
	}

	public String getIsfz() {
		if(isfz.equals("0")){
			return "不发证";
		}else if(isfz.equals("1")){
			return  "发证";
		}
		
		return isfz;
		
	}

	public void setIsfz(String isfz) {
		this.isfz = isfz;
	}

	public String getIssf() {
		if(issf==null){
			return "空";
		}else if(issf.equals("1")){
			return "收费";
		}else if(issf.equals("0")){
			return "不收费";
		}else if(issf.trim().equals("null")){
			return "空";
			
		}else{
			return issf;
		}
		
	}

	public void setIssf(String issf) {
		this.issf = issf;
	}

	public String getIswssb() {
		return iswssb;
	}

	public void setIswssb(String iswssb) {
		this.iswssb = iswssb;
	}

	public String getJdjg() {
		return jdjg;
	}

	public void setJdjg(String jdjg) {
		this.jdjg = jdjg;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getQtbldd() {
		return qtbldd;
	}

	public void setQtbldd(String qtbldd) {
		this.qtbldd = qtbldd;
	}

	public String getSfbz() {
		if((sfbz==null)||(sfbz!=null&&sfbz.trim().equals("null"))){
			return "空";
		}
		return sfbz;
	}

	public void setSfbz(String sfbz) {
		this.sfbz = sfbz;
	}

	public String getSfjbj() {
		if(sfjbj.equals("1")){
			return "承诺件";
		}else{
			return sfjbj;
		}
		
	}

	public void setSfjbj(String sfjbj) {
		this.sfjbj = sfjbj;
	}

	public String getSltj() {
		return sltj;
	}

	public void setSltj(String sltj) {
		this.sltj = sltj;
	}

	public String getSqsljg() {
		return sqsljg;
	}

	public void setSqsljg(String sqsljg) {
		this.sqsljg = sqsljg;
	}

	public String getXzfwzxbl() {
		if(xzfwzxbl.equals("1")){
			return "是";
		}else{
			return "否";
		}
		
	}

	public void setXzfwzxbl(String xzfwzxbl) {
		this.xzfwzxbl = xzfwzxbl;
	}

	public List<GoverMaterials> getGoverMaterials() {
		return goverMaterials;
	}

	public void setGoverMaterials(List<GoverMaterials> goverMaterials) {
		this.goverMaterials = goverMaterials;
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
