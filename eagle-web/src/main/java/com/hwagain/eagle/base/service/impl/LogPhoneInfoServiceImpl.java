package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.LogPhoneInfo;
import com.hwagain.eagle.base.dto.LogPhoneInfoDto;
import com.hwagain.eagle.base.mapper.LogPhoneInfoMapper;
import com.hwagain.eagle.base.service.ILogPhoneInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
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
 * @since 2019-06-21
 */
@Service("logPhoneInfoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogPhoneInfoServiceImpl extends ServiceImpl<LogPhoneInfoMapper, LogPhoneInfo> implements ILogPhoneInfoService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(LogPhoneInfo.class, LogPhoneInfoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(LogPhoneInfoDto.class, LogPhoneInfo.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//查询用户手机型号信息
	@Override
	public LogPhoneInfo getPhoneInfo(Long userId){
		Wrapper<LogPhoneInfo> wrapper=new CriterionWrapper<LogPhoneInfo>(LogPhoneInfo.class);
		wrapper.eq("user_id", userId);
		wrapper.orderBy("login_time",false);
		LogPhoneInfo logPhoneInfo=super.selectFirst(wrapper);
		return logPhoneInfo;		
	}
}
