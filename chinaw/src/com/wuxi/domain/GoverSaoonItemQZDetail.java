package com.wuxi.domain;

/**
 * 
 * @author wanglu 泰得利通 办件 行政强制
 * 
 */
public class GoverSaoonItemQZDetail {

	private String bbh;
	private String bgdd;// 办公地址
	private String bm;// 事项编码
	private String jddh;
	private String ljdz;
	private String sfsbssp;
	private String sszt;// 实施主体
	private String ssztbm;// 实施主体编码
	private String ssztxz;// 实施主体性质
	private String wtjg;// 委托机关
	private String xzqh;
	private String flfg;// 法律法规
	private String qzcx;// 强制流程
	private String name;
	private String id;
	private String deptid;
	private String deptname;

	public String getBbh() {
		return bbh;
	}

	public void setBbh(String bbh) {
		this.bbh = bbh;
	}

	public String getBgdd() {
		return bgdd;
	}

	public void setBgdd(String bgdd) {
		this.bgdd = bgdd;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getJddh() {
		return jddh;
	}

	public void setJddh(String jddh) {
		this.jddh = jddh;
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

	public String getFlfg() {
		return flfg;
	}

	public void setFlfg(String flfg) {
		this.flfg = flfg;
	}

	public String getQzcx() {
		return qzcx;
	}

	public void setQzcx(String qzcx) {
		this.qzcx = qzcx;
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
