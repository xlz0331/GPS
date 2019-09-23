package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.CarInfo;
import com.hwagain.eagle.base.entity.CarInfo;
import com.hwagain.eagle.base.dto.CarInfoDto;
import com.hwagain.eagle.base.dto.CarInfoDto;
import com.hwagain.eagle.base.mapper.CarInfoMapper;
import com.hwagain.eagle.base.service.ICarInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2019-03-25
 */
@Service("carInfoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements ICarInfoService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired BaseContextHandler baseContextHandler;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(CarInfo.class, CarInfoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(CarInfoDto.class, CarInfo.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	@Override
	public List<CarInfoDto> getCarInfoBySupplierId(){
		Long supplierId=Long.valueOf(String.valueOf(baseContextHandler.getSupplierId()));
		Wrapper<CarInfo> wrapper=new CriterionWrapper<CarInfo>(CarInfo.class);
		wrapper.eq("supplier_id", supplierId);
		wrapper.eq("state", 1);
		List<CarInfo> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, CarInfoDto.class);		
	}
	
	//查询
	@Override
	public List<CarInfoDto> findAll(String supplierName,String CarType){
		Wrapper<CarInfo> wrapper=new CriterionWrapper<CarInfo>(CarInfo.class);
		wrapper.eq("supplier_name", supplierName);
//		wrapper.eq("car_type", CarType);
		wrapper.like("car_type", CarType, SqlLike.DEFAULT);
		wrapper.eq("state", 1);
		List<CarInfo> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, CarInfoDto.class);
	}
	
	//新增
	@Override
	public CarInfoDto addOne(CarInfoDto dto){
		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
		String creatorName=UserUtils.getUserInfo().getName();
		
		Wrapper<CarInfo> wrapper=new CriterionWrapper<CarInfo>(CarInfo.class);
		wrapper.eq("supplier_name", dto.getSupplierName());
		wrapper.eq("plate_number", dto.getPlateNumber());
		wrapper.eq("state", 1);
		CarInfo list=super.selectFirst(wrapper);
		if(list!=null){
			dto.setFdId(list.getFdId());
			dto.setCreatorName(creatorName);
			dto.setLastAlterTime(new Date());
			dto.setLastAltorId(creatorId);
			super.updateById(dtoToEntityMapper.map(dto, CarInfo.class));
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
			dto.setCreateTime(new Date());
			dto.setCreatorId(creatorId);
			dto.setCreatorName(creatorName);
			super.insert(dtoToEntityMapper.map(dto, CarInfo.class));
		}
		return dto;
	}
	
	//删除
	@Override
	public String deleteByIds(String ids){
		if(ids.isEmpty()){
			Assert.throwException("未选中要删除的行");
		}
		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
		String[] id = ids.split(";");
		for(String fdId:id){
			CarInfo car=super.selectById(fdId);
			car.setState(2);
			car.setLastAltorId(creatorId);
			car.setLastAlterTime(new Date());
			super.updateById(car);
		}
		return ids;
		
	}
}
