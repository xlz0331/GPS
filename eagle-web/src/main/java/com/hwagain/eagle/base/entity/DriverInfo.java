package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
@ToString
@TableName("driver_info")
public class DriverInfo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 供应商名称
     */
	
	@TableField("supplier_name")
	private String supplierName;
    /**
     * 司机名称
     */
	@ApiModelProperty(example="张三")
	@TableField("driver_name")
	private String driverName;
    /**
     * 手机号
     */
	@ApiModelProperty(example="19912345678")
	private String mobile;
    /**
     * 性别
     */
	@ApiModelProperty(example="M")
	private String sex;
    /**
     * 年龄
     */
	@ApiModelProperty(example="30")
	private Integer age;
	/**
     * 驾驶证编号
     */
	@ApiModelProperty(example="B123456677889999")
	@TableField("license_id")
	private String licenseId;
    /**
     * 驾驶证类型
     */
	@ApiModelProperty(example="B1")
	@TableField("license_type")
	private String licenseType;
    /**
     * 驾驶证有效期
     */
	@TableField("license_end_date")
	private Date licenseEndDate;
    /**
     * 供应商ID
     */
	@TableField("supplier_id")
	private Long supplierId;
    /**
     * 状态（1有效，2失效）
     */
	private Integer state;
	@TableField("create_time")
	private Date createTime;
	@TableField("creator_id")
	private Long creatorId;
	@TableField("creator_name")
	private String creatorName;
	@TableField("last_alter_time")
	private Date lastAlterTime;
	@TableField("last_altor_id")
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
public static void main(String[] args) {
	DriverInfo driverInfo=new DriverInfo();
	System.err.println(driverInfo);
}
}
