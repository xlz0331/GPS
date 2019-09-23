package com.hwagain.eagle.base.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-03-21
 */
public class LogLoginUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
	private Long userId;
	private String userName;
	private Long parentId;
	private Integer state;
	private Date createTime;
	private Long creatorId;
	private Date lastAlterTime;
	private Long lastAltorId;


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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	private String stateText="";
	public String getStateText(){
		if(this.state!=null&&this.state==1){
			stateText="在线";
		}
		if(this.state!=null&&this.state==2){
			stateText="离线";
		}
		return stateText;
		
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
