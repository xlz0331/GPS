package com.hwagain.eagle.user.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwagain.eagle.org.dto.OrgRelationshipDto;

import java.io.Serializable;


/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xionglz
 * @since 2019-04-14
 */
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
//    private OrgRelationshipDto orgRelationshipDto;
    private String companyName;
    private String deptName;

    /**
     * ID
     */
	private Long fdId;
    /**
     * 登录名。如果hwagain_employee=1，则是hwagain组织架构域名
     */
	private String loginname;
    /**
     * 是否华劲员工。1：是；2：否
     */
	private Integer hwagainEmployee;
    /**
     * 密码
     */
	private String password;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 上级Id
     */
	private Long parentId;
    /**
     * 车牌号码
     */
	private String plateNumber;
    /**
     * 手机IMEI
     */
	private String imei;
    /**
     * 用户类型。参见user_type表。如果是司机，imei等字段不能为空
     */
	private String userType;
    /**
     * 头像路径id
     */
	private Long headPathId;
    /**
     * 状态。1：有效；2：无效
     */
	private Integer state;
    /**
     * 1：Locked；2：正常状态；。Locked的原因如果是司机，IMEI不一致，需要解锁
     */
	private Integer locked;
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
     * 最后修改人id
     */
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Integer getHwagainEmployee() {
		return hwagainEmployee;
	}

	public void setHwagainEmployee(Integer hwagainEmployee) {
		this.hwagainEmployee = hwagainEmployee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getHeadPathId() {
		return headPathId;
	}

	public void setHeadPathId(Long headPathId) {
		this.headPathId = headPathId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	private String stateText="";	
	public String getStateText() {
		if(this.state!=null&&this.state==1){
			stateText="正常";
		}
		if(this.state!=null&&this.state==0){
			stateText="已失效";
		}
		return stateText;
	}
	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}



}
