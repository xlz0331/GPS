package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.OaAduitDetail;
import com.hwagain.eagle.base.dto.OaAduitDetailDto;
import com.hwagain.eagle.base.mapper.OaAduitDetailMapper;
import com.hwagain.eagle.base.service.IOaAduitDetailService;
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
 * @since 2019-03-26
 */
@Service("oaAduitDetailService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OaAduitDetailServiceImpl extends ServiceImpl<OaAduitDetailMapper, OaAduitDetail> implements IOaAduitDetailService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OaAduitDetail.class, OaAduitDetailDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OaAduitDetailDto.class, OaAduitDetail.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
