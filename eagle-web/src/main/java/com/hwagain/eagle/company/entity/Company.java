package com.hwagain.eagle.company.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;

/**
 * <p>
 * 公司信息表
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 公司名称
     */
	private String name;
    /**
     * 公司简称
     */
	@TableField("name_simple_name")
	private String nameSimpleName;
    /**
     * 状态。1：有效，2：无效
     */
	private Integer state;
    /**
     * 公司id，组织架构ID
     */
	@TableField("org_id")
	private String orgId;
    /**
     * 预设GPS位置1
     */
	private String gps1;
    /**
     * 预设GPS位置地址
     */
	@TableField("gps1_address")
	private String gps1Address;
    /**
     * 预设GPS位置2
     */
	private String gps2;
    /**
     * 预设GPS位置2地址
     */
	@TableField("gps2_address")
	private String gps2Address;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人id
     */
	@TableField("creator_id")
	private Long creatorId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSimpleName() {
		return nameSimpleName;
	}

	public void setNameSimpleName(String nameSimpleName) {
		this.nameSimpleName = nameSimpleName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getGps1() {
		return gps1;
	}

	public void setGps1(String gps1) {
		this.gps1 = gps1;
	}

	public String getGps1Address() {
		return gps1Address;
	}

	public void setGps1Address(String gps1Address) {
		this.gps1Address = gps1Address;
	}

	public String getGps2() {
		return gps2;
	}

	public void setGps2(String gps2) {
		this.gps2 = gps2;
	}

	public String getGps2Address() {
		return gps2Address;
	}

	public void setGps2Address(String gps2Address) {
		this.gps2Address = gps2Address;
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
