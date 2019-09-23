package com.hwagain.eagle.supplier.service.impl;

import com.hwagain.eagle.supplier.entity.MaterialPurchaseBound;
import com.hwagain.eagle.supplier.dto.MaterialPurchaseBoundDto;
import com.hwagain.eagle.supplier.mapper.MaterialPurchaseBoundMapper;
import com.hwagain.eagle.supplier.service.IMaterialPurchaseBoundService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import java.util.Date;
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
 * @since 2019-04-09
 */
@Service("materialPurchaseBoundService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MaterialPurchaseBoundServiceImpl extends ServiceImpl<MaterialPurchaseBoundMapper, MaterialPurchaseBound> implements IMaterialPurchaseBoundService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(MaterialPurchaseBound.class, MaterialPurchaseBoundDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(MaterialPurchaseBoundDto.class, MaterialPurchaseBound.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Override
	public  MaterialPurchaseBoundDto addOne(MaterialPurchaseBoundDto dto){
		Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
		Wrapper<MaterialPurchaseBound> wrapper=new CriterionWrapper<MaterialPurchaseBound>(MaterialPurchaseBound.class);
		wrapper.eq("state", 1);
		List<MaterialPurchaseBound> list=super.selectList(wrapper);
		for(MaterialPurchaseBound entity:list){
			entity.setState(2);
			entity.setLastAlterTime(new Date());
			entity.setLastAltorId(creator);
			super.updateById(entity);
		}
		dto.setFdId(Long.valueOf(IdWorker.getId()));
		dto.setCreateTime(new Date());
		dto.setCreatorId(creator);
		super.insert(dtoToEntityMapper.map(dto, MaterialPurchaseBound.class));
		return dto;		
	}
	
	@Override
	public MaterialPurchaseBoundDto findBound(){
		Wrapper<MaterialPurchaseBound> wrapper=new CriterionWrapper<MaterialPurchaseBound>(MaterialPurchaseBound.class);
		wrapper.eq("state", 1);
		wrapper.orderBy("create_time",false);
		MaterialPurchaseBound list=super.selectFirst(wrapper);
		return entityToDtoMapper.map(list, MaterialPurchaseBoundDto.class);
		
	}
	
}
