package com.hwagain.eagle.region.service.impl;

import com.hwagain.eagle.region.entity.TaskRoute;
import com.hwagain.eagle.region.dto.TaskRouteDto;
import com.hwagain.eagle.region.mapper.TaskRouteMapper;
import com.hwagain.eagle.region.service.ITaskRouteService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-08-28
 */
@Service("taskRouteService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TaskRouteServiceImpl extends ServiceImpl<TaskRouteMapper, TaskRoute> implements ITaskRouteService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(TaskRoute.class, TaskRouteDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(TaskRouteDto.class, TaskRoute.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
