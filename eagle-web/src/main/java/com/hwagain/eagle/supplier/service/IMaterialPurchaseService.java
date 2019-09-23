package com.hwagain.eagle.supplier.service;

import java.util.List;

import com.hwagain.eagle.supplier.dto.MaterialPurchaseDto;
import com.hwagain.eagle.supplier.entity.MaterialPurchase;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-04-09
 */
public interface IMaterialPurchaseService extends IService<MaterialPurchase> {

	List<MaterialPurchaseDto> getNowPlan();

	List<MaterialPurchaseDto> getTotalPlan();
	
}
