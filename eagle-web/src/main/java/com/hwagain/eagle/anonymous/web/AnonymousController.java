package com.hwagain.eagle.anonymous.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.region.service.IRegionOaService;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;

/**
 * <p>
 * 片区表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@RestController
@RequestMapping(value="/app",method={RequestMethod.GET,RequestMethod.POST})
//@Api(tags="匿名接口")
public class AnonymousController extends BaseController{
	
//	@Autowired
//	IRegionOaService regionOaService;
//	
//	@GetMapping("/regionOa/approve")
//	@ApiOperation(notes="【审批】补助标准",value="【审批】补助标准",httpMethod="GET")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="status",value="审批状态",dataType="int",paramType="query",required=true)
//	})
//	public Response insert(Integer status) {
//		return SuccessResponseData.newInstance(regionOaService.approveRegion(status));
//	}
}
