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
import com.hwagain.eagle.base.dto.DictDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.service.IDictService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-26
 */
@RestController
@RequestMapping(value="/base/dict",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="1.数据字典API")
public class DictController extends BaseController{
	
	@Autowired
	IDictService dictService;
	
	/**
	 * 查询
	 * @param typeName
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="查询所有",notes="查询所有",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "typeName", value = "类型名称", paramType = "query", required = false, dataType = "String")

	})
	public Response findAll(String typeName){
		return SuccessResponseData.newInstance(dictService.findAll(typeName));
	}
	
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增",notes="新增",httpMethod="POST")
	public Response addOne(@RequestBody DictDto dto){
		return SuccessResponseData.newInstance(dictService.addOne(dto));
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
		return SuccessResponseData.newInstance(dictService.deleteByIds(ids));
	}
	
	/**
	 * 修改
	 * @param dict
	 * @return
	 */
	@PostMapping("/updateOneByFdId")
	@ApiOperation(value="修改",notes="修改",httpMethod="POST")
	public Response updateOneByFdId(@RequestBody Dict dict){
		return SuccessResponseData.newInstance(dictService.updateOneByFdId(dict));
	}
}
