package com.hwagain.eagle.region.service;

import java.util.List;

import com.hwagain.eagle.region.entity.RegionHistory;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 片区表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
public interface IRegionHistoryService extends IService<RegionHistory> {
	
	public Integer insertBatchByRegionTable();
	
	public List<RegionHistory> findVsersions();
	
	public List<RegionHistory> findListByVersion(Integer version);
}
