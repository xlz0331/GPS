package com.hwagain.eagle.base.dto;

import java.math.BigDecimal;
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
public class CarInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 供应商ID
     */
	private Long supplierId;
    /**
     * 供应商名称
     */
	private String supplierName;
    /**
     * 车辆型号
     */
	private String carType;
    /**
     * 车牌号
     */
	private String plateNumber;
    /**
     * 车身长度
     */
	private BigDecimal carLength;
    /**
     * 车身高度
     */
	private BigDecimal carHeight;
    /**
     * 车重
     */
	private BigDecimal carWeight;
    /**
     * 车载重量
     */
	private BigDecimal loadWeight;
    /**
     * 所需驾照类型
     */
	private String licenseType;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public BigDecimal getCarLength() {
		return carLength;
	}

	public void setCarLength(BigDecimal carLength) {
		this.carLength = carLength;
	}

	public BigDecimal getCarHeight() {
		return carHeight;
	}

	public void setCarHeight(BigDecimal carHeight) {
		this.carHeight = carHeight;
	}

	public BigDecimal getCarWeight() {
		return carWeight;
	}

	public void setCarWeight(BigDecimal carWeight) {
		this.carWeight = carWeight;
	}

	public BigDecimal getLoadWeight() {
		return loadWeight;
	}

	public void setLoadWeight(BigDecimal loadWeight) {
		this.loadWeight = loadWeight;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
