package com.hwagain.eagle.supplier.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.supplier.dto.SupplyPlanDto;
import com.hwagain.eagle.supplier.service.ISupplyPlanService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 供货计划信息表 前端控制器
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
@RestController
@RequestMapping(value="/supplier/supplyPlan")
@Api(tags="供货计划管理API")
public class SupplyPlanController extends BaseController{
	
	@Autowired
	ISupplyPlanService supplyPlanService;
	
	/**
	 * 新增供货计划信息
	 *
	 * @param supplyPlanDto
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增供货计划信息", notes = "新增供货计划信息",httpMethod="POST")
	public Response save(@RequestBody SupplyPlanDto supplyPlanDto){
		return SuccessResponseData.newInstance(supplyPlanService.save(supplyPlanDto));
	}

	/**
	 * 修改供货计划信息
	 *
	 * @param supplyPlanDto
	 * @return SuccessResponseData
	 */
	@PutMapping(value="/update/{fdId}")
	@ApiOperation(value = "修改供货计划信息", notes = "修改供货计划信息",httpMethod="PUT")
	@ApiImplicitParam(name = "fdId", value = "供货计划信息ID", paramType = "path", required = true, dataType = "String")
	public Response update(@PathVariable Long fdId, @RequestBody SupplyPlanDto supplyPlanDto){
		supplyPlanDto.setFdId(fdId);
		return SuccessResponseData.newInstance(supplyPlanService.update(supplyPlanDto));
	}

	/**
	 * 删除供货计划信息
	 *
	 * @param fdId 供货计划信息ID
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/delete/{fdId}")
	@ApiOperation(value = "删除供货计划信息", notes = "删除供货计划信息",httpMethod="DELETE")
	@ApiImplicitParam(name = "fdId",  value = "供货计划信息ID", paramType = "path", required = true, dataType = "String")
	public Response delete(@PathVariable String fdId){
		return SuccessResponse.newInstance(supplyPlanService.delete(fdId));
	}

	/**
	 * 批量删除供货计划信息
	 *
	 * @param ids  格式：1;2;3;4....
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/deleteBath")
	@ApiOperation(value = "批量删除供货计划信息", notes = "批量删除供货计划信息",httpMethod="DELETE")
	@ApiImplicitParam(name = "ids", value = "供货计划信息集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	public Response deleteBatch(String ids){
		return SuccessResponse.newInstance(supplyPlanService.deleteByIds(ids));
	}

	/**
	 * 查询全部供货计划信息
	 *
	 * @return SuccessResponseData
	 */
	@GetMapping("/findAll")
	@ApiOperation(value = "查詢供货计划信息列表", notes = "查詢供货计划信息列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(supplyPlanService.findAll());
	}

	/**
	 * 根据ID查询供货计划信息
	 * @param fdId
	 * @return SuccessResponseData
	 */
	@GetMapping(value="/findOne/{fdId}")
	@ApiOperation(value = "根据ID查询供货计划信息", notes = "根据ID查询供货计划信息",httpMethod="GET")
	@ApiImplicitParam(name = "fdId", value = "供货计划信息ID", paramType = "path", required = true, dataType = "String")
	public Response findOne(@PathVariable String fdId){
		return SuccessResponseData.newInstance(supplyPlanService.findOne(fdId));
	}
	
}
