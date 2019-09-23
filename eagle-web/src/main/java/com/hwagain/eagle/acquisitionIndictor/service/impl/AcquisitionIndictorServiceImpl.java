package com.hwagain.eagle.acquisitionIndictor.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.acquisitionIndictor.dto.AcquisitionIndictorDto;
import com.hwagain.eagle.acquisitionIndictor.entity.AcquisitionIndictor;
import com.hwagain.eagle.acquisitionIndictor.mapper.AcquisitionIndictorMapper;
import com.hwagain.eagle.acquisitionIndictor.service.IAcquisitionIndictorService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 收购指标信息表 服务实现类
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
@Service("acquisitionIndictorService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AcquisitionIndictorServiceImpl extends ServiceImpl<AcquisitionIndictorMapper, AcquisitionIndictor> implements IAcquisitionIndictorService {
	
	@Autowired
	private AcquisitionIndictorMapper acquisitionIndictorMapper;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(AcquisitionIndictor.class, AcquisitionIndictorDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(AcquisitionIndictorDto.class, AcquisitionIndictor.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public String save(AcquisitionIndictorDto acquisitionIndictorDto) {
		validate(acquisitionIndictorDto);
		AcquisitionIndictor entity = dtoToEntityMapper.map(acquisitionIndictorDto, AcquisitionIndictor.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(AcquisitionIndictorDto acquisitionIndictorDto) {
		validate(acquisitionIndictorDto);
		AcquisitionIndictor entity = dtoToEntityMapper.map(acquisitionIndictorDto, AcquisitionIndictor.class);
		entity.setLastAlterTime(new Date());
		super.updateById(entity);
		return "修改成功";
	}

	@Override
	public String delete(String fdId) {
		AcquisitionIndictor entity = new AcquisitionIndictor();
		entity.setFdId(Long.parseLong(fdId));
		entity.setState(2);
		super.updateById(entity);
		return "删除成功";
	}

	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		try {
			acquisitionIndictorMapper.deleteByIds(id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("删除异常");
		}
		return "删除成功";
	}

	@Override
	public List<AcquisitionIndictorDto> findAll() {
		Wrapper<AcquisitionIndictor> wrapper = new CriterionWrapper<>(AcquisitionIndictor.class);
		List<AcquisitionIndictor> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, AcquisitionIndictorDto.class);
	}

	@Override
	public AcquisitionIndictor findOne(String fdId) {
		return super.selectById(fdId);
	}

	private void validate(AcquisitionIndictorDto acquisitionIndictorDto){
		Assert.notNull(acquisitionIndictorDto.getIndicator(), "指标不能为空");
		Assert.notNull(acquisitionIndictorDto.getYear(), "年份不能为空");
		Assert.notNull(acquisitionIndictorDto.getState(), "状态不能为空");
		Assert.notNull(acquisitionIndictorDto.getPrice(), "指标单价不能为空");
		Assert.notNull(acquisitionIndictorDto.getCompanyId(), "公司ID不能为空");
		Assert.notNull(acquisitionIndictorDto.getSupplierId(), "供应商ID不能为空");
		if (acquisitionIndictorDto.getFdId() != null) {
			Assert.notNull(acquisitionIndictorDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(acquisitionIndictorDto.getCreatorId(), "创建人ID不能为空");
		}
	}
}
