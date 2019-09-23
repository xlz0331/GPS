package com.hwagain.eagle.user.web;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.user.dto.UserInfoDto;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value="/user/userInfo")
@Api(tags="用户信息管理API")
public class UserInfoController extends BaseController{
	
	@Autowired
	IUserInfoService userInfoService;

	/**
	 * 新增用户
	 *
	 * @param userInfoDto
	 * @return
	 */
	@PostMapping(value="/save")
	@ApiOperation(value = "新增用户", notes = "新增用户",httpMethod="POST")
	public Response save(@RequestBody UserInfoDto userInfoDto){
		return SuccessResponseData.newInstance(userInfoService.save(userInfoDto));
	}

	/**
	 * 修改用户
	 *
	 * @param userInfoDto
	 * @return
	 */
	@PutMapping(value="/update/{fdId}")
	@ApiOperation(value = "修改用户", notes = "修改用户",httpMethod="PUT")
	@ApiImplicitParam(name = "fdId", value = "用户ID", paramType = "path", required = true, dataType = "String")
	public Response update(@PathVariable Long fdId, @RequestBody UserInfoDto userInfoDto){
		userInfoDto.setFdId(fdId);
		return SuccessResponseData.newInstance(userInfoService.update(userInfoDto));
	}

	/**
	 * 删除用户
	 *
	 * @param fdId 用户ID
	 * @return SuccessResponse
	 */
	@DeleteMapping(value="/delete/{fdId}")
	@ApiOperation(value = "删除用户", notes = "删除用户",httpMethod="DELETE")
	@ApiImplicitParam(name = "fdId",  value = "用户ID", paramType = "path", required = true, dataType = "String")
	public Response delete(@PathVariable String fdId){
		return SuccessResponse.newInstance(userInfoService.delete(fdId));
	}

	/**
	 * 批量删除用户
	 *
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@ApiOperation(value = "批量删除用户", notes = "批量删除用户",httpMethod="DELETE")
	@ApiImplicitParam(name = "ids", value = "用户集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String")
	public Response deleteBatch(String ids){
		return SuccessResponse.newInstance(userInfoService.deleteByIds(ids));
	}

	/**
	 * 查询全部用户
	 *
	 * @return
	 */
	@GetMapping("/findAll")
	@ApiOperation(value = "查詢用户列表", notes = "查詢用户列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(userInfoService.findAll());
	}

	/**
	 * 根据ID查询用户
	 *
	 * @param fdId
	 * @return
	 */
	@GetMapping(value="/findOne/{fdId}")
	@ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户",httpMethod="GET")
	@ApiImplicitParam(name = "fdId", value = "用户ID", paramType = "path", required = true, dataType = "String")
	public Response findOne(@PathVariable String fdId){
		return SuccessResponseData.newInstance(userInfoService.findOne(fdId));
	}

	/**
	 * 根据参数查询用户
	 * @param userInfoDto
	 * @return
	 */
	@PostMapping(value="/queryByParam")
	@ApiOperation(value = "根据参数查询用户",
			notes = "根据参数查询用户(mobile, parentId, hwagainEmployee, state, imei, userType, lock)",
			httpMethod="POST")
	public Response queryByParam(@RequestBody UserInfoDto userInfoDto) {
		return SuccessResponseData.newInstance(userInfoService.queryByParam(userInfoDto));
	}
	
	/**
	 * 用户登录
	 *  
	 * @param userAccount
	 * @param password
	 * @param imei
	 * @param request
	 * @param businessName
	 * @param productName
	 * @param mobileBrand
	 * @param phoneModel
	 * @param mainboardName
	 * @param deviceName
	 * @param systemVersions
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/userLogin")
	@ApiOperation(value = "用户登录", notes = "用户登录",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userAccount", value = "登录账号（手机号）", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "password", value = "密码/车牌号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "imei", value = "imei号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "businessName", value = "手机厂商名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "productName", value = "产品名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "mobileBrand", value = "手机品牌", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "phoneModel", value = "手机型号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "mainboardName", value = "主板名", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "deviceName", value = "设备名", paramType = "query", required = true, dataType = "String")
	})
	public Response login(String userAccount, String password,String imei,HttpServletRequest request,String businessName,
			String productName,String mobileBrand,String phoneModel,String mainboardName,String deviceName,String systemVersions)throws Exception{
		return SuccessResponseData.newInstance(userInfoService.login(userAccount, password, imei, request, businessName, productName, mobileBrand, phoneModel, mainboardName, deviceName,systemVersions));
	}
	
	/**
	 * 修改密码
	 * @param fdId
	 * @param params
	 * @return
	 */
//	@PostMapping(value="/changePwd/{fdId}")
//	@ApiOperation(value = "修改密码", notes = "修改密码 params：{'oldPwd':'旧密码','newPwd':'新密码'}",httpMethod="POST")
	@ApiImplicitParam(name = "fdId", value = "用戶ID", paramType = "path", required = true, dataType = "String")
	public Response changPwd(@PathVariable String fdId, @RequestBody Map<String, String> params){
		return SuccessResponseData.newInstance(userInfoService.changPwd(fdId, params));
	}
	
	/**
	 * 找回密码
	 * @param fdId
	 * @param password
	 * @return
	 */
//	@PostMapping(value="/retrievePassword/{fdId}")
//	@ApiOperation(value = "找回密码", notes = "找回密码",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "用戶ID", paramType = "path", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "password", value = "新密码", paramType = "body", required = true, dataType = "String")
	})
	public Response retrievePassword(@PathVariable Long fdId, @RequestBody String password) {
		return SuccessResponseData.newInstance(userInfoService.retrievePassword(fdId, password));
	}
	
	/**
	 * 修改用户
	 *
	 * @param userInfoDto
	 * @return
	 */
//	@PutMapping(value="/updatePlateNumber/{fdId}")
//	@ApiOperation(value = "司机修改车牌号码", notes = "司机修改车牌号码",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "用户ID", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "plateNumber", value = "车牌号码", paramType = "body", required = true, dataType = "String")
	})
	public Response updatePlateNumber(@PathVariable Long fdId, @RequestBody String plateNumber){
		return SuccessResponseData.newInstance(userInfoService.updatePlateNumber(fdId, plateNumber));
	}

	/**
	 * 修改用户状态
	 * @param fdId
	 * @param state
	 * @param altorId
	 * @return
	 */
	@PutMapping(value="updateState/{fdId}/{state}/{altorId}")
	@ApiOperation(value = "修改用户状态", notes = "修改用户状态",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "用户ID", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态。1：有效；2：无效", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "altorId", value = "修改人ID", paramType = "path", required = true, dataType = "String")
	})
	public Response updateState(@PathVariable Long fdId, @PathVariable Integer state, @PathVariable Long altorId){
		return SuccessResponseData.newInstance(userInfoService.updateState(fdId, state, altorId));
	}

	/**
	 * 修改用户手机IMEI
	 * @param fdId
	 * @param imei
	 * @param altorId
	 * @return
	 */
	@PutMapping(value="updateImei/{fdId}/{imei}/{altorId}")
	@ApiOperation(value = "修改用户手机IMEI", notes = "修改用户手机IMEI",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "用户ID", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "imei", value = "手机IMEI", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "altorId", value = "修改人ID", paramType = "path", required = true, dataType = "String")
	})
	public Response updateImei(@PathVariable Long fdId, @PathVariable String imei, @PathVariable Long altorId){
		return SuccessResponseData.newInstance(userInfoService.updateImei(fdId, imei, altorId));
	}
	
	/**
	 * 分配账号
	 * @param name
	 * @param mobile
	 * @return
	 */
	@GetMapping(value="/allotAccount")
	@ApiOperation(value="分配账号",notes="分配账号",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "司机姓名", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = false, dataType = "String")
	})
	public Response allotAccount(String name,String mobile){
		return SuccessResponseData.newInstance(userInfoService.allotAccount(name, mobile));
	}
	
	/**
	 * 所有司机
	 * @param loginName
	 * @param mobile
	 * @return
	 */
	@GetMapping(value="/findAllDriver")
	@ApiOperation(value="所有司机",notes="所有司机",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "loginName", value = "用户名", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllDriver(String loginName,String mobile){
		return SuccessResponseData.newInstance(userInfoService.findAllDriver(loginName,mobile));
	}
	
	/**
	 * 新增用户
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/addNowUser")
	@ApiOperation(value = "新增用户", notes = "新增用户",httpMethod="POST")
	public Response addNowUser(@RequestBody UserInfoDto dto){
		return SuccessResponseData.newInstance(userInfoService.addNowUser(dto));
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/deteleByIds")
	@ApiOperation(value="批量删除",notes="批量删除",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")
	})
	public Response deleteByIds(String ids){
		return SuccessResponseData.newInstance(userInfoService.deleteByIds(ids));
	}
	
	/**
	 * 所有非华劲员工
	 * @param userType
	 * @param loginname
	 * @return
	 */
	@GetMapping(value="/findAllNotHwagain")
	@ApiOperation(value="所有非华劲员工",notes="所有非华劲员工",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userType", value = "用户类型", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "loginname", value = "用户名称", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllNotHwagain(String userType,String loginname){
		return SuccessResponseData.newInstance(userInfoService.findAllNotHwagain(userType,loginname));
	}
	
	/**
	 * 所有华劲员工
	 * @param userType
	 * @return
	 */
	@GetMapping(value="/findAllHwagain")
	@ApiOperation(value="所有华劲员工",notes="所有华劲员工",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userType", value = "用户类型", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllHwagain(String userType){
		return SuccessResponseData.newInstance(userInfoService.findAllHwagain(userType));
	}
	
	/**
	 * 新增系统用户
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/addOneHwagain")
	@ApiOperation(value = "新增系统用户", notes = "新增系统用户",httpMethod="POST")
	public Response addOneHwagain(@RequestBody UserInfoDto dto){
		return SuccessResponseData.newInstance(userInfoService.addOneHwagain(dto));
	}
	
	/**
	 * 批量删除系统用户 
	 * @param ids
	 * @return
	 */
	@GetMapping("/deleteHwagainByIds")
	@ApiOperation(value="批量删除系统用户",notes="批量删除系统用户",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")
	})
	public Response deleteHwagainByIds(String ids){
		return SuccessResponseData.newInstance(userInfoService.deleteHwagainByIds(ids));
	}
	
	/**
	 * 根据条件分页返回供应商列表
	 * @param pageNum
	 * @param pageSize
	 * @param userType
	 * @param loginname
	 * @return
	 */
	@PostMapping(value="/findByPage/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回供应商列表", notes = "分页返回供应商列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "userType", value = "用户类型", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "loginname", value = "用户姓名", paramType = "query", required = false, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize,String userType,String loginname){
		return SuccessResponseData.newInstance(userInfoService.findByPage(pageNum, pageSize, userType, loginname));
	}
	
	/**
	 * 新增华劲用户
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/addHwa")
	@ApiOperation(value = "新增华劲用户", notes = "新增华劲用户",httpMethod="POST")
	public Response addHwa(@RequestBody UserInfoDto dto){
		return SuccessResponseData.newInstance(userInfoService.addHwa(dto));
	}
	
	/**
	 * 所有员工
	 * @param userType
	 * @return
	 */
	@GetMapping(value="/findAllHwa")
	@ApiOperation(value="所有员工",notes="所有员工",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userType", value = "用户类型", paramType = "query", required = false, dataType = "String")
	})
	public Response findAllHwa(String userType){
		return SuccessResponseData.newInstance(userInfoService.findAllHwa(userType));
	}
	
	/**
	 * 查询
	 * @param userType
	 * @param name
	 * @return
	 */
	@GetMapping(value="/findByName")
	@ApiOperation(value="findByName",notes="findByName",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userType", value = "用户类型", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "userTynamepe", value = "name", paramType = "query", required = false, dataType = "String")
	})
	public Response findByName(String userType,String name){
		return SuccessResponseData.newInstance(userInfoService.findByName(name, userType));
	}

}
