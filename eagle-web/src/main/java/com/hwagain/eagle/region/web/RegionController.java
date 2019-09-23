package com.hwagain.eagle.region.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 片区表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@RestController
@RequestMapping(value="/region",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="竹木原料运输补助标准查询")
public class RegionController extends BaseController{
	
	@Autowired
	IRegionService regionService;
	
	/**
	 * 【查询全部】补助标准
	 * @return SuccessResponseData
	 */
	@GetMapping("/findList")
	@ApiOperation(notes="【查询全部】补助标准",value="【查询全部】补助标准",httpMethod="GET")
	public Response findList() {
		return SuccessResponseData.newInstance(regionService.findList());
	}
	
	/**
	 * 【散户】补助标准
	 * @return SuccessResponseData
	 */
	@GetMapping("/findListRetail")
	@ApiOperation(notes="【散户】补助标准",value="【散户】补助标准",httpMethod="GET")
	public Response findListRetail() {
		return SuccessResponseData.newInstance(regionService.findListRetail());
	}
	
	/**
	 * 【查询单条】补助标准
	 * @param entity
	 * @return SuccessResponseData
	 */
	@GetMapping("/findOneByParam")
	@ApiOperation(notes="【查询单条】补助标准",value="【查询单条】补助标准",httpMethod="GET")
	public Response findOneByParam(Region entity) {
		return SuccessResponseData.newInstance(regionService.findOneByParam(entity));
	}
	
	/**
	 * 根据供应商名称查询对应片区
	 * @param supplierName	供应商名称
	 * @param material		原料名称
	 * @return SuccessResponseData
	 */
	@GetMapping("/findRegionBySupplier")
	@ApiOperation(notes="findRegionBySupplier",value="findRegionBySupplier",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierName",value="供应商名称",dataType="String",paramType="query",required=true),
		@ApiImplicitParam(name="material",value="原料名称",dataType="String",paramType="query",required=true),
	})
	public Response findRegionBySupplier(String supplierName,String material) {
		return SuccessResponseData.newInstance(regionService.findRegionBySupplier(supplierName, material));
	}
	
	/**
	 * 【查询单条】补助标准
	 * @param supplierName	供应商名称
	 * @param city			城市
	 * @param material		原料名称
	 * @param poundTime		过磅时间（不同时间标准不一样）
	 * @return SuccessResponseData
	 */
	@GetMapping("/findOneByParam2")
	@ApiOperation(notes="【查询单条】补助标准",value="【查询单条】补助标准",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierName",value="供应商名称",dataType="String",paramType="query",required=true),
		@ApiImplicitParam(name="material",value="原料名称",dataType="String",paramType="query",required=true),		
		@ApiImplicitParam(name="city",value="市",dataType="String",paramType="query",required=true),
		@ApiImplicitParam(name="poundTime",value="过磅时间",dataType="String",paramType="query",required=true)
	})
	public Response findOneByParam2(String supplierName,String city,String material,String poundTime) {
		return SuccessResponseData.newInstance(regionService.findOneByParam2(supplierName, city, material, poundTime));
	}
}
