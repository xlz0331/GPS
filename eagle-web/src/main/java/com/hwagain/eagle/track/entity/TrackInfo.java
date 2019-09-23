package com.hwagain.eagle.track.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
@TableName("track_info")
public class TrackInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 任务ID
     */
	@ApiModelProperty(example="任务ID:11103576509762249")
	@TableField("task_id")
	private Long taskId;
    /**
     * 纬度
     */
	@ApiModelProperty(example="纬度:22.810285")
	private Double latitude;
    /**
     * 经度
     */
	@ApiModelProperty(example="经度:108.390945")
	private Double longitude;
    /**
     * 与上一个位置间经过的距离
     */
	@ApiModelProperty(example="与上一个位置间经过的距离:100")
	private Double distance;
    /**
     * 方向（0-359度）
     */
	@ApiModelProperty(example="方向（0-359度）:30")
	private Integer direction;
    /**
     * 高度（米）
     */
	@ApiModelProperty(example=" 高度（米）:30")
	private Double height;
    /**
     * 时速（千米/时）
     */
	@ApiModelProperty(example=" 时速（千米/时）:100")
	private Double speed;
    /**
     * 楼层
     */
	@ApiModelProperty(example="楼层")
	private String floor;
    /**
     * 定位精度（米）
     */
	@ApiModelProperty(example="定位精度（米）:10")
	private Double radius;
    /**
     * 定位时间
输入的loc_time不能大于当前服务端时间10分钟以上，即不支持存未来的轨迹点
     */
	@ApiModelProperty(example="定位时间输入的loc_time不能大于当前服务端时间10分钟以上，即不支持存未来的轨迹点")
	@TableField("loc_time")
	private Date locTime;
	  /**
     * 定位时间
     */
	@ApiModelProperty(example="定位时间")
	@TableField("save_time")
	private Date saveTime;
    /**
     *  坐标类型
		默认值：bd09ll
		该字段用于描述上传的坐标类型。可选值为：
		wgs84：GPS 坐标
		gcj02：国测局加密坐标
		bd09ll：百度经纬度坐标

     */
	@ApiModelProperty(example="坐标类型默认值：bd09ll")
	@TableField("coord_type_input")
	private String coordTypeInput;
    /**
     * 远端IP
     */
	@ApiModelProperty(example="远端IP")
	@TableField("remote_ip")
	private String remoteIp;
	 /**
     * 一审
     */
	private Integer audit1;
	/**
	 * 说明
	 */
	@TableField("audit_reason1")
	private String auditReason1;
    /**
     * 一审人
     */
	private String auditor1;
    /**
     * 一审时间
     */
	@TableField("audit_time1")
	private Date auditTime1;
    /**
     * 二审
     */
	private Integer audit2;
	/**
	 * 说明
	 */
	@TableField("audit_reason2")
	private String auditReason2;
    /**
     * 二审人
     */
	private String auditor2;
    /**
     * 二审时间
     */
	@TableField("audit_time2")
	private Date auditTime2;
    /**
     * 三审
     */
	private Integer audit3;
	/**
	 * 说明
	 */
	@TableField("audit_reason3")
	private String auditReason3;
    /**
     * 三审人
     */
	private String auditor3;
    /**
     * 三审时间
     */
	@TableField("audit_time3")
	private Date auditTime3;
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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getLocTime() {
		return locTime;
	}

	public void setLocTime(Date locTime) {
		this.locTime = locTime;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public String getCoordTypeInput() {
		return coordTypeInput;
	}

	public void setCoordTypeInput(String coordTypeInput) {
		this.coordTypeInput = coordTypeInput;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
	public Integer getAudit1() {
		return audit1;
	}

	public void setAudit1(Integer audit1) {
		this.audit1 = audit1;
	}

	public String getAuditReason1() {
		return auditReason1;
	}

	public void setAuditReason1(String auditReason1) {
		this.auditReason1 = auditReason1;
	}

	public String getAuditor1() {
		return auditor1;
	}

	public void setAuditor1(String auditor1) {
		this.auditor1 = auditor1;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getAuditTime1() {
		return auditTime1;
	}

	public void setAuditTime1(Date auditTime1) {
		this.auditTime1 = auditTime1;
	}

	public Integer getAudit2() {
		return audit2;
	}

	public void setAudit2(Integer audit2) {
		this.audit2 = audit2;
	}

	public String getAuditReason2() {
		return auditReason2;
	}

	public void setAuditReason2(String auditReason2) {
		this.auditReason2 = auditReason2;
	}

	public String getAuditor2() {
		return auditor2;
	}

	public void setAuditor2(String auditor2) {
		this.auditor2 = auditor2;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getAuditTime2() {
		return auditTime2;
	}

	public void setAuditTime2(Date auditTime2) {
		this.auditTime2 = auditTime2;
	}

	public Integer getAudit3() {
		return audit3;
	}

	public void setAudit3(Integer audit3) {
		this.audit3 = audit3;
	}

	public String getAuditReason3() {
		return auditReason3;
	}

	public void setAuditReason3(String auditReason3) {
		this.auditReason3 = auditReason3;
	}

	public String getAuditor3() {
		return auditor3;
	}

	public void setAuditor3(String auditor3) {
		this.auditor3 = auditor3;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getAuditTime3() {
		return auditTime3;
	}

	public void setAuditTime3(Date auditTime3) {
		this.auditTime3 = auditTime3;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH-mm-ss",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
