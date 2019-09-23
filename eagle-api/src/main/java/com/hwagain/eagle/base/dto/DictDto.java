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
public class DictDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fd_Id
     */
	private Long fdId;
    /**
     * 类型
     */
	private String typeName;
	private Integer itemNo;
	private String itemName;
	private String groupName;
	private String code;
	private Integer orderby;
	private String remark;
	private String text;
    /**
     * 创建人
     */
	private String createrId;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 最后修改人
     */
	private String lastAlterId;
    /**
     * 最后修改时间
     */
	private Date lastAlterTime;
	private Integer isDelete;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getLastAlterId() {
		return lastAlterId;
	}

	public void setLastAlterId(String lastAlterId) {
		this.lastAlterId = lastAlterId;
	}

	public Date getLastAlterTime() {
		return lastAlterTime;
	}

	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
