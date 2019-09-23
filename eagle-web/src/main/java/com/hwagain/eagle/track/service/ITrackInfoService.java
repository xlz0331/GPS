package com.hwagain.eagle.track.service;

import java.util.List;
import java.util.Map;

import com.hwagain.eagle.task.entity.TaskAnalyze;
import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackAnalyze;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.entity.TrackInfoVo;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
public interface ITrackInfoService extends IService<TrackInfo> {

	String save(List<TrackInfo> trackInfoDtos);

	List<TrackInfoDto> getListByTaskId(Long taskId);

	String warnDriver();

	String sendSms(String data);

	List<TrackInfoDto> getBugListByTaskId(Long taskId);

	String trackAudit(Long taskId, Integer pathAcceptanceResult, String reason);

	String trackAudit3(Long taskId, Integer pathAcceptanceResult, String reason);

	String trackAudit2(Long taskId, Integer pathAcceptanceResult, String reason);

	String trackAudit1(Long taskId, Integer pathAcceptanceResult, String reason);

	List<String> checkStartPoint(Long TaskId);

	TrackInfo getTrackPointByTaskId(Long taskId);

	List<TrackInfoVo> findTrackByTaskId(Long taskId);

	List<TrackAnalyze> findAllTrackAnalyze(String startTime,String endTime,String status);

	List<TrackAnalyze> findAllTaskTrackAnalyze(String startTime, String endTime,String supplierName);

	List<TaskAnalyze> findAllTaskAnalyze(String startTime, String endTime);

	String taskAnalyzeSync();

//	List<TrackInfoDto> checkBugPoint(Long taskId);
	
}
