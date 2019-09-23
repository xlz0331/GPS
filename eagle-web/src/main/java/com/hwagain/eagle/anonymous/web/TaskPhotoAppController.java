package com.hwagain.eagle.anonymous.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
@RestController
@RequestMapping(value="/app/taskPhoto")
@Api(tags="APP-任务图片管理API")
public class TaskPhotoAppController extends BaseController{
	
	@Autowired
	ITaskPhotoService taskPhotoService;
	
	/**
	 * 新增任务图片
	 *
	 * @param taskPhotoVo
	 * @return SuccessResponseData
	 */
	@IgnoreUserToken
	@PostMapping(value="/save")
	@ApiOperation(value = "新增任务图片", notes = "新增任务图片",httpMethod="POST")
	public Response save(@RequestBody TaskPhotoVo taskPhotoVo){
		return SuccessResponseData.newInstance(taskPhotoService.save(taskPhotoVo));
	}
	
	/**
	 * 查询任务图片
	 * 
	 * @param taskId
	 * @param photoType
	 * @return
	 */
//	@IgnoreUserToken
	@GetMapping(value="/findByParam/{taskId}")
	@ApiOperation(value = "查询任务图片", notes = "查询任务图片",httpMethod="GET")
	public Response findByParam(@PathVariable Long taskId, @RequestParam(required=false) String photoType){
		return SuccessResponseData.newInstance(taskPhotoService.findByParam(taskId, photoType));
	}
	
	/**
	 * 图片审核
	 * @param taskPhotoDtos
	 * @return
	 */
	@PostMapping(value="/photoAudit1")
	@ApiOperation(value="图片审核",notes="图片审核",httpMethod="POST")
	public Response photoAudit1(@RequestBody List<TaskPhotoDto> taskPhotoDtos){
		return SuccessResponseData.newInstance(taskPhotoService.photoAudit1(taskPhotoDtos));
	}
}
