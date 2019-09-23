package com.hwagain.eagle.region.mapper;

import java.util.List;

import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xionglz
 * @since 2019-04-13
 */
public interface RegionDetailMapper extends BaseMapper<RegionDetail> {
	public List<RegionDetail> querySupplier();

}