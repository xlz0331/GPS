package com.hwagain.eagle.supplier.mapper;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.supplier.entity.SupplyPlan;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 供货计划信息表 Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
public interface SupplyPlanMapper extends BaseMapper<SupplyPlan> {

	void deleteByIds(@Param("list")String[] ids);
}