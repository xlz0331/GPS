package com.hwagain.eagle.anonymous.web;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import com.google.common.collect.Interner;
import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.base.service.ICarInfoService;
import com.hwagain.eagle.base.service.IDriverInfoService;
import com.hwagain.eagle.base.service.ILogPhoneInfoService;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.region.entity.RegionSupplier;
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.region.service.IRegionSupplierService;
import com.hwagain.eagle.supplier.service.IMaterialPurchasePlanService;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.dto.TaskNewDto;
import com.hwagain.eagle.task.dto.EaglePropertyDto;
import com.hwagain.eagle.task.entity.TaskPhotosVo;
import com.hwagain.eagle.task.entity.TaskVo;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.util.ApiResult;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.val;

/**
 * <p>
 * 任务表
注意：只有角色为HWAGAIN、DRIVER的数据才能写入TASK表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-02-25
 */
@RestController
@RequestMapping(value="/app/task")
@Api(tags="APP-任务管理API")
public class TaskAppController extends BaseController{
	
	@Autowired
	ITaskService taskService;
	@Autowired IDriverInfoService driverInfoService;
	@Autowired ICarInfoService carInfoService;
	@Autowired IMaterialPurchasePlanService materialPurchasePlanService;
	@Autowired IMaterialService materialService;
	@Autowired IRegionDetailService regionDetailService;
	@Autowired ITrackInfoService trackInfoService;
	@Autowired ILogPhoneInfoService logPhoneInfoService;
	
	//区域供应商服务接口
	@Autowired IRegionSupplierService regionSupplierService;

	/**
	 *  司机查询任务
	 *  
	 * @param status	状态（0：未开始；1：进行中；2：运输已完成，待审核；3：审核已完成；4：取消；5：暂停；）
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/getTaskByDirver")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "status", value = "状态（0：未开始；1：进行中；2：运输已完成，待审核；3：审核已完成；4：取消；5：暂停；）", paramType = "query", required = true, dataType = "String")
	})
	@ApiOperation(value="(driver)获取任务",notes="(driver)获取任务",httpMethod="GET")
	public Response getTaskByDirver(Integer status){
		return SuccessResponseData.newInstance(taskService.getTaskByDirver(status));
	}
	
	
	/**
	 * (supplier)获取任务列表（进行中、已完成任务）
	 * 
	 * @param status	状态（0：未开始；1：进行中；2：运输已完成，待审核；3：审核已完成；4：取消；5：暂停；）
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/getTaskBysupplier")
	@ApiOperation(value="(supplier)获取任务列表（进行中、已完成任务）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "status", value = "状态（0：未开始；1：进行中；2：运输已完成，待审核；3：审核已完成；4：取消；5：暂停；）", paramType = "query", required = true, dataType = "String")
	})
	public Response getTaskBysupplier(Integer status){
		return SuccessResponseData.newInstance(taskService.getTaskBysupplier(status));
	}
	
	/**
	 *结束行程
	 *
	 * @param dto
	 * @return
	 */
	@IgnoreUserToken
	@PostMapping(value="/endTask")
	@ApiOperation(value="结束行程",notes="结束行程（）",httpMethod="POST")
	public Response endTask(@RequestBody TaskDto dto){
		return SuccessResponseData.newInstance(taskService.endTask(dto));	
	}

	
	/**
	 * 司机创建任务
	 * 
	 * @param status				状态（0未开始，1进行中）
	 * @param plateNumber			车牌号
	 * @param supplierName			供应商姓名
	 * @param gps					GPS经纬度
	 * @param startPositionAddress	出发地点
	 * @param startPositionCity		出发城市
	 * @param photos				任务图片对象
	 * @return
	 */
	@IgnoreUserToken
	@PostMapping(value="/createTaskByDriver")
	@ApiOperation(value = "司机创建任务", notes = "司机创建任务",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "plateNumber", value = "车牌号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "supplierName", value = "供应商姓名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态（0未开始，1进行中）", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "gps", value = "GPS经纬度", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "startPositionAddress", value = "出发地点", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "startPositionCity", value = "出发城市", paramType = "query", required = true, dataType = "String")
	})
	public Response createTaskByDriver(Integer status, String plateNumber,String supplierName,String gps,String startPositionAddress,
			String startPositionCity, @RequestBody List<TaskPhotosVo> photos){
		return SuccessResponseData.newInstance(taskService.createTaskByDriver(status,plateNumber, supplierName,gps, startPositionAddress, 
				startPositionCity, photos));
	}
	
	/**
	 * 获取当前司机任务
	 * 
	 * @param status 状态（状态。0：未开始；1：进行中；2：待审核；3：已完成；4：取消；5：暂停；）
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/queryNowTaskByDriver")
	@ApiOperation(value="获取当前司机任务",notes="获取当前司机任务",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "status", value = "状态（状态。0：未开始；1：进行中；2：待审核；3：已完成；4：取消；5：暂停；）", paramType = "query", required = true, dataType = "String")
	})
	public Response queryNowTaskByDriver(Integer status){
		return SuccessResponseData.newInstance(taskService.queryNowTaskByDriver(status));
	}
	
	/**
	 *  获取收购点经纬度
	 *  
	 * @param address 地址：赣州、崇左
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/queryPurchaseGps")
	@ApiOperation(value="获取收购点经纬度",notes="获取收购点经纬度",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "address", value = "例：赣州、崇左", paramType = "query", required = false, dataType = "String")
	})
	public Response queryPurchaseGps(String address){
		return SuccessResponseData.newInstance(regionDetailService.queryPurchaseGps(address));
	}
	
	/**
	 * 获取版本号
	 * 
	 * @param fdId
	 * @param status
	 * @return
	 */
	@GetMapping(value="/getVersion")
	@ApiOperation(value = " 获取版本号", notes = " 获取版本号",httpMethod="GET")
	public void version(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://wxmp.hwagain.com:60008/hwagain/apk/version");
    }

	/**
	 * 竹木原料运费补助起点照片审核
	 * 
	 * @param plateNumber	车牌号
	 * @param poundId		磅单号
	 * @param supplierName	供应商名称
	 * @param empName		审核人
	 * @param userRight		权限
	 * @param secret		密文
	 * @return
	 */
	@RequestMapping(value="/findAuditTask",method = {RequestMethod.POST, RequestMethod.GET})
	@ApiOperation(value="竹木原料运费补助起点照片审核",notes="竹木原料运费补助起点照片审核")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "plateNumber", value = "车牌号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "poundId", value = "磅单号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empName", value = "审核人", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "userRight", value = "权限", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "secret", value = "密文", paramType = "query", required = true, dataType = "String")
	})
	public Response findAuditTask(String plateNumber,String poundId,String supplierName,String empName,String userRight,String secret){
		return SuccessResponseData.newInstance(taskService.findAuditTask(plateNumber, poundId, supplierName, empName, userRight,secret));
	}

	/**
	 * 更正车牌号
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/updatePlateNumber")
	@ApiOperation(value="更正车牌号",notes="更正车牌号",httpMethod="POST")
	public Response updatePlateNumber(@RequestBody TaskDto dto){
		return SuccessResponseData.newInstance(taskService.updatePlateNumber(dto));
	}
	
	/**
	 * 竹木原料供应商拉运计划明细表
	 * 
	 * @param supplierId	供应商ID
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param status		状态
	 * @return
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
	 * 查询在途任务及轨迹明细
	 * 
	 * @param taskId	任务id
	 * @return TaskVO
	 */
	@IgnoreUserToken
	@GetMapping("/queryTaskDetail")
	@ApiOperation(value="查询在途任务明细",notes="查询在途任务明细",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String")
	})
	public Response queryTaskDetail(Long taskId){
		return SuccessResponseData.newInstance(taskService.queryTaskDetail(taskId));
	}
	
	/**
	 * 查询任务明细
	 * 
	 * @param fdId
	 * @return TaskDto
	 */
	@GetMapping(value="/findTaskById")
	@ApiOperation(value="findTaskById",notes="findTaskById",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = true, dataType = "Long")
	})
	public Response findTaskById(Long fdId){
		return SuccessResponseData.newInstance(taskService.findTaskById(fdId));
	}
	
	/**
	 * 已审核数据详情
	 * 
	 * @param poundId 磅单id
	 * @return
	 */
	@GetMapping("/findByPoundId")
	@ApiOperation(value="已审核数据详情",notes="poundId",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "poundId", value = "poundId", paramType = "query", required = true, dataType = "String")
	})
	public Response findByPoundId(String poundId){
		return SuccessResponseData.newInstance(taskService.findByPoundId(poundId));
	}
	
	/**
	 * 检测是否有图片
	 * 
	 * @param plateNumber	车牌号
	 * @return
	 */
	@RequestMapping(value="/checkIsPhoto",method = {RequestMethod.POST, RequestMethod.GET})
	@ApiOperation(value="检测是否有图片",notes="检测是否有图片")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "plateNumber", value = "plateNumber", paramType = "query", required = true, dataType = "String")
	})
	public Response checkIsPhoto(String plateNumber){
		return SuccessResponseData.newInstance(taskService.checkIsPhoto(plateNumber));
	}
	
	/**
	 * 查询在途任务（Driver）
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findTaskPhotoByDriver")
	@ApiOperation(value="查询在途任务（Driver）",notes="查询在途任务（Driver）",httpMethod="GET")
	public Response findTaskPhotoByDriver(){
		return SuccessResponseData.newInstance(taskService.findTaskPhotoByDriver());
		
	}
	
	/**
	 * 照片重拍
	 * 
	 * @param taskId 任务id
	 * @param photos 图片对象
	 * @return
	 */
	@IgnoreUserToken
	@PostMapping(value="/rephotograph")
	@ApiOperation(value = "照片重拍", notes = "照片重拍",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务Id", paramType = "query", required = true, dataType = "String")
	})
	public Response rephotograph(Long taskId, @RequestBody List<TaskPhotosVo> photos){
		return SuccessResponseData.newInstance(taskService.rephotograph(taskId, photos));
	}
	
	/**
	 * 供应商查询任务列表
	 * 
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @param driverName司机
	 * @param material	原材料
	 * @param startCity	开始城市
	 * @param endCity	到达城市
	 * @return	
	 */
	@IgnoreUserToken
	@GetMapping("/findTaskListBySupplier")
	@ApiOperation(value="供应商查询任务列表（0614）",notes="供应商查询任务列表（0614）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "driverName", value = "司机", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "material", value = "原材料", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "startCity", value = "开始城市", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endCity", value = "到达城市", paramType = "query", required = false, dataType = "String")
	})
	public Response findTaskListBySupplier(Date startTime, Date endTime, String driverName,String material,String startCity,String endCity){
		return SuccessResponseData.newInstance(taskService.findTaskListBySupplier(startTime, endTime, driverName, material, startCity, endCity));		
	}
	
	/**
	 * 供应商查询任务明细
	 * 
	 * @param taskId 任务id
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findTaskDetailBySupplier")
	@ApiOperation(value="供应商查询任务明细（0709）",notes="供应商查询任务明细（0709）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String")
	})
	public Response getListByTaskId(Long taskId){
		return SuccessResponseData.newInstance(trackInfoService.getListByTaskId(taskId));		
	}
	
	/**
	 * 供应商查询司机手机型号
	 * 
	 * @param userId 用户ID
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/getPhoneInfo")
	@ApiOperation(value="供应商查询司机手机型号（0801）",notes="供应商查询司机手机型号（0801）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query", required = true, dataType = "String")
	})
	public Response getPhoneInfo(Long userId){
		return SuccessResponseData.newInstance(logPhoneInfoService.getPhoneInfo(userId));		
	}
	
	/**
	 * 根据城市名称返回区域供应商信息
	 * @param city 城市名
	 * @return
	 *  ApiResult object
	 * 
	 * @author 黎昌盛
	 * @date 2019-08-21
	 */
	@GetMapping("/findRegionSupplierInfoByCityName")
	@ApiOperation(value="根据城市名称返回区域供应商信息",notes="city",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "city", value = "城市名称", paramType = "query", required = true, dataType = "String")
	})
	public Response findRegionSupplierInfoByCityName(String city){
		return SuccessResponseData.newInstance(regionSupplierService.findByCityName(city));
		
	}
	
	/**
	 * 根据县/区名称返回区域供应商信息
	 * @param county 县/区名称
	 * @param city 城市名称
	 * @return
	 * ApiResult object(List<RegionSupplier>)
	 */
	@GetMapping("/findRegionSupplierInfoByCountyName")
	@ApiOperation(value="根据县/区名称返回区域供应商信息",notes="county",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "county", value = "县/区名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "city", value = "城市名称", paramType = "query", required = true, dataType = "String")
	})
	public Response findByCountyName(String county,String city){
		return SuccessResponseData.newInstance(regionSupplierService.findByCountyName(county,city));
		
	}
}
