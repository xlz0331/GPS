package com.hwagain.eagle.company.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.company.dto.CompanyDto;
import com.hwagain.eagle.company.service.ICompanyService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公司信息表 前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
//@RestController
//@RequestMapping(value="/company")
@Api(tags="公司信息管理API")
public class CompanyController extends BaseController{
	
	@Autowired
	ICompanyService companyService;

	/**
	 * 新增公司信息
	 *
	 * @param companyDto
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增公司信息", notes = "新增公司信息",httpMethod="POST")
	public Response save(@RequestBody CompanyDto companyDto){
		return SuccessResponseData.newInstance(companyService.save(companyDto));
	}

	/**
	 * 修改公司信息
	 *
	 * @param companyDto
	 * @return SuccessResponseData
	 */
	@PutMapping(value="/update/{fdId}")
	@ApiOperation(value = "修改公司信息", notes = "修改公司信息",httpMethod="PUT")
	@ApiImplicitParam(name = "fdId", value = "公司信息ID", paramType = "path", required = true, dataType = "String")
	public Response update(@PathVariable Long fdId, @RequestBody CompanyDto companyDto){
		companyDto.setFdId(fdId);
		return SuccessResponseData.newInstance(companyService.update(companyDto));
	}

	/**
	 * 删除公司信息
	 *
	 * @param fdId 公司信息ID
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/delete/{fdId}")
	@ApiOperation(value = "删除公司信息", notes = "删除公司信息",httpMethod="DELETE")
	@ApiImplicitParam(name = "fdId",  value = "公司信息ID", paramType = "path", required = true, dataType = "String")
	public Response delete(@PathVariable String fdId){
		return SuccessResponse.newInstance(companyService.delete(fdId));
	}

	/**
	 * 批量删除公司信息
	 *
	 * @param ids  格式：1;2;3;4....
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/deleteBath")
	@ApiOperation(value = "批量删除公司信息", notes = "批量删除公司信息",httpMethod="DELETE")
	@ApiImplicitParam(name = "ids", value = "公司信息集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	public Response deleteBatch(String ids){
		return SuccessResponse.newInstance(companyService.deleteByIds(ids));
	}

	/**
	 * 查询全部公司信息
	 *
	 * @return SuccessResponseData
	 */
	@GetMapping("/findAll")
	@ApiOperation(value = "查詢公司信息列表", notes = "查詢公司信息列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(companyService.findAll());
	}

	/**
	 * 根据ID查询公司信息
	 * @param fdId
	 * @return SuccessResponseData
	 */
	@GetMapping(value="/findOne/{fdId}")
	@ApiOperation(value = "根据ID查询公司信息", notes = "根据ID查询公司信息",httpMethod="GET")
	@ApiImplicitParam(name = "fdId", value = "公司信息ID", paramType = "path", required = true, dataType = "String")
	public Response findOne(@PathVariable String fdId){
		return SuccessResponseData.newInstance(companyService.findOne(fdId));
	}
	
}
