package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.dto.LogUserLockDto;
import com.hwagain.eagle.base.entity.LogUserLock;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 用户上锁/解锁操作日志表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
public interface ILogUserLockService extends IService<LogUserLock> {

	LogUserLockDto addOneLog(LogUserLockDto dto);

	List<LogUserLockDto> findAll();
	
}
