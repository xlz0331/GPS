package com.hwagain.eagle.company.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.company.dto.CompanyDto;
import com.hwagain.eagle.company.entity.Company;
import com.hwagain.eagle.company.mapper.CompanyMapper;
import com.hwagain.eagle.company.service.ICompanyService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 公司信息表 服务实现类
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
@Service("companyService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {
	
	@Autowired
	private CompanyMapper companyMapper;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Company.class, CompanyDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factoryTwo = new DefaultMapperFactory.Builder().build();
		factoryTwo.classMap(CompanyDto.class, Company.class).byDefault().register();
		dtoToEntityMapper = factoryTwo.getMapperFacade();
	}

	@Override
	public String save(CompanyDto companyDto) {
		validate(companyDto);
		Company entity = dtoToEntityMapper.map(companyDto, Company.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(CompanyDto companyDto) {
		validate(companyDto);
		Company entity = dtoToEntityMapper.map(companyDto, Company.class);
		entity.setLastAlterTime(new Date());
		super.updateById(entity);
		return "修改成功";
	}

	@Override
	public String delete(String fdId) {
		Company entity = new Company();
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
	public List<CompanyDto> findAll() {
		Wrapper<Company> wrapper = new CriterionWrapper<>(Company.class);
		List<Company> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, CompanyDto.class);
	}

	@Override
	public Company findOne(String fdId) {
		return super.selectById(fdId);
	}

	private void validate(CompanyDto companyDto){
		Assert.notBlank(companyDto.getName(), "用户名不能为空");
		Assert.notBlank(companyDto.getNameSimpleName(), "公司简称不能为空");
		Assert.notNull(companyDto.getState(), "状态不能为空");
		Assert.notNull(companyDto.getOrgId(), "公司ID不能为空");
		if (companyDto.getFdId() != null) {
			Assert.notNull(companyDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(companyDto.getCreatorId(), "创建人ID不能为空");
		}
	}
}
