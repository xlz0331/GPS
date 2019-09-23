package com.hwagain.eagle.supplier.service.impl;

import com.hwagain.eagle.supplier.entity.MaterialPurchase;
import com.hwagain.eagle.supplier.entity.MaterialPurchasePlan;
import com.hwagain.eagle.base.entity.Material;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.supplier.dto.MaterialPurchaseDto;
import com.hwagain.eagle.supplier.mapper.MaterialPurchaseMapper;
import com.hwagain.eagle.supplier.service.IMaterialPurchasePlanService;
import com.hwagain.eagle.supplier.service.IMaterialPurchaseService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import io.swagger.annotations.Api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.codehaus.janino.Java.NewClassInstance;
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
 * @since 2019-04-09
 */
@Service("materialPurchaseService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MaterialPurchaseServiceImpl extends ServiceImpl<MaterialPurchaseMapper, MaterialPurchase> implements IMaterialPurchaseService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired IMaterialPurchasePlanService materialPurchasePlanService;
	@Autowired IMaterialService materialService;
	
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(MaterialPurchase.class, MaterialPurchaseDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(MaterialPurchaseDto.class, MaterialPurchase.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Override
	public List<MaterialPurchaseDto> getNowPlan(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		String date=df.format(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date2=null;
		try {
			date2=sdf.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Wrapper<MaterialPurchase> wrapper=new CriterionWrapper<MaterialPurchase>(MaterialPurchase.class);
		wrapper.eq("create_time", date2);
//		wrapper.eq(column, params)
		wrapper.orderBy("material");
		List<MaterialPurchase> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, MaterialPurchaseDto.class);		
	}
	
	@Override
	public List<MaterialPurchaseDto> getTotalPlan(){
		BigDecimal bignum1 = new BigDecimal("100");
		Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
		Date createDate=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		String date=df.format(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date2=null;
		try {
			date2=sdf.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Wrapper<MaterialPurchase> wraMatera=new CriterionWrapper<MaterialPurchase>(MaterialPurchase.class);
		wraMatera.eq("create_time", date2);
		List<MaterialPurchase> matera=super.selectList(wraMatera);
		for(MaterialPurchase mata:matera){
			super.deleteById(mata.getFdId());
		}
		Wrapper<Material> wr=new CriterionWrapper<Material>(Material.class);
		wr.eq("state", 1);
		List<Material> ma=materialService.selectList(wr);
		for(Material materi:ma){
			MaterialPurchase mater3=new MaterialPurchase();
			mater3.setFdId(Long.valueOf(IdWorker.getId()));
			mater3.setMaterial(materi.getName());
			mater3.setCreateTime(date2);
			mater3.setCreatorId(creator);
			super.insert(mater3);
		}
		Wrapper<MaterialPurchasePlan> wrapper=new CriterionWrapper<MaterialPurchasePlan>(MaterialPurchasePlan.class);
		wrapper.eq("state", 3);
		wrapper.orderBy("material");
		wrapper.orderBy("supplier_name");
		List<MaterialPurchasePlan> list=materialPurchasePlanService.selectList(wrapper);
		List<MaterialPurchasePlan> list1=new ArrayList<>();
		for(MaterialPurchasePlan dto:list){
			String da=df.format(dto.getPlanStarttime());
			if(da.equals(date)){
				Wrapper<MaterialPurchase> wraMater=new CriterionWrapper<MaterialPurchase>(MaterialPurchase.class);
				wraMater.eq("create_time", date2);
				wraMater.eq("material", dto.getMaterial());
				MaterialPurchase mater=super.selectFirst(wraMater);
				if(mater!=null){
					mater.setPurchaseNum(dto.getPurchasePlanNum().add(mater.getPurchaseNum()));
					mater.setMaxPurchaseNum(dto.getMaxPurchaseDayNum().add(mater.getMaxPurchaseNum()));
					mater.setMinPurchaseNum(dto.getMinPurchaseDayNum().add(mater.getMinPurchaseNum()));
					super.updateById(mater);
				}else{
					MaterialPurchase mater2=new MaterialPurchase();
					mater2.setFdId(Long.valueOf(IdWorker.getId()));
					mater2.setPurchaseNum(dto.getPurchasePlanNum());
					mater2.setMaxPurchaseNum(dto.getMaxPurchaseDayNum());
					mater2.setMinPurchaseNum(dto.getMinPurchaseDayNum());
					mater2.setMaterial(dto.getMaterial());
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					mater2.setCreatorId(creator);
//			        System.out.println();
					mater2.setCreateTime(date2);			
					super.insert(mater2);
				}
				list1.add(dto);
			}
		}
		return entityToDtoMapper.mapAsList(list1, MaterialPurchaseDto.class);
	}
}
