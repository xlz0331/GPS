package com.hwagain.eagle.correct.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
public class Correct implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 任务Id
     */
	@TableField("task_id")
	private Long taskId;
    /**
     * 补正供货区域
     */
	@TableField("region_name2")
	private String regionName2;
    /**
     * 单价
     */
	private BigDecimal price;
    /**
     * 总价
     */
	@TableField("total_price")
	private BigDecimal totalPrice;
    /**
     * 补正申请理由
     */
	@TableField("apply_reason")
	private String applyReason;
	 /**
     * oa_code
     */
	@TableField("oa_code")
	private String oaCode;
    /**
     * 一审
     */
	private String audit1;
    /**
     * 一审理由
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
	private String audit2;
    /**
     * 二审理由
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
	private String audit3;
    /**
     * 三审理由
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
    /**
     * 状态
     */
	private Integer state;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人
     */
	@TableField("creator_id")
	private String creatorId;
    /**
     * 最后修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
    /**
     * 最后修改人
     */
	@TableField("last_altor_id")
	private String lastAltorId;


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

	public String getRegionName2() {
		return regionName2;
	}

	public void setRegionName2(String regionName2) {
		this.regionName2 = regionName2;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getOaCode() {
		return oaCode;
	}

	public void setOaCode(String oaCode) {
		this.oaCode = oaCode;
	}

	public String getAudit1() {
		return audit1;
	}

	public void setAudit1(String audit1) {
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

	public Date getAuditTime1() {
		return auditTime1;
	}

	public void setAuditTime1(Date auditTime1) {
		this.auditTime1 = auditTime1;
	}

	public String getAudit2() {
		return audit2;
	}

	public void setAudit2(String audit2) {
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

	public Date getAuditTime2() {
		return auditTime2;
	}

	public void setAuditTime2(Date auditTime2) {
		this.auditTime2 = auditTime2;
	}

	public String getAudit3() {
		return audit3;
	}

	public void setAudit3(String audit3) {
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

	public Date getAuditTime3() {
		return auditTime3;
	}

	public void setAuditTime3(Date auditTime3) {
		this.auditTime3 = auditTime3;
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

}
