package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.CarInfo;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.dto.CarInfoDto;
import com.hwagain.eagle.base.dto.DictDto;
import com.hwagain.eagle.base.mapper.DictMapper;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.org.entity.OrgRelationship;
import com.hwagain.eagle.org.service.IOrgRelationshipService;
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
import oracle.net.aso.d;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-26
 */
@Service("dictService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired IOrgRelationshipService orgRelationshipSrvice;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Dict.class, DictDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(DictDto.class, Dict.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//按条件查询
	@Override
	public Dict findOaTemId(String itemName,String groupName) {
		System.err.println(itemName+groupName);
		Wrapper<Dict> wrapper = new CriterionWrapper<Dict>(Dict.class);
		wrapper.eq("item_name",itemName);
		wrapper.eq("group_name",groupName);
		wrapper.eq("is_delete", 0);
		wrapper.orderBy("orderby");
		Dict list=super.selectFirst(wrapper);
		System.err.println(list);
		return super.selectFirst(wrapper);
		
	}
	
	//查询全部
	@Override
	public List<DictDto> findAll(String typeName){
		Wrapper<Dict> wrapper=new CriterionWrapper<Dict>(Dict.class);
		wrapper.like("type_name", typeName,SqlLike.DEFAULT);
		wrapper.eq("is_delete", 0);
		List<Dict> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, DictDto.class);
	}
	
	//新增
	@Override
	public DictDto addOne(DictDto dto){
		String creatorName=UserUtils.getUserInfo().getName();
		
		Wrapper<Dict> wrapper=new CriterionWrapper<Dict>(Dict.class);
		wrapper.eq("type_name", dto.getTypeName());
		wrapper.eq("item_name", dto.getItemName());
		wrapper.eq("item_no", dto.getItemNo());
		wrapper.eq("group_name",dto.getGroupName());
		wrapper.eq("is_delete", 0);
		Dict list=super.selectFirst(wrapper);
		if(list!=null){
			dto.setFdId(list.getFdId());
			dto.setLastAlterTime(new Date());
			dto.setLastAlterId(UserUtils.getUserInfo().getFdId());
			super.updateById(dtoToEntityMapper.map(dto, Dict.class));
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
			dto.setCreateTime(new Date());
			dto.setCreaterId(UserUtils.getUserInfo().getFdId());
			super.insert(dtoToEntityMapper.map(dto, Dict.class));
		}
		return dto;
	}
	
	//删除
	@Override
	public String deleteByIds(String ids){
		if(ids.isEmpty()){
			Assert.throwException("未选中要删除的行");
		}
		String[] id = ids.split(";");
		for(String fdId:id){
			Dict car=super.selectById(fdId);
			car.setIsDelete(1);
			car.setLastAlterId(UserUtils.getUserInfo().getFdId());
			car.setLastAlterTime(new Date());
			super.updateById(car);
		}
		return ids;
		
	}
	
	//按条件查询
	@Override
	public List<Dict> findBytypeName(String typeName){
		Wrapper<Dict> wrapper=new CriterionWrapper<Dict>(Dict.class);
		wrapper.eq("type_name", typeName);
		wrapper.eq("is_delete", 0);
		List<Dict> dicts=super.selectList(wrapper);
		return dicts;
	}
	
	//更新
	@Override
	public Dict updateOneByFdId(Dict dict){
		if(dict.getTypeName().equals("供货点审批人")){
			Wrapper<OrgRelationship> wraOrg=new CriterionWrapper<OrgRelationship>(OrgRelationship.class);
	    	wraOrg.eq("RelaType", "PDU");
	    	wraOrg.eq("RelaPrimary", 1);
	    	wraOrg.eq("UserName",dict.getItemName());
	    	OrgRelationship org=orgRelationshipSrvice.selectFirst(wraOrg);
	    	if(org!=null){
	    		if(!org.getUserID().equals(dict.getGroupName())){
	    			Assert.throwException("工号跟姓名不匹配，请重新输入");
	    		}
	    	}else{
	    		Assert.throwException("找不到当前员工");
	    	}
		}
		dict.setLastAlterTime(new Date());
		super.updateById(dict);
		return dict;
		
	}
}
