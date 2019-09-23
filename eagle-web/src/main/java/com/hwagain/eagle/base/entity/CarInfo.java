package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
@TableName("car_info")
public class CarInfo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 供应商ID
     */
	@TableField("supplier_id")
	private Long supplierId;
    /**
     * 供应商名称
     */
	@TableField("supplier_name")
	private String supplierName;
    /**
     * 车辆型号
     */
	@TableField("car_type")
	private String carType;
    /**
     * 车牌号
     */
	@TableField("plate_number")
	private String plateNumber;
    /**
     * 车身长度
     */
	@TableField("car_length")
	private BigDecimal carLength;
    /**
     * 车身高度
     */
	@TableField("car_height")
	private BigDecimal carHeight;
    /**
     * 车重
     */
	@TableField("car_weight")
	private BigDecimal carWeight;
    /**
     * 车载重量
     */
	@TableField("load_weight")
	private BigDecimal loadWeight;
    /**
     * 所需驾照类型
     */
	@TableField("license_type")
	private String licenseType;
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

}
