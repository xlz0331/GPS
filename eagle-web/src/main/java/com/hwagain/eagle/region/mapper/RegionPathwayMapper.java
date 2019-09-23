package com.hwagain.eagle.region.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.region.entity.RegionPathway;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xionglz
 * @since 2019-05-09
 */
public interface RegionPathwayMapper extends BaseMapper<RegionPathway> {
	public List<RegionPathway> queryPrivince();
	public List<RegionPathway> queryCity(@Param("privince") String privince);
	public List<RegionPathway> queryCounty(@Param("city") String city);
	public List<RegionPathway> queryHaulway(@Param("privince") String privince,@Param("city") String city,@Param("county") String county);

}