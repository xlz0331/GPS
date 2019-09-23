package com.hwagain.eagle.region.service;

import java.util.List;
import java.util.Map;

import com.hwagain.eagle.region.dto.RegionPathwayDto;
import com.hwagain.eagle.region.entity.RegionPathway;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-09
 */
public interface IRegionPathwayService extends IService<RegionPathway> {

	List<RegionPathway> queryPrivince();

	List<RegionPathway> queryCity(String privince);

	List<RegionPathway> queryCounty(String city);

	List<RegionPathway> queryHaulway(String privince, String city, String county);

	String addOne(RegionPathway entity);

	Map findAllRegion();
	
}
