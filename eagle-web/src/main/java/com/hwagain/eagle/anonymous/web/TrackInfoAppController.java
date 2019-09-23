package com.hwagain.eagle.anonymous.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.base.dto.DictDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.JwtUtil;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import oracle.net.aso.j;

/**
 * <p>
 * 轨迹 前端控制器
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
@RestController
@RequestMapping(value="/app/track")
@Api(tags="APP-轨迹管理API")
public class TrackInfoAppController extends BaseController{
	
	@Autowired
	ITrackInfoService trackInfoService;
	@Autowired JwtUtil jwtUtil;
	@Autowired IDictService dictService;
	
	/**
	 * 新增轨迹
	 *
	 * @param trackInfoDto
	 * @return SuccessResponseData
	 */
	@IgnoreUserToken
	@PostMapping(value="/save")
	@ApiOperation(value = "新增轨迹", notes = "新增轨迹",httpMethod="POST")
	public Response save(@RequestBody List<TrackInfo> trackInfos){
		return SuccessResponseData.newInstance(trackInfoService.save(trackInfos));
	}
	
	/**
	 * 根据任务ID查询轨迹列表
	 * 
	 * @param taskId 任务ID
	 * @return
	 */
//	@IgnoreUserToken
	@GetMapping("/getListByTaskId/{taskId}")
	@ApiOperation(value = "根据任务ID查询轨迹列表", notes = "根据任务ID查询轨迹列表",httpMethod="GET")
	@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "path", required = true, dataType = "String")
	public Response getListByTaskId(@PathVariable Long taskId){
		return SuccessResponseData.newInstance(trackInfoService.getListByTaskId(taskId));
	}
	
	/**
	 * 轨迹审核
	 * 
	 * @param taskId 				任务ID
	 * @param pathAcceptanceResult	轨迹审核结果
	 * @param reason				不合格原因
	 * @return
	 */
	@GetMapping("/trackAudit1")
	@ApiOperation(value="轨迹审核",notes="轨迹审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pathAcceptanceResult", value = "轨迹审核结果", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "不合格原因", paramType = "query", required = false, dataType = "String")
	})
	public Response trackAudit1(Long taskId,Integer pathAcceptanceResult,String reason){
		return SuccessResponseData.newInstance(trackInfoService.trackAudit1(taskId, pathAcceptanceResult, reason));
	}
	
	/**
	 * 根据任务ID查询当前轨迹点
	 * 
	 * @param taskId 任务id
	 * @return
	 */
	@GetMapping("/getTrackPointByTaskId/{taskId}")
	@ApiOperation(value = "根据任务ID查询当前轨迹点", notes = "根据任务ID查询当前轨迹点",httpMethod="GET")
	@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "path", required = true, dataType = "String")
	public Response getTrackPointByTaskId(@PathVariable Long taskId){
		return SuccessResponseData.newInstance(trackInfoService.getTrackPointByTaskId(taskId));
	}
	
	/**
	 * 获取定点间隔时间
	 * @return
	 */
	@GetMapping(value="/getIntervalTime")
	@ApiOperation(value = " 获取间隔时间", notes = "  获取间隔时间",httpMethod="GET")
	public Map<String, String> getIntervalTime(){
		List<Dict> list=dictService.findBytypeName("定点间隔时间");
		if (list.size()==0){
			Assert.throwException("定点间隔时间未配置");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("intervalTime", String.valueOf(list.get(0).getItemNo()));
		return map;
	}
}
