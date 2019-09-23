package com.hwagain.eagle.task.vo;

import java.util.List;

import com.hwagain.eagle.base.entity.LogPhoneInfo;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.track.entity.TrackInfo;

public class TaskVO extends TaskDto{
   private List<TrackInfo> list;
   private LogPhoneInfo logPhoneInfo;

public List<TrackInfo> getList() {
	return list;
}

public void setList(List<TrackInfo> list) {
	this.list = list;
}

public LogPhoneInfo getLogPhoneInfo() {
	return logPhoneInfo;
}

public void setLogPhoneInfo(LogPhoneInfo logPhoneInfo) {
	this.logPhoneInfo = logPhoneInfo;
}

}
