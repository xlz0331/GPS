package com.hwagain.eagle.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.user.dto.UserRoleDto;
import com.hwagain.eagle.user.service.IUserRoleService;
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
 * 用户用户角色表 前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
@RestController
@RequestMapping(value="/user/userRole")
@Api(tags="用户角色管理API")
public class UserRoleController extends BaseController{

	@Autowired
	IUserRoleService userRoleService;

	/**
	 * 修改用户角色
	 *
	 * @param userRoleDto
	 * @return
	 */
	@PutMapping(value="/update{fdId}")
	@ApiOperation(value = "修改用户角色", notes = "修改用户角色",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "用户角色ID", paramType = "path", required = true, dataType = "String"),
	})
	public Response update(@PathVariable Long fdId, @RequestBody UserRoleDto userRoleDto){
		userRoleDto.setFdId(fdId);
		return SuccessResponseData.newInstance(userRoleService.update(userRoleDto));
	}

	/**
	 * 新增用户角色
	 *
	 * @param userRoleDto
	 * @return
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增用户角色", notes = "新增用户角色",httpMethod="POST")
	public Response save(@RequestBody UserRoleDto userRoleDto){
		return SuccessResponseData.newInstance(userRoleService.save(userRoleDto));
	}

	/**
	 * 删除用户角色
	 *
	 * @param fdId 用户角色ID
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/delete/{fdId}")
	@ApiOperation(value = "删除用户角色", notes = "删除用户角色",httpMethod="DELETE")
	@ApiImplicitParam(name = "fdId",  value = "用户角色ID", paramType = "path", required = true, dataType = "String")
	public Response delete(@PathVariable String fdId){
		return SuccessResponse.newInstance(userRoleService.delete(fdId));
	}

	/**
	 * 批量删除用户角色
	 *
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@DeleteMapping(value="/deleteBath")
	@ApiOperation(value = "批量删除用户角色", notes = "批量删除用户角色",httpMethod="DELETE")
	@ApiImplicitParam(name = "ids", value = "用户角色集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	public Response deleteBatch(String ids){
		return SuccessResponse.newInstance(userRoleService.deleteByIds(ids));
	}

	/**
	 * 查询全部用户角色
	 *
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value = "查詢用户角色列表", notes = "查詢用户角色列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(userRoleService.findAll());
	}

	/**
	 * 根据ID查询用户角色
	 *
	 * @param fdId
	 * @return
	 */
	@GetMapping(value="/findOne/{fdId}")
	@ApiOperation(value = "根据ID查询用户角色", notes = "根据ID查询用户角色",httpMethod="GET")
	@ApiImplicitParam(name = "fdId", value = "用户角色ID", paramType = "path", required = true, dataType = "String")
	public Response findOne(@PathVariable String fdId){
		return SuccessResponseData.newInstance(userRoleService.findOne(fdId));
	}

	/**
	 * 根据参数查询用户角色
	 * @param userRoleDto
	 * @return
	 */
	@PostMapping(value="/queryParam")
	@ApiOperation(value = "根据参数查询用户角色", notes = "根据参数查询用户角色(userId, roleId, state)",httpMethod="POST")
	public Response queryParam(@RequestBody UserRoleDto userRoleDto) {
		return SuccessResponseData.newInstance(userRoleService.queryParam(userRoleDto));
	}
}
