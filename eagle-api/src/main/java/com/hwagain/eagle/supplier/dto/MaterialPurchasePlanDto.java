package com.hwagain.eagle.supplier.dto;

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
 * @since 2019-03-22
 */
public class MaterialPurchasePlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 供应商名称
     */
	private String supplierName;
    /**
     * 公司Id
     */
	private Long companyId;
    /**
     * 供应商Id
     */
	private Long supplierId;
    /**
     * 原料名称
     */
	private String material;
    /**
     * 计划收购总量（鲜吨）
     */
	private BigDecimal purchasePlanNum;
    /**
     * 日均收购量上限（鲜吨）
     */
	private BigDecimal maxPurchaseDayNum;
    /**
     * 日均收购量下限（鲜吨）
     */
	private BigDecimal minPurchaseDayNum;
    /**
     * 平均每车总量（吨）
     */
	private BigDecimal averagePercarNum;
    /**
     * 日均上限车数
     */
	private Integer maxCarNum;
    /**
     * 日均下限车数
     */
	private Integer minCarNum;
	/**
	 * 已完成吨数
	 */
	private BigDecimal completedNum;
    /**
     * 计划开始时间
     */
	private Date planStarttime;
    /**
     * 计划结束时间
     */
	private Date planEndtime;
	/**
	 * oa_code
	 */
	private String oaCode;
	private Integer state;
	private Date createTime;
	private Long creatorId;
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
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getPlanStarttime() {
		return planStarttime;
	}

	public void setPlanStarttime(Date planStarttime) {
		this.planStarttime = planStarttime;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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
	private String StateText="";
	public String getStateText(){
		if(this.state!=null&&this.state==1){
			StateText="待提交";
		}
		if(this.state!=null&&this.state==2){
			StateText="待审核";
		}
		if(this.state!=null&&this.state==3){
			StateText="已审核";
		}
		if(this.state!=null&&this.state==4){
			StateText="审核不通过";
		}
		return StateText;
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
