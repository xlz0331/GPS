package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.dto.CarInfoDto;
import com.hwagain.eagle.base.entity.CarInfo;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
public interface ICarInfoService extends IService<CarInfo> {

	List<CarInfoDto> getCarInfoBySupplierId();

	List<CarInfoDto> findAll(String supplierName, String carType);

	CarInfoDto addOne(CarInfoDto dto);

	String deleteByIds(String ids);
	
}
