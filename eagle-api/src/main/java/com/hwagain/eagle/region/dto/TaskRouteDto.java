package com.hwagain.eagle.region.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-08-28
 */
public class TaskRouteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fdId
     */
	private Long fdId;
    /**
     * 片区Id
     */
	private Long regionId;
    /**
     * 省
     */
	private String province;
    /**
     * 市
     */
	private String city;
    /**
     * 区
     */
	private String county;
    /**
     * 加工厂/收购点
     */
	private String purchasePoint;
    /**
     * 地址
     */
	private String address;
    /**
     * 路名
     */
	private String roadName;
    /**
     * 纬度
     */
	private Double latitude;
    /**
     * 经度
     */
	private Double longitude;
    /**
     * 线路总距离
     */
	private Double totalDistance;
    /**
     * 距离
     */
	private Double distance;
    /**
     * 起点（纬度,经度）
     */
	private String origin;
    /**
     * 路线类型（0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费）
     */
	private String tactics;
    /**
     * 标识
     */
	private Integer state;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 创建人
     */
	private String creatorId;
    /**
     * 修改时间
     */
	private Date lastAlterTime;
    /**
     * 修改人
     */
	private String lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
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

	public String getPurchasePoint() {
		return purchasePoint;
	}

	public void setPurchasePoint(String purchasePoint) {
		this.purchasePoint = purchasePoint;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
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

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTactics() {
		return tactics;
	}

	public void setTactics(String tactics) {
		this.tactics = tactics;
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
