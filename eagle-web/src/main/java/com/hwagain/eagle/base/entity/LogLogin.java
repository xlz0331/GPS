package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户登录日志表
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@TableName("log_login")
public class LogLogin implements Serializable {

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
     * 会话ID。如果开发平台没有，则生成一个
     */
	@TableField("session_id")
	private String sessionId;
    /**
     * 登录时间
     */
	@TableField("login_time")
	private Date loginTime;
    /**
     * 登出时间
     */
	@TableField("logout_time")
	private Date logoutTime;
    /**
     * 终端类型。0：未知，1：pc端，2：移动端
     */
	@TableField("terminal_type")
	private Integer terminalType;
    /**
     * 移动端信息
     */
	@TableField("terminal_info")
	private String terminalInfo;
    /**
     * 创建时间
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getTerminalInfo() {
		return terminalInfo;
	}

	public void setTerminalInfo(String terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
