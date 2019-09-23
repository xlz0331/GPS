package com.hwagain.eagle.supplier.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 供货计划信息表
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
public class SupplyPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 公司ID
     */
	private Long companyId;
    /**
     * 供应商ID
     */
	private Long supplierId;
    /**
     * 月份。格式：yyyy-MM
     */
	private String month;
    /**
     * 指标。单位:T
     */
	private Double indictor;
    /**
     * 实际完成指标。单位: T
     */
	private Double actualIndictor;
    /**
     * 状态。1：有效，2：作废
     */
	private Integer state;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 创建人id
     */
	private Long creatorId;
    /**
     * 最后修改时间
     */
	private Date lastAlterTime;
    /**
     * 最后修改人id
     */
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getIndictor() {
		return indictor;
	}

	public void setIndictor(Double indictor) {
		this.indictor = indictor;
	}

	public Double getActualIndictor() {
		return actualIndictor;
	}

	public void setActualIndictor(Double actualIndictor) {
		this.actualIndictor = actualIndictor;
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
