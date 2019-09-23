package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.LogOperator;
import com.hwagain.eagle.base.dto.LogOperatorDto;
import com.hwagain.eagle.base.mapper.LogOperatorMapper;
import com.hwagain.eagle.base.service.ILogOperatorService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 操作日志表。
注意：以后开发平台的日志服务上线后，需要做功能迁移 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@Service("logOperatorService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogOperatorServiceImpl extends ServiceImpl<LogOperatorMapper, LogOperator> implements ILogOperatorService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(LogOperator.class, LogOperatorDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(LogOperatorDto.class, LogOperator.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
