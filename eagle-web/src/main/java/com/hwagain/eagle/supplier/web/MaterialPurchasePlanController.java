package com.hwagain.eagle.supplier.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.role.dto.RoleDto;
import com.hwagain.eagle.supplier.dto.MaterialPurchasePlanDto;
import com.hwagain.eagle.supplier.service.IMaterialPurchasePlanService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-22
 */
@RestController
@RequestMapping(value="/supplier/materialPurchasePlan",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="原料收购计划API")
public class MaterialPurchasePlanController extends BaseController{
	
	@Autowired
	IMaterialPurchasePlanService materialPurchasePlanService;
	
	/**
	 * 新增计划
	 * @param dto
	 * @param response
	 * @return SuccessResponseData
	 */
	@RequestMapping("/addOnePlan")
	@ApiOperation(value="新增计划",notes="新增计划",httpMethod="POST")
	public Response addOne(@RequestBody MaterialPurchasePlanDto dto,HttpServletResponse response){
//		System.err.println(response);
		return SuccessResponseData.newInstance(materialPurchasePlanService.addOne(dto));
	}
	
	/**
	 * 查询计划列表
	 * @param supplierId 供应商id
	 * @param state		  计划状态
	 * @return SuccessResponseData
	 */
	@GetMapping("/findBySupplier")
	@ApiOperation(value="查询计划列表",notes="查询计划列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierId", value = "供应商Id", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态", paramType = "query", required = false, dataType = "Integer")
	})
	public Response findBySupplier(Long supplierId, Integer state){
		return SuccessResponseData.newInstance(materialPurchasePlanService.findBySupplier(supplierId,state));
	}
	
	/**
	 * 历史计划列表
	 * @param supplierName	供应商名称
	 * @param material		原料名称
	 * @param planStartTime	计划开始时间
	 * @param planEndTime	计划结束时间
	 * @return SuccessResponseData
	 */
	@GetMapping("/findMaterialHistory")
	@ApiOperation(value="历史计划列表",notes="历史计划列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "material", value = "原料名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "planStartTime", value = "开始日期", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "planEndTime", value = "结束日期", paramType = "query", required = false, dataType = "String")
	})
	public Response findMaterialHistory(String supplierName, String material, Date planStartTime,
			Date planEndTime){
		return SuccessResponseData.newInstance(materialPurchasePlanService.findMaterialHistory(supplierName, material, planStartTime, planEndTime));
	}
	
	/**
	 * 批量删除
	 * @param ids(1;2;3...)
	 * @return SuccessResponseData
	 */
	@GetMapping("/deteleByIds")
	@ApiOperation(value="批量删除",notes="批量删除",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")

	})
	public Response deleteByIds(String ids){
		return SuccessResponseData.newInstance(materialPurchasePlanService.deleteByIds(ids));
	}
	
	/**
	 * 提交OA
	 * 
	 * @param ids
	 * @return
	 */
	@GetMapping("/inputSendPurchasePlanToOa")
	@ApiOperation(value="提交OA审核",notes="提交OA审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ids",value="ids(1;2;3...)",paramType="query",required=true,dataType="STring")
	})
	public Response inputSendPurchasePlanToOa(String ids){
		System.err.println(ids);
		return SuccessResponseData.newInstance(materialPurchasePlanService.inputSendPurchasePlanToOa(ids));
	}
	
	/**
	 * OA查询列表
	 * @param oACode 
	 * @return SuccessResponseData
	 */
	@GetMapping("/inputQueryByOaCode")
	@ApiOperation(value="OA查询列表",notes="OA查询列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="oACode",value="oACode",paramType="query",required=true,dataType="String")
	})
	public Response inputQueryByOaCode(String oACode){
		return SuccessResponseData.newInstance(materialPurchasePlanService.inputQueryByOaCode(oACode));
	}
	
	/**
	 * OA审批
	 * @param oACode
	 * @param status	状态
	 * @param nodeName	节点名称
	 * @param empName	审批人姓名
	 * @param empNo		审批人工号
	 * @param flowDjbh	OA单据编号
	 * @param flowDjlsh	OA单据流水号
	 * @return
	 */
	@GetMapping("/inputOaAduitFlow")
	@ApiOperation(value="OA审批",notes="OA审批",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oACode", value = "oACode", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "状态(3:已审核，4:审核不通过)", paramType = "query", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "nodeName", value = "OA流程节点名称", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empName", value = "审核人审批姓名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empNo", value = "审核审批人工号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "flowDjbh", value = "OA单据编号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "flowDjlsh", value = "OA单据流水号", paramType = "query", required = false, dataType = "String") })
	public Response inputOaAduitFlow(String oACode, Integer status, String nodeName, String empName, String empNo,
			String flowDjbh, String flowDjlsh){
		return SuccessResponseData.newInstance(materialPurchasePlanService.inputOaAduitFlow(oACode, status, nodeName, empName, empNo, flowDjbh, flowDjlsh));
	}
	
	/**
	 * 获取执行计划
	 * @return
	 */
	@GetMapping("/getNowPlan")
	@ApiOperation(value="获取执行计划",notes="获取执行计划",httpMethod="GET")
	public Response getNowPlan(){
		return SuccessResponseData.newInstance(materialPurchasePlanService.getNowPlan());
	}
	
	/**
	 * 收购计划
	 * @return
	 */
	@GetMapping("/getPurchasePlan")
	@ApiOperation(value="getPurchasePlan",notes="getPurchasePlan",httpMethod="GET")
	public Response getPurchasePlan(){
		return SuccessResponseData.newInstance(materialPurchasePlanService.getPurchasePlan());
	}
}
