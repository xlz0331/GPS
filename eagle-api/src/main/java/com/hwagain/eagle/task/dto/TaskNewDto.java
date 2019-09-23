package com.hwagain.eagle.task.dto;

import java.io.Serializable;

public class TaskNewDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private  String mobile;
	private String plateNumber;
	private String driverName;
	private String material;
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
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
