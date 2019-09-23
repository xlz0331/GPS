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

import com.hwagain.eagle.base.service.ILogLoginService;

/**
 * <p>
 * 用户登录日志表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@RestController
@RequestMapping(value="/base/logLogin",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="日志（登录）管理API")
public class LogLoginController extends BaseController{
	
	@Autowired
	ILogLoginService logLoginService;
	
	/**
	 * 查询logLogin日志
	 * @param type		日志类型
	 * @param userId	用户id
	 * @param sessionId
	 * @return
	 */
	@GetMapping("/findByType")
	@ApiOperation(value="查询logLogin",notes="查询logLogin",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "类型", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "sessionId", value = "sessionId", paramType = "query", required = false, dataType = "String")
	})
	public Response findByType(String type,Long userId,String sessionId){
		return SuccessResponseData.newInstance(logLoginService.findByType(type, userId, sessionId));
	}
}
