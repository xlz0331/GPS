package com.hwagain.eagle.user.service.impl;

import com.hwagain.eagle.user.dto.UserRoleDto;
import com.hwagain.eagle.user.entity.UserRole;
import com.hwagain.eagle.user.mapper.UserRoleMapper;
import com.hwagain.eagle.user.service.IUserRoleService;
import com.hwagain.framework.api.org.api.ISysOrgService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
@Service("userRoleService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;

	// entity转dto
	static MapperFacade entityToDtoMapper;
	@Autowired ISysOrgService sysOrgService;
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(UserRole.class, UserRoleDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factoryTwo = new DefaultMapperFactory.Builder().build();
		factoryTwo.classMap(UserRoleDto.class, UserRole.class).byDefault().register();
		dtoToEntityMapper = factoryTwo.getMapperFacade();
	}

	@Override
	public String save(UserRoleDto userRoleDto) {
		validate(userRoleDto);
		UserRole entity = dtoToEntityMapper.map(userRoleDto, UserRole.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(UserRoleDto userRoleDto) {
		validate(userRoleDto);
		UserRole entity = dtoToEntityMapper.map(userRoleDto, UserRole.class);
		entity.setLastAlterTime(new Date());
		super.updateById(entity);
		return "修改成功";
	}

	@Override
	public String delete(String fdId) {
		UserRole userRole = new UserRole();
		userRole.setFdId(Long.parseLong(fdId));
		userRole.setState(2);
		super.updateById(userRole);
		return "删除成功";
	}

	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		try {
			userRoleMapper.deleteByIds(id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("删除异常");
		}
		return "删除成功";
	}

	@Override
	public List<UserRoleDto> findAll() {
		System.err.println(sysOrgService.getPersonInfoById("925648663121702912"));
		Wrapper<UserRole> wrapper = new CriterionWrapper<>(UserRole.class);
		List<UserRole> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, UserRoleDto.class);
	}

	@Override
	public UserRole findOne(String fdId) {
		return super.selectById(fdId);
	}

	@Override
	public List<UserRole> queryParam(UserRoleDto userRoleDto) {
		return userRoleMapper.queryParam(userRoleDto);
	}

	private void validate(UserRoleDto userRoleDto){
		Assert.notNull(userRoleDto.getUserId(), "用户ID不能为空");
		Assert.notNull(userRoleDto.getCreatorId(), "创建人ID不能为空");
		Assert.notNull(userRoleDto.getRoleId(), "角色ID不能为空");
		if (userRoleDto.getFdId() != null) {
			Assert.notNull(userRoleDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(userRoleDto.getCreatorId(), "创建人ID不能为空");
		}
	}

}
