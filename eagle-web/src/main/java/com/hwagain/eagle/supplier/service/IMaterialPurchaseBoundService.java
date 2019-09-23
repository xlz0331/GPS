package com.hwagain.eagle.supplier.service;

import com.hwagain.eagle.supplier.dto.MaterialPurchaseBoundDto;
import com.hwagain.eagle.supplier.entity.MaterialPurchaseBound;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-04-09
 */
public interface IMaterialPurchaseBoundService extends IService<MaterialPurchaseBound> {

	MaterialPurchaseBoundDto addOne(MaterialPurchaseBoundDto dto);

	MaterialPurchaseBoundDto findBound();
	
}
