package com.hwagain.eagle.task.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hwagain.eagle.correct.entity.CorrectVo;
import com.hwagain.eagle.task.dto.EaglePropertyDto;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.dto.TaskNewDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.entity.TaskPhotosVo;
import com.hwagain.eagle.task.entity.TaskResultVo;
import com.hwagain.eagle.task.entity.TaskVo;
import com.hwagain.eagle.task.entity.TaskVo_a;
import com.hwagain.eagle.task.entity.TaskVo_b;
import com.hwagain.eagle.task.vo.TaskVO;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.util.PageDto;

/**
 * <p>
 * 任务表
注意：只有角色为HWAGAIN、DRIVER的数据才能写入TASK表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-02-25
 */
public interface ITaskService extends IService<Task> {

	public String save(TaskDto taskDto);

	public PageDto<TaskDto> findByPage(TaskDto taskDto, int pageNum, int pageSize);

	public String updateStatus(Long fdId, int status);

	public List<TaskDto> findBySupplierId(Long supplierId,int status);

	public String createTask(List<TaskNewDto> dtos);

	public Task addOneTask(String orderId,String userMobile,String driverName,String userPlateNumber,String material);

	public Task endTask(TaskDto dto);

	public Task startTask(Long fdId, String startPositionAddress, String startPositionCity);

	public List<TaskDto> getTaskByDirver(Integer status);

	public List<TaskDto> getTaskBysupplierId();

	public List<TaskDto> getDetailByOrderId(String orderId);

	public List<TaskDto> getTaskBysupplier(Integer status);

	public String sumbitTask(List<TaskVo> taskVos);

	public List<TaskDto> queryNowTask();

	public Task createTaskByDriver(Integer status,String plateNumber,String supplierName,String gps,String startPositionAddress,String startPositionCity,List<TaskPhotosVo> photos);

	public TaskDto queryNowTaskByDriver(Integer status);

	public EaglePropertyDto getVersion();

	public List<TaskDto> findAll(String status,String driverName);

	public TaskDto findTaskById(Long fdId);

	public PageDto<TaskDto> findByPagea(int pageNum, int pageSize, String status, String driverName) throws CustomException;

	public List<TaskDto> queryTaskDetailList(Long supplierId, Date startTime, Date endTime, Integer status);

	public TaskDto findAuditTask(String plateNumber, String poundId, String supplierName,String empName,String userRight,String secret);

	public TaskDto updatePlateNumber(TaskDto dto);

	public TaskVO queryTaskDetail(Long taskId);

	public List<TaskDto> queryTaskDetailList2(Long supplierId, Date startTime, Date endTime, Integer status);

	public List<TaskResultVo> queryTaskResultByPoundId(String poundId);

	public List<CorrectVo> findByPoundId(String poundId);

	public List<CorrectVo> findByTaskAb(Long supplierId, Date startTime, Date endTime, Integer status);

	public TaskDto updateBypoundId(Long fdId, String poundId);

	public Map<String, String> checkIsPhoto(String plateNumber);

	public Task endUpTask(Long fdId,String purchasePoint);

	public TaskDto findTaskPhotoByDriver();

	public String rephotograph(Long taskId, List<TaskPhotosVo> photos);

	public List<TaskDto> findTaskListBySupplier(Date startTime, Date endTime, String driverName,String material,String startCity,String endCity);

	public TaskDto findTaskDetailBySupplier(Long fdId);

	public String endUpTaskByFirstAudit(Long fdId,Date lastAlterTime,String plateNumber,String purchasePoint);

	public PageDto<CorrectVo> findByTaskAbByPage(int pageNum, int pageSize, Long supplierId, Date startTime, Date endTime,
			Integer status);

	public String updateError();

	public String getMaterialName();

	public String addSupplierName();

	public List<TaskVo_a> findTaskResult(Integer status, Date startTime, Date endTime, String supplierName);

	public List<TaskVo_b> findTaskResultA(Integer status, Date startTime, Date endTime, String supplierName);

	public List<Task> findAllNoTrack(Date startTime, Date endTime, String supplierName);

	public String updatePhoto(Long fdId);

	public Map<String, String> licensePlate() throws IOException, NoSuchAlgorithmException, KeyManagementException;

	public String recordTaskRoute(Long taskId, String tactics);


//	TaskDto findAuditTask(String plateNumber, String poundId, String supplierName, String material,
//			BigDecimal weightTons);

//	TaskDto auditTrack(Integer pathAcceptanceResult, String reason, Long fdId);
	
}
