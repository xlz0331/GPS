package com.hwagain.eagle.supplier.entity;

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
 * @since 2019-03-22
 */
@TableName("material_purchase_plan")
public class MaterialPurchasePlan implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 供应商名称
     */
	@TableField("supplier_name")
	private String supplierName;
    /**
     * 公司Id
     */
	@TableField("company_id")
	private Long companyId;
    /**
     * 供应商Id
     */
	@TableField("supplier_id")
	private Long supplierId;
    /**
     * 原料名称
     */
	private String material;
    /**
     * 计划收购总量（鲜吨）
     */
	@TableField("purchase_plan_num")
	private BigDecimal purchasePlanNum;
    /**
     * 日均收购量上限（鲜吨）
     */
	@TableField("max_purchase_day_num")
	private BigDecimal maxPurchaseDayNum;
    /**
     * 日均收购量下限（鲜吨）
     */
	@TableField("min_purchase_day_num")
	private BigDecimal minPurchaseDayNum;
    /**
     * 平均每车总量（吨）
     */
	@TableField("average_percar_num")
	private BigDecimal averagePercarNum;
    /**
     * 日均上限车数
     */
	@TableField("max_car_num")
	private Integer maxCarNum;
    /**
     * 日均下限车数
     */
	@TableField("min_car_num")
	private Integer minCarNum;
	/**
	 * 已完成吨数
	 */
	@TableField("completed_num")
	private BigDecimal completedNum;
    /**
     * 计划开始时间
     */
	@TableField("plan_starttime")
	private Date planStarttime;
    /**
     * 计划结束时间
     */
	@TableField("plan_endtime")
	private Date planEndtime;
	/**
	 * oa_code
	 */
	@TableField("oa_code")
	private String oaCode;
	private Integer state;
	@TableField("create_time")
	private Date createTime;
	@TableField("creator_id")
	private Long creatorId;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public BigDecimal getPurchasePlanNum() {
		return purchasePlanNum;
	}

	public void setPurchasePlanNum(BigDecimal purchasePlanNum) {
		this.purchasePlanNum = purchasePlanNum;
	}

	public BigDecimal getMaxPurchaseDayNum() {
		return maxPurchaseDayNum;
	}

	public void setMaxPurchaseDayNum(BigDecimal maxPurchaseDayNum) {
		this.maxPurchaseDayNum = maxPurchaseDayNum;
	}

	public BigDecimal getMinPurchaseDayNum() {
		return minPurchaseDayNum;
	}

	public void setMinPurchaseDayNum(BigDecimal minPurchaseDayNum) {
		this.minPurchaseDayNum = minPurchaseDayNum;
	}

	public BigDecimal getAveragePercarNum() {
		return averagePercarNum;
	}

	public void setAveragePercarNum(BigDecimal averagePercarNum) {
		this.averagePercarNum = averagePercarNum;
	}

	public Integer getMaxCarNum() {
		return maxCarNum;
	}

	public void setMaxCarNum(Integer maxCarNum) {
		this.maxCarNum = maxCarNum;
	}

	public Integer getMinCarNum() {
		return minCarNum;
	}

	public void setMinCarNum(Integer minCarNum) {
		this.minCarNum = minCarNum;
	}

	public BigDecimal getCompletedNum() {
		return completedNum;
	}

	public void setCompletedNum(BigDecimal completedNum) {
		this.completedNum = completedNum;
	}

	public Date getPlanStarttime() {
		return planStarttime;
	}

	public void setPlanStarttime(Date planStarttime) {
		this.planStarttime = planStarttime;
	}

	public Date getPlanEndtime() {
		return planEndtime;
	}

	public void setPlanEndtime(Date planEndtime) {
		this.planEndtime = planEndtime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOaCode() {
		return oaCode;
	}

	public void setOaCode(String oaCode) {
		this.oaCode = oaCode;
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

}
