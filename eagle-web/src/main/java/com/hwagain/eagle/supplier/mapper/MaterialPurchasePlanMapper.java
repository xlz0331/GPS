package com.hwagain.eagle.supplier.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.supplier.dto.MaterialPurchasePlanRptDto;
import com.hwagain.eagle.supplier.entity.MaterialPurchasePlan;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xionglz
 * @since 2019-03-22
 */
public interface MaterialPurchasePlanMapper extends BaseMapper<MaterialPurchasePlan> {
	public List<MaterialPurchasePlanRptDto> getNowPlan();
	public List<MaterialPurchasePlan> getNowPlan2(@Param("date") String date);

}