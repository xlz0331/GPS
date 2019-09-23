package com.hwagain.eagle.region.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.eagle.correct.dto.CorrectDto;
import com.hwagain.eagle.region.entity.RegionOa;
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.region.service.IRegionOaService;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *补助标准OA审批
 * </p>
 *
 * @author hanjin
 * @since 2019-05-11
 */
@RestController
@RequestMapping(value="/regionOa",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="竹木原料运输补助标准调整")
public class RegionOaController extends BaseController{
	@Autowired
	IRegionDetailService regionDetailService;
	@Autowired
	IRegionOaService regionOaService;
	
	/**
	 * 【新增】补助标准
	 * @param entity
	 * @return
	 */
	@PostMapping("/insertOne")
	@ApiOperation(notes="【新增】补助标准",value="【新增】补助标准",httpMethod="POST")
	public Response insertOne(@RequestBody RegionOa entity) {
		return SuccessResponseData.newInstance(regionOaService.insertOne(entity));
	}
	
	/**
	 * 【修改】补助标准
	 * @param entity
	 * @return
	 */
	@PostMapping("/updateById")
	@ApiOperation(notes="【修改】补助标准",value="【修改】补助标准",httpMethod="POST")
	public Response updateById(@RequestBody RegionOa entity) {
		return SuccessResponseData.newInstance(regionOaService.updateById(entity));
	}
	
	/**
	 * 【删除】补助标准
	 * @param fdId
	 * @return
	 */
	@GetMapping("/delete/{fdId}")
	@ApiOperation(notes="【删除】补助标准",value="【删除】补助标准",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="fdId",value="数据ID",dataType="Long",paramType="path",required=true),
	})
	public Response updateById(@PathVariable Long fdId) {
		return SuccessResponseData.newInstance(regionOaService.deleteById(fdId));
	}
	
	/**
	 * 【查询全部】补助标准
	 * @return
	 */
	@GetMapping("/findList")
	@ApiOperation(notes="【查询全部】补助标准",value="【查询全部】补助标准",httpMethod="GET")
	public Response findList() {
		return SuccessResponseData.newInstance(regionOaService.findList());
	}
	
	/**
	 * 【散户】补助标准
	 * @return
	 */
	@GetMapping("/findListRetail")
	@ApiOperation(notes="【散户】补助标准",value="【散户】补助标准",httpMethod="GET")
	public Response findListRetail() {
		return SuccessResponseData.newInstance(regionOaService.findListRetail());
	}
	
	/**
	 * 【查询全部】补助标准
	 * @return
	 */
	@GetMapping("/findList3")
	@ApiOperation(notes="【查询全部】补助标准",value="【查询全部】补助标准",httpMethod="GET")
	public Response findList3() {
		return SuccessResponseData.newInstance(regionOaService.findList3());
	}
	
	/**
	 * 【散户】补助标准
	 * @return
	 */
	@GetMapping("/findListRetail3")
	@ApiOperation(notes="【散户】补助标准",value="【散户】补助标准",httpMethod="GET")
	public Response findListRetail3() {
		return SuccessResponseData.newInstance(regionOaService.findListRetail3());
	}
	
	/**
	 * 提交OA
	 * @return
	 */
	@PostMapping("/sentToOA")
	@ApiOperation(value="提交OA",notes="提交OA",httpMethod="POST")
	public Response sentToOA(){
		return SuccessResponseData.newInstance(regionOaService.sendToOA());		
	}
	
	/**
	 * 提交OA(散户)
	 * @return
	 */
	@PostMapping("/sendToOARetail")
	@ApiOperation(value="提交OA(散户)",notes="提交OA（散户）",httpMethod="POST")
	public Response sendToOARetail(){
		return SuccessResponseData.newInstance(regionOaService.sendToOARetail());		
	}
	
	/**
	 * 查询版本
	 * @param node
	 * @return
	 */
	@GetMapping("/findVersion")
	@ApiOperation(notes="查询版本",value="查询版本",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="node",value="节点名称",dataType="String",paramType="query",required=true),
	})
	public Response findVersion(String node) {
		return SuccessResponseData.newInstance(regionOaService.findVersion(node));
	}
}
