package com.hwagain.eagle.role.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.hwagain.eagle.role.dto.RoleDto;
import com.hwagain.eagle.role.service.IRoleService;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@RestController
@RequestMapping(value="/role/role",method={RequestMethod.GET,RequestMethod.POST})
//@Api(value="角色管理",description="角色管理")
@Api(tags="用户系统角色管理API")
public class RoleController extends BaseController{
	
	@Autowired
	IRoleService roleService;
	
	/**
	 * 新增/修改数据
	 * 
	 * @param dto
	 * @return SuccessResponseData
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增角色",notes="新增角色",httpMethod="POST")
	public Response addOne(@RequestBody RoleDto dto){
		return SuccessResponseData.newInstance(roleService.addOne(dto));
	}
	
	/**
	 * 删除数据，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return SuccessResponseData
	 */
	@RequestMapping(value="/deleteByIds",method={RequestMethod.GET})
	@ApiOperation(value = "删除数据", notes = "删除数据",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		
		return SuccessResponse.newInstance(roleService.deleteByIds(ids));
	}
	
	/**
	 * 查询角色列表数据
	 *
	 * @param 
	 * @return SuccessResponseData
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="角色列表",notes="角色列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(roleService.findAll());
	}
	/**
	 * 分页返回角色列表
	 * @param pageNum
	 * @param pageSize
	 * @return SuccessResponseData
	 */
	@PostMapping(value="/findByPage/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回角色列表", notes = "分页返回角色列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize){
		return SuccessResponseData.newInstance(roleService.findByPage(pageNum, pageSize));
	}
}
