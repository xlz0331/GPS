package com.hwagain.eagle.comparison.web;

import org.springframework.web.bind.annotation.GetMapping;
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

import com.hwagain.eagle.comparison.service.IComparisonService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-05-26
 */
@RestController
@RequestMapping(value="/comparison/comparison",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="新旧系统比较API")
public class ComparisonController extends BaseController{
	
	@Autowired
	IComparisonService comparisonService;
	
	/**
	 * 查询
	 * @return
	 */
	@GetMapping(value="/findAll")
	@ApiOperation(value = "findAll", notes = "findAll",httpMethod="GET")
	public Response findAll(){		
		return SuccessResponseData.newInstance(comparisonService.findAll());
	}
	
	/**
	 * 更新
	 * @param fdId		主表id
	 * @param reason	原因
	 * @return
	 */
	@GetMapping(value="/updateByFdId")
	@ApiOperation(value = "updateByFdId", notes = "updateByFdId",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "reason", value = "reason", paramType = "query", required = true, dataType = "String"),		
	})
	public Response finupdateByFdIddAll(Long fdId, String reason){		
		return SuccessResponseData.newInstance(comparisonService.updateByFdId(fdId, reason));
	}
	
	/**
	 * 查询
	 * @return
	 */
	@GetMapping(value="/findAllResult")
	@ApiOperation(value = "findAllResult", notes = "findAllResult",httpMethod="GET")
	public Response findAllResult(){		
		return SuccessResponseData.newInstance(comparisonService.findAllResult());
	}
	
	/**
	 * 更新
	 * @return
	 */
	@GetMapping(value="/updateAllOld")
	@ApiOperation(value = "updateAllOld", notes = "updateAllOld",httpMethod="GET")
	public Response updateAllOld(){		
		return SuccessResponseData.newInstance(comparisonService.updateAllOld());
	}
}
