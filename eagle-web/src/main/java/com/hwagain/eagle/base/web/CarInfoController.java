package com.hwagain.eagle.base.web;

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

import com.hwagain.eagle.base.dto.CarInfoDto;
import com.hwagain.eagle.base.dto.DriverInfoDto;
import com.hwagain.eagle.base.service.ICarInfoService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
//@RestController
//@RequestMapping(value="/base/carInfo",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="车辆信息管理API")
public class CarInfoController extends BaseController{
	
	@Autowired
	ICarInfoService carInfoService;
	
	/**
	 * 查询所有车辆列表
	 * @param supplierName  供应商名称
	 * @param carType		车辆类型
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="查询所有车辆列表",notes="查询所有车辆列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "carType", value = "车辆类型", paramType = "query", required = false, dataType = "String")

	})
	public Response findAll(String supplierName,String carType){
		return SuccessResponseData.newInstance(carInfoService.findAll(supplierName, carType));
	}
	
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增",notes="新增",httpMethod="POST")
	public Response addOne(@RequestBody CarInfoDto dto){
		return SuccessResponseData.newInstance(carInfoService.addOne(dto));
	}
	
	/**
	 * 批量删除
	 * @param ids 格式： 1;2;3...
	 * @return
	 */
	@GetMapping("/deteleByIds")
	@ApiOperation(value="批量删除",notes="批量删除",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")

	})
	public Response deleteByIds(String ids){
		return SuccessResponseData.newInstance(carInfoService.deleteByIds(ids));
	}
}
