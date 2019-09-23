package com.hwagain.eagle.region.service;

import java.util.List;

import com.hwagain.eagle.region.dto.RegionSupplierDto;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.entity.RegionSupplier;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 片区表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-06-17
 */
public interface IRegionSupplierService extends IService<RegionSupplier> {

	List<RegionSupplierDto> findList() throws CustomException;

	List<RegionSupplier> findRegionBySupplier(String supplierName, String material) throws CustomException;

	List<RegionSupplier> queryAllRegionSupplier();
	
	//根据城市名称返回区域供应商信息  ---add by 黎昌盛
	List<RegionSupplier> findByCityName(String city);

	public List<RegionSupplier> findByCountyName(String county,String city);
	
}
