package com.hwagain.eagle.task.entity;

import java.util.Date;
import java.util.List;

import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.enums.IdType;

public class TaskVo_b {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /**
     * id
     */
	@TableId(value="fd_id",type = IdType.AUTO)
	private Long fdId;
	 /**
     * 磅单号
     */
	@TableField("pound_id")
	private String poundId;
	/**
     * 所属供应商
     */
	@TableField("supplier_id")
	private Long supplierId;
	 /**
     * 所属供应商
     */
	@TableField("supplier_name")
	private String supplierName;
	 /**
     * 用户车牌号
     */
	@TableField("user_plate_number")
	private String userPlateNumber;
	/**
     * 开始城市
     */
	@TableField("start_position_city")
	private String startPositionCity;
	private String startPositionAddress;
	/**
     * 补正申请
     */
	@TableField("corrections")
	private Integer corrections;
	 /**
     * 状态。0：未开始；1：进行中；2：待审核；3：已完成；4：取消；5：暂停；
     */
	private Integer status;
	 /**
     * 开始时间
     */
	@TableField("create_time")
	private Date createTime;
	/**
     * 最后修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
	private String trackAudit2;
	private String trackAuditor2;
	private Date trackAuditTime2;
	private String trackAuditReason2;
	
	private String trackAudit3;
	private String trackAuditor3;
	
	private Date trackAuditTime3;
	private String trackAuditReason3;
	private List<TaskPhotoVo> taskPhotoVos;
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
	public String getUserPlateNumber() {
		return userPlateNumber;
	}
	public void setUserPlateNumber(String userPlateNumber) {
		this.userPlateNumber = userPlateNumber;
	}
	public String getStartPositionCity() {
		return startPositionCity;
	}
	public void setStartPositionCity(String startPositionCity) {
		this.startPositionCity = startPositionCity;
	}
	public String getStartPositionAddress() {
		return startPositionAddress;
	}
	public void setStartPositionAddress(String startPositionAddress) {
		this.startPositionAddress = startPositionAddress;
	}
	public Integer getCorrections() {
		return corrections;
	}
	public void setCorrections(Integer corrections) {
		this.corrections = corrections;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastAlterTime() {
		return lastAlterTime;
	}
	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}
	public String getTrackAudit2() {
		return trackAudit2;
	}
	public void setTrackAudit2(String trackAudit2) {
		this.trackAudit2 = trackAudit2;
	}
	public String getTrackAuditor2() {
		return trackAuditor2;
	}
	public void setTrackAuditor2(String trackAuditor2) {
		this.trackAuditor2 = trackAuditor2;
	}
	public Date getTrackAuditTime2() {
		return trackAuditTime2;
	}
	public void setTrackAuditTime2(Date trackAuditTime2) {
		this.trackAuditTime2 = trackAuditTime2;
	}
	public String getTrackAuditReason2() {
		return trackAuditReason2;
	}
	public void setTrackAuditReason2(String trackAuditReason2) {
		this.trackAuditReason2 = trackAuditReason2;
	}
	public String getTrackAudit3() {
		return trackAudit3;
	}
	public void setTrackAudit3(String trackAudit3) {
		this.trackAudit3 = trackAudit3;
	}
	public String getTrackAuditor3() {
		return trackAuditor3;
	}
	public void setTrackAuditor3(String trackAuditor3) {
		this.trackAuditor3 = trackAuditor3;
	}
	public Date getTrackAuditTime3() {
		return trackAuditTime3;
	}
	public void setTrackAuditTime3(Date trackAuditTime3) {
		this.trackAuditTime3 = trackAuditTime3;
	}
	public String getTrackAuditReason3() {
		return trackAuditReason3;
	}
	public void setTrackAuditReason3(String trackAuditReason3) {
		this.trackAuditReason3 = trackAuditReason3;
	}
	public List<TaskPhotoVo> getTaskPhotoVos() {
		return taskPhotoVos;
	}
	public void setTaskPhotoVos(List<TaskPhotoVo> taskPhotoVos) {
		this.taskPhotoVos = taskPhotoVos;
	}
	
}
