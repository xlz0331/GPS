package com.hwagain.eagle.supplier.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.supplier.dto.SupplyPlanDto;
import com.hwagain.eagle.supplier.entity.SupplyPlan;
import com.hwagain.eagle.supplier.mapper.SupplyPlanMapper;
import com.hwagain.eagle.supplier.service.ISupplyPlanService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 供货计划信息表 服务实现类
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
@Service("supplyPlanService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SupplyPlanServiceImpl extends ServiceImpl<SupplyPlanMapper, SupplyPlan> implements ISupplyPlanService {
	
	@Autowired
	private SupplyPlanMapper companyMapper;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(SupplyPlan.class, SupplyPlanDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(SupplyPlanDto.class, SupplyPlan.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Override
	public String save(SupplyPlanDto supplyPlanDto) {
		validate(supplyPlanDto);
		SupplyPlan entity = dtoToEntityMapper.map(supplyPlanDto, SupplyPlan.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(SupplyPlanDto supplyPlanDto) {
		validate(supplyPlanDto);
		SupplyPlan entity = dtoToEntityMapper.map(supplyPlanDto, SupplyPlan.class);
		entity.setLastAlterTime(new Date());
		super.updateById(entity);
		return "修改成功";
	}

	@Override
	public String delete(String fdId) {
		SupplyPlan entity = new SupplyPlan();
		entity.setFdId(Long.parseLong(fdId));
		entity.setState(2);
		super.updateById(entity);
		return "删除成功";
	}

	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		try {
			companyMapper.deleteByIds(id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("删除异常");
		}
		return "删除成功";
	}

	@Override
	public List<SupplyPlanDto> findAll() {
		Wrapper<SupplyPlan> wrapper = new CriterionWrapper<>(SupplyPlan.class);
		List<SupplyPlan> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, SupplyPlanDto.class);
	}

	@Override
	public SupplyPlan findOne(String fdId) {
		return super.selectById(fdId);
	}

	private void validate(SupplyPlanDto supplyPlanDto){
		Assert.notNull(supplyPlanDto.getCompanyId(), "公司ID不能为空");
		Assert.notNull(supplyPlanDto.getSupplierId(), "供应商ID不能为空");
		Assert.notNull(supplyPlanDto.getMonth(), "月份(格式：yyyy-MM)不能为空");
		Assert.notNull(supplyPlanDto.getState(), "状态不能为空");
		Assert.notNull(supplyPlanDto.getIndictor(), "公司ID不能为空");
		if (supplyPlanDto.getFdId() != null) {
			Assert.notNull(supplyPlanDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(supplyPlanDto.getCreatorId(), "创建人ID不能为空");
		}
	}
}
