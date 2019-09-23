package com.hwagain.eagle.task.entity;

import java.io.Serializable;

public class TaskAnalyze extends Task implements Serializable {
	private static final long serialVersionUID = 1L;
	private String photoAcceptanceResultText;
	private String pathAcceptanceResultText;
	private String statusText;
	private String photoType;
	private String photoAudit1;
	private String photoAuditReason1;
	private String auditor1;
	private String photoAudit2;
	private String photoAuditReason2;
	private String auditor2;
	private String photoAudit3;
	private String photoAuditReason3;
	private String auditor3;
	private String trackAudit2;
	private String trackAuditReason2;
	private String trackAudit3;
	private String trackAuditReason3;
	private Integer bugPoint;
	private Integer totalTime;
	public String getPhotoType() {
		return photoType;
	}
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	public String getPhotoAudit1() {
		return photoAudit1;
	}
	public void setPhotoAudit1(String photoAudit1) {
		this.photoAudit1 = photoAudit1;
	}
	public String getPhotoAuditReason1() {
		return photoAuditReason1;
	}
	public void setPhotoAuditReason1(String photoAuditReason1) {
		this.photoAuditReason1 = photoAuditReason1;
	}
	public String getAuditor1() {
		return auditor1;
	}
	public void setAuditor1(String auditor1) {
		this.auditor1 = auditor1;
	}
	public String getPhotoAudit2() {
		return photoAudit2;
	}
	public void setPhotoAudit2(String photoAudit2) {
		this.photoAudit2 = photoAudit2;
	}
	public String getPhotoAuditReason2() {
		return photoAuditReason2;
	}
	public void setPhotoAuditReason2(String photoAuditReason2) {
		this.photoAuditReason2 = photoAuditReason2;
	}
	public String getAuditor2() {
		return auditor2;
	}
	public void setAuditor2(String auditor2) {
		this.auditor2 = auditor2;
	}
	public String getPhotoAudit3() {
		return photoAudit3;
	}
	public void setPhotoAudit3(String photoAudit3) {
		this.photoAudit3 = photoAudit3;
	}
	public String getPhotoAuditReason3() {
		return photoAuditReason3;
	}
	public void setPhotoAuditReason3(String photoAuditReason3) {
		this.photoAuditReason3 = photoAuditReason3;
	}
	public String getAuditor3() {
		return auditor3;
	}
	public void setAuditor3(String auditor3) {
		this.auditor3 = auditor3;
	}
	public String getTrackAudit2() {
		return trackAudit2;
	}
	public void setTrackAudit2(String trackAudit2) {
		this.trackAudit2 = trackAudit2;
	}
	public String getTrackAuditReason2() {
		return trackAuditReason2;
	}
	public void setTrackAuditReason2(String trackAuditReason2) {
		this.trackAuditReason2 = trackAuditReason2;
	}
	public String getTrackAudit3() {
		return trackAudit3;
	}
	public void setTrackAudit3(String trackAudit3) {
		this.trackAudit3 = trackAudit3;
	}
	public String getTrackAuditReason3() {
		return trackAuditReason3;
	}
	public void setTrackAuditReason3(String trackAuditReason3) {
		this.trackAuditReason3 = trackAuditReason3;
	}
	public Integer getBugPoint() {
		return bugPoint;
	}
	public void setBugPoint(Integer bugPoint) {
		this.bugPoint = bugPoint;
	}
	public Integer getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
	public String getPhotoAcceptanceResultText() {
		return photoAcceptanceResultText;
	}
	public void setPhotoAcceptanceResultText(String photoAcceptanceResultText) {
		this.photoAcceptanceResultText = photoAcceptanceResultText;
	}
	public String getPathAcceptanceResultText() {
		return pathAcceptanceResultText;
	}
	public void setPathAcceptanceResultText(String pathAcceptanceResultText) {
		this.pathAcceptanceResultText = pathAcceptanceResultText;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	
}
