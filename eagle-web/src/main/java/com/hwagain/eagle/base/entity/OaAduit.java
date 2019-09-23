package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-03-26
 */
@TableName("oa_aduit")
public class OaAduit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	@TableField("fd_id")
	private Long fdId;
	@TableField("oa_code")
	private String oaCode;
    /**
     * (传到OA系统中间表的meno)
     */
	private String platform;
    /**
     * 数据库表名
     */
	@TableField("table_name")
	private String tableName;
    /**
     * 流程名称
     */
	@TableField("flow_name")
	private String flowName;
    /**
     * OA流程节点名称
     */
	@TableField("node_name")
	private String nodeName;
    /**
     * 审核审批人工号
     */
	@TableField("emp_no")
	private String empNo;
    /**
     * 审核审批人姓名
     */
	@TableField("emp_name")
	private String empName;
    /**
     * OA单据编号
     */
	@TableField("flow_djbh")
	private String flowDjbh;
    /**
     * OA单据流水号
     */
	@TableField("flow_djlsh")
	private String flowDjlsh;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 创建人
     */
	@TableField("creater_id")
	private String createrId;
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

	public String getOaCode() {
		return oaCode;
	}

	public void setOaCode(String oaCode) {
		this.oaCode = oaCode;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getFlowDjbh() {
		return flowDjbh;
	}

	public void setFlowDjbh(String flowDjbh) {
		this.flowDjbh = flowDjbh;
	}

	public String getFlowDjlsh() {
		return flowDjlsh;
	}

	public void setFlowDjlsh(String flowDjlsh) {
		this.flowDjlsh = flowDjlsh;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
