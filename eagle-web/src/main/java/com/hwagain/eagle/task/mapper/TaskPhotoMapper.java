package com.hwagain.eagle.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.task.dto.RptTask;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
public interface TaskPhotoMapper extends BaseMapper<TaskPhoto> {
	public RptTask getPhotoDetail(@Param("photoId") Long photoId);
	public List<TaskPhotoDto> getAuditPhoto(@Param("taskId") Long taskId,@Param("acceptanceResult") Integer acceptanceResult);
}