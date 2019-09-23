package com.hwagain.eagle.anonymous.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.eagle.task.service.ITaskService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-09
 */
@RestController
@RequestMapping(value="/app/regionDetail",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="APP-供货点管理")
public class RegionDetailAppController extends BaseController{
	
	@Autowired
	IRegionDetailService regionDetailService;
	@Autowired IRegionService regionService;
	@Autowired ITaskService taskService;
	
	/**
	 * 新增供货点
	 * @param entity
	 * @return
	 */
	@IgnoreUserToken
	@PostMapping("/addOne")
	@ApiOperation(value="新增供货点",notes="新增供货点",httpMethod="POST")
	public Response addOne(@RequestBody RegionDetail entity){
		System.err.println("ces");
		return SuccessResponseData.newInstance(regionDetailService.addOneDetailRegion(entity));
	}
	
	/**
	 * 【查询单条】补助标准
	 * @param supplierName	供应商名称
	 * @param city			市
	 * @param material		原料名称
	 * @param poundTime		过磅时间
	 * @return
	 */
	@RequestMapping(value="/findOneByParam2",method = {RequestMethod.POST, RequestMethod.GET})
	@ApiOperation(notes="【查询单条】补助标准",value="【查询单条】补助标准")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierName",value="供应商名称",dataType="String",paramType="query",required=true),
		@ApiImplicitParam(name="material",value="原料名称",dataType="String",paramType="query",required=true),		
		@ApiImplicitParam(name="city",value="市",dataType="String",paramType="query",required=true),
		@ApiImplicitParam(name="poundTime",value="过磅时间",dataType="String",paramType="query",required=true)
	})
	public Response findOneByParam2(String supplierName,String city,String material,String poundTime) {
		return SuccessResponseData.newInstance(regionService.findOneByParam2(supplierName, city, material, poundTime));
	}
	
	/**
	 * 省市县
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findByParam")
	@ApiOperation(value="省市县",notes="省市县",httpMethod="GET")
	public Response findByParam(){
		return SuccessResponseData.newInstance(regionService.findByParam());
	}
	
	/**
	 * 获取单条路线信息
	 * @param taskId	任务id
	 * @param tactics	路线类型：0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/getRoute")
	@ApiOperation(value = "获取单条路线信息", notes = "获取单条路线信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "tactics", value = "路线类型：0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费", paramType = "query", required = true, dataType = "String")
	})
	public Response getRoute(Long taskId,String tactics){
		return SuccessResponseData.newInstance(regionDetailService.getRoute(taskId,tactics));
	}
	
	/**
	 * 获取已规划路线信息
	 * @param taskId任务ID
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/getTaskRoute")
	@ApiOperation(value = "获取已规划路线信息", notes = "获取已规划路线信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String")
	})
	public Response getTaskRoute(Long taskId){
		return SuccessResponseData.newInstance(regionDetailService.getTaskRoute(taskId));
	}
	
	/**
	 * 记录规划路线
	 * @param taskId	任务ID
	 * @param tactics	路线类型：0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/recordTaskRoute")
	@ApiOperation(value = "记录规划路线", notes = "记录规划路线",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "tactics", value = "路线类型：0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费", paramType = "query", required = true, dataType = "String")
	})
	public Response recordTaskRoute(Long taskId,String tactics){
		return SuccessResponseData.newInstance(taskService.recordTaskRoute(taskId, tactics));
	}
}

