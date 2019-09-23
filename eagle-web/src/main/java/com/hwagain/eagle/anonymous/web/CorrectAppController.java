package com.hwagain.eagle.anonymous.web;

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
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.region.service.IRegionOaService;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@RestController
@RequestMapping(value="/app/correct",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="OA-运费补助补正申请API")
public class CorrectAppController extends BaseController{
	
	@Autowired
	ICorrectService correctService;
	@Autowired
	IRegionDetailService regionDetailService;
	@Autowired
	IRegionOaService regionOaService;
	@Autowired ISupplierInfoService supplierInfoService;
	
	/**
	 * 运费补助补正申请【详情】
	 * @param oaCode
	 * @return
	 */
	@GetMapping("/findCorrentDetailByOaCode")
	@ApiOperation(value="运费补助补正申请【详情】",notes="运费补助补正申请【详情】",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String")
	})
	public Response findByOaCode(String oaCode){
		return SuccessResponseData.newInstance(correctService.findByOaCode(oaCode));
		
	}
	
	/**
	 * 运费补助补正申请【审批】
	 * @param oaCode
	 * @param correntId 
	 * @param reason	理由
	 * @param state		状态(1:同意，2:不同意)
	 * @param node		A流程节点名称[1：财务总经理审核、2：运用总经理审批、3：生产支持副总裁审批]
	 * @param empName	审核审批人姓名
	 * @param empNo		审核审批人工号
	 * @param flowDjbh	OA单据编号
	 * @param flowDjlsh	OA单据流水号
	 * @return
	 */
	@PostMapping("/auditCorrent")
	@ApiOperation(value = "运费补助补正申请【审批】 ", notes = "运费补助补正申请【审批】 ", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "correntId", value = "correntId", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "理由", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态(1:同意，2:不同意)", paramType = "query", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "node", value = "OA流程节点名称[1：财务总经理审核、2：运用总经理审批、3：生产支持副总裁审批]", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empName", value = "审核审批人姓名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empNo", value = "审核审批人工号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "flowDjbh", value = "OA单据编号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "flowDjlsh", value = "OA单据流水号", paramType = "query", required = false, dataType = "String") })	
	public Response auditCorrent(String oaCode, Long correntId, String reason,Integer state,String node, String empName, String empNo,
			String flowDjbh, String flowDjlsh){
		return SuccessResponseData.newInstance(correctService.audit(state, reason, oaCode,correntId, node, empName, empNo, flowDjbh, flowDjlsh));
	}
	
	/**
	 * 供应商收购地点申报【详情】
	 * @param oaCode
	 * @return
	 */
	@GetMapping("/findRegionDetailByOaCode")
	@ApiOperation(value="供应商收购地点申报【详情】",notes="供应商收购地点申报【详情】",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String")
	})
	public Response findRegionDetailByOaCode(String oaCode){
		return SuccessResponseData.newInstance(regionDetailService.findRegionDetailByOaCode(oaCode));
		
	}
	
	/**
	 * 供应商收购地点申报【审批】 
	 * @param state		状态(1:收购点信息确实无误，2:信息有误请重新报备)
	 * @param oaCode
	 * @param node		OA流程节点名称[1：原料收购部主管审核、2：运营总经理审批]
	 * @param empName	审核审批人姓名
	 * @param empNo		审核审批人工号
	 * @param flowDjbh	OA单据编号
	 * @param flowDjlsh	OA单据流水号
	 * @return
	 */
	@PostMapping("/auditRegionDetail")
	@ApiOperation(value = "供应商收购地点申报【审批】 ", notes = "供应商收购地点申报【审批】 ", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态(1:收购点信息确实无误，2:信息有误请重新报备)", paramType = "query", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "node", value = "OA流程节点名称[1：原料收购部主管审核、2：运营总经理审批]", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empName", value = "审核审批人姓名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empNo", value = "审核审批人工号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "flowDjbh", value = "OA单据编号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "flowDjlsh", value = "OA单据流水号", paramType = "query", required = false, dataType = "String") })	
	public Response auditRegionDetail(Integer state,String oaCode, String node, String empName, String empNo,
			String flowDjbh, String flowDjlsh){
		return SuccessResponseData.newInstance(regionDetailService.audit(state, oaCode, node, empName, empNo, flowDjbh, flowDjlsh));
	}
	
	/**
	 * 补助标准【详情】(废除)
	 * @return
	 */
	@GetMapping("/findRegionList")
	@ApiOperation(notes="补助标准【详情】(废除)",value="补助标准【详情】(废除)",httpMethod="GET")
	public Response findList() {
		return SuccessResponseData.newInstance(regionOaService.findList());
	}
	
	/**
	 * 补助标准【详情】
	 * @param oaCode
	 * @return
	 */
	@GetMapping("/findRegionList2")
	@ApiOperation(notes="补助标准【详情】",value="补助标准【详情】",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="oaCode",value="oaCode",dataType="Long",paramType="query",required=true),
	})
	public Response findList2(String oaCode) {
		return SuccessResponseData.newInstance(regionOaService.findList2(oaCode));
	}
	
	/**
	 * 补助标准【审批】
	 * @param status	审批状态
	 * @param oaCode
	 * @param node		OA流程节点名称[补助标准审批]
	 * @param empName	审核审批人姓名
	 * @param empNo		审核审批人工号
	 * @param flowDjbh	OA单据编号
	 * @param flowDjlsh	OA单据流水号
	 * @return
	 */
	@GetMapping("/auditregionOa")
	@ApiOperation(notes="补助标准【审批】",value="补助标准【审批】",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="status",value="审批状态",dataType="int",paramType="query",required=true),
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "node", value = "OA流程节点名称[补助标准审批]", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empName", value = "审核审批人姓名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empNo", value = "审核审批人工号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "flowDjbh", value = "OA单据编号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "flowDjlsh", value = "OA单据流水号", paramType = "query", required = false, dataType = "String")
	})
	public Response insert(Integer status,String oaCode,String node,String empName,String empNo,String flowDjbh,String flowDjlsh) {
		return SuccessResponseData.newInstance(regionOaService.approveRegion(status, oaCode, node, empName, empNo, flowDjbh, flowDjlsh));
	}
	
	/**
	 * [散户]补助标准【审批】
	 * @param status	审批状态
	 * @param oaCode
	 * @param node		OA流程节点名称[补助标准审批]
	 * @param empName	审核审批人姓名
	 * @param empNo		审核审批人工号
	 * @param flowDjbh	OA单据编号
	 * @param flowDjlsh	OA单据流水号
	 * @return
	 */
	@GetMapping("/approveRegionRetail")
	@ApiOperation(notes="[散户]补助标准【审批】",value="[散户]补助标准【审批】",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="status",value="审批状态",dataType="int",paramType="query",required=true),
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "node", value = "OA流程节点名称[补助标准审批]", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empName", value = "审核审批人姓名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "empNo", value = "审核审批人工号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "flowDjbh", value = "OA单据编号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "flowDjlsh", value = "OA单据流水号", paramType = "query", required = false, dataType = "String")
	})
	public Response approveRegionRetail(Integer status,String oaCode,String node,String empName,String empNo,String flowDjbh,String flowDjlsh) {
		return SuccessResponseData.newInstance(regionOaService.approveRegionRetail(status, oaCode, node, empName, empNo, flowDjbh, flowDjlsh));
	}
	
	/**
	 * 	运费补助补正申请【详情】
	 * @param oaCode
	 * @return
	 */
	@GetMapping("/queryTaskResultByOaCode")
	@ApiOperation(value="运费补助补正申请【详情】",notes="运费补助补正申请【详情】",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oaCode", value = "oaCode", paramType = "query", required = true, dataType = "String")
	})
	public Response queryTaskResultByOaCode(String oaCode){
		return SuccessResponseData.newInstance(correctService.queryTaskResultByOaCode(oaCode));		
	}
	
	/**
	 * 所有供应商基础信息
	 * @return
	 */
	@GetMapping("/findAllSupplierForOA")
	@ApiOperation(value="所有供应商基础信息",notes="所有供应商基础信息",httpMethod="GET")
	public Response findAllSupplierForOA(){
		return SuccessResponseData.newInstance(supplierInfoService.findAllSupplierForOA());		
	}
}
