package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.LogUserLock;
import com.hwagain.eagle.base.dto.LogUserLockDto;
import com.hwagain.eagle.base.mapper.LogUserLockMapper;
import com.hwagain.eagle.base.service.ILogUserLockService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import cn.hutool.core.lang.Assert;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 用户上锁/解锁操作日志表 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@Service("logUserLockService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogUserLockServiceImpl extends ServiceImpl<LogUserLockMapper, LogUserLock> implements ILogUserLockService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(LogUserLock.class, LogUserLockDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(LogUserLockDto.class, LogUserLock.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	private void validate(LogUserLockDto dto) {
		Assert.notNull(dto.getUserId(),"用户ID不能为空");
		Assert.notBlank(dto.getCurrentImei(),"原IMEI不能为空");
		Assert.notBlank(dto.getImei(),"新IMEI不能为空");
		Assert.notNull(dto.getState(),"状态不能为空");
	}
	
	//用户上锁、解锁操作日志
	@Override
	public LogUserLockDto addOneLog(LogUserLockDto dto){
		validate(dto);
		Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
		dto.setFdId(Long.valueOf(IdWorker.getId()));
		dto.setOperatorId(creator);
		super.insert(entityToDtoMapper.map(dto, LogUserLock.class));
		return dto;		
	}
	
	//日志查询
	@Override
	public List<LogUserLockDto> findAll(){
		Wrapper<LogUserLock> wrapper=new CriterionWrapper<LogUserLock>(LogUserLock.class);
		List<LogUserLock> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, LogUserLockDto.class);	
	}
}
