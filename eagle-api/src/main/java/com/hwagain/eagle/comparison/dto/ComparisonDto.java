package com.hwagain.eagle.comparison.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-05-26
 */
public class ComparisonDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 磅单号
     */
	private String poundId;
    /**
     * 任务Id
     */
	private Long taskId;
    /**
     * 拉运日期
     */
	private Date transportTime;
    /**
     * 车牌号
     */
	private String plateNumber;
    /**
     * 供应商
     */
	private String supplierName;
    /**
     * 原料名称
     */
	private String material;
    /**
     * 司磅员图片审核结果(old)
     */
	private String oldAuditPhoto1;
    /**
     * 原料收购部图片审核结果(old)
     */
	private String oldAuditPhoto2;
    /**
     * 司磅员图片审核结果(new)
     */
	private String newAuditPhoto1;
    /**
     * 原料收购部图片审核结果(new)
     */
	private String newAuditPhoto2;
    /**
     * 财务部图片审核结果(new)
     */
	private String newAuditPhoto3;
    /**
     * 原料收购部轨迹审核结果(old)
     */
	private String oldAuditTrack2;
    /**
     * 原料收购部轨迹审核结果(new)
     */
	private String newAuditTrack2;
    /**
     * 财务部轨迹审核结果(new)
     */
	private String newAuditTrack3;
    /**
     * 审核结果(old)
     */
	private String oldResult;
    /**
     * 审核结果(new)
     */
	private String newResult;
    /**
     * 原因
     */
	private String reason;
    /**
     * 状态
     */
	private Integer state;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 创建人
     */
	private Long creatorId;
    /**
     * 修改时间
     */
	private Date lastAlterTime;
    /**
     * 修改人
     */
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getPoundId() {
		return poundId;
	}

	public void setPoundId(String poundId) {
		this.poundId = poundId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Date getTransportTime() {
		return transportTime;
	}

	public void setTransportTime(Date transportTime) {
		this.transportTime = transportTime;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getOldAuditPhoto1() {
		return oldAuditPhoto1;
	}

	public void setOldAuditPhoto1(String oldAuditPhoto1) {
		this.oldAuditPhoto1 = oldAuditPhoto1;
	}

	public String getOldAuditPhoto2() {
		return oldAuditPhoto2;
	}

	public void setOldAuditPhoto2(String oldAuditPhoto2) {
		this.oldAuditPhoto2 = oldAuditPhoto2;
	}

	public String getNewAuditPhoto1() {
		return newAuditPhoto1;
	}

	public void setNewAuditPhoto1(String newAuditPhoto1) {
		this.newAuditPhoto1 = newAuditPhoto1;
	}

	public String getNewAuditPhoto2() {
		return newAuditPhoto2;
	}

	public void setNewAuditPhoto2(String newAuditPhoto2) {
		this.newAuditPhoto2 = newAuditPhoto2;
	}

	public String getNewAuditPhoto3() {
		return newAuditPhoto3;
	}

	public void setNewAuditPhoto3(String newAuditPhoto3) {
		this.newAuditPhoto3 = newAuditPhoto3;
	}

	public String getOldAuditTrack2() {
		return oldAuditTrack2;
	}

	public void setOldAuditTrack2(String oldAuditTrack2) {
		this.oldAuditTrack2 = oldAuditTrack2;
	}

	public String getNewAuditTrack2() {
		return newAuditTrack2;
	}

	public void setNewAuditTrack2(String newAuditTrack2) {
		this.newAuditTrack2 = newAuditTrack2;
	}

	public String getNewAuditTrack3() {
		return newAuditTrack3;
	}

	public void setNewAuditTrack3(String newAuditTrack3) {
		this.newAuditTrack3 = newAuditTrack3;
	}

	public String getOldResult() {
		return oldResult;
	}

	public void setOldResult(String oldResult) {
		this.oldResult = oldResult;
	}

	public String getNewResult() {
		return newResult;
	}

	public void setNewResult(String newResult) {
		this.newResult = newResult;
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
