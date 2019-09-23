package com.hwagain.eagle.region.service;

import java.util.List;
import java.util.Map;

import com.hwagain.eagle.region.dto.RegionDto;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 片区表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
public interface IRegionService extends IService<Region> {
	
	public List<RegionDto> findList() throws CustomException;
	
	public Region findOneByParam(Region entity) throws CustomException;

	List<Region> findRegionBySupplier(String supplierName, String material) throws CustomException;

	Region findOneByParam2(String supplierName,String city,String material,String poundTime) throws CustomException;

	Map findByParam();

	List<RegionDto> findListRetail() throws CustomException;
}
