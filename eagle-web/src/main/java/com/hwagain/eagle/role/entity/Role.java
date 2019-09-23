package com.hwagain.eagle.role.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 角色
     */
	private String role;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 状态.1:有效，2：已作废
     */
	private Integer state;
    /**
     * 备注
     */
	private String remark;
    /**
     * 创建人Id
     */
	@TableField("creator_id")
	private Long creatorId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 最后修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
    /**
     * 最后修改人id
     */
	@TableField("last_altor_id")
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
