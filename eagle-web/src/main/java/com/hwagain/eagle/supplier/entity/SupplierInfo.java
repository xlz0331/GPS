package com.hwagain.eagle.supplier.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 供应商信息表
 * </p>
 *
 * @author lufl
 * @since 2019-02-22
 */
@TableName("supplier_info")
public class SupplierInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 供应商名称
     */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
     * 供应商编码
     */
	private String code;
    /**
     * 法人
     */
	@TableField("legal_person")
	private String legalPerson;
    /**
     * 法人身份证号码
     */
	@TableField("legal_person_id_card_no")
	private String legalPersonIdCardNo;
	@TableField("id_number")
	private String idNumber;
    /**
     * 税号
     */
	@TableField("duty_paragraph")
	private String dutyParagraph;
    /**
     * 联系电话
     */
	private String contact;
    /**
     * 联系人手机
     */
	private String mobile;
    /**
     * 固定电话
     */
	private String phone;
    /**
     * 所属区域id。具体参见region_info表
     */
	@TableField("region_id")
	private Long regionId;
    /**
     * 状态。1：有效，2：无效
     */
	private Integer state;
    /**
     * 开户行名称
     */
	@TableField("bank_name")
	private String bankName;
    /**
     * 开户行账号
     */
	@TableField("bank_account")
	private String bankAccount;
    /**
     * 开户行户名
     */
	@TableField("bank_account_name")
	private String bankAccountName;
    /**
     * 开户行地址
     */
	@TableField("bank_address")
	private String bankAddress;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalPersonIdCardNo() {
		return legalPersonIdCardNo;
	}

	public void setLegalPersonIdCardNo(String legalPersonIdCardNo) {
		this.legalPersonIdCardNo = legalPersonIdCardNo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getDutyParagraph() {
		return dutyParagraph;
	}

	public void setDutyParagraph(String dutyParagraph) {
		this.dutyParagraph = dutyParagraph;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
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
