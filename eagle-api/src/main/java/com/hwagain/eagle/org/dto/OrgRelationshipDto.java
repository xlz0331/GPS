package com.hwagain.eagle.org.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-04-17
 */
public class OrgRelationshipDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	private String fdId;
    /**
     * 用户ID
     */
	private String UserID;
    /**
     * RelaType
     */
	private String RelaType;
    /**
     * UserName
     */
	private String UserName;
    /**
     * DeptID
     */
	private String DeptID;
    /**
     * DeptID_PS
     */
	private String DeptIDPS;
    /**
     * PosiID
     */
	private String PosiID;
    /**
     * PosiName
     */
	private String PosiName;
    /**
     * StatID
     */
	private Integer StatID;
    /**
     * StatName
     */
	private String StatName;
    /**
     * DeptLeader
     */
	private Integer DeptLeader;
    /**
     * UserOrder
     */
	private Integer UserOrder;
    /**
     * RelaPrimary
     */
	private Integer RelaPrimary;
    /**
     * EMPL_RCD
     */
	private Integer emplRcd;
	private Date effdt;
    /**
     * JOB_INDICATOR
     */
	private String jobIndicator;
    /**
     * POSITION_NBR
     */
	private String positionNbr;
    /**
     * SUPV_LVL_ID
     */
	private String supvLvlId;
    /**
     * SUPV_LVL_DESCR
     */
	private String supvLvlDescr;
    /**
     * HR_STATUS
     */
	private String hrStatus;
    /**
     * DeptID_L1
     */
	private String DeptIDL1;
    /**
     * DeptID_L2
     */
	private String DeptIDL2;
    /**
     * DeptID_L3
     */
	private String DeptIDL3;
    /**
     * DeptID_L4
     */
	private String DeptIDL4;
    /**
     * DeptID_L5
     */
	private String DeptIDL5;
    /**
     * DeptID_L6
     */
	private String DeptIDL6;
    /**
     * CompanyName
     */
	private String CompanyName;
	private String deptname;
	private String docCreateId;
	private Date docCreateTime;
	private String docLastUpdateId;
	private Date docLastUpdateTime;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String UserID) {
		this.UserID = UserID;
	}

	public String getRelaType() {
		return RelaType;
	}

	public void setRelaType(String RelaType) {
		this.RelaType = RelaType;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getDeptID() {
		return DeptID;
	}

	public void setDeptID(String DeptID) {
		this.DeptID = DeptID;
	}

	public String getDeptIDPS() {
		return DeptIDPS;
	}

	public void setDeptIDPS(String DeptIDPS) {
		this.DeptIDPS = DeptIDPS;
	}

	public String getPosiID() {
		return PosiID;
	}

	public void setPosiID(String PosiID) {
		this.PosiID = PosiID;
	}

	public String getPosiName() {
		return PosiName;
	}

	public void setPosiName(String PosiName) {
		this.PosiName = PosiName;
	}

	public Integer getStatID() {
		return StatID;
	}

	public void setStatID(Integer StatID) {
		this.StatID = StatID;
	}

	public String getStatName() {
		return StatName;
	}

	public void setStatName(String StatName) {
		this.StatName = StatName;
	}

	public Integer getDeptLeader() {
		return DeptLeader;
	}

	public void setDeptLeader(Integer DeptLeader) {
		this.DeptLeader = DeptLeader;
	}

	public Integer getUserOrder() {
		return UserOrder;
	}

	public void setUserOrder(Integer UserOrder) {
		this.UserOrder = UserOrder;
	}

	public Integer getRelaPrimary() {
		return RelaPrimary;
	}

	public void setRelaPrimary(Integer RelaPrimary) {
		this.RelaPrimary = RelaPrimary;
	}

	public Integer getEmplRcd() {
		return emplRcd;
	}

	public void setEmplRcd(Integer emplRcd) {
		this.emplRcd = emplRcd;
	}

	public Date getEffdt() {
		return effdt;
	}

	public void setEffdt(Date effdt) {
		this.effdt = effdt;
	}

	public String getJobIndicator() {
		return jobIndicator;
	}

	public void setJobIndicator(String jobIndicator) {
		this.jobIndicator = jobIndicator;
	}

	public String getPositionNbr() {
		return positionNbr;
	}

	public void setPositionNbr(String positionNbr) {
		this.positionNbr = positionNbr;
	}

	public String getSupvLvlId() {
		return supvLvlId;
	}

	public void setSupvLvlId(String supvLvlId) {
		this.supvLvlId = supvLvlId;
	}

	public String getSupvLvlDescr() {
		return supvLvlDescr;
	}

	public void setSupvLvlDescr(String supvLvlDescr) {
		this.supvLvlDescr = supvLvlDescr;
	}

	public String getHrStatus() {
		return hrStatus;
	}

	public void setHrStatus(String hrStatus) {
		this.hrStatus = hrStatus;
	}

	public String getDeptIDL1() {
		return DeptIDL1;
	}

	public void setDeptIDL1(String DeptIDL1) {
		this.DeptIDL1 = DeptIDL1;
	}

	public String getDeptIDL2() {
		return DeptIDL2;
	}

	public void setDeptIDL2(String DeptIDL2) {
		this.DeptIDL2 = DeptIDL2;
	}

	public String getDeptIDL3() {
		return DeptIDL3;
	}

	public void setDeptIDL3(String DeptIDL3) {
		this.DeptIDL3 = DeptIDL3;
	}

	public String getDeptIDL4() {
		return DeptIDL4;
	}

	public void setDeptIDL4(String DeptIDL4) {
		this.DeptIDL4 = DeptIDL4;
	}

	public String getDeptIDL5() {
		return DeptIDL5;
	}

	public void setDeptIDL5(String DeptIDL5) {
		this.DeptIDL5 = DeptIDL5;
	}

	public String getDeptIDL6() {
		return DeptIDL6;
	}

	public void setDeptIDL6(String DeptIDL6) {
		this.DeptIDL6 = DeptIDL6;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String CompanyName) {
		this.CompanyName = CompanyName;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getDocCreateId() {
		return docCreateId;
	}

	public void setDocCreateId(String docCreateId) {
		this.docCreateId = docCreateId;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocLastUpdateId() {
		return docLastUpdateId;
	}

	public void setDocLastUpdateId(String docLastUpdateId) {
		this.docLastUpdateId = docLastUpdateId;
	}

	public Date getDocLastUpdateTime() {
		return docLastUpdateTime;
	}

	public void setDocLastUpdateTime(Date docLastUpdateTime) {
		this.docLastUpdateTime = docLastUpdateTime;
	}

}
