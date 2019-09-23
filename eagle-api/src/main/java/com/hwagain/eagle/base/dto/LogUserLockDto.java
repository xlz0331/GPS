package com.hwagain.eagle.base.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 用户上锁/解锁操作日志表
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
public class LogUserLockDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long fdId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 原IMEI
     */
	private String imei;
    /**
     * 新的IMEI
     */
	private String currentImei;
    /**
     * 状态。1：Locked状态；2：解锁状态
     */
	private Integer state;
    /**
     * 操作员
     */
	private Long operatorId;
    /**
     * 创建时间/操作时间
     */
	private Date createTime;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCurrentImei() {
		return currentImei;
	}

	public void setCurrentImei(String currentImei) {
		this.currentImei = currentImei;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
