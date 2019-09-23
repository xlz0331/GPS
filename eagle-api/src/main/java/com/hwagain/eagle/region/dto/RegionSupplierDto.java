package com.hwagain.eagle.region.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 片区表
 * </p>
 *
 * @author xionglz
 * @since 2019-06-17
 */
public class RegionSupplierDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long fdId;
    /**
     * 供应商名称
     */
	private String supplierName;
    /**
     * 供应商ID
     */
	private Long supplierId;
    /**
     * 省
     */
	private String province;
    /**
     * 原料名称
     */
	private String material;
    /**
     * 所属城市
     */
	private String city;
    /**
     * 区域名称
     */
	private String regionName;
    /**
     * 是否有额外/运费等补助。1：有，2：无
     */
	private Integer extraSubsidy;
    /**
     * 运费补助
     */
	private Float subsidy;
    /**
     * 平均运距
     */
	private Float regionDistance;
    /**
     * 最近区域距离。单位：千米
     */
	private Integer minRegionDistance;
    /**
     * 调整原因
     */
	private String reason;
    /**
     * 状态。1：有效，2：作废
     */
	private Integer state;
    /**
     * 生效日期
     */
	private Long effdt;
	/**
	 * effdtText
	 */
	private String effdtText;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 创建人id
     */
	private String creatorId;
    /**
     * 最后修改时间
     */
	private Date lastAlterTime;
    /**
     * 最后修改人id
     */
	private String lastAltorId;


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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getExtraSubsidy() {
		return extraSubsidy;
	}

	public void setExtraSubsidy(Integer extraSubsidy) {
		this.extraSubsidy = extraSubsidy;
	}

	public Float getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Float subsidy) {
		this.subsidy = subsidy;
	}

	public Float getRegionDistance() {
		return regionDistance;
	}

	public void setRegionDistance(Float regionDistance) {
		this.regionDistance = regionDistance;
	}

	public Integer getMinRegionDistance() {
		return minRegionDistance;
	}

	public void setMinRegionDistance(Integer minRegionDistance) {
		this.minRegionDistance = minRegionDistance;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getEffdt() {
		return effdt;
	}

	public void setEffdt(Long effdt) {
		this.effdt = effdt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getLastAlterTime() {
		return lastAlterTime;
	}

	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}

	public String getLastAltorId() {
		return lastAltorId;
	}

	public void setLastAltorId(String lastAltorId) {
		this.lastAltorId = lastAltorId;
	}

	public String getEffdtText() {
		return effdtText;
	}

	public void setEffdtText(String effdtText) {
		this.effdtText = effdtText;
	}

}
