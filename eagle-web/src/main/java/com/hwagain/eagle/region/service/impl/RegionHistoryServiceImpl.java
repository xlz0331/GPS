package com.hwagain.eagle.region.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.region.entity.RegionHistory;
import com.hwagain.eagle.region.mapper.RegionHistoryMapper;
import com.hwagain.eagle.region.service.IRegionHistoryService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * 片区表 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@Service("regionHistoryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegionHistoryServiceImpl extends ServiceImpl<RegionHistoryMapper, RegionHistory> implements IRegionHistoryService {

	@Autowired RegionHistoryMapper regionHistoryMapper;
	
	@Override
	public Integer insertBatchByRegionTable() {
		return regionHistoryMapper.insertBatchByRegionTable();
	}

	@Override
	public List<RegionHistory> findVsersions() {
		return regionHistoryMapper.findVersions();
	}

	//查询历史版本
	@Override
	public List<RegionHistory> findListByVersion(Integer version) {
		Wrapper<RegionHistory> wrapper = new CriterionWrapper<RegionHistory>(RegionHistory.class);
		wrapper.eq("version", version);
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("effdt");
		List<RegionHistory> list = super.selectList(wrapper);
		return list;
	}
	
}
