package com.hwagain.eagle.correct.entity;

import java.util.List;

import com.hwagain.eagle.attachment.dto.AttachmentDto;
import com.hwagain.eagle.correct.dto.CorrectDto;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.entity.TaskPhotosVo;
import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackInfo;

public class CorrectVo {
	private TaskDto taskDto;
	private CorrectDto correctDto;
	private List<TrackInfo> trackInfos;
//	private List<TaskPhotoDto> taskPhotoDtos;
	private List<TaskPhotoVo> taskPhotoVos;
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
	public List<TaskPhotoVo> getTaskPhotoVos() {
		return taskPhotoVos;
	}
	public void setTaskPhotoVos(List<TaskPhotoVo> taskPhotoVos) {
		this.taskPhotoVos = taskPhotoVos;
	}

	
}
