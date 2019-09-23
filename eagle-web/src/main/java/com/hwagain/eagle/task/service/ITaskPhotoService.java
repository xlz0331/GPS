package com.hwagain.eagle.task.service;

import java.util.List;

import com.hwagain.eagle.task.dto.RptTask;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
public interface ITaskPhotoService extends IService<TaskPhoto> {

	String save(TaskPhotoVo taskPhotoVo);

	List<TaskPhotoVo> findByParam(Long taskId, String photoType);

	RptTask getPhotoDetail(Long photoId);

	TaskPhotoDto auditPhoto(String auditType,Integer acceptanceResult, String reason,Long fdId);

	String upadteResult(Long taskId,Integer acceptanceResult);

	String photoAudit(List<TaskPhotoDto> taskPhotoDtos);

	String photoAudit1(List<TaskPhotoDto> taskPhotoDtos);

	String photoAudit2(List<TaskPhotoDto> taskPhotoDtos,Long taskId,Integer pathAcceptanceResult,String reason);

	String photoAudit3(List<TaskPhotoDto> taskPhotoDtos,Long taskId,Integer pathAcceptanceResult,String reason);

	List<TaskPhotoVo> findByParam2(Long taskId, String photoType);

	String returnAudit();

	TaskPhoto getStartPoint(Long taskId);

	String startPoint();

	List<TaskPhoto> deletePoint();

	String endPoint();

	
	
}
