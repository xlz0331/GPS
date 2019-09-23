package com.hwagain.eagle.region.service.impl;

import com.hwagain.eagle.region.entity.RegionSupplier;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.region.dto.RegionSupplierDto;
import com.hwagain.eagle.region.mapper.RegionSupplierMapper;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.eagle.region.service.IRegionSupplierService;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
 * 片区表 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-06-17
 */
@Service("regionSupplierService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegionSupplierServiceImpl extends ServiceImpl<RegionSupplierMapper, RegionSupplier> implements IRegionSupplierService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired RegionSupplierMapper regionSupplierMapper;
	@Autowired IMaterialService materialService;
	@Autowired IRegionService regionService;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegionSupplier.class, RegionSupplierDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegionSupplierDto.class, RegionSupplier.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Override
	public List<RegionSupplierDto> findList() throws CustomException {
		Wrapper<RegionSupplier> wrapper = new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
		wrapper.eq("state", 1);
		wrapper.notIn("supplier_name", "散户");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("effdt");
		List<RegionSupplier> list=super.selectList(wrapper);
		List<RegionSupplierDto> regionDtos=new ArrayList<>();
		for(RegionSupplierDto regionDto:entityToDtoMapper.mapAsList(list, RegionSupplierDto.class)){
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			regionDto.setEffdtText(df.format(new Date(regionDto.getEffdt())));
			regionDtos.add(regionDto);
		}
		return regionDtos;
	}
	
	@Override
	public List<RegionSupplier> findRegionBySupplier(String supplierName,String material) throws CustomException {
		Wrapper<RegionSupplier> wrapper = new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
		wrapper.eq("state", 1);
		wrapper.eq("supplier_name", supplierName);
		wrapper.eq("material", material);
		wrapper.le("effdt", System.currentTimeMillis());
		wrapper.orderBy("province");
		wrapper.orderBy("city");
//		wrapper.orderBy("region_name");
		List<RegionSupplier> list=super.selectList(wrapper);
		List<RegionSupplier> list2=new ArrayList<>();
		if(list.size()==0){
			return list2;
		}else{
			list2.addAll(list);
		}
		return list2;
	}

	@Override
	public List<RegionSupplier> queryAllRegionSupplier(){
//		String sSql="SELECT top 1 * FROM [dbo].[t_Contract] WHERE ContractNO LIKE 'XJ%' and EndDate>getdate() ORDER BY CheckTime desc";
////		sSql="EXECUTE splitCitys @citys='广东省广州市'";
//		List<Map<String, Object>> list=sync.getHTZDatas(sSql);
//		for(int i=0;i<list.size();i++){
//			String supplierName=String.valueOf(list.get(i).get("Supplier"));
//			String material=String.valueOf(list.get(i).get("ZhuMCs"));
//			String materialName=materialService.findOneByName(material).getCode();
//			String effdt=String.valueOf(list.get(i).get("StartDate"));
//			String area1=String.valueOf(list.get(i).get("Areas"));
//			String [] areas=area1.split(",");
//			System.err.println(area1);
//			for(String area2:areas){
//				String sql="EXECUTE splitCitys @citys='"+area2+"'";
//				List<Map<String, Object>> list1=sync.getHTZDatas(sql);
//				String province=String.valueOf(list1.get(0).get("province"));
//				String city=String.valueOf(list1.get(0).get("city"));
//				String area=String.valueOf(list1.get(0).get("area"));
//				Float subsidy=Float.valueOf("0");
//				Wrapper<Region> wrapper=new CriterionWrapper<Region>(Region.class);
//				wrapper.eq("supplier_name", supplierName);
//				wrapper.eq("province", province);
//				wrapper.eq("city", city);
////			wrapper.like("region_name", area, SqlLike.DEFAULT);
//				Region region=regionService.selectOne(wrapper);
//				if(region!=null){
//					subsidy=region.getSubsidy();
//				}
//				System.err.println(supplierName+"/"+province+"/"+city+"/"+area+"/"+materialName+"/"+subsidy);
//			}
//		}
//		System.err.println(list.size());
		return regionSupplierMapper.queryAllSupplier();	
	}
	
	//根据城市名称返回区域供应商信息  ---add by 黎昌盛
	@Override
	public List<RegionSupplier> findByCityName(String city)
	{
		Wrapper<RegionSupplier> wrapper = new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
		wrapper.eq("state", 1);
		wrapper.like("city", city);
//		wrapper.orderBy("region_name");
		List<RegionSupplier> list=super.selectList(wrapper);
		List<RegionSupplier> list2=new ArrayList<>();
		if(list.size()==0){
			return list2;
		}else{
			list2.addAll(list);
		}
		return list2;
	}
	
	//根据先去返回区域供应商信息
	@Override
	public List<RegionSupplier> findByCountyName(String county,String city){
		String [] hainanCounty={"昌江黎族自治县","澄迈县","儋州市","定安县","东方市","白沙黎族自治县","乐东黎族自治县","临高县","琼中黎族苗族自治县","屯昌县"};
		
		if (Arrays.asList(hainanCounty).contains(city)) {
			Wrapper<RegionSupplier> wrahainan = new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
			wrahainan.eq("state", 1);
			wrahainan.like("region_name", city,SqlLike.DEFAULT);
			List<RegionSupplier> list3=super.selectList(wrahainan);
			return list3;
		}
		Wrapper<RegionSupplier> wrapper = new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
		wrapper.eq("state", 1);
		wrapper.like("region_name", county,SqlLike.DEFAULT);
//		wrapper.orderBy("region_name");
		List<RegionSupplier> list=super.selectList(wrapper);
		if (list.size()>0&&list.size()<5) {
			return list;
		}
		Wrapper<RegionSupplier> wrapcity = new CriterionWrapper<RegionSupplier>(RegionSupplier.class);
		wrapcity.eq("state", 1);
		wrapcity.like("city", city);
//		wrapper.orderBy("region_name");
		List<RegionSupplier> list2=super.selectList(wrapcity);
		return list2;
		
	}
}
