package com.hwagain.eagle.base.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.base.dto.CarInfoDto;
import com.hwagain.eagle.base.dto.MaterialDto;
import com.hwagain.eagle.base.service.IMaterialService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-04-02
 */
@RestController
@RequestMapping(value="/base/material",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="原材料品种维护API")
public class MaterialController extends BaseController{
	
	@Autowired
	IMaterialService materialService;
	
	
	/**
	 * 查询所有原料品类
	 * @param name
	 * @param level
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="查询所有原料品类",notes="查询所有原料品类",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "原料名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "level", value = "原料等级", paramType = "query", required = false, dataType = "String")
	})
	public Response findAll(String name,String level){
		return SuccessResponseData.newInstance(materialService.findAll(name, level));
	}
	
	
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增",notes="新增",httpMethod="POST")
	public Response addOne(@RequestBody MaterialDto dto){
		return SuccessResponseData.newInstance(materialService.addOne(dto));
	}
	
	/**
	 * 批量删除
	 * @param ids 格式：1;2;3...
	 * @return
	 */
	@GetMapping("/deteleByIds")
	@ApiOperation(value="批量删除",notes="批量删除",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")

	})
	public Response deleteByIds(String ids){
		return SuccessResponseData.newInstance(materialService.deleteByIds(ids));
	}
	
	/**
	 * 更新
	 * @param fdId 主表id
	 * @param code 材料编码
	 * @return
	 */
	@GetMapping("/updateMaterial")
	@ApiOperation(value="更新",notes="更新",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "code", value = "code", paramType = "query", required = true, dataType = "String")
	})
	public Response updateMaterial(Long fdId,String code){
		return SuccessResponseData.newInstance(materialService.updateMaterial(fdId, code));
	}
	
}
