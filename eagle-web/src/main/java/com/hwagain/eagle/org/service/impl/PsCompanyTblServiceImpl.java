package com.hwagain.eagle.org.service.impl;

import com.hwagain.eagle.org.entity.PsCompanyTbl;
import com.hwagain.eagle.org.dto.PsCompanyTblDto;
import com.hwagain.eagle.org.mapper.PsCompanyTblMapper;
import com.hwagain.eagle.org.service.IPsCompanyTblService;
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
 * @since 2019-04-17
 */
@Service("psCompanyTblService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PsCompanyTblServiceImpl extends ServiceImpl<PsCompanyTblMapper, PsCompanyTbl> implements IPsCompanyTblService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(PsCompanyTbl.class, PsCompanyTblDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(PsCompanyTblDto.class, PsCompanyTbl.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
