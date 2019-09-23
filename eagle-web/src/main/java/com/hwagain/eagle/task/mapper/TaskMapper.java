package com.hwagain.eagle.task.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskAnalyze;
import com.hwagain.eagle.task.entity.TaskVo_a;
import com.hwagain.eagle.task.entity.TaskVo_b;
import com.hwagain.eagle.track.entity.TrackAnalyze;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 任务表
注意：只有角色为HWAGAIN、DRIVER的数据才能写入TASK表 Mapper 接口
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
public interface TaskMapper extends BaseMapper<Task> {
	public List<Task> findOrder(@Param("supplierId")Long supplierId);
	public Task queryTaskFirst(@Param("plateNumber")String plateNumber);
	public List<TaskVo_a> findTaskResult(@Param("status")Integer status);
	public List<TaskVo_a> findTaskResult2(@Param("status")Integer status,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	public List<TaskVo_a> findTaskResult3(@Param("status")Integer status,@Param("supplierName")String supplierName);
	public List<TaskVo_a> findTaskResult4(@Param("status")Integer status,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("supplierName")String supplierName);
	public List<TaskVo_b> findTaskResultA(@Param("status")Integer status);
	public List<TaskVo_b> findTaskResultB(@Param("status")Integer status,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	public List<TaskVo_b> findTaskResultC(@Param("status")Integer status,@Param("supplierName")String supplierName);
	public List<TaskVo_b> findTaskResultD(@Param("status")Integer status,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("supplierName")String supplierName);
	public List<Task> findNoTrack(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("supplierName")String supplierName,@Param("trackNum") Integer trackNum);
	public List<Task> findNoTrack2(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("trackNum") Integer trackNum);
	public List<Task> findNoTrack3(@Param("supplierName")String supplierName,@Param("trackNum") Integer trackNum);
	public List<Task> findNoTrack4(@Param("trackNum") Integer trackNum);
	public List<TaskAnalyze> findAllTaskAnalyze(@Param("startTime") String startTime,@Param("endTime") String endTime);
}