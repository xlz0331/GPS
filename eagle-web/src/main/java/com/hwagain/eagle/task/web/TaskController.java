package com.hwagain.eagle.task.web;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.json.JSONObject;
import com.google.common.collect.Interner;
import com.hwagain.eagle.correct.entity.CorrectVo;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhotosVo;
import com.hwagain.eagle.task.entity.TaskVo_a;
import com.hwagain.eagle.task.entity.TaskVo_b;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.util.syncSupplierData;
import com.hwagain.eagle.util.syncYuanLZhu;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.util.PageDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 任务表
注意：只有角色为HWAGAIN、DRIVER的数据才能写入TASK表 前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
@RestController
@RequestMapping(value="/task")
@Api(tags="2.任务管理API")
public class TaskController extends BaseController{
	
	@Autowired
	ITaskService taskService;
	@Autowired syncYuanLZhu syncylz;
	@Autowired syncSupplierData sync;
	@Value("${sys.config.app.version}")
	private String version;
	@Value("${sys.config.app.link}")
	private String link;
	@Value("${sys.config.app.description}")
	private String description;
	@Value("${sys.config.app.versionCode}")
	private String versionCode;
	
	/**
	 * 新增任务
	 * 
	 * @param userInfoDto
	 * @return
	 */
//	@PostMapping(value="/save")
//	@ApiOperation(value = "新增任务", notes = "新增任务",httpMethod="POST")
	public Response save(@RequestBody TaskDto taskDto){
		return SuccessResponseData.newInstance(taskService.save(taskDto));
	}
	
	/**
	 * 根据条件分页返回任务列表
	 * @param pageNum
	 * @param pageSize
	 * @param taskDto
	 * @return
	 */
	@PostMapping(value="/findByPage/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回任务列表", notes = "分页返回任务列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize, @RequestBody(required=false) TaskDto taskDto){
		return SuccessResponseData.newInstance(taskService.findByPage(taskDto, pageNum, pageSize));
	}
	
	/**
	 * 查询所有有效任务
	 * @param status		任务状态
	 * @param driverName	司机姓名
	 * @return List<TaskDto> 
	 */
	@GetMapping(value="/findAll")
	@ApiOperation(value = "查询所有有效任务", notes = " 查询所有有效任务",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "driverName", value = "司机名称", paramType = "query", required = false, dataType = "String")
	})
	public Response findAll(String status,String driverName){		
		return SuccessResponseData.newInstance(taskService.findAll(status,driverName));
	}
	
	/**
	 * 修改任务状态
	 * 
	 * @param fdId	任务id
	 * @return
	 */
	@PostMapping(value="/updateStatus/{fdId}")
	@ApiOperation(value = "修改任务状态", notes = "修改任务状态",httpMethod="POST")
	public Response updateStatus(@PathVariable Long fdId, @RequestBody int status){
		return SuccessResponseData.newInstance(taskService.updateStatus(fdId, status));
	}
	
	/**
	 * 供应商任务列表
	 * @param supplierId 	供应商id
	 * @param status		任务状态
	 * return
	 */
	@GetMapping(value="/findBySupplierId")
	@ApiOperation(value="供应商任务列表",notes="供应商任务列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierId", value = "供应商id", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "任务状态", paramType = "path", required = true, dataType = "String")
	})
	public Response findBySupplierId(Long supplierId,Integer status){
		return SuccessResponseData.newInstance(taskService.findBySupplierId(supplierId, status));
	}

	/**
	 * 获取版本号
	 * 
	 * @return Map<String, String> 
	 */
	@GetMapping(value="/getVersion")
	@ApiOperation(value = " 获取版本号", notes = " 获取版本号",httpMethod="GET")
	public Map<String, String> getVersion(){
		System.err.println(DigestUtils.md5Hex("123456"));
		System.err.println(DigestUtils.md5Hex(DigestUtils.md5Hex("123456")));
		Map<String, String> map = new HashMap<String, String>();
		map.put("version", version);
		map.put("link", link);
		map.put("description", description);
		map.put("versionCode", versionCode);
		return map;
	}
	
	/**
	 * 查询单条任务
	 * @param fdId 任务ID
	 * @return TaskDto
	 */
	@GetMapping(value="/findTaskById")
	@ApiOperation(value="findTaskById",notes="findTaskById",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = true, dataType = "Long")
	})
	public Response findTaskById(Long fdId){
		System.err.println(fdId);
		return SuccessResponseData.newInstance(taskService.findTaskById(fdId));
	}
	
	/**
	 * 分页返回任务列表
	 * @param pageNum
	 * @param pageSize
	 * @param status
	 * @param driverName
	 * @return PageDto<TaskDto>
	 */
	@PostMapping(value="/findByPagea/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回列表", notes = "分页返回列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "供应商名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "driverName", value = "省份", paramType = "query", required = false, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize,String status,String driverName){
		return SuccessResponseData.newInstance(taskService.findByPagea(pageNum, pageSize, status, driverName));
	}
	
	/**
	 * 同步供应数据
	 * @return String
	 */
	@GetMapping(value="/sycnSupplier")
	@ApiOperation(value="同步供应数据",notes="同步供应数据",httpMethod="GET")
	public Response getPsBaseData(){
		return SuccessResponseData.newInstance(sync.getPsBaseData());
	}
	
	/**
	 * 竹木原料供应商拉运计划明细表
	 * @param supplierId	供应商ID
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		任务状态
	 * @return List<TaskDto> 
	 */
	@PostMapping(value="/queryTaskDetailList")
	@ApiOperation(value = "竹木原料供应商拉运计划明细表", notes = "竹木原料供应商拉运计划明细表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierId", value = "供应商ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = false, dataType = "String")
	})
	public Response queryTaskDetailList(Long supplierId,Date startTime,Date endTime,Integer status){
		System.err.println(supplierId+"="+startTime);
		return SuccessResponseData.newInstance(taskService.queryTaskDetailList(supplierId, startTime, endTime, status));
	}
	

	/**
	 * 竹木原料供应商拉运计划明细表
	 * @param supplierId	供应商ID
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		任务状态
	 * @return List<TaskDto>
	 */
	@PostMapping(value="/queryTaskDetailList2")
	@ApiOperation(value = "竹木原料供应商拉运计划明细表", notes = "竹木原料供应商拉运计划明细表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierId", value = "供应商ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = false, dataType = "String")
	})
	public Response queryTaskDetailList2(Long supplierId,Date startTime,Date endTime,Integer status){
		System.err.println(supplierId+"="+startTime);
		return SuccessResponseData.newInstance(taskService.queryTaskDetailList2(supplierId, startTime, endTime, status));
	}
	
	/**
	 * 竹木原料供应商拉运计划明细表
	 * @param supplierId	供应商ID
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		任务状态
	 * @return	List<CorrectVo>
	 */
	@PostMapping(value="/findByTaskAb")
	@ApiOperation(value = "findByTaskAb", notes = "findByTaskAb",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierId", value = "供应商ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = false, dataType = "String")
	})
	public Response findByTaskAb(Long supplierId,Date startTime,Date endTime,Integer status){
		System.err.println(supplierId+"="+startTime+"findBYTaskAb");
		return SuccessResponseData.newInstance(taskService.findByTaskAb(supplierId, startTime, endTime, status));
	}
	
	/**
	 * 
	 * @param poundId	磅单号
	 * @param TaskId	任务id
	 * @return String
	 */
	@GetMapping(value="/syncgetYLZData")
	@ApiOperation(value = "syncgetYLZData", notes = " syncgetYLZData",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "poundId", value = "poundId", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "TaskId", value = "TaskId", paramType = "query", required = false, dataType = "String")
	})
	public Response getYLZData(String poundId,Long TaskId){		
		return SuccessResponseData.newInstance(syncylz.getYLZData(poundId, TaskId));
	}
	
	/**
	 * 
	 * @param poundId	磅单号
	 * @param fdId		任务id
	 * @return
	 */
	@GetMapping(value="/updateBypoundId")
	@ApiOperation(value = "updateBypoundId", notes = " updateBypoundId",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "poundId", value = "poundId", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = false, dataType = "String")
	})
	public Response updateBypoundId(String poundId,Long fdId){		
		return SuccessResponseData.newInstance(taskService.updateBypoundId(fdId, poundId));
	}
	
	/**
	 * 手动结束任务
	 * @param purchasePoint		收购点
	 * @param fdId				任务id
	 * @return	String
	 */
	@GetMapping(value="/endUpTask")
	@ApiOperation(value = "endUpTask", notes = " endUpTask",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "purchasePoint", value = "purchasePoint", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = false, dataType = "String")
	})
	public Response endUpTask(String purchasePoint,Long fdId){		
		return SuccessResponseData.newInstance(taskService.endUpTask(fdId, purchasePoint));
	}
	
	/**
	 * 修改任务状态
	 * 
	 * @param fdId		任务Id
	 * @param status	任务状态
	 * @return
	 */
	@GetMapping(value="/endUpTaskByFirstAudit")
	@ApiOperation(value = "供应部人员手动结束任务", notes = "供应部人员手动结束任务",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "lastAlterTime", value = "lastAlterTime", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "plateNumber", value = "plateNumber", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "purchasePoint", value = "purchasePoint", paramType = "query", required = true, dataType = "String")
	})
	public Response endUpTaskByFirstAudit(Long fdId,Date lastAlterTime,String plateNumber,String purchasePoint){
		return SuccessResponseData.newInstance(taskService.endUpTaskByFirstAudit(fdId, lastAlterTime, plateNumber,purchasePoint));
	}
	
	/**
	 * 
	 * @param pageNum 
	 * @param pageSize
	 * @param supplierId	供应商Id
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		任务状态
	 * @return PageDto<CorrectVo> 
	 */
	@PostMapping(value="/findByTaskAbByPage")
	@ApiOperation(value = "findByTaskAbByPage", notes = "findByTaskAbByPage",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "pageNum", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "supplierId", value = "供应商ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = false, dataType = "String")
	})
	public Response findByTaskAbByPage(int pageNum, int pageSize,Long supplierId,Date startTime,Date endTime,Integer status){
		System.err.println(supplierId+"="+startTime+"findBYTaskAb");
		return SuccessResponseData.newInstance(taskService.findByTaskAbByPage(pageNum, pageSize, supplierId, startTime, endTime, status));
	}
	
	/**
	 * 
	 * @return
	 */
//	@GetMapping(value="/updateError")
//	@ApiOperation(value="updateError",notes="updateError",httpMethod="GET")
	public Response updateError(){
		return SuccessResponseData.newInstance(taskService.updateError());
	}
	/**
	 * 获取原料名称
	 * @return String
	 */
	@GetMapping(value="/getMaterialName")
	@ApiOperation(value="getMaterialName",notes="getMaterialName",httpMethod="GET")
	public Response getMaterialName(){
		return SuccessResponseData.newInstance(taskService.getMaterialName());
	}
	
	/**
	 * 
	 * @return
	 */
//	@GetMapping(value="/addSupplierName")
//	@ApiOperation(value="addSupplierName",notes="addSupplierName",httpMethod="GET")
	public Response addSupplierName(){
		return SuccessResponseData.newInstance(taskService.addSupplierName());
	}
	
	/**
	 * 
	 * @param supplierName	 供应商名称
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		任务状态
	 * @return List<TaskVo_a>
	 */
	@GetMapping(value="/findTaskResult")
	@ApiOperation(value = "findTaskResult", notes = "findTaskResult",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = true, dataType = "String")
	})
	public Response findTaskResult(String supplierName,Date startTime,Date endTime,Integer status){
		return SuccessResponseData.newInstance(taskService.findTaskResult(status, startTime, endTime, supplierName));
	}
	
	/**
	 * 
	 * @param supplierName  供应商名称
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		任务状态
	 * @return List<TaskVo_b>
	 */
	@GetMapping(value="/findTaskResultA")
	@ApiOperation(value = "findTaskResultA", notes = "findTaskResultA",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = true, dataType = "String")
	})
	public Response findTaskResultA(String supplierName,Date startTime,Date endTime,Integer status){
		return SuccessResponseData.newInstance(taskService.findTaskResultA(status, startTime, endTime, supplierName));
	}
	
	/**
	 * 
	 * @param supplierName 供应商名称
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 * @return List<Task>
	 */
	@GetMapping(value="/findAllNoTrack")
	@ApiOperation(value = "findAllNoTrack", notes = "findAllNoTrack",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "startTime", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "endTime", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllNoTrack(String supplierName,Date startTime,Date endTime){
		return SuccessResponseData.newInstance(taskService.findAllNoTrack(startTime, endTime, supplierName));
	}
	
	/**
	 * 
	 * @param fdId 任务ID
	 * @return String
	 */
	@GetMapping(value="/updatePhoto")
	@ApiOperation(value = "updatePhoto", notes = "updatePhoto",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = false, dataType = "String")
	})
	public Response updatePhoto(Long fdId){
		return SuccessResponseData.newInstance(taskService.updatePhoto(fdId));
	}
	
//	@GetMapping(value="/licensePlate")
//	@ApiOperation(value = "licensePlate", notes = "licensePlate",httpMethod="GET")
//	public Response licensePlate() throws IOException, NoSuchAlgorithmException, KeyManagementException{
//		return SuccessResponseData.newInstance(taskService.licensePlate());
//	}
}
