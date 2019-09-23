package com.hwagain.eagle.region.mapper;

import java.util.List;

import com.hwagain.eagle.region.entity.RegionHistory;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanjin
 * @since 2019-05-11
 */
public interface RegionHistoryMapper extends BaseMapper<RegionHistory> {

	public Integer insertBatchByRegionTable();
	
	public List<RegionHistory> findVersions();
}