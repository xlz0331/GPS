package com.hwagain.eagle.supplier.service;

import java.util.List;

import com.hwagain.eagle.supplier.dto.SupplyPlanDto;
import com.hwagain.eagle.supplier.entity.SupplyPlan;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 供货计划信息表 服务类
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
public interface ISupplyPlanService extends IService<SupplyPlan> {
	
	String save(SupplyPlanDto supplyPlanDto);

    String update(SupplyPlanDto supplyPlanDto);

    String delete(String fdId);

    String deleteByIds(String ids);

    List<SupplyPlanDto> findAll();

    SupplyPlan findOne(String fdId);
}
