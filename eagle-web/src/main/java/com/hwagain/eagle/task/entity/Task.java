package com.hwagain.eagle.task.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.enums.IdType;

/**
 * <p>
 * 任务表
注意：只有角色为HWAGAIN、DRIVER的数据才能写入TASK表
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="fd_id",type = IdType.AUTO)
	private Long fdId;
	@TableField("order_id")
	private String orderId;
	 /**
     * 磅单号
     */
	@TableField("pound_id")
	private String poundId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 司机名称
     */
	@TableField("driver_name")
	private String driverName;
    /**
     * 用户手机号
     */
	@TableField("user_mobile")
	private String userMobile;
    /**
     * 用户车牌号
     */
	@TableField("user_plate_number")
	private String userPlateNumber;
    /**
     * 车型
     */
	@TableField("car_type")
	private String carType;
    /**
     * 品种
     */
	private String material;
	/**
	 * 绝干吨
	 */
	@TableField("weight_tons")
	private BigDecimal weightTons;
    /**
     * 所属供应商
     */
	@TableField("supplier_id")
	private Long supplierId;
	 /**
     * 所属供应商
     */
	@TableField("supplier_name")
	private String supplierName;
    /**
     * 当前区域id
     */
	@TableField("current_region_id")
	private Long currentRegionId;
    /**
     * 目的地id。相见destination_info表。目前目的地要支持赣纸、崇左等
     */
	@TableField("destination_id")
	private Long destinationId;
    /**
     * 区域距离。单位：千米
     */
	@TableField("region_distance")
	private Integer regionDistance;
    /**
     * 照片数量
     */
	@TableField("photo_counts")
	private Integer photoCounts;
    /**
     * 照片验收结果。1：通过，2：不通过
     */
	@TableField("photo_acceptance_result")
	private Integer photoAcceptanceResult;
    /**
     * 在途距离，单位：千米
     */
	@TableField("travelling_distance")
	private Float travellingDistance;
    /**
     * 开始位置
     */
	@TableField("start_position_address")
	private String startPositionAddress;
    /**
     * 开始城市
     */
	@TableField("start_position_city")
	private String startPositionCity;
    /**
     * 到达位置地址
     */
	@TableField("reach_location_address")
	private String reachLocationAddress;
    /**
     * 到达城市
     */
	@TableField("reach_location_city")
	private String reachLocationCity;
    /**
     * 路径验收结果，系统自动计算，出发城市所取GPS个数不能低于30个。1：通过，2：不通过
     */
	@TableField("path_acceptance_result")
	private Integer pathAcceptanceResult;
	/**
     * 路径不合格原因
     */
	@TableField("path_reason")
	private Integer pathReason;
	/**
     * 补正申请
     */
	@TableField("corrections")
	private Integer corrections;
    /**
     * 状态。1：有效，2：作废
     */
	private Integer state;
    /**
     * 状态。0：未开始；1：进行中；2：待审核；3：已完成；4：取消；5：暂停；
     */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
     * 任务路线
     */
	@TableField("task_route")
	private String taskRoute;
	/**
     * 定点数量
     */
	@TableField("track_num")
	private Integer trackNum;
    /**
     * 定点持续时长
     */
	@TableField("track_time")
	private Integer trackTime;
    /**
     * 轨迹是否完整
     */
	@TableField("is_full")
	private Integer isFull;
	  /**
     * 中断总时长
     */
	@TableField("total_break_time")
	private Integer totalBreakTime;
    /**
     * 开始时间
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

	public String getPoundId() {
		return poundId;
	}

	public void setPoundId(String poundId) {
		this.poundId = poundId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserPlateNumber() {
		return userPlateNumber;
	}

	public void setUserPlateNumber(String userPlateNumber) {
		this.userPlateNumber = userPlateNumber;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public BigDecimal getWeightTons() {
		return weightTons;
	}

	public void setWeightTons(BigDecimal weightTons) {
		this.weightTons = weightTons;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getCurrentRegionId() {
		return currentRegionId;
	}

	public void setCurrentRegionId(Long currentRegionId) {
		this.currentRegionId = currentRegionId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

	public Integer getRegionDistance() {
		return regionDistance;
	}

	public void setRegionDistance(Integer regionDistance) {
		this.regionDistance = regionDistance;
	}

	public Integer getPhotoCounts() {
		return photoCounts;
	}

	public void setPhotoCounts(Integer photoCounts) {
		this.photoCounts = photoCounts;
	}

	public Integer getPhotoAcceptanceResult() {
		return photoAcceptanceResult;
	}

	public void setPhotoAcceptanceResult(Integer photoAcceptanceResult) {
		this.photoAcceptanceResult = photoAcceptanceResult;
	}

	public Float getTravellingDistance() {
		return travellingDistance;
	}

	public void setTravellingDistance(Float travellingDistance) {
		this.travellingDistance = travellingDistance;
	}

	public String getStartPositionAddress() {
		return startPositionAddress;
	}

	public void setStartPositionAddress(String startPositionAddress) {
		this.startPositionAddress = startPositionAddress;
	}

	public String getStartPositionCity() {
		return startPositionCity;
	}

	public void setStartPositionCity(String startPositionCity) {
		this.startPositionCity = startPositionCity;
	}

	public String getReachLocationAddress() {
		return reachLocationAddress;
	}

	public void setReachLocationAddress(String reachLocationAddress) {
		this.reachLocationAddress = reachLocationAddress;
	}

	public String getReachLocationCity() {
		return reachLocationCity;
	}

	public void setReachLocationCity(String reachLocationCity) {
		this.reachLocationCity = reachLocationCity;
	}

	public Integer getPathAcceptanceResult() {
		return pathAcceptanceResult;
	}

	public void setPathAcceptanceResult(Integer pathAcceptanceResult) {
		this.pathAcceptanceResult = pathAcceptanceResult;
	}

	public Integer getPathReason() {
		return pathReason;
	}

	public void setPathReason(Integer pathReason) {
		this.pathReason = pathReason;
	}

	public Integer getCorrections() {
		return corrections;
	}

	public void setCorrections(Integer corrections) {
		this.corrections = corrections;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskRoute() {
		return taskRoute;
	}

	public void setTaskRoute(String taskRoute) {
		this.taskRoute = taskRoute;
	}

	public Integer getTrackNum() {
		return trackNum;
	}

	public void setTrackNum(Integer trackNum) {
		this.trackNum = trackNum;
	}

	public Integer getTrackTime() {
		return trackTime;
	}

	public void setTrackTime(Integer trackTime) {
		this.trackTime = trackTime;
	}

	public Integer getIsFull() {
		return isFull;
	}

	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}

	public Integer getTotalBreakTime() {
		return totalBreakTime;
	}

	public void setTotalBreakTime(Integer totalBreakTime) {
		this.totalBreakTime = totalBreakTime;
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
