package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.dto.LogLoginUserDto;
import com.hwagain.eagle.base.entity.LogLoginUser;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-21
 */
public interface ILogLoginUserService extends IService<LogLoginUser> {

	public List<LogLoginUser> findAll();

	public List<LogLoginUser> findByType(Integer state);

	public String deleteByFdId(Long fdId);

	public List<LogLoginUserDto> findAllTransport();
	
}
