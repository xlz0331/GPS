package com.hwagain.eagle.anonymous.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.role.dto.RoleDto;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 供应商信息表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-02-22
 */
@RestController
@RequestMapping(value="/app/supplierInfo")
@Api(tags="APP-供应商管理API")
public class SupplierInfoAppController extends BaseController{
	
	@Autowired
	ISupplierInfoService supplierInfoService;
	
	/**
	 * 新增供应商
	 *
	 * @param supplierInfoDto
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增供应商", notes = "新增供应商",httpMethod="POST")
	public Response save(@RequestBody SupplierInfoDto supplierInfoDto){
		return SuccessResponseData.newInstance(supplierInfoService.save(supplierInfoDto));
	}

	/**
	 * 修改供应商
	 * 
	 * @param fdId	主表id
	 * @param supplierInfoDto
	 * @return
	 */
	@PutMapping(value="/update/{fdId}")
	@ApiOperation(value = "修改供应商", notes = "修改供应商",httpMethod="PUT")
	@ApiImplicitParam(name = "fdId", value = "供应商ID", paramType = "path", required = true, dataType = "String")
	public Response update(@PathVariable Long fdId, @RequestBody SupplierInfoDto supplierInfoDto){
		supplierInfoDto.setFdId(fdId);
		return SuccessResponseData.newInstance(supplierInfoService.update(supplierInfoDto));
	}

	/**
	 * 删除供应商
	 * 
	 * @param fdId 供应商ID
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/delete/{fdId}")
	@ApiOperation(value = "删除供应商", notes = "删除供应商",httpMethod="DELETE")
	@ApiImplicitParam(name = "fdId",  value = "供应商ID", paramType = "path", required = true, dataType = "String")
	public Response delete(@PathVariable String fdId){
		return SuccessResponse.newInstance(supplierInfoService.delete(fdId));
	}

	/**
	 * 批量删除供应商
	 *
	 * @param ids  格式：1;2;3;4....
	 * @return SuccessResponse
	 */
	@GetMapping(value="/deleteByIds")
	@ApiOperation(value = "批量删除供应商", notes = "批量删除供应商",httpMethod="GET")
	@ApiImplicitParam(name = "ids", value = "供应商集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	public Response deleteBatch(String ids){
		return SuccessResponse.newInstance(supplierInfoService.deleteByIds(ids));
	}

	/**
	 * 查询全部供应商
	 *
	 * @return SuccessResponseData
	 */
	@GetMapping("/findAll")
	@ApiOperation(value = "查詢供应商列表", notes = "查詢供应商列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(supplierInfoService.findAll());
	}

	/**
	 * 根据ID查询供应商
	 * 
	 * @param fdId
	 * @return SuccessResponseData
	 */
	@GetMapping(value="/findOne/{fdId}")
	@ApiOperation(value = "根据ID查询供应商", notes = "根据ID查询供应商",httpMethod="GET")
	@ApiImplicitParam(name = "fdId", value = "供应商ID", paramType = "path", required = true, dataType = "String")
	public Response findOne(@PathVariable String fdId){
		return SuccessResponseData.newInstance(supplierInfoService.findOne(fdId));
	}
	
	/**
	 * 根据条件分页返回供应商列表
	 * 
	 * @param pageNum	当前页码
	 * @param pageSize	每页显示的条数
	 * @param name		姓名
	 * @param mobile	电话号码
	 * @return
	 */
	@PostMapping(value="/findByPage/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回供应商列表", notes = "分页返回供应商列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "mobile", value = "电话号码", paramType = "query", required = false, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize,String name,String mobile){
		return SuccessResponseData.newInstance(supplierInfoService.findByPage(pageNum, pageSize, name, mobile));
	}
	
	/**
	 * 新增供应商
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增供应商",notes="新增供应商",httpMethod="POST")
	public Response addOne(@RequestBody SupplierInfoDto dto){
		return SuccessResponseData.newInstance(supplierInfoService.addOne(dto));
	}
}
