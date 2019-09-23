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

import com.hwagain.eagle.base.service.IChinaRegionService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-20
 */
@RestController
@RequestMapping(value="/base/chinaRegion",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="省市区API")
public class ChinaRegionController extends BaseController{
	
	@Autowired
	IChinaRegionService chinaRegionService;
	
	/**
	 * 查询省市县
	 * @param parentId 上级id
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="省市县",notes="省市县",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentId", value = "parentId", paramType = "query", required = false, dataType = "String")

	})
	public Response findAll(Long parentId){
		return SuccessResponseData.newInstance(chinaRegionService.findAll(parentId));
	}
}
