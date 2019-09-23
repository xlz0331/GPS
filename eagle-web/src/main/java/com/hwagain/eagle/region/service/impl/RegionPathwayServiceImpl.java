package com.hwagain.eagle.region.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.region.dto.RegionPathwayDto;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.entity.RegionPathway;
import com.hwagain.eagle.region.mapper.RegionPathwayMapper;
import com.hwagain.eagle.region.service.IRegionPathwayService;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-09
 */
@Service("regionPathwayService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegionPathwayServiceImpl extends ServiceImpl<RegionPathwayMapper, RegionPathway> implements IRegionPathwayService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired IRegionService regionService;
	@Autowired RegionPathwayMapper regionPathwayMapper;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegionPathway.class, RegionPathwayDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegionPathwayDto.class, RegionPathway.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	private void validate(RegionPathwayDto dto) {
		Assert.notBlank(dto.getProvince(), "省份不能为空");
		Assert.notBlank(dto.getCity(), "市不能为空");
		Assert.notBlank(dto.getCounty(), "区/县不能为空");
		Assert.notBlank(dto.getHaulway(), "途径路线不能为空");
//		Assert.notBlank(dto.getAddress(), "详细地址不能为空");
	}
	
	//省
	@Override
	public List<RegionPathway> queryPrivince(){
		List<RegionPathway> privinceList=regionPathwayMapper.queryPrivince();
		return privinceList;		
	}
	
	//市
	@Override
	public List<RegionPathway> queryCity(String privince){
		List<RegionPathway> cityList=regionPathwayMapper.queryCity(privince);
		return cityList;
		
	}
	
	//县/区
	@Override
	public List<RegionPathway> queryCounty(String city){
		List<RegionPathway> countyLista=new ArrayList<>();
		List<RegionPathway> countyList=regionPathwayMapper.queryCounty(city);
		for(RegionPathway rPathway:countyList){			
			String[] arr = rPathway.getCounty().split("、"); // 用,分割
			for(String str:arr){
				RegionPathway regionPathway=new RegionPathway();
				regionPathway.setCounty(str);
				countyLista.add(regionPathway);
			}
		}
		return countyLista;		
	}
	
	//
	@Override
	public List<RegionPathway> queryHaulway(String privince,String city,String county){
		List<RegionPathway> haulwayList=regionPathwayMapper.queryHaulway(privince, city, county);
		return haulwayList;		
	}
	
	//新增
	@Override
	public String addOne(RegionPathway entity){
		validate(entityToDtoMapper.map(entity, RegionPathwayDto.class));
		Wrapper<RegionPathway> wrapper=new CriterionWrapper<RegionPathway>(RegionPathway.class);
		wrapper.eq("province", entity.getProvince());
		wrapper.eq("city", entity.getCity());
		wrapper.eq("county", entity.getCounty());
		wrapper.eq("address", entity.getAddress());
		wrapper.eq("haulway", entity.getHaulway());
		wrapper.eq("latitude", entity.getLatitude());
		wrapper.eq("longitude", entity.getLongitude());
		wrapper.eq("state", 1);
		RegionPathway regionPathway=super.selectFirst(wrapper);
		if(regionPathway!=null){
			Assert.throwException("当前途经点已存在");
		}else{
			entity.setState(1);
			entity.setFdId(Long.valueOf(IdWorker.getId()));
			entity.setCreateTime(new Date());
//			entity.setCreatorId(UserUtils.getUserInfo().getName());
			super.insert(entity);
		}
		return "新增【"+entity.getHaulway()+"】途经点成功";		
	}
	
	//省市县多级联查
	@Override
	public Map findAllRegion(){
		Wrapper<Region> wrapper=new CriterionWrapper<Region>(Region.class);
		wrapper.eq("state", 1);
		List<Region> list=regionService.selectList(wrapper);
		for(Region region:list){
			region.setSupplierName(null);
			region.setExtraSubsidy(null);
			region.setMaterial(null);
			region.setRegionDistance(null);
			region.setMinRegionDistance(null);
			region.setState(null);
			region.setCreateTime(null);
		}
		Map<String, Map<String, List<Region>>> maps = list.stream().collect(Collectors.groupingBy(Region::getProvince,Collectors.groupingBy(Region::getCity)));
		return maps;
		
	}
}
