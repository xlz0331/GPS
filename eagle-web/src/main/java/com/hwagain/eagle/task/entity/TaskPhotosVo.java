package com.hwagain.eagle.task.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
@ToString
public class TaskPhotosVo {
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
	
	/**
	 * GPS
	 * @return
	 */
	@ApiModelProperty(example="108.390766/22.820541")
	private String gps;
	public Integer getPhotoType() {
		return photoType;
	}
	public void setPhotoType(Integer photoType) {
		this.photoType = photoType;
	}
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
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	
}
