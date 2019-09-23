package com.hwagain.eagle.base.web;

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
@RequestMapping(value="/base/driverInfo",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="用户（司机）信息管理API")
public class DriverInfoController extends BaseController{
	
	@Autowired
	IDriverInfoService driverInfoService;
	
	/**
	 * 查询所有司机列表
	 * @param mobile
	 * @param driverName
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="查询所有司机列表",notes="查询所有司机列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "driverName", value = "司机名称", paramType = "query", required = false, dataType = "String")

	})
	public Response findAll(String mobile,String driverName){
		return SuccessResponseData.newInstance(driverInfoService.findAll(mobile, driverName));
	}
	
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增",notes="新增",httpMethod="POST")
	public Response addOne(@RequestBody DriverInfoDto dto){
		System.err.println(dto.getAge());
		return SuccessResponseData.newInstance(driverInfoService.addOne(dto));
	}
	
	/**
	 * 批量删除
	 * @param ids 格式：1;2;3...
	 * @return
	 */
	@GetMapping("/deteleByIds")
	@ApiOperation(value="批量删除",notes="批量删除",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")
	})
	public Response deleteByIds(String ids){
		return SuccessResponseData.newInstance(driverInfoService.deleteByIds(ids));
	}
	
	/**
	 * 根据条件分页返回司机列表
	 * @param pageNum 		当前页码
	 * @param pageSize		每页显示的条数
	 * @param driverInfoDto 对象
	 * @return
	 */
	@PostMapping(value="/findByPage/{pageNum}/{pageSize}")
	@ApiOperation(value = "根据条件分页返回司机列表", notes = "根据条件分页返回司机列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize, @RequestBody(required=false) DriverInfoDto driverInfoDto){
		return SuccessResponseData.newInstance(driverInfoService.findByPage(driverInfoDto, pageNum, pageSize));
	}
	
	/**
	 * 根据条件分页返回司机列表
	 * @param pageNum		当前页码
	 * @param pageSize		每页显示的条数
	 * @param mobile		手机号
	 * @param driverName	司机姓名
	 * @return
	 */
	@PostMapping(value="/findByPagea/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回供应商列表", notes = "分页返回供应商列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "driverName", value = "司机姓名", paramType = "query", required = false, dataType = "String")
	})
	public Response findByPagea(@PathVariable int pageNum, @PathVariable int pageSize,String mobile,String driverName){
		return SuccessResponseData.newInstance(driverInfoService.findByPagea(pageNum, pageSize, mobile, driverName));
	}
}
