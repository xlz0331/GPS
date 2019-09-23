package com.hwagain.eagle.region.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.eagle.region.dto.RegionDetailDto;
import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.util.PageDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-04-13
 */
public interface IRegionDetailService extends IService<RegionDetail> {

	String deleteByIds(String ids);

	List<RegionDetailDto> getRegionDetailBySupplierId();

	List<RegionDetailDto> findAll(String supplierName, String regionName);

	RegionDetailDto addOne(RegionDetailDto dto);

	String InsertRegionDeatilData(InputStream inputStream);

	List<RegionDetailDto> queryPurchaseGps(String address);

	PageDto<RegionDetailDto> findByPage(int pageNum, int pageSize, String supplierName, String regionName)
			throws CustomException;

	RegionDetailDto findOne(Long fdId);

	String addOneDetailRegion(RegionDetail entity);

	RegionDetailDto findRegionDetailByOaCode(String oaCode);

	String audit(Integer state, String oaCode, String node, String empName, String empNo, String flowDjbh,
			String flowDjlsh);

	String sendToOA();

	List<RegionDetail> querySupplier();

	BigDecimal baiduDistance(String path)throws IOException;

	List<RegionDetailDto> findBySupplier(String supplierName, String regionName) throws CustomException;

	RegionDetail findById(Long fdId);

	public List<Map<String, Double>> getRoute(Long taskId,String tactics);

	public List<Map<String, Double>> getLine(String tactics);

	public List<List<Map<String, String>>> getRoutes(Long taskId);

	public List<Map<String, Double>> getTaskRoute(Long taskId);
	
}
