package com.hwagain.eagle.task.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * <p>
 * 任务表
注意：只有角色为HWAGAIN、DRIVER的数据才能写入TASK表
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
public class TaskDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long fdId;
	private String orderId;
	 /**
     * 磅单号
     */
	private String poundId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 司机名称
     */
	private String driverName;
    /**
     * 用户手机号
     */
	private String userMobile;
    /**
     * 用户车牌号
     */
	private String userPlateNumber;
    /**
     * 车型
     */
	private String carType;
    /**
     * 品种
     */
	private String material;
	/**
	 * 绝干吨
	 */
	private BigDecimal weightTons;
    /**
     * 所属供应商
     */
	private Long supplierId;

	private String supplierName;
    /**
     * 当前区域id
     */
	private Long currentRegionId;
    /**
     * 目的地id。相见destination_info表。目前目的地要支持赣纸、崇左等
     */
	private Long destinationId;
    /**
     * 区域距离。单位：千米
     */
	private Integer regionDistance;
    /**
     * 照片数量
     */
	private Integer photoCounts;
    /**
     * 照片验收结果。1：通过，2：不通过
     */
	private Integer photoAcceptanceResult;
    /**
     * 在途距离，单位：千米
     */
	private Float travellingDistance;
    /**
     * 开始位置
     */
	private String startPositionAddress;
	/**
	 * 出发片区
	 */
	private String currentRegion;
    /**
     * 开始城市
     */
	private String startPositionCity;
    /**
     * 到达位置地址
     */
	private String reachLocationAddress;
    /**
     * 到达城市
     */
	private String reachLocationCity;
    /**
     * 路径验收结果，系统自动计算，出发城市所取GPS个数不能低于30个。1：通过，2：不通过
     */
	private Integer pathAcceptanceResult;
	/**
     * 路径不合格原因
     */
	private Integer pathReason;
	/**
     * 补正申请
     */
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
	private String taskRoute;
	/**
     * 定点数量
     */
	private Integer trackNum;
    /**
     * 定点持续时长
     */
	private Integer trackTime;
    /**
     * 轨迹是否完整
     */
	private Integer isFull;
	   /**
     * 中断总时长
     */
	private Integer totalBreakTime;
    /**
     * 开始时间
     */
	private Date createTime;
    /**
     * 创建人id
     */
	private Long creatorId;
    /**
     * 最后修改时间
     */
	private Date lastAlterTime;
    /**
     * 最后修改人id
     */
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPoundId() {
		return poundId;
	}

	public void setPoundId(String poundId) {
		this.poundId = poundId;
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
	private String PhotoAcceptanceResultText="";
	public String getPhotoAcceptanceResultText(){
		if(this.photoAcceptanceResult!=null&&this.photoAcceptanceResult==0){
			PhotoAcceptanceResultText="未审核";
		}
		if(this.photoAcceptanceResult!=null&&this.photoAcceptanceResult==1){
			PhotoAcceptanceResultText="合格";
		}
		if(this.photoAcceptanceResult!=null&&this.photoAcceptanceResult==2){
			PhotoAcceptanceResultText="不合格";
		}
//		if(String.valueOf(this.photoAcceptanceResult) == null){
//			PhotoAcceptanceResultText="";
//		}
//		if(this.photoAcceptanceResult){
//			PhotoAcceptanceResultText="";
//		}
		return PhotoAcceptanceResultText;
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
	private String CorrectionsText="";
	public String getCorrectionsText(){
		if (this.corrections!=null) {
			if(this.corrections==3){
				CorrectionsText="";
			}
			if(this.corrections==1){
				CorrectionsText="已申请";
			}
			if(this.corrections==2){
				CorrectionsText="已申请";
			}
			if(this.corrections==3){
				CorrectionsText="未申请";
			}
			if(this.corrections==4){
				CorrectionsText="已申请";
			}
			if(this.corrections==5){
				CorrectionsText="已申请";
			}
			if(this.corrections==6){
				CorrectionsText="已申请";
			}
			if(this.corrections==7){
				CorrectionsText="已申请";
			}
			if(this.corrections==8){
				CorrectionsText="已申请";
			}
		}
		
		return CorrectionsText;
	}

	public void setPathAcceptanceResult(Integer pathAcceptanceResult) {
		this.pathAcceptanceResult = pathAcceptanceResult;
	}
	private String PathAcceptanceResultText="";
	public String getPathAcceptanceResultText(){
		if(this.pathAcceptanceResult==0){
			PathAcceptanceResultText="未审核";
		}
		if(this.pathAcceptanceResult==1){
			PathAcceptanceResultText="合格";
		}
		if(this.pathAcceptanceResult==2){
			PathAcceptanceResultText="不合格";
		}
//		if(String.valueOf(this.pathAcceptanceResult) == null){
//			PathAcceptanceResultText="";
//		}
		return PathAcceptanceResultText;
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
	private String StatusText="";
	public String getStatusText(){
		if(this.status==0){
			StatusText="未开始";
		}
		if(this.status==1){
			StatusText="运输中";
		}
		if(this.status==2){
			StatusText="待审核";
		}
		if(this.status==3){
			StatusText="审核中";
		}
		if(this.status==13&&this.corrections==2){
			StatusText="不合格";
		}
		if(this.status==13&&this.corrections==3){
			StatusText="不合格";
		}
		if(this.status==13&&this.corrections==4){
			StatusText="不合格";
		}
		if(this.status==13&&this.corrections==5){
			StatusText="不合格";
		}
		if(this.status==13&&this.corrections==6){
			StatusText="不合格";
		}
		if(this.status==13&&this.corrections==7){
			StatusText="不合格";
		}
		if(this.status==13&&this.corrections==8){
			StatusText="不合格";
		}
		if(this.status==10){
			StatusText="合格";
		}
		if(this.status==13&&this.corrections==1){
			StatusText="补正合格";
		}
		return StatusText;
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

	@JsonFormat(pattern="yyyy年MM月dd日 HH:mm",timezone="GMT+8")
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
	@JsonFormat(pattern="yyyy年MM月dd日 HH:mm",timezone="GMT+8")
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

	public String getCurrentRegion() {
		return currentRegion;
	}

	public void setCurrentRegion(String currentRegion) {
		this.currentRegion = currentRegion;
	}

}
