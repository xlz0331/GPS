package com.hwagain.eagle.task.entity;

import java.util.Date;
import java.util.List;

import com.hwagain.eagle.correct.dto.CorrectDto;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.track.entity.TrackInfo;

public class TaskResultVo {
	private TaskDto taskDto;
	private CorrectDto correctDto;	
	private String audit1;
	private String audit2;
	private String audit3;
	private String auditTrack2;
	private String auditTrack3;
	private String auditor1;
	private String auditor2;
	private String auditor3;
	private Date auditTime1;
	private Date auditTime2;
	private Date auditTime3;
	private String auditReason1;
	private String auditReason2;
	private String auditReason3;
	private String auditReasonTrack2;
	private String auditReasonTrack3;
	private List<String> photoPath;
	private List<TrackInfo> trackInfos;
	public TaskDto getTaskDto() {
		return taskDto;
	}
	public void setTaskDto(TaskDto taskDto) {
		this.taskDto = taskDto;
	}
	public CorrectDto getCorrectDto() {
		return correctDto;
	}
	public void setCorrectDto(CorrectDto correctDto) {
		this.correctDto = correctDto;
	}
	public List<TrackInfo> getTrackInfos() {
		return trackInfos;
	}
	public void setTrackInfos(List<TrackInfo> trackInfos) {
		this.trackInfos = trackInfos;
	}
	public String getAudit1() {
		return audit1;
	}
	public void setAudit1(String audit1) {
		this.audit1 = audit1;
	}
	public String getAudit2() {
		return audit2;
	}
	public void setAudit2(String audit2) {
		this.audit2 = audit2;
	}
	public String getAudit3() {
		return audit3;
	}
	public void setAudit3(String audit3) {
		this.audit3 = audit3;
	}
	public String getAuditor1() {
		return auditor1;
	}
	public void setAuditor1(String auditor1) {
		this.auditor1 = auditor1;
	}
	public String getAuditor2() {
		return auditor2;
	}
	public void setAuditor2(String auditor2) {
		this.auditor2 = auditor2;
	}
	public String getAuditor3() {
		return auditor3;
	}
	public void setAuditor3(String auditor3) {
		this.auditor3 = auditor3;
	}
	public Date getAuditTime1() {
		return auditTime1;
	}
	public void setAuditTime1(Date auditTime1) {
		this.auditTime1 = auditTime1;
	}
	public Date getAuditTime2() {
		return auditTime2;
	}
	public void setAuditTime2(Date auditTime2) {
		this.auditTime2 = auditTime2;
	}
	public Date getAuditTime3() {
		return auditTime3;
	}
	public void setAuditTime3(Date auditTime3) {
		this.auditTime3 = auditTime3;
	}
	public String getAuditReason1() {
		return auditReason1;
	}
	public void setAuditReason1(String auditReason1) {
		this.auditReason1 = auditReason1;
	}
	public String getAuditReason2() {
		return auditReason2;
	}
	public void setAuditReason2(String auditReason2) {
		this.auditReason2 = auditReason2;
	}
	public String getAuditReason3() {
		return auditReason3;
	}
	public void setAuditReason3(String auditReason3) {
		this.auditReason3 = auditReason3;
	}
	public List<String> getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(List<String> photoPath) {
		this.photoPath = photoPath;
	}
	public String getAuditTrack2() {
		return auditTrack2;
	}
	public void setAuditTrack2(String auditTrack2) {
		this.auditTrack2 = auditTrack2;
	}
	public String getAuditTrack3() {
		return auditTrack3;
	}
	public void setAuditTrack3(String auditTrack3) {
		this.auditTrack3 = auditTrack3;
	}
	public String getAuditReasonTrack2() {
		return auditReasonTrack2;
	}
	public void setAuditReasonTrack2(String auditReasonTrack2) {
		this.auditReasonTrack2 = auditReasonTrack2;
	}
	public String getAuditReasonTrack3() {
		return auditReasonTrack3;
	}
	public void setAuditReasonTrack3(String auditReasonTrack3) {
		this.auditReasonTrack3 = auditReasonTrack3;
	}
	

}
