package com.hwagain.eagle.track.entity;

import java.io.Serializable;
import java.util.Date;

import com.hwagain.eagle.base.dto.LogPhoneInfoDto;
import com.hwagain.framework.mybatisplus.annotations.TableField;

public class TrackAnalyze extends LogPhoneInfoDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer timeNum;
	private Integer trackNum;
	private String isFull;
	private Integer loginNum;
	private Integer status;
	private Date lastAlterTime;
	private String supplierName;
	private Integer totalTime;
	private String isContinue;
	private String startAddress;
	private String startcity;
	public Integer getTimeNum() {
		return timeNum;
	}
	public void setTimeNum(Integer timeNum) {
		this.timeNum = timeNum;
	}
	public Integer getTrackNum() {
		return trackNum;
	}
	public void setTrackNum(Integer trackNum) {
		this.trackNum = trackNum;
	}
	public String getIsFull() {
		return isFull;
	}
	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}
	public Integer getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	private String StatusText="";
	public String getStatusText(){
		if (this.status!=null) {
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
			if(this.status==13){
				StatusText="不合格";
			}
			if(this.status==10){
				StatusText="合格";
			}
			
		}
		return StatusText;
	}
	public Date getLastAlterTime() {
		return lastAlterTime;
	}
	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Integer getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
	public String getIsContinue() {
		return isContinue;
	}
	public void setIsContinue(String isContinue) {
		this.isContinue = isContinue;
	}
	public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public String getStartcity() {
		return startcity;
	}
	public void setStartcity(String startcity) {
		this.startcity = startcity;
	} 

}
