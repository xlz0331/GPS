package com.hwagain.eagle.base.service;

import com.hwagain.eagle.base.entity.LogPhoneInfo;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-06-21
 */
public interface ILogPhoneInfoService extends IService<LogPhoneInfo> {

	LogPhoneInfo getPhoneInfo(Long userId);
	
}
