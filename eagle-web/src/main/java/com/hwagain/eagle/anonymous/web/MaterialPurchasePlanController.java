package com.hwagain.eagle.anonymous.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.supplier.service.IMaterialPurchasePlanService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-22
 */
//@RestController
//@RequestMapping(value="/app/materialPurchasePlan",method={RequestMethod.GET,RequestMethod.POST})
//@Api(tags="APP-原料收购计划API")
public class MaterialPurchasePlanController extends BaseController{
	
	@Autowired
	IMaterialPurchasePlanService materialPurchasePlanService;
	
	/**
	 * 查询供应商计划列表
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findBySupplierId")
	@ApiOperation(value="查询供应商计划列表",notes="查询供应商计划列表",httpMethod="GET")
	public Response findBySupplierId(){
		return SuccessResponseData.newInstance(materialPurchasePlanService.findBySupplierId());
	}
}
