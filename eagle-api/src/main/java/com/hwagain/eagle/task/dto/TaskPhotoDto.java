package com.hwagain.eagle.task.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
public class TaskPhotoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	private Long fdId;
    /**
     * 任务id
     */
	private Long taskId;
    /**
     * 用户当前所用的手机号码
     */
	private String mobile;
    /**
     * 照片在附件表中的id
     */
	private Long photoId;
    /**
     * 验收结果。1：合格，2：不合格。缺省合格
     */
	private Integer acceptanceResult;
    /**
     * 照片类型。1：车牌，2：车头，3：货物，4：车尾
     */
	private Integer photoType;
    /**
     * GPS经纬度
     */
	private String gps;
    /**
     * 拍照所在城市
     */
	private String city;
    /**
     * 拍照地址
     */
	private String address;
    /**
     * 状态。1：有效，2：已作废
     */
	private Integer state;
    /**
     * 拍照时间
     */
	private Date photoTime;
	  /**
     * 一审
     */
	private Integer audit1;
	/**
	 * 说明
	 */
	private String auditReason1;
    /**
     * 一审人
     */
	private String auditor1;
    /**
     * 一审时间
     */
	private Date auditTime1;
    /**
     * 二审
     */
	private Integer audit2;
	/**
	 * 说明
	 */
	private String auditReason2;
    /**
     * 二审人
     */
	private String auditor2;
    /**
     * 二审时间
     */
	private Date auditTime2;
    /**
     * 三审
     */
	private Integer audit3;
	/**
	 * 说明
	 */
	private String auditReason3;
    /**
     * 三审人
     */
	private String auditor3;
    /**
     * 三审时间
     */
	private Date auditTime3;
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
     * 最后修改人Id
     */
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Integer getAcceptanceResult() {
		return acceptanceResult;
	}

	public void setAcceptanceResult(Integer acceptanceResult) {
		this.acceptanceResult = acceptanceResult;
	}

	public Integer getPhotoType() {
		return photoType;
	}

	public void setPhotoType(Integer photoType) {
		this.photoType = photoType;
	}
	private String photoTypeText="";
	public String getPhotoTypeText(){
		if (this.photoType!=null) {
			if(this.photoType==1){
				photoTypeText="车牌";
			}
			if(this.photoType==2){
				photoTypeText="车头";
			}
			if(this.photoType==3){
				photoTypeText="车身";
			}
			if(this.photoType==4){
				photoTypeText="车尾";
			}
		}
		
		return photoTypeText;
	}
	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(Date photoTime) {
		this.photoTime = photoTime;
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

	public String getAuditReason1() {
		return auditReason1;
	}

	public void setAuditReason1(String auditReason1) {
		this.auditReason1 = auditReason1;
	}

	public String getAuditReason2() {
		return auditReason2;
	}

	public void setAuditReason2(String auditReason2) {
		this.auditReason2 = auditReason2;
	}

	public String getAuditReason3() {
		return auditReason3;
	}

	public void setAuditReason3(String auditReason3) {
		this.auditReason3 = auditReason3;
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
