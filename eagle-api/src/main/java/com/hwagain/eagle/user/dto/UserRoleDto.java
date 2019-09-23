package com.hwagain.eagle.user.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
public class UserRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long fdId;
    /**
     * 用户Id
     */
	private Long userId;
    /**
     * 角色Id
     */
	private Long roleId;
    /**
     * 状态。1：正常。2：已作废
     */
	private Integer state;
    /**
     * 创建人id
     */
	private Long creatorId;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 最后创建人id
     */
	private Long lastAltorId;
    /**
     * 最后修改时间
     */
	private Date lastAlterTime;


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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getLastAltorId() {
		return lastAltorId;
	}

	public void setLastAltorId(Long lastAltorId) {
		this.lastAltorId = lastAltorId;
	}

	public Date getLastAlterTime() {
		return lastAlterTime;
	}

	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}

}
