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
import com.hwagain.eagle.region.entity.RegionPathway;
import com.hwagain.eagle.region.service.IRegionPathwayService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-09
 */
@RestController
@RequestMapping(value="/app/regionPathway",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="APP-途经点管理")
public class RegionPathwayAppController extends BaseController{
	
	@Autowired
	IRegionPathwayService regionPathwayService;
	
	/**
	 * 省
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/queryPrivince")
	@ApiOperation(value="省",notes="省",httpMethod="GET")
	public Response queryPrivince(){
		return SuccessResponseData.newInstance(regionPathwayService.queryPrivince());
	}
	
	/**
	 * 市
	 * @param privince	省
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/queryCity")
	@ApiOperation(value="市",notes="市",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "privince", value = "省", paramType = "query", required = true, dataType = "String")
	})
	public Response queryCity(String privince){
		return SuccessResponseData.newInstance(regionPathwayService.queryCity(privince));
	}
	
	/**
	 * 区/县
	 * @param city	市
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/queryCounty")
	@ApiOperation(value="区/县",notes="区/县",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "city", value = "市", paramType = "query", required = true, dataType = "String")
	})
	public Response queryCounty(String city){
		return SuccessResponseData.newInstance(regionPathwayService.queryCounty(city));
	}
	
	/**
	 * 运输路线列表
	 * @param privince	省
	 * @param city		市
	 * @param county	县/区
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/queryHaulway")
	@ApiOperation(value="运输路线列表",notes="运输路线列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "privince", value = "省", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "city", value = "市", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "county", value = "区/县", paramType = "query", required = true, dataType = "String")
	})
	public Response queryHaulway(String privince,String city,String county){
		return SuccessResponseData.newInstance(regionPathwayService.queryHaulway(privince, city, county));
	}
	
	/**
	 * 新增途经点
	 * @param entity
	 * @return
	 */
	@IgnoreUserToken
	@PostMapping("/addOne")
	@ApiOperation(value="新增途经点",notes="新增途经点",httpMethod="POST")
	public Response addOne(@RequestBody RegionPathway entity){
		return SuccessResponseData.newInstance(regionPathwayService.addOne(entity));
	}
	
	/**
	 * 省市县
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findAllRegion")
	@ApiOperation(value="省市县",notes="省市县",httpMethod="GET")
	public Response findAllRegion(){
		return SuccessResponseData.newInstance(regionPathwayService.findAllRegion());
	}
}

