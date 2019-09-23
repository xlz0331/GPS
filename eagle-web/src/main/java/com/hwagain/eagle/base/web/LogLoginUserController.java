package com.hwagain.eagle.base.web;

import org.springframework.web.bind.annotation.GetMapping;
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

import com.hwagain.eagle.base.service.ILogLoginUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-21
 */
@RestController
@RequestMapping(value="/base/logLoginUser",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="日志（用户登录状态）管理API")
public class LogLoginUserController extends BaseController{
	
	@Autowired
	ILogLoginUserService logLoginUserService;
	
	/**
	 * 查询所有logLoginUser
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="查询logLoginUser",notes="查询logLoginUser",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(logLoginUserService.findAll());
	}
	
	/**
	 * 查询logLoginUser
	 * @param state 状态（1在线，2不在线）
	 * @return
	 */
	@GetMapping("/findByType")
	@ApiOperation(value="查询logLoginUser",notes="查询logLoginUser",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "state", value = "状态（1在线，2不在线）", paramType = "query", required = false, dataType = "String"),
	})
	public Response findByType(Integer state){
		return SuccessResponseData.newInstance(logLoginUserService.findByType(state));
	}
	
	/**
	 * 删除
	 * @param fdId 主表id
	 * @return
	 */
	@GetMapping("/deleteByFdId")
	@ApiOperation(value="deleteByFdId",notes="deleteByFdId",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = false, dataType = "String"),
	})
	public Response deleteByFdId(Long fdId){
		return SuccessResponseData.newInstance(logLoginUserService.deleteByFdId(fdId));
	}
	
	/**
	 * 查询在途司机状态
	 * @return
	 */
	@GetMapping("/findAllTransport")
	@ApiOperation(value="查询在途司机状态",notes="查询在途司机状态",httpMethod="GET")
	public Response findAllTransport(){
		return SuccessResponseData.newInstance(logLoginUserService.findAllTransport());
	}
}
