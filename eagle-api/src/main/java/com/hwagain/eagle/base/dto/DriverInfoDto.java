package com.hwagain.eagle.base.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
public class DriverInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 供应商名称
     */
	private String supplierName;
    /**
     * 司机名称
     */
	private String driverName;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 性别
     */
	private String sex;
    /**
     * 年龄
     */
	private Integer age;
	/**
     * 驾驶证编号
     */
	private String licenseId;
    /**
     * 驾驶证类型
     */
	private String licenseType;
    /**
     * 驾驶证有效期
     */
	private Date licenseEndDate;
    /**
     * 供应商ID
     */
	private Long supplierId;
    /**
     * 状态（1有效，2失效）
     */
	private Integer state;
	private Date createTime;
	private Long creatorId;
	private String creatorName;
	private Date lastAlterTime;
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	private String sexText="";
	
	public String getSexText() {
		if(this.sex!=null&&!this.sex.isEmpty()&&this.sex.equals("M")){
			sexText="男";
		}else{
			sexText="女";
		}
		return sexText;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getLicenseEndDate() {
		return licenseEndDate;
	}

	public void setLicenseEndDate(Date licenseEndDate) {
		this.licenseEndDate = licenseEndDate;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	private String stateText="";	
	public String getStateText() {
		if(this.state!=null&&this.state==1){
			stateText="正常";
		}
		if(this.state!=null&&this.state==0){
			stateText="已失效";
		}
		return stateText;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getLastAlterTime() {
		return lastAlterTime;
	}

	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}

	public Long getLastAltorId() {
		return lastAltorId;
	}

	public void setLastAltorId(Long lastAltorId) {
		this.lastAltorId = lastAltorId;
	}

}
