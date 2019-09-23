package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.dto.LogLoginDto;
import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 用户登录日志表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
public interface ILogLoginService extends IService<LogLogin> {

	LogLoginDto addOne(LogLoginDto dto);

	List<LogLogin> findByType(String type, Long userId, String sessionId);

	String addLog(String log);
	
}
