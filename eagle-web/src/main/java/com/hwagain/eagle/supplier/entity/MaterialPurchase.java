package com.hwagain.eagle.supplier.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-04-09
 */
@TableName("material_purchase")
public class MaterialPurchase implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 原料名称
     */
	private String material;
    /**
     * 收购量
     */
	@TableField("purchase_num")
	private BigDecimal purchaseNum;
    /**
     * 收购下限
     */
	@TableField("min_purchase_num")
	private BigDecimal minPurchaseNum;
    /**
     * 收购上限
     */
	@TableField("max_purchase_num")
	private BigDecimal maxPurchaseNum;
    /**
     * 状态
     */
	private Integer state;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人
     */
	@TableField("creator_id")
	private Long creatorId;
    /**
     * 修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
    /**
     * 修改人
     */
	@TableField("last_altor_id")
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public BigDecimal getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(BigDecimal purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	public BigDecimal getMinPurchaseNum() {
		return minPurchaseNum;
	}

	public void setMinPurchaseNum(BigDecimal minPurchaseNum) {
		this.minPurchaseNum = minPurchaseNum;
	}

	public BigDecimal getMaxPurchaseNum() {
		return maxPurchaseNum;
	}

	public void setMaxPurchaseNum(BigDecimal maxPurchaseNum) {
		this.maxPurchaseNum = maxPurchaseNum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
