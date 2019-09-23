package com.hwagain.eagle.base.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-03-26
 */
public class OaAduitDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	private Long fdId;
	private String oaCode;
    /**
     * (传到OA系统中间表的meno)
     */
	private String platform;
    /**
     * 数据库表名
     */
	private String tableName;
    /**
     * 流程名称
     */
	private String flowName;
    /**
     * OA流程节点名称
     */
	private String nodeName;
    /**
     * 审核审批人工号
     */
	private String empNo;
    /**
     * 审核审批人姓名
     */
	private String empName;
    /**
     * OA单据编号
     */
	private String flowDjbh;
    /**
     * OA单据流水号
     */
	private String flowDjlsh;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 创建人
     */
	private String createrId;
    /**
     * 创建时间
     */
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
