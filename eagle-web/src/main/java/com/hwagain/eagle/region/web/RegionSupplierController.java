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

import com.hwagain.eagle.region.service.IRegionSupplierService;

/**
 * <p>
 * 片区表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-06-17
 */
@RestController
@RequestMapping(value="/region/regionSupplier",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="合同供应商竹木原料运输补助标准查询")
public class RegionSupplierController extends BaseController{
	
	@Autowired
	IRegionSupplierService regionSupplierService;
	
	/**
	 * 【查询全部】补助标准
	 * @return
	 */
	@GetMapping("/findList")
	@ApiOperation(notes="【查询全部】补助标准",value="【查询全部】补助标准",httpMethod="GET")
	public Response findList() {
		return SuccessResponseData.newInstance(regionSupplierService.findList());
	}
	
	/**
	 * 查询片区列表BYSupplier
	 * @param supplierName	供应商名称
	 * @param material		原料名称
	 * @return
	 */
	@GetMapping("/findRegionBySupplier")
	@ApiOperation(notes="findRegionBySupplier",value="findRegionBySupplier",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierName",value="供应商名称",dataType="String",paramType="query",required=true),
		@ApiImplicitParam(name="material",value="原料名称",dataType="String",paramType="query",required=true),
	})
	public Response findRegionBySupplier(String supplierName,String material) {
		return SuccessResponseData.newInstance(regionSupplierService.findRegionBySupplier(supplierName, material));
	}
	
	/**
	 * 【查询全部】片区列表
	 * @return
	 */
	@GetMapping("/queryAllRegionSupplier")
	@ApiOperation(notes="【查询全部】queryAllRegionSupplier",value="【查询全部】queryAllRegionSupplier",httpMethod="GET")
	public Response queryAllRegionSupplier() {
		return SuccessResponseData.newInstance(regionSupplierService.queryAllRegionSupplier());
	}
}
