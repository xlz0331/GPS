package com.hwagain.eagle.role.service.impl;

import com.hwagain.eagle.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.hwagain.eagle.company.dto.CompanyDto;
import com.hwagain.eagle.company.entity.Company;
import com.hwagain.eagle.role.dto.RoleDto;
import com.hwagain.eagle.role.mapper.RoleMapper;
import com.hwagain.eagle.role.service.IRoleService;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.entity.SupplierInfo;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.util.PageDto;
import com.hwagain.framework.api.org.api.ISysOrgService;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;

import java.util.ArrayList;
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
 * 角色表 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@Service("roleService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired IUserInfoService userInfoService;
	@Autowired ISysOrgService bbb;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Role.class, RoleDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RoleDto.class, Role.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	private void validate(RoleDto dto) {
		Assert.notBlank(dto.getRole(),"角色编码不能为空");
		Assert.notBlank(dto.getName(),"角色名称不能为空");
	}
	
	//新增角色（修改）
	@Override
	public RoleDto addOne(RoleDto dto){
		Wrapper<UserInfo> wrappera=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrappera.eq("loginname", UserUtils.getUserInfo().getName());
    	wrappera.eq("state", 1);
    	wrappera.eq("locked", 2);
    	wrappera.eq("user_type", "ADMIN");
    	UserInfo userInfoa=userInfoService.selectFirst(wrappera);
    	if(userInfoa==null){
    		Assert.throwException("您没有权限配置系统角色");
    	}
		Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
		Date createDate=new Date();
		validate(dto);
		Wrapper<Role> wrapper=new CriterionWrapper<Role>(Role.class);
		wrapper.eq("role", dto.getRole());
		wrapper.eq("name", dto.getName());
		wrapper.eq("state", 1);
		Role list=super.selectFirst(wrapper);
		if(list!=null){
			dto.setFdId(list.getFdId());
			dto.setLastAlterTime(createDate);
			dto.setLastAltorId(creator);
			super.updateById(entityToDtoMapper.map(dto, Role.class));
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
			dto.setCreateTime(createDate);
			dto.setCreatorId(creator);
			super.insert(entityToDtoMapper.map(dto, Role.class));
		}		
		return entityToDtoMapper.map(super.selectById(dto.getFdId()), RoleDto.class);		
	}
	
	//逻辑删除
	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		for(String fd:id){
			Long fdId=Long.parseLong(fd);
			Role role=super.selectById(fd);
			if(role==null){
				Assert.throwException("找不到该角色");
			}
			Role entity=new Role();
			entity.setFdId(fdId);
			entity.setState(2);
			super.updateById(entity);
		}
		return "删除成功";
	}
	
	@Override
	public List<RoleDto> findAll() {
//		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
//		wraUser.eq("user_type", "ADMIN");
//		wraUser.eq("state", 1);
//		wraUser.eq("locked", 2);
//		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
//		UserInfo userInfo=userInfoService.selectFirst(wraUser);
//		if(userInfo==null){
//			List<RoleDto> list1=new ArrayList<>();
//			return list1;
//		}
//		System.err.println(UserUtils.getUserInfo());
		Wrapper<Role> wrapper = new CriterionWrapper<>(Role.class);
		wrapper.eq("state", 1);
		List<Role> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RoleDto.class);
	}
	
	@Override
	public PageDto<RoleDto> findByPage(int pageNum, int pageSize) throws CustomException {
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("user_type", "ADMIN");
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			PageDto<RoleDto> lia = new PageDto<RoleDto>();
			return lia;
		}
		PageDto<RoleDto> pageDto = new PageDto<RoleDto>();
		pageDto.setPage(pageNum + 1);
		pageDto.setPageSize(pageSize);
		Wrapper<Role> wrapper = new CriterionWrapper<Role>(Role.class);
		wrapper.eq("state", 1);
		Page<Role> page = super.selectPage(new Page<Role>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RoleDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
