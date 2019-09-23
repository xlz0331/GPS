package com.hwagain.eagle.task.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.enums.IdType;

public class TaskVo_a implements Serializable{
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
	private String photoType;
	 /**
     * 一审
     */
	private Integer audit1;
	/**
	 * 说明
	 */
	@TableField("audit_reason1")
	private String auditReason1;
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
	 * 说明
	 */
	@TableField("audit_reason2")
	private String auditReason2;
    /**
     * 二审人
     */
	private String auditor2;
    /**
     * 二审时间
     */
	@TableField("audit_time2")
	private Date auditTime2;
    /**
     * 三审
     */
	private Integer audit3;
	/**
	 * 说明
	 */
	@TableField("audit_reason3")
	private String auditReason3;
    /**
     * 三审人
     */
	private String auditor3;
    /**
     * 三审时间
     */
	@TableField("audit_time3")
	private Date auditTime3;
	private String audit1Text;
	private String audit2Text;
	private String audit3Text;
	
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
	public Integer getCorrections() {
		return corrections;
	}
	public void setCorrections(Integer corrections) {
		this.corrections = corrections;
	}
	private String CorrectionsText="";
	public String getCorrectionsText(){
		if(this.corrections!=null){
			if(this.corrections==3){
				CorrectionsText="";
			}
			if(this.corrections==1){
				CorrectionsText="已申请";
			}
			if(this.corrections==2){
				CorrectionsText="已申请";
			}
			if(this.corrections==3){
				CorrectionsText="未申请";
			}
			if(this.corrections==4){
				CorrectionsText="已申请";
			}
			if(this.corrections==5){
				CorrectionsText="已申请";
			}
			if(this.corrections==6){
				CorrectionsText="已申请";
			}
			if(this.corrections==7){
				CorrectionsText="已申请";
			}
			if(this.corrections==8){
				CorrectionsText="已申请";
			}
		}
		
		return CorrectionsText;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonFormat(pattern="yyyy年MM月dd日 HH:mm",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonFormat(pattern="yyyy年MM月dd日 HH:mm",timezone="GMT+8")
	public Date getLastAlterTime() {
		return lastAlterTime;
	}
	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}
	public String getPhotoType() {
		return photoType;
	}
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
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
	public Integer getAudit1() {
		return audit1;
	}
	public void setAudit1(Integer audit1) {
		this.audit1 = audit1;
	}
	public String getAuditReason1() {
		return auditReason1;
	}
	public void setAuditReason1(String auditReason1) {
		this.auditReason1 = auditReason1;
	}
	public String getAuditor1() {
		return auditor1;
	}
	public void setAuditor1(String auditor1) {
		this.auditor1 = auditor1;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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
	public String getAuditReason2() {
		return auditReason2;
	}
	public void setAuditReason2(String auditReason2) {
		this.auditReason2 = auditReason2;
	}
	public String getAuditor2() {
		return auditor2;
	}
	public void setAuditor2(String auditor2) {
		this.auditor2 = auditor2;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getAuditTime2() {
		return auditTime2;
	}
	public void setAuditTime2(Date auditTime2) {
		this.auditTime2 = auditTime2;
	}
	public Integer getAudit3() {
		return audit3;
	}
	public void setAudit3(Integer audit3) {
		this.audit3 = audit3;
	}
	public String getAuditReason3() {
		return auditReason3;
	}
	public void setAuditReason3(String auditReason3) {
		this.auditReason3 = auditReason3;
	}
	public String getAuditor3() {
		return auditor3;
	}
	public void setAuditor3(String auditor3) {
		this.auditor3 = auditor3;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getAuditTime3() {
		return auditTime3;
	}
	public void setAuditTime3(Date auditTime3) {
		this.auditTime3 = auditTime3;
	}
	public String getStartPositionAddress() {
		return startPositionAddress;
	}
	public void setStartPositionAddress(String startPositionAddress) {
		this.startPositionAddress = startPositionAddress;
	}
	public String getAudit1Text() {
		return audit1Text;
	}
	public void setAudit1Text(String audit1Text) {
		this.audit1Text = audit1Text;
	}
	public String getAudit2Text() {
		return audit2Text;
	}
	public void setAudit2Text(String audit2Text) {
		this.audit2Text = audit2Text;
	}
	public String getAudit3Text() {
		return audit3Text;
	}
	public void setAudit3Text(String audit3Text) {
		this.audit3Text = audit3Text;
	}
	
}
