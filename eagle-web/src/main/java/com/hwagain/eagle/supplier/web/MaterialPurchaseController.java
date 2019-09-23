package com.hwagain.eagle.supplier.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.supplier.service.IMaterialPurchaseService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-04-09
 */
@RestController
@RequestMapping(value="/supplier/materialPurchase",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="原材料收购执行表API")
public class MaterialPurchaseController extends BaseController{
	
	@Autowired
	IMaterialPurchaseService materialPurchaseService;
	
	/**
	 * 执行表
	 * @return
	 */
	@GetMapping("/getNowPlan")
	@ApiOperation(value="执行表",notes="执行表",httpMethod="GET")
	public Response getNowPlan(){
		return SuccessResponseData.newInstance(materialPurchaseService.getNowPlan());
	}
	
	/**
	 * 执行总表
	 * @return
	 */
	@GetMapping("/getTotalPlan")
	@ApiOperation(value="执行总表",notes="执行总表",httpMethod="GET")
	public Response getTotalPlan(){
		return SuccessResponseData.newInstance(materialPurchaseService.getTotalPlan());
	}
	
}
