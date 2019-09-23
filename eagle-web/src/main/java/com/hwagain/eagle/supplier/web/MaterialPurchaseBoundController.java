package com.hwagain.eagle.supplier.web;

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

import com.hwagain.eagle.supplier.dto.MaterialPurchaseBoundDto;
import com.hwagain.eagle.supplier.service.IMaterialPurchaseBoundService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-04-09
 */
@RestController
@RequestMapping(value="/supplier/materialPurchaseBound",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="原料收购量上下限设置API")
public class MaterialPurchaseBoundController extends BaseController{
	
	@Autowired
	IMaterialPurchaseBoundService materialPurchaseBoundService;
	
	/**
	 * 新增上下限设置
	 * @param dto
	 * @return
	 */
	@RequestMapping("/addOneBound")
	@ApiOperation(value="新增上下限设置",notes="新增上下限设置",httpMethod="POST")
	public Response addOne(@RequestBody MaterialPurchaseBoundDto dto){
		return SuccessResponseData.newInstance(materialPurchaseBoundService.addOne(dto));
	}
	
	/**
	 * 查询当前执行上下限范围
	 * @return
	 */
	@RequestMapping("/findBound")
	@ApiOperation(value="查询当前执行上下限范围",notes="查询当前执行上下限范围",httpMethod="GET")
	public Response findBound(){
		return SuccessResponseData.newInstance(materialPurchaseBoundService.findBound());
	}
}
