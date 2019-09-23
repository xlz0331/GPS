package com.hwagain.eagle.acquisitionIndictor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.acquisitionIndictor.dto.AcquisitionIndictorDto;
import com.hwagain.eagle.acquisitionIndictor.service.IAcquisitionIndictorService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 收购指标信息表 前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
@RestController
@RequestMapping(value="/acquisitionIndictor")
@Api(tags="收购指标管理API")
public class AcquisitionIndictorController extends BaseController{
	
	@Autowired
	IAcquisitionIndictorService acquisitionIndictorService;

	/**
	 * 新增收购指标信息
	 *
	 * @param acquisitionIndictorDto
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增收购指标信息", notes = "新增收购指标信息",httpMethod="POST")
	public Response save(@RequestBody AcquisitionIndictorDto acquisitionIndictorDto){
		return SuccessResponseData.newInstance(acquisitionIndictorService.save(acquisitionIndictorDto));
	}

	/**
	 * 修改收购指标信息
	 *
	 * @param acquisitionIndictorDto
	 * @return SuccessResponseData
	 */
	@PutMapping(value="/update/{fdId}")
	@ApiOperation(value = "修改收购指标信息", notes = "修改收购指标信息",httpMethod="PUT")
	@ApiImplicitParam(name = "fdId", value = "收购指标信息ID", paramType = "path", required = true, dataType = "String")
	public Response update(@PathVariable Long fdId, @RequestBody AcquisitionIndictorDto acquisitionIndictorDto){
		acquisitionIndictorDto.setFdId(fdId);
		return SuccessResponseData.newInstance(acquisitionIndictorService.update(acquisitionIndictorDto));
	}

	/**
	 * 删除收购指标信息
	 *
	 * @param fdId 收购指标信息ID
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/delete/{fdId}")
	@ApiOperation(value = "删除收购指标信息", notes = "删除收购指标信息",httpMethod="DELETE")
	@ApiImplicitParam(name = "fdId",  value = "收购指标信息ID", paramType = "path", required = true, dataType = "String")
	public Response delete(@PathVariable String fdId){
		return SuccessResponse.newInstance(acquisitionIndictorService.delete(fdId));
	}

	/**
	 * 批量删除收购指标信息
	 *
	 * @param ids  格式：1;2;3;4....
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/deleteBath")
	@ApiOperation(value = "批量删除收购指标信息", notes = "批量删除收购指标信息",httpMethod="DELETE")
	@ApiImplicitParam(name = "ids", value = "收购指标信息集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	public Response deleteBath(String ids){
		return SuccessResponse.newInstance(acquisitionIndictorService.deleteByIds(ids));
	}

	/**
	 * 查询全部收购指标信息
	 *
	 * @return SuccessResponseData
	 */
	@GetMapping("/findAll")
	@ApiOperation(value = "查詢收购指标信息列表", notes = "查詢收购指标信息列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(acquisitionIndictorService.findAll());
	}

	/**
	 * 根据ID查询收购指标信息
	 * @param fdId
	 * @return SuccessResponseData
	 */
	@GetMapping(value="/findOne/{fdId}")
	@ApiOperation(value = "根据ID查询收购指标信息", notes = "根据ID查询收购指标信息",httpMethod="GET")
	@ApiImplicitParam(name = "fdId", value = "收购指标信息ID", paramType = "path", required = true, dataType = "String")
	public Response findOne(@PathVariable String fdId){
		return SuccessResponseData.newInstance(acquisitionIndictorService.findOne(fdId));
	}
	
}
