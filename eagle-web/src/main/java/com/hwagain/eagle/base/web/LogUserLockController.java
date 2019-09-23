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
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.base.dto.LogUserLockDto;
import com.hwagain.eagle.base.service.ILogUserLockService;

/**
 * <p>
 * 用户上锁/解锁操作日志表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
//@RestController
//@RequestMapping(value="/base/logUserLock",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="日志-用户上锁/解锁操作日志API")
public class LogUserLockController extends BaseController{
	
	@Autowired
	ILogUserLockService logUserLockService;
	
	/**
	 * 新增日志
	 * @param dto
	 * @return
	 */
	@PostMapping("/addOneLog")
	@ApiOperation(value="新增日志",notes="新增日志",httpMethod="POST")
	public Response addOneLog(@RequestBody LogUserLockDto dto){
		return SuccessResponseData.newInstance(logUserLockService.addOneLog(dto));
	}
	
	/**
	 * 查询日志
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="日志列表",notes="日志列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(logUserLockService.findAll());
	}
}
