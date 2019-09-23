package com.hwagain.eagle.org.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-04-17
 */
public class PsCompanyTblDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	private String fdId;
    /**
     * 公司
     */
	private String company;
    /**
     * 生效日期
     */
	private Date effDate;
    /**
     * 状态
     */
	private String effStatus;
    /**
     * 描述
     */
	private String descr;
    /**
     * 简述
     */
	private String descrShort;
    /**
     * 地址
     */
	private String address1;
    /**
     * 组织架构公司id
     */
	private String companyId;
    /**
     * 顺序
     */
	private Integer sequence;
    /**
     * 是否显示组织架构公司
     */
	private Integer isOrg;
    /**
     * 是否显示财务公司
     */
	private Integer isSalary;
    /**
     * 是否删除
     */
	private Integer isDelete;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public String getEffStatus() {
		return effStatus;
	}

	public void setEffStatus(String effStatus) {
		this.effStatus = effStatus;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescrShort() {
		return descrShort;
	}

	public void setDescrShort(String descrShort) {
		this.descrShort = descrShort;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(Integer isOrg) {
		this.isOrg = isOrg;
	}

	public Integer getIsSalary() {
		return isSalary;
	}

	public void setIsSalary(Integer isSalary) {
		this.isSalary = isSalary;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
