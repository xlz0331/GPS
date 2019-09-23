package com.hwagain.eagle.track.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackAnalyze;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.entity.TrackInfoVo;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.JwtUtil;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import oracle.net.aso.j;

/**
 * <p>
 * 轨迹 前端控制器
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
@Configurable
@EnableScheduling
@Component
@RestController
@RequestMapping(value="/track")
@Api(tags="4.轨迹管理API")
public class TrackInfoController extends BaseController{
	
	@Autowired
	ITrackInfoService trackInfoService;
	@Autowired JwtUtil jwtUtil;
	
	/**
	 * 新增轨迹
	 *
	 * @param trackInfoDto
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增轨迹", notes = "新增轨迹",httpMethod="POST")
	public Response save(@RequestBody List<TrackInfo> trackInfos){
		return SuccessResponseData.newInstance(trackInfoService.save(trackInfos));
	}
	
	/**
	 * 根据任务ID查询轨迹列表
	 * @param taskId  任务id
	 * @param handler
	 * @return SuccessResponseData
	 */
	@GetMapping("/getListByTaskId/{taskId}")
	@ApiOperation(value = "根据任务ID查询轨迹列表", notes = "根据任务ID查询轨迹列表",httpMethod="GET")
	@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "path", required = true, dataType = "String")
	public Response getListByTaskId(@PathVariable Long taskId, Object handler){
		return SuccessResponseData.newInstance(trackInfoService.getListByTaskId(taskId));
	}
	
	/**
	 * 根据任务ID查询轨迹列表
	 *
	 * @return SuccessResponseData
	 */
	@GetMapping("/getBugListByTaskId")
	@ApiOperation(value = "根据任务ID查询轨迹列表", notes = "根据任务ID查询轨迹列表",httpMethod="GET")
	@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String")
	public Response getBugListByTaskId(Long taskId){
		return SuccessResponseData.newInstance(trackInfoService.getBugListByTaskId(taskId));
	}

	/**
	 * 轨迹审核（一审）
	 * @param taskId				任务id
	 * @param pathAcceptanceResult	轨迹审核结果
	 * @param reason				审核不合格原因（审核合格时为空）
	 * @return SuccessResponseData
	 */
	@GetMapping("/trackAudit")
	@ApiOperation(value="轨迹审核",notes="轨迹审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pathAcceptanceResult", value = "轨迹审核结果", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "不合格原因", paramType = "query", required = false, dataType = "String")
	})
	public Response trackAudit(Long taskId,Integer pathAcceptanceResult,String reason){
		return SuccessResponseData.newInstance(trackInfoService.trackAudit(taskId, pathAcceptanceResult, reason));
	}
	
	/**
	 * 轨迹审核（二审）
	 * @param taskId				任务id
	 * @param pathAcceptanceResult	轨迹审核结果
	 * @param reason				轨迹审核不合格原因（审核合格时为空）
	 * @return SuccessResponseData
	 */
	@GetMapping("/trackAudit2")
	@ApiOperation(value="轨迹审核",notes="轨迹审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pathAcceptanceResult", value = "轨迹审核结果", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "不合格原因", paramType = "query", required = false, dataType = "String")
	})
	public Response trackAudit2(Long taskId,Integer pathAcceptanceResult,String reason){
		return SuccessResponseData.newInstance(trackInfoService.trackAudit2(taskId, pathAcceptanceResult, reason));
	}
	
	/**
	 * 轨迹审核（三审）
	 * @param taskId				任务id
	 * @param pathAcceptanceResult	轨迹审核结果
	 * @param reason				轨迹审核不合格原因（审核合格时为空）
	 * @return SuccessResponseData
	 */
	@GetMapping("/trackAudit3")
	@ApiOperation(value="轨迹审核",notes="轨迹审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pathAcceptanceResult", value = "轨迹审核结果", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "不合格原因", paramType = "query", required = false, dataType = "String")
	})
	public Response trackAudit3(Long taskId,Integer pathAcceptanceResult,String reason){
		return SuccessResponseData.newInstance(trackInfoService.trackAudit3(taskId, pathAcceptanceResult, reason));
	}
	
	/**
	 * 判断出发点异常情况（拍照点与发车点距离是否在允许的误差范围内）
	 * @param taskId 任务id
	 * @return SuccessResponseData
	 */
	@GetMapping("/checkStartPoint")
	@ApiOperation(value="判断出发点异常情况",notes="判断出发点异常情况",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String")
	})
	public Response checkStartPoint(Long taskId){
		return SuccessResponseData.newInstance(trackInfoService.checkStartPoint(taskId));
	}
	
	/**
	 * 查询任务轨迹
	 * @param taskId	任务id
	 * @return  List<TrackInfoVo>
	 */
	@GetMapping("/findTrackByTaskId")
	@ApiOperation(value="findTrackByTaskId",notes="findTrackByTaskId",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String")
	})
	public Response findTrackByTaskId(Long taskId){
		return SuccessResponseData.newInstance(trackInfoService.findTrackByTaskId(taskId));
	}
	
	/**
	 * 手机型号分析轨迹定点情况
	 * @param startTime	开始时间
	 * @param endTime	结束会死机
	 * @param status	任务状态
	 * @return List<TrackAnalyze>
	 */
	@GetMapping("/findAllTrackAnalyze")
	@ApiOperation(value="findAllTrackAnalyze",notes="手机型号分析",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllTrackAnalyze(String startTime,String endTime,String status){
		return SuccessResponseData.newInstance(trackInfoService.findAllTrackAnalyze(startTime,endTime,status));
	}
	
	/**
	 * 运输记录分析轨迹定点情况
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param supplierName	供应商名称
	 * @return List<TrackAnalyze> 
	 */
	@GetMapping("/findAllTaskTrackAnalyze")
	@ApiOperation(value="findAllTaskTrackAnalyze",notes="运输记录分析",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "supplierName", value = "供应商", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllTaskTrackAnalyze(String startTime,String endTime,String supplierName){
		return SuccessResponseData.newInstance(trackInfoService.findAllTaskTrackAnalyze(startTime,endTime,supplierName));
	}
	
	/**
	 * 审核结果分析
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return List<TrackAnalyze>
	 */
	@GetMapping("/findAllTaskAnalyze")
	@ApiOperation(value="findAllTaskAnalyze",notes="审核结果分析",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "开始时间", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllTaskAnalyze(String startTime,String endTime){
		return SuccessResponseData.newInstance(trackInfoService.findAllTaskAnalyze(startTime,endTime));
	}
	
	/**
	 * 轨迹统计定时任务
	 * @return SuccessResponseData
	 */
	@GetMapping("/taskAnalyzeSync")
	@ApiOperation(value="轨迹统计定时任务",notes="轨迹统计定时任务",httpMethod="GET")
	public Response taskAnalyzeSync(){
		System.err.println("每天2点执行");
		return SuccessResponseData.newInstance(trackInfoService.taskAnalyzeSync());
	}
}
