package com.hwagain.eagle.task.entity;

import com.hwagain.eagle.attachment.dto.AttachmentDto;
import com.hwagain.eagle.task.dto.TaskPhotoDto;

public class TaskPhotoVo {

	private TaskPhotoDto taskPhotoDto;
	private AttachmentDto attachmentDto;
	
	public TaskPhotoDto getTaskPhotoDto() {
		return taskPhotoDto;
	}
	public void setTaskPhotoDto(TaskPhotoDto taskPhotoDto) {
		this.taskPhotoDto = taskPhotoDto;
	}
	public AttachmentDto getAttachmentDto() {
		return attachmentDto;
	}
	public void setAttachmentDto(AttachmentDto attachmentDto) {
		this.attachmentDto = attachmentDto;
	}
	
	
	
}
