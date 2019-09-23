package com.hwagain.eagle.region.mapper;

import java.util.List;

import com.hwagain.eagle.region.entity.RegionSupplier;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 片区表 Mapper 接口
 * </p>
 *
 * @author xionglz
 * @since 2019-06-17
 */
public interface RegionSupplierMapper extends BaseMapper<RegionSupplier> {
	public List<RegionSupplier> queryAllSupplier();
}