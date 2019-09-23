package com.hwagain.eagle.region.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.base.entity.Material;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.region.dto.RegionDetailDto;
import com.hwagain.eagle.region.dto.RegionDto;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.eagle.region.entity.RegionHistory;
import com.hwagain.eagle.region.entity.RegionSupplier;
import com.hwagain.eagle.region.mapper.RegionMapper;
import com.hwagain.eagle.region.service.IRegionHistoryService;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.eagle.region.service.IRegionSupplierService;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;


import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 片区表 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@Service("regionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired IRegionHistoryService regionHistoryService;
	@Autowired IMaterialService materialService;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired IRegionSupplierService regionSupplierService;
	
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegionDetail.class, RegionDetailDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegionDetailDto.class, RegionDetail.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	
	@Override
	public List<RegionDto> findList() throws CustomException {
		Wrapper<Region> wrapper = new CriterionWrapper<Region>(Region.class);
		wrapper.eq("state", 1);
		wrapper.notIn("supplier_name", "散户");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("city");
		wrapper.orderBy("effdt");
		List<Region> list=super.selectList(wrapper);
		List<RegionDto> regionDtos=new ArrayList<>();
		for(RegionDto regionDto:entityToDtoMapper.mapAsList(list, RegionDto.class)){
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			regionDto.setEffdtText(df.format(new Date(regionDto.getEffdt())));
			regionDtos.add(regionDto);
		}
		return regionDtos;
	}
	
	
	@Override
	public List<RegionDto> findListRetail() throws CustomException {
		Wrapper<Region> wrapper = new CriterionWrapper<Region>(Region.class);
		wrapper.eq("state", 1);
		wrapper.eq("supplier_name", "散户");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("city");
		wrapper.orderBy("material");
		wrapper.orderBy("effdt");
		List<Region> list=super.selectList(wrapper);
		List<RegionDto> regionDtos=new ArrayList<>();
		for(RegionDto regionDto:entityToDtoMapper.mapAsList(list, RegionDto.class)){
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			regionDto.setEffdtText(df.format(new Date(regionDto.getEffdt())));
			regionDtos.add(regionDto);
		}
		return regionDtos;
	}
	
	
	@Override
	public Region findOneByParam(Region param) throws CustomException {
		SupplierInfoDto supplierInfoDto=supplierInfoService.findByName(param.getSupplierName());
		Wrapper<Region> wrapper = new CriterionWrapper<Region>(Region.class);
		if(supplierInfoDto.getCode()=="合同供应商"){
			wrapper.eq("supplier_name", param.getSupplierName());
		}else {
			wrapper.eq("supplier_name", "散户");
		}
		wrapper.eq("province", param.getProvince());
		wrapper.eq("material", param.getMaterial());
		wrapper.eq("city", param.getCity());
		wrapper.eq("region_name", param.getRegionName());
		wrapper.orderBy("create_time",false);
		Region entity = super.selectOne(wrapper);
		Assert.notNull(entity,"查询数据为空");
		//判断数据是否达到执行时间
		if(entity.getEffdt()>System.currentTimeMillis()) {
			//未达到执行时间,去历史表查询数据
			Wrapper<RegionHistory> ew = new CriterionWrapper<RegionHistory>(RegionHistory.class);
			ew.eq("fd_id", entity.getFdId())
			.orderBy("version", false);
			RegionHistory rh = regionHistoryService.selectOne(ew);
			entity = new Region();
			BeanUtils.copyProperties(rh, entity);
		}
		return entity;
	}
	
	@Override
	public Region findOneByParam2(String supplierName,String city,String material,String poundTime) throws CustomException {
		Material mater=materialService.findOneByName(material);
		if(material==null){
			Assert.throwException("找不到当前原料名称对应新系统的名称");
		}
		Wrapper<Region> wrapper = new CriterionWrapper<Region>(Region.class);
		wrapper.eq("supplier_name", supplierName)
		.eq("material", mater.getCode())
		.eq("city",city)
		.orderBy("create_time",false);
		Region entity = super.selectOne(wrapper);
		if(entity==null){
			Assert.throwException("查询数据为空");
			//	Assert.notNull(entity,"查询数据为空");
		}
		SimpleDateFormat sdf=new SimpleDateFormat();
		try {
			Date date=sdf.parse(poundTime);
			//判断数据是否达到执行时间
			if(entity.getEffdt()>date.getTime()) {
				//未达到执行时间,去历史表查询数据
				Wrapper<RegionHistory> ew = new CriterionWrapper<RegionHistory>(RegionHistory.class);
				ew.eq("fd_id", entity.getFdId())
				.orderBy("version", false);
				RegionHistory rh = regionHistoryService.selectOne(ew);
				entity = new Region();
				BeanUtils.copyProperties(rh, entity);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entity;
	}
	
	
	@Override
	public List<Region> findRegionBySupplier(String supplierName,String material) throws CustomException {
		Wrapper<Region> wrapper = new CriterionWrapper<Region>(Region.class);
		wrapper.eq("state", 1);
		wrapper.eq("supplier_name", supplierName);
		wrapper.eq("material", material);
		wrapper.le("effdt", System.currentTimeMillis());
		List<Region> list=super.selectList(wrapper);
		List<Region> list2=new ArrayList<>();
		if(list.size()==0){
			return list2;
		}else{
			list2.addAll(list);
		}
		return list2;
	}
	
	//获取原料名称
	@Override
	public Map findByParam(){
		String supplierName=BaseContextHandler.getUsername();
		SupplierInfoDto supplierInfoDto=supplierInfoService.findByName(supplierName);
		if(supplierInfoDto==null){
			Assert.throwException("找不到当前供应商数据，请联系相关人员");
		}
		System.err.println(supplierInfoDto.getCode());
		if(supplierInfoDto.getCode().equals("合同供应商")){
			Wrapper<RegionSupplier> wrapperSu=new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
			wrapperSu.eq("supplier_name", supplierName);
			wrapperSu.eq("state", 1);
			List<RegionSupplier> list=regionSupplierService.selectList(wrapperSu);
			for(RegionSupplier regionSu:list){
				regionSu.setSupplierName(null);
				regionSu.setExtraSubsidy(null);
				regionSu.setRegionDistance(null);
				regionSu.setMinRegionDistance(null);
				regionSu.setState(null);
				regionSu.setCreateTime(null);
			}
			Map<String, Map<String, Map<String, List<RegionSupplier>>>> maps = list.stream().collect(Collectors.groupingBy(RegionSupplier::getMaterial,Collectors.groupingBy(RegionSupplier::getProvince,Collectors.groupingBy(RegionSupplier::getCity))));
			return maps;
		}else{
			Wrapper<Region> wrapper=new CriterionWrapper<Region>(Region.class);
//			wrapper.eq("supplier_name", supplierName);
			wrapper.eq("state", 1);
			List<Region> list=super.selectList(wrapper);
			for(Region region:list){
				region.setSupplierName(null);
				region.setExtraSubsidy(null);
				region.setRegionDistance(null);
				region.setMinRegionDistance(null);
				region.setState(null);
				region.setCreateTime(null);
				region.setLastAlterTime(null);
				region.setLastAltorId(null);
			}
			Map<String, Map<String, Map<String, List<Region>>>> maps = list.stream().collect(Collectors.groupingBy(Region::getMaterial,Collectors.groupingBy(Region::getProvince,Collectors.groupingBy(Region::getCity))));
			return maps;
		}		
		//Region::getProvince,Collectors.groupingBy(Region::getCity))		
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime=df.format(new Date());
		System.err.println(nowTime);
		try {
			Date date = sf.parse("2019-08-01 00:00:00");
			System.err.println(date.getTime());
			System.err.println(new Date(Long.valueOf("1559088000000")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
