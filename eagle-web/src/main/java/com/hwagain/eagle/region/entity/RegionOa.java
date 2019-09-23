package com.hwagain.eagle.region.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 片区表
 * </p>
 *
 * @author hanjin
 * @since 2019-05-11
 */
@TableName("region_oa")
public class RegionOa implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
	@TableField("region_name")
	private String regionName;
    /**
     * 是否有额外/运费等补助。1：有，2：无
     */
	@TableField("extra_subsidy")
	private Integer extraSubsidy;
	
	private Float subsidy;
	
	private Float subsidy2;

	/**
     * 平均运距
     */
	@TableField("region_distance")
	private Float regionDistance;
    /**
     * 最近区域距离。单位：千米
     */
	@TableField("min_region_distance")
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
     * 审批状态。0：待审批，1：审批通过，-1：不通过
     */
	private Integer status;
    /**
     * 生效日期
     */
	private Long effdt;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人id
     */
	@TableField("creator_id")
	private String creatorId;
    /**
     * 最后修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
    /**
     * 最后修改人id
     */
	@TableField("last_altor_id")
	private String lastAltorId;
	 /**
     * oa_code
     */
	@TableField("oa_code")
	private String oaCode;
	
    public Float getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Float subsidy) {
		this.subsidy = subsidy;
	}

	public Float getSubsidy2() {
		return subsidy2;
	}

	public void setSubsidy2(Float subsidy2) {
		this.subsidy2 = subsidy2;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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

	public String getOaCode() {
		return oaCode;
	}

	public void setOaCode(String oaCode) {
		this.oaCode = oaCode;
	}

}
