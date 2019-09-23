package com.hwagain.eagle.region.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-04-13
 */
@TableName("region_detail")
public class RegionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 供应商名称
     */
	@TableField("supplier_name")
	private String supplierName;
    /**
     * 供应商ID
     */
	@TableField("supplier_id")
	private Long supplierId;
    /**
     * 片区名称
     */
	@ApiModelProperty(example="南宁市")
	@TableField("region_name")
	private String regionName;
	/**
	 * 县/区
	 */
	@ApiModelProperty(example="青秀区")
	private String county;
	/**
     * 原料名称
     */
	@ApiModelProperty(example="从生竹片")
	private String material;
	/**
     * 省
     */
	@ApiModelProperty(example="广西")
	private String province;
	/**
     * 收购点/加工厂
     */
	@ApiModelProperty(example="XXX加工厂")
	@TableField("purchase_point")
	private String purchasePoint;
    /**
     * 地址
     */
	@ApiModelProperty(example="民族大道131号")
	private String address;
    /**
     * 纬度
     */
	@ApiModelProperty(example="22.189523")
	private Double latitude;
    /**
     * 经度
     */
	@ApiModelProperty(example="108.116568")
	private Double longitude;
    /**
     * 距离
     */
	@ApiModelProperty(example="360")
	@TableField("region_distance")
	private BigDecimal regionDistance;
    /**
     * 元/绝干吨
     */
	private BigDecimal subsidy1;
    /**
     * 元/鲜吨
     */
	private BigDecimal subsidy2;
	 /**
     * oa_code
     */
	@TableField("oa_code")
	private String oaCode;
    /**
     * 状态
     */
	private Integer state;
	 /**
     * mobile
     */
	private String mobile;
	  /**
     * 一审
     */
	private Integer audit1;
    /**
     * 一审人
     */
	private String auditor1;
    /**
     * 一审时间
     */
	@TableField("audit_time1")
	private Date auditTime1;
    /**
     * 二审
     */
	private Integer audit2;
    /**
     * 二审人
     */
	private String auditor2;
    /**
     * 二审时间
     */
	@TableField("audit_time2")
	private Date auditTime2;
	@TableField("create_time")
	private Date createTime;
	@TableField("creator_id")
	private Long creatorId;
	@TableField("last_alter_time")
	private Date lastAlterTime;
	@TableField("last_altor_id")
	private Long lastAltorId;
	 /**
     * gps纬度
     */
	@TableField("gps_latitude")
	private Double gpsLatitude;
    /**
     * gps经度
     */
	@TableField("gps_longitude")
	private Double gpsLongitude;

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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPurchasePoint() {
		return purchasePoint;
	}

	public void setPurchasePoint(String purchasePoint) {
		this.purchasePoint = purchasePoint;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getRegionDistance() {
		return regionDistance;
	}

	public void setRegionDistance(BigDecimal regionDistance) {
		this.regionDistance = regionDistance;
	}

	public BigDecimal getSubsidy1() {
		return subsidy1;
	}

	public void setSubsidy1(BigDecimal subsidy1) {
		this.subsidy1 = subsidy1;
	}

	public BigDecimal getSubsidy2() {
		return subsidy2;
	}

	public void setSubsidy2(BigDecimal subsidy2) {
		this.subsidy2 = subsidy2;
	}

	public String getOaCode() {
		return oaCode;
	}

	public void setOaCode(String oaCode) {
		this.oaCode = oaCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAudit1() {
		return audit1;
	}

	public void setAudit1(Integer audit1) {
		this.audit1 = audit1;
	}

	public String getAuditor1() {
		return auditor1;
	}

	public void setAuditor1(String auditor1) {
		this.auditor1 = auditor1;
	}

	public Date getAuditTime1() {
		return auditTime1;
	}

	public void setAuditTime1(Date auditTime1) {
		this.auditTime1 = auditTime1;
	}

	public Integer getAudit2() {
		return audit2;
	}

	public void setAudit2(Integer audit2) {
		this.audit2 = audit2;
	}

	public String getAuditor2() {
		return auditor2;
	}

	public void setAuditor2(String auditor2) {
		this.auditor2 = auditor2;
	}

	public Date getAuditTime2() {
		return auditTime2;
	}

	public void setAuditTime2(Date auditTime2) {
		this.auditTime2 = auditTime2;
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

	public Double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(Double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public Double getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(Double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

}
