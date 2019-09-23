package com.hwagain.eagle.region.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-05-09
 */
@TableName("region_pathway")
public class RegionPathway implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 省
     */
	@ApiModelProperty(example="广西")
	private String province;
    /**
     * 市
     */
	@ApiModelProperty(example="南宁市")
	private String city;
    /**
     * 区
     */
	@ApiModelProperty(example="青秀区")
	private String county;
    /**
     * 地址
     */
	@ApiModelProperty(example="民族大道131号")
	private String address;
    /**
     * 路线
     */
	@ApiModelProperty(example="XXX高速路线")
	private String haulway;
    /**
     * 纬度
     */
	@ApiModelProperty(example="22.85648")
	private Double latitude;
    /**
     * 经度
     */
	@ApiModelProperty(example="108.123445")
	private Double longitude;
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
	private String creatorId;
    /**
     * 修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
    /**
     * 修改人
     */
	@TableField("last_altor_id")
	private String lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHaulway() {
		return haulway;
	}

	public void setHaulway(String haulway) {
		this.haulway = haulway;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getLastAlterTime() {
		return lastAlterTime;
	}

	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}

	public String getLastAltorId() {
		return lastAltorId;
	}

	public void setLastAltorId(String lastAltorId) {
		this.lastAltorId = lastAltorId;
	}

}
