package com.hwagain.eagle.region.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.response.ErrorResponseData;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.hwagain.eagle.region.dto.RegionDetailDto;
import com.hwagain.eagle.region.service.IRegionDetailService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-04-13
 */
@RestController
@RequestMapping(value="/region/regionDetail",method={RequestMethod.GET,RequestMethod.POST})
@Api(tags="供货区域明细管理API")
public class RegionDetailController extends BaseController{
	
	@Autowired
	IRegionDetailService regionDetailService;
	
	/**
	 * 查询所有列表
	 * @param supplierName	供应商名称
	 * @param regionName	片区名称
	 * @return SuccessResponseData
	 */
	@GetMapping("/findAll")
	@ApiOperation(value="查询所有列表",notes="查询所有列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "regionName", value = "区域名称", paramType = "query", required = false, dataType = "String")

	})
	public Response findAll(String supplierName,String regionName){
		return SuccessResponseData.newInstance(regionDetailService.findAll(supplierName, regionName));
	}
	
	/**
	 * 获取单条数据
	 * @param fdId
	 * @return SuccessResponseData
	 */
	@GetMapping("/findById")
	@ApiOperation(value="findById",notes="findById",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = false, dataType = "String")

	})
	public Response findById(Long fdId){
		return SuccessResponseData.newInstance(regionDetailService.findById(fdId));
	}
	
	/**
	 * 新增数据
	 * @param dto
	 * @return SuccessResponseData
	 */
	@PostMapping("/addOne")
	@ApiOperation(value="新增",notes="新增",httpMethod="POST")
	public Response addOne(@RequestBody RegionDetailDto dto){
		return SuccessResponseData.newInstance(regionDetailService.addOne(dto));
	}
	
	/**
	 * 批量删除
	 * @param ids 格式1;2;3...
	 * @return
	 */
	@GetMapping("/deteleByIds")
	@ApiOperation(value="批量删除",notes="批量删除",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ids(1;2;3...)", paramType = "query", required = true, dataType = "String")

	})
	public Response deleteByIds(String ids){
		return SuccessResponseData.newInstance(regionDetailService.deleteByIds(ids));
	}
	
	/**
	 * 导入数据[上传附件]
	 * 
	 * @return
	 */
	@RequestMapping(value = "importDataForFileTest", method = RequestMethod.POST)
	@ApiOperation(value = "导入数据[上传附件]", notes = "导入数据[上传附件]")
	public Response importDataForFileTest(HttpServletRequest request, MultipartFile multipartFile)throws Exception {
		String filepath = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			MultipartFile mf = multiRequest.getFile("test");
			String strPath = ",webapps,files,";
			filepath = System.getProperty("catalina.base");
			Iterator iter = multiRequest.getFileNames(); // 表单里可以提交了多个附件控件
			System.err.println(filepath);
			if (iter.hasNext()) {
				String formFileName = iter.next().toString();// 附件控件的名字
				List<MultipartFile> files = multiRequest.getFiles(formFileName);// 获取相应名字的所有附件
				if (files != null && files.size() > 0) {
					// 遍历附件并上传
					for (MultipartFile file : files) {
//							List<QuesSingleChoiceDto> list = quseSingleChoiceServiceImpl.readXlsQuesSingleChoice(file.getInputStream(), "txt");
						String list = regionDetailService.InsertRegionDeatilData(file.getInputStream());
						return SuccessResponseData.newInstance(list);
					}
				}
			}
			return SuccessResponse.newInstance(filepath);
		} else {
			ErrorResponseData.newInstance("附件上传失败");
		}
		return SuccessResponse.newInstance("导入成功");
	}
	
	/**
	 * 分页返回片区列表
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的条数
	 * @param supplierName 供应商名称
	 * @param regionName 片区名称
	 * @return
	 */
	@PostMapping(value="/findByPage/{pageNum}/{pageSize}")
	@ApiOperation(value = "分页返回片区列表", notes = "分页返回片区列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "当前页码", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示的条数", paramType = "path", required = true, dataType = "String"),
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "regionName", value = "片区名称", paramType = "query", required = false, dataType = "String")
	})
	public Response findByPage(@PathVariable int pageNum, @PathVariable int pageSize,String supplierName,String regionName){
		return SuccessResponseData.newInstance(regionDetailService.findByPage(pageNum, pageSize, supplierName, regionName));
	}
	
	/**
	 * 查询单条数据
	 * @param fdId 主表id
	 * @return
	 */
	@GetMapping(value="/findOne")
	@ApiOperation(value="findOne",notes="findOne",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="fdId",value="fdId",paramType="query",required=true,dataType="String")
	})
	public Response findOne(Long fdId){
		return SuccessResponseData.newInstance(regionDetailService.findOne(fdId));
	}
	
	/**
	 * 获取拥有对应片区的供应商列表
	 * @return
	 */
	@GetMapping(value="/querySupplier")
	@ApiOperation(value="querySupplier[菜单]",notes="querySupplier[菜单]",httpMethod="GET")
	public Response querySupplier(){
		return SuccessResponseData.newInstance(regionDetailService.querySupplier());
	}
	
	/**
	 * 获取百度批量算路距离
	 * @param path url
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value="/baiduDistance")
	@ApiOperation(value="baiduDistance",notes="baiduDistance",httpMethod="GET")
	@ApiImplicitParam(name="path",value="path",paramType="query",required=true,dataType="String")
	public Response baiDuApi(String path)throws IOException{
		return SuccessResponseData.newInstance(regionDetailService.baiduDistance(path));
	}
	
	/**
	 * 根据供应商查询供货点明细
	 * @param supplierName 供应商名称
	 * @param regionName   片区名称
	 * @return
	 */
	@PostMapping(value="/findBySupplier")
	@ApiOperation(value = "根据供应商查询供货点明细", notes = "根据供应商查询供货点明细",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "regionName", value = "片区名称", paramType = "query", required = false, dataType = "String")
	})
	public Response findBySupplier(String supplierName,String regionName){
		return SuccessResponseData.newInstance(regionDetailService.findBySupplier(supplierName, regionName));
	}
	
	/**
	 * 获取路线信息
	 * @param taskId	任务id
	 * @param tactics	路线类型
	 * @return
	 */
	@GetMapping(value="/getRoute")
	@ApiOperation(value = "获取路线信息", notes = "获取路线信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "tactics", value = "路线类型：0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费", paramType = "query", required = false, dataType = "String")
	})
	public Response getRoute(Long taskId,String tactics){
		return SuccessResponseData.newInstance(regionDetailService.getRoute(taskId,tactics));
	}
	
	/**
	 * 初始化路线信息
	 * @param tactics	路线类型
	 * @return
	 */
	@GetMapping(value="/getLine")
	@ApiOperation(value = "初始化路线信息", notes = "初始化路线信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tactics", value = "路线类型：0：默认；3：不走高速；4：高速优先；5：躲避拥堵；6：少收费", paramType = "query", required = false, dataType = "String")
	})
	public Response getLine(String tactics){
		return SuccessResponseData.newInstance(regionDetailService.getLine(tactics));
	}
	
	/**
	 * 获取对应任务所有路线信息
	 * @param taskId 任务id
	 * @return
	 */
	@GetMapping(value="/getRoutes")
	@ApiOperation(value = "获取所有路线信息", notes = "获取所有路线信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", required = false, dataType = "String")
	})
	public Response getRoutes(Long taskId){
		return SuccessResponseData.newInstance(regionDetailService.getRoutes(taskId));
	}
}
