package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户上锁/解锁操作日志表
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@TableName("log_user_lock")
public class LogUserLock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 原IMEI
     */
	private String imei;
    /**
     * 新的IMEI
     */
	@TableField("current_imei")
	private String currentImei;
    /**
     * 状态。1：Locked状态；2：解锁状态
     */
	private Integer state;
    /**
     * 操作员
     */
	@TableField("operator_id")
	private Long operatorId;
    /**
     * 创建时间/操作时间
     */
	@TableField("create_time")
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
