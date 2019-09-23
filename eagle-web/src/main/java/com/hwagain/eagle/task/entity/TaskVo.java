package com.hwagain.eagle.task.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;

public class TaskVo {
	 /**
	  * 任务id
	  */
	@ApiModelProperty(example="11103576509762249")
	private Long taskId;
	/**
	 * 用户手机号
	 */
	@ApiModelProperty(example="15172312311")
	private String mobile;
	/**
	 * GPS经纬度
	 */
	@ApiModelProperty(example="longitue/latitude")
	private String gps;
	 /**
     * 开始位置
     */
	@ApiModelProperty(example="江西赣州市XXX县")
	private String startPositionAddress;
    /**
     * 开始城市
     */
	@ApiModelProperty(example="赣州市")
	private String startPositionCity;
    /**
     * 到达位置地址
     */
	@ApiModelProperty(example="江西赣州市XXX县")
	private String reachLocationAddress;
    /**
     * 到达城市
     */
	@ApiModelProperty(example="赣州市")
	private String reachLocationCity;
	/**
     * 照片类型。1：车牌，2：车头，3：货物，4：车尾
     */
	@ApiModelProperty(example="1(照片类型。1：车牌，2：车头，3：货物，4：车尾)")
	private Integer photoType;
	/**
     * 拍照时间
     */
	@ApiModelProperty(example="2019-04-01")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date photoTime;
	/**
     * 相对路径
     */
	@ApiModelProperty(example="20190225/592db0f3ffee406c89ab73e48450be5c_Tulips.jpg")
	private String relatedPath;
	 /**
     * 文件扩展名
     */
	@ApiModelProperty(example="jpg")
	private String ext;
    /**
     * 附件大小
     */
	@ApiModelProperty(example="60")
	private Integer size;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
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
	public Integer getPhotoType() {
		return photoType;
	}
	public void setPhotoType(Integer photoType) {
		this.photoType = photoType;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getPhotoTime() {
		return photoTime;
	}
	public void setPhotoTime(Date photoTime) {
		this.photoTime = photoTime;
	}
	public String getRelatedPath() {
		return relatedPath;
	}
	public void setRelatedPath(String relatedPath) {
		this.relatedPath = relatedPath;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
}
