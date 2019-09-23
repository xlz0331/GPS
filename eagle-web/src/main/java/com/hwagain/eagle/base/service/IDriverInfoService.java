package com.hwagain.eagle.base.service;

import java.io.InputStream;
import java.util.List;

import com.hwagain.eagle.base.dto.DriverInfoDto;
import com.hwagain.eagle.base.entity.DriverInfo;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.util.PageDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
public interface IDriverInfoService extends IService<DriverInfo> {

	List<DriverInfoDto> getDriverBySupplierId();

	List<DriverInfoDto> findAll(String mobile, String driverName);

	DriverInfoDto addOne(DriverInfoDto dto);

	String deleteByIds(String ids);

	String updateUserState();

	PageDto<DriverInfoDto> findByPage(DriverInfoDto driverInfoDto, int pageNum, int pageSize) throws CustomException;

	PageDto<DriverInfoDto> findByPagea(int pageNum, int pageSize, String mobile, String driverName)
			throws CustomException;

	String addDriver(DriverInfo dto);

	List<DriverInfoDto> findAllBySupplier(String mobile, String driverName);
	
}
