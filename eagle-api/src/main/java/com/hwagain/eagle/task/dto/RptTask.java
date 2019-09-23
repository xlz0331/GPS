package com.hwagain.eagle.task.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RptTask implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long taskId;

	private Long photoId;
	
	private Integer photoType;
	
	private String material;

	private String region;
	
	private String address;
	
	private Long creatorId;
	
	private String dirverName;
	
	private String carType;
	
	private Date createTime;
	
	private String mobile;
	
	private String plateNumber;
	
	private Integer photoNumber;
	
	private String remark;
	
	private String acceptanceResult;
	
	private String reason;
	
	private String visapoint;
	
	private Integer distance;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Integer getPhotoType() {
		return photoType;
	}

	public void setPhotoType(Integer photoType) {
		this.photoType = photoType;
	}
	private String photoTypeText="";
	public String getPhotoTypeText(){
		if(this.photoType!=null&&this.photoType==1){
			photoTypeText="车牌";
		}
		if(this.photoType!=null&&this.photoType==2){
			photoTypeText="车头";
		}
		if(this.photoType!=null&&this.photoType==3){
			photoTypeText="货物";
		}
		if(this.photoType!=null&&this.photoType==4){
			photoTypeText="车尾";
		}
		return photoTypeText;
	}
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getDirverName() {
		return dirverName;
	}

	public void setDirverName(String dirverName) {
		this.dirverName = dirverName;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Integer getPhotoNumber() {
		return photoNumber;
	}

	public void setPhotoNumber(Integer photoNumber) {
		this.photoNumber = photoNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAcceptanceResult() {
		return acceptanceResult;
	}

	public void setAcceptanceResult(String acceptanceResult) {
		this.acceptanceResult = acceptanceResult;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getVisapoint() {
		return visapoint;
	}

	public void setVisapoint(String visapoint) {
		this.visapoint = visapoint;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance =distance;
	}
	
	
}
