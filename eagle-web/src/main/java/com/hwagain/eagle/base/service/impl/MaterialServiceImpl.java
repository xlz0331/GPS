package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.CarInfo;
import com.hwagain.eagle.base.entity.Material;
import com.hwagain.eagle.base.dto.MaterialDto;
import com.hwagain.eagle.base.mapper.MaterialMapper;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.util.syncYuanLZhu;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.e;
import oracle.net.aso.n;

import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-04-02
 */
@Configurable
@EnableScheduling
@Component
@Service("materialService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	@Autowired syncYuanLZhu sync;
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Material.class, MaterialDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(MaterialDto.class, Material.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//
	@Override
	public MaterialDto addOne(MaterialDto dto){
		Wrapper<Material> wrapper=new CriterionWrapper<Material>(Material.class);
		List<Material> list=super.selectList(wrapper);
//		String code="MATERIAL"+list.size();
		Wrapper<Material> wrapper1=new CriterionWrapper<Material>(Material.class);
		wrapper1.eq("name", dto.getName());
		wrapper1.eq("level", dto.getLevel());
		Material material=super.selectFirst(wrapper1);
		if(material!=null){
			dto.setLastAlterTime(new Date());
			dto.setLastAltorId(UserUtils.getUserInfo().getName());
			super.updateById(dtoToEntityMapper.map(dto, Material.class));
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
//			dto.setCode(code);
			dto.setCreatorId(UserUtils.getUserInfo().getName());
			dto.setCreateTime(new Date());
			super.insert(dtoToEntityMapper.map(dto, Material.class));
		}
		return dto;		
	}
	
	@Override
	public List<MaterialDto> findAll(String name,String level){
		String [] state={"0","1"};
		Wrapper<Material> wrapper=new CriterionWrapper<Material>(Material.class);
		wrapper.like("name", name, SqlLike.DEFAULT);
		wrapper.like("level", level, SqlLike.DEFAULT);
		wrapper.in("state", state);
		wrapper.orderBy("code");
		List<Material> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, MaterialDto.class);		
	}
	
	
	@Override
	public List<MaterialDto> getMaterial(){
		Wrapper<Material> wrapper=new CriterionWrapper<Material>(Material.class);
		wrapper.eq("state", 1);
		List<Material> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, MaterialDto.class);		
	}
	
	
	@Override
	public String deleteByIds(String ids){
		if(ids.isEmpty()){
			Assert.throwException("未选中要删除的行");
		}
		String[] id = ids.split(";");
		for(String fdId:id){
			Material car=super.selectById(fdId);
			car.setState(2);
			car.setLastAltorId(UserUtils.getUserInfo().getName());
			car.setLastAlterTime(new Date());
			super.updateById(car);
		}
		return ids;	
	}
	
	
	@Override
	public Material findOneByName(String name){
		Wrapper<Material> wrapper=new CriterionWrapper<Material>(Material.class);
		wrapper.eq("name", name);
		wrapper.eq("state", 1);
		Material material=super.selectOne(wrapper);
		return material;		
	}
	
	
	//同步原料竹系统的原料名称信息
	@Scheduled(cron = "0 0 1 ? * *") // 每天1点执行-首先执行
	@Override 
	public String syncMaterial(){
		String [] state={"1","0"};
		String sql="SELECT * FROM [dbo].[V_ZhuMC]";
		List<Map<String,Object>> list=sync.getHTZDatas(sql);
		for(int i=0;i<list.size();i++){
			String name=String.valueOf(list.get(i).get("mc"));
			Wrapper<Material> wrapper=new CriterionWrapper<Material>(Material.class);
			wrapper.eq("name", name);
			wrapper.in("state", state);
			Material material=super.selectOne(wrapper);
			if(material==null){
				Material entity=new Material();
				entity.setFdId(IdWorker.getId());
				entity.setName(name);
				entity.setState(0);
				entity.setLevel("D");
				entity.setCreateTime(new Date());
//				entity.setCreatorId(UserUtils.getUserInfo().getName());
				super.insert(entity);
			}
		}
		return "同步原料名称成功";		
	}
	
	//修改
	@Override
	public Material updateMaterial(Long fdId,String code){
		Material entity=super.selectById(fdId);
		if(entity!=null){
			entity.setCode(code);
			entity.setCreatorId(UserUtils.getUserInfo().getName());
			entity.setState(1);
			super.updateById(entity);
		}
		return entity;
		
	}
}
