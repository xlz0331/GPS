package com.hwagain.eagle.acquisitionIndictor.dto;

import java.math.BigDecimal;
import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 收购指标信息表
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
public class AcquisitionIndictorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	private Long fdId;
    /**
     * 指标
     */
	private Double indicator;
    /**
     * 年份
     */
	private Integer year;
    /**
     * 指标单价
     */
	private BigDecimal price;
    /**
     * 额外补助单价
     */
	private BigDecimal extraSubsidyPrice;
    /**
     * 公司ID
     */
	private Long companyId;
    /**
     * 供应商ID
     */
	private Long supplierId;
    /**
     * 状态。1：有效；2：作废
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
     * 最后更新时间
     */
	private Date lastAlterTime;
    /**
     * 最后更新人id
     */
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Double getIndicator() {
		return indicator;
	}

	public void setIndicator(Double indicator) {
		this.indicator = indicator;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getExtraSubsidyPrice() {
		return extraSubsidyPrice;
	}

	public void setExtraSubsidyPrice(BigDecimal extraSubsidyPrice) {
		this.extraSubsidyPrice = extraSubsidyPrice;
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
