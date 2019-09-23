package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.ChinaRegion;
import com.hwagain.eagle.base.dto.ChinaRegionDto;
import com.hwagain.eagle.base.mapper.ChinaRegionMapper;
import com.hwagain.eagle.base.service.IChinaRegionService;
import com.hwagain.eagle.util.syncSupplierData;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

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
 * @since 2019-05-20
 */
@Service("chinaRegionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChinaRegionServiceImpl extends ServiceImpl<ChinaRegionMapper, ChinaRegion> implements IChinaRegionService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(ChinaRegion.class, ChinaRegionDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(ChinaRegionDto.class, ChinaRegion.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//查询
	@Override
	public List<ChinaRegion> findAll(Long parentId){
		Wrapper<ChinaRegion> wrapper=new CriterionWrapper<ChinaRegion>(ChinaRegion.class);
		wrapper.eq("parent_id", parentId);
		List<ChinaRegion> list=super.selectList(wrapper);
		return list;
		
	}
}
