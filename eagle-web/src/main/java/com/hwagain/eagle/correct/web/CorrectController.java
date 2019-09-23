package com.hwagain.eagle.correct.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.correct.dto.CorrectDto;
import com.hwagain.eagle.correct.service.ICorrectService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@RestController
@RequestMapping(value="/correct/correct",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="运费补助补正申请API")
public class CorrectController extends BaseController{
	
	@Autowired
	ICorrectService correctService;
	/**
	 * findRegionByParam
	 */
	
	@GetMapping("/findRegionByParam")
	@ApiOperation(value="区域信息",notes="区域信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "supplierName", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "province", value = "province", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "material", value = "material", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "city", value = "city", paramType = "query", required = false, dataType = "String")
	})
	public Response subsidyCorrect(String supplierName, String province, String material, String city){
		return SuccessResponseData.newInstance(correctService.findRegionByParam(supplierName, province, material, city));
		
	}
	/**
	 * findAllAudited
	 */
	@GetMapping("/findAllAudited")
	@ApiOperation(value="补正申请审核明细信息",notes="补正申请审核明细信息",httpMethod="GET")
	
	public Response findAllAudited(){
		return SuccessResponseData.newInstance(correctService.findAllAudited());
		
	}
	
	/**
	 * findAllAudited
	 */
	@GetMapping("/findAllAudited1")
	@ApiOperation(value="补正申请审核明细信息",notes="补正申请审核明细信息",httpMethod="GET")
	
	public Response findAllAudited1(){
		return SuccessResponseData.newInstance(correctService.findAllAudited1());
		
	}
	@GetMapping("/subsidyCorrect")
	@ApiOperation(value="运费补助补正申请",notes="运费补助补正申请",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids;格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	})
	public Response subsidyCorrect(String ids){
		return SuccessResponseData.newInstance(correctService.subsidyCorrect(ids));
		
	}
	
	@PostMapping("/sentToOA")
	@ApiOperation(value="提交OA",notes="提交OA",httpMethod="POST")
	public Response sentToOA(@RequestBody List<CorrectDto> dtos){
		return SuccessResponseData.newInstance(correctService.sentToOA(dtos));		
	}
	
	@GetMapping("/findAllSentOA")
	@ApiOperation(value="提交OA",notes="提交OA",httpMethod="GET")
	public Response findAllSentOA(){
		return SuccessResponseData.newInstance(correctService.findAllSentOA());		
	}
}
