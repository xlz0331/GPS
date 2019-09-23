package com.hwagain.eagle.anonymous.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.user.dto.UserInfoDto;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.UploadUtil;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value="/app/userInfoService",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="APP-用户信息管理API")
public class UserInfoAppController extends BaseController{
	
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ILogLoginService logLoginService;

	/**
	 * 查詢用户列表
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping("/findAll")
	@ApiOperation(value = "", notes = "查詢用户列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(userInfoService.findAll());
	}

	/**
	 * 用户登录
	 * 
	 * @param userAccount 	账号
	 * @param password		密码
	 * @param imei			imei
	 * @param request		
	 * @param businessName	手机厂商名
	 * @param productName	产品名
	 * @param mobileBrand	手机品牌
	 * @param phoneModel	手机型号
	 * @param mainboardName	主板名
	 * @param deviceName	设备名
	 * @param systemVersions系统名称
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/userLogin")
	@ApiOperation(value = "用户登录", notes = "用户登录",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userAccount", value = "登录账号（手机号）", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "password", value = "密码/车牌号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "imei", value = "imei号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "businessName", value = "手机厂商名", paramType = "query", required =false , dataType = "String"),
		@ApiImplicitParam(name = "productName", value = "产品名", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "mobileBrand", value = "手机品牌", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "phoneModel", value = "手机型号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "mainboardName", value = "主板名", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "deviceName", value = "	", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "systemVersions", value = "系统名称", paramType = "query", required = false, dataType = "String")
	})
	public Response login(String userAccount, String password,String imei,HttpServletRequest request,String businessName,
			String productName,String mobileBrand,String phoneModel,String mainboardName,String deviceName,String systemVersions)throws Exception{
		return SuccessResponseData.newInstance(userInfoService.login(userAccount, password, imei, request, businessName, productName, mobileBrand, phoneModel, mainboardName, deviceName,systemVersions));
	}
	
	/**
	 * 修改密码
	 * 
	 * @param mobile 登录账号（手机号）
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return
	 */
	@PostMapping(value="/changPassword")
	@ApiOperation(value = "修改密码", notes = "修改密码",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "登录账号（手机号）", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "oldPwd", value = "原密码", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "newPwd", value = "新密码", paramType = "query", required = true, dataType = "String")
	})
	public Response changPassword(String mobile, String oldPwd,String newPwd){
		return SuccessResponseData.newInstance(userInfoService.changPassword(mobile, oldPwd, newPwd));
	}
	
	/**
	 * 重置密码
	 * 
	 * @param mobile
	 * @return
	 */
	@PostMapping(value="/resetPwd")
	@ApiOperation(value = "重置密码", notes = "重置密码",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "登录账号（手机号）", paramType = "query", required = true, dataType = "String")
	})
	public Response changPassword(String mobile){
		return SuccessResponseData.newInstance(userInfoService.resetPwd(mobile));
	}
	
	/**
	 * 在线检测
	 * @return
	 */
	@IgnoreUserToken
	@GetMapping(value="/onLine")
	@ApiOperation(value="在线检测",notes="在线检测",httpMethod="GET")
	public Response onLine(){
		return SuccessResponseData.newInstance(userInfoService.onLine());
	}
	
	/**
	 * 获取Token
	 * @param mobile 手机号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/getToken")
	@ApiOperation(value="获取Token",notes="获取Token",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "供应商：18376756800、司机：13333333333", paramType = "query", required = true, dataType = "String")
	})
	public Response getToken(String mobile,HttpServletRequest request) throws Exception{
		return SuccessResponseData.newInstance(userInfoService.getToken(mobile, request));
	}
	
	/**
	 * 新增异常日志
	 * @param log
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/addLog")
	@ApiOperation(value="addLog",notes="addLog",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "log", value = "log", paramType = "query", required = true, dataType = "String")
	})
	public Response addLog(String log) throws Exception{
		return SuccessResponseData.newInstance(logLoginService.addLog(log));
	}
}
