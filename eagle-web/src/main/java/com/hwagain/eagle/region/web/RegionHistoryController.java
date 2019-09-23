package com.hwagain.eagle.region.web;

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

import com.hwagain.eagle.region.service.IRegionHistoryService;

/**
 * <p>
 * 片区表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@RestController
@RequestMapping(value="/region/regionHistory",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="竹木原料运输补助标准历史查询")
public class RegionHistoryController extends BaseController{
	
	@Autowired
	IRegionHistoryService regionHistoryService;
	
	/**
	 * 查询版本号集合
	 * @return
	 */
	@GetMapping("/findVsersions")
	@ApiOperation(value="查询版本号集合",notes="查询版本号集合",httpMethod="GET")
	public Response findVsersions() {
		return SuccessResponseData.newInstance(regionHistoryService.findVsersions());
	}
	
	/**
	 * 根据版本号查询版本
	 * @param version 版本号
	 * @return
	 */
	@GetMapping("/findVsersions1")
	@ApiOperation(value="根据版本号查询版本",notes="根据版本号查询版本",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="version",value="数据ID",dataType="int",paramType="query",required=true),
	})
	public Response findVsersions1(Integer version) {
		System.err.println(version);
		return SuccessResponseData.newInstance(regionHistoryService.findListByVersion(version));
	}
}
