package com.hwagain.eagle.anonymous.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.hwagain.eagle.base.dto.DriverInfoDto;
import com.hwagain.eagle.base.entity.DriverInfo;
import com.hwagain.eagle.base.service.IDriverInfoService;
import com.hwagain.eagle.task.dto.TaskDto;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
@RestController
@RequestMapping(value="/app/driverInfo",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="APP-司机信息管理API")
public class DriverInfoAppController extends BaseController{
	
	@Autowired
	IDriverInfoService driverInfoService;
	
	/**
	 * 查询所有司机列表
	 * @param mobile		手机号
	 * @param driverName	司机姓名
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findAll")
	@ApiOperation(value="查询所有司机列表",notes="查询所有司机列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "driverName", value = "司机名称", paramType = "query", required = false, dataType = "String")

	})
	public Response findAll(String mobile,String driverName){
		return SuccessResponseData.newInstance(driverInfoService.findAllBySupplier(mobile, driverName));
	}
	
	/**
	 * 新增司机账号
	 * @param dto
	 * @return
	 */
	@IgnoreUserToken
	@PostMapping("/addDriver")
	@ApiOperation(value="新增司机账号",notes="新增司机账号",httpMethod="POST")
	public Response addDriver(@RequestBody DriverInfo dto){
		return SuccessResponseData.newInstance(driverInfoService.addDriver(dto));
		
	}
}
