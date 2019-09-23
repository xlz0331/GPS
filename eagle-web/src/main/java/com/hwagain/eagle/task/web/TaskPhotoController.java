package com.hwagain.eagle.task.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.util.syncPhoto;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
@RestController
@RequestMapping(value="/task/taskPhoto")
@Api(tags="3.任务图片管理API")
public class TaskPhotoController extends BaseController{
	static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();// 本次要处理的列表
	@Autowired
	ITaskPhotoService taskPhotoService;
	@Autowired
	syncPhoto sync;
	
	/**
	 * 新增任务图片
	 *
	 * @param taskPhotoVo
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增任务图片", notes = "新增任务图片",httpMethod="POST")
	public Response save(@RequestBody TaskPhotoVo taskPhotoVo){
		return SuccessResponseData.newInstance(taskPhotoService.save(taskPhotoVo));
	}
	
	/**
	 * 查询任务图片
	 * 
	 * @param taskId
	 * @param photoType
	 * @return
	 */
	@GetMapping(value="/findByParam/{taskId}")
	@ApiOperation(value = "查询任务图片", notes = "查询任务图片",httpMethod="GET")
	public Response findByParam(@PathVariable Long taskId, @RequestParam(required=false) String photoType){
		return SuccessResponseData.newInstance(taskPhotoService.findByParam(taskId, photoType));
	}
	
	/**
	 * 查询任务图片明细
	 * 
	 * @param taskId
	 * @param photoType
	 * @return
	 */
//	@GetMapping(value="/getPhotoDetail")
//	@ApiOperation(value = "查询任务图片明细", notes = "查询任务图片明细",httpMethod="GET")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="photoId",value="photoId",paramType = "query", required = true, dataType = "String")
//	})
	public Response getPhotoDetail(Long photoId){
		return SuccessResponseData.newInstance(taskPhotoService.getPhotoDetail(photoId));
	}
	
	
//	@GetMapping(value="/auditPhoto")
//	@ApiOperation(value="图片审核",notes="图片审核")
	@ApiImplicitParams({
		@ApiImplicitParam(name="auditType",value="审核节点（auditOne[司磅员],auditTwo[收购部],auditThree[财务部]）",paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name="acceptanceResult",value="审核结果（1合格，2不合格）",paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name="reason",value="不合格原因",paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name="fdId",value="fdId",paramType = "query", required = true, dataType = "String")
	})
	public Response auditPhoto(String auditType,Integer acceptanceResult, String reason,Long fdId){
		return SuccessResponseData.newInstance(taskPhotoService.auditPhoto(auditType,acceptanceResult, reason, fdId));
	}
	
	/**
	 * 图片审核
	 * @param taskPhotoDtos 任务图片对象列表
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/photoAudit")
	@ApiOperation(value="图片审核",notes="图片审核",httpMethod="POST")
	public Response photoAudit(@RequestBody List<TaskPhotoDto> taskPhotoDtos){
		return SuccessResponseData.newInstance(taskPhotoService.photoAudit(taskPhotoDtos));
	}
	
	/**
	 * 获取过磅图片
	 * @param plateNumber	车牌号
	 * @param poundId		磅单号
	 * @param taskId		创建时间
	 * @param createTime	创建时间
	 * @param guoband		过磅时间
	 * @return SuccessResponseData
	 */
	@RequestMapping(value = "getPhotoData", method = RequestMethod.GET)
	@ApiOperation(value = "获取过磅图片", notes = "获取过磅图片")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "plateNumber", value = "车牌号", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "poundId", value = "磅单号", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "taskId", value = "创建时间", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "createTime", value = "创建时间", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "guoband", value = "过磅时间", paramType = "query", required = true, dataType = "String") 
	})
	public Response getPhotoData(String plateNumber,String poundId,Long taskId,String createTime,String guoband) {
		return SuccessResponseData.newInstance(sync.getPhotoData(plateNumber, poundId,taskId,createTime,guoband));
	}
	
	/**
	 * 图片审核（二审）
	 * @param taskPhotoDtos 		任务图片对象列表
	 * @param taskId				任务id
	 * @param pathAcceptanceResult	轨迹审核结果
	 * @param reason				轨迹不合格原因
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/photoAudit2")
	@ApiOperation(value="图片审核",notes="图片审核",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "taskId", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pathAcceptanceResult", value = "pathAcceptanceResult", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "reason", paramType = "query", required = true, dataType = "String"),
	})
	public Response photoAudit2(@RequestBody List<TaskPhotoDto> taskPhotoDtos,Long taskId,Integer pathAcceptanceResult,String reason){
		return SuccessResponseData.newInstance(taskPhotoService.photoAudit2(taskPhotoDtos, taskId, pathAcceptanceResult, reason));
	}
	
	/**
	 * 图片审核（三审）
	 * @param taskPhotoDtos			任务图片对象列表
	 * @param taskId				任务id
	 * @param pathAcceptanceResult	轨迹审核结果
	 * @param reason				轨迹不合格原因
	 * @return SuccessResponseData
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "taskId", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pathAcceptanceResult", value = "pathAcceptanceResult", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "reason", paramType = "query", required = true, dataType = "String"),
	})
	@PostMapping(value="/photoAudit3")
	@ApiOperation(value="图片审核",notes="图片审核",httpMethod="POST")
	public Response photoAudit3(@RequestBody List<TaskPhotoDto> taskPhotoDtos,Long taskId,Integer pathAcceptanceResult,String reason){
		return SuccessResponseData.newInstance(taskPhotoService.photoAudit3(taskPhotoDtos, taskId, pathAcceptanceResult, reason));
	}
	
	/**
	 * 对二审三审同时审核造成结果为及时统计的任务进行回审
	 * @return SuccessResponseData
	 */
	@GetMapping(value="/returnAudit")
	@ApiOperation(value = "回审", notes = "回审",httpMethod="GET")
	public Response returnAudit(){
		return SuccessResponseData.newInstance(taskPhotoService.returnAudit());
	}
	
	/**
	 * 获取出发点 
	 * @param taskId 任务id
	 * @return SuccessResponseData
	 */
	@GetMapping(value="/getStartPoint")
	@ApiOperation(value = "获取出发点", notes = "获取出发点",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="taskId",value="taskId",paramType = "query", required = true, dataType = "String")
	})
	public Response getStartPoint(Long taskId){
		return SuccessResponseData.newInstance(taskPhotoService.getStartPoint(taskId));
	}
	
	/**
	 * 轨迹初始点(将出发点写入轨迹表)
	 * @return
	 */
	@GetMapping(value="/startPoint")
	@ApiOperation(value = "startPoint", notes = "startPoint",httpMethod="GET")
	public Response startPoint(){
		return SuccessResponseData.newInstance(taskPhotoService.startPoint());
	}
	
	/**
	 * 
	 * @return
	 */
//	@GetMapping(value="/deletePoint")
//	@ApiOperation(value = "deletePoint", notes = "deletePoint",httpMethod="GET")
//	public Response deletePoint(){
//		return SuccessResponseData.newInstance(taskPhotoService.deletePoint());
//	}
}
