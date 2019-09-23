package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.DriverInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.hwagain.eagle.base.dto.DriverInfoDto;
import com.hwagain.eagle.base.mapper.DriverInfoMapper;
import com.hwagain.eagle.base.service.IDriverInfoService;
import com.hwagain.eagle.config.LogAspect;
import com.hwagain.eagle.role.dto.RoleDto;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.entity.SupplierInfo;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.user.dto.UserInfoDto;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.framework.api.category.dto.languageDto;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.hwagain.util.PageDto;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.b;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-25
 */
@Slf4j
@Service("driverInfoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DriverInfoServiceImpl extends ServiceImpl<DriverInfoMapper, DriverInfo> implements IDriverInfoService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired BaseContextHandler baseContextHandler;
	@Autowired IUserInfoService userInfoService;
	@Autowired ISupplierInfoService supplierInfoService;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(DriverInfo.class, DriverInfoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(DriverInfoDto.class, DriverInfo.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	private void validate(DriverInfoDto entity){
//		Assert.notBlank(entity.getSupplierName(), "供应商名称不能为空");
		Assert.notBlank(entity.getDriverName(), "司机名称不能为空");
//		Assert.notNull(entity.getAge(), "司机年龄不能为空");
		Assert.notNull(entity.getMobile(), "手机号不能为空");
		Assert.notBlank(entity.getSex(), "性别不能为空");
		Assert.notBlank(entity.getLicenseType(), "驾照类型不能为空");
//		Assert.notNull(entity.getLicenseEndDate(), "驾照有效期不能为空");
		Assert.notBlank(entity.getLicenseId(), "驾照编号不能为空");
	}
	
	//根据供应商ID获取司机列表
	@Override
	public List<DriverInfoDto> getDriverBySupplierId(){
		Long supplierId=Long.valueOf(String.valueOf(baseContextHandler.getSupplierId()));
		Wrapper<DriverInfo> wrapper=new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.eq("supplier_id", supplierId);
		wrapper.eq("state", 1);
		List<DriverInfo> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, DriverInfoDto.class);		
	}
	
	//根据供应商ID获取司机列表
	@Override
	public List<DriverInfoDto> findAllBySupplier(String mobile,String driverName){
		Wrapper<DriverInfo> wrapper=new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.like("mobile", mobile,SqlLike.DEFAULT);
		wrapper.like("driver_name", driverName,SqlLike.DEFAULT);
		wrapper.notIn("state", 2);
		wrapper.eq("supplier_name", baseContextHandler.getUsername());
		List<DriverInfo> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, DriverInfoDto.class);
	}
	
	//查询
	@Override
	public List<DriverInfoDto> findAll(String mobile,String driverName){
		Wrapper<DriverInfo> wrapper=new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.like("mobile", mobile,SqlLike.DEFAULT);
		wrapper.like("driver_name", driverName,SqlLike.DEFAULT);
		wrapper.notIn("state", 2);
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("driver_name");
		List<DriverInfo> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, DriverInfoDto.class);
	}
	
	/**
	 * 分页查询
	 * params:mobile,driverName
	 */
	@Override
	public PageDto<DriverInfoDto> findByPagea(int pageNum, int pageSize,String mobile,String driverName) throws CustomException {
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("user_type", "USERADMIN");
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			PageDto<DriverInfoDto> lia = new PageDto<DriverInfoDto>();
			return lia;
		}
		PageDto<DriverInfoDto> pageDto = new PageDto<DriverInfoDto>();
		pageDto.setPage(pageNum + 1);
		pageDto.setPageSize(pageSize);
		Wrapper<DriverInfo> wrapper = new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.eq("state", 1);
		wrapper.eq("mobile", mobile);
		Page<DriverInfo> page = super.selectPage(new Page<DriverInfo>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), DriverInfoDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	
	//分页查询
	@Override
	public PageDto<DriverInfoDto> findByPage(DriverInfoDto driverInfoDto, int pageNum, int pageSize) throws CustomException {
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("user_type", "USERADMIN");
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			PageDto<DriverInfoDto> lia = new PageDto<DriverInfoDto>();
			return lia;
		}
		PageDto<DriverInfoDto> pageDto = new PageDto<DriverInfoDto>();
		pageDto.setPage(pageNum + 1);
		pageDto.setPageSize(pageSize);
		Wrapper<DriverInfo> wrapper = new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.like("mobile", driverInfoDto.getMobile(), SqlLike.DEFAULT);
		wrapper.eq("state", driverInfoDto.getState());
		wrapper.like("driver_name", driverInfoDto.getDriverName(), SqlLike.DEFAULT);
		Page<DriverInfo> page = super.selectPage(new Page<DriverInfo>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), DriverInfoDto.class));
		System.err.println(pageDto.getList().size());
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	
	//新增
	@Override
	public DriverInfoDto addOne(DriverInfoDto dto){
		Wrapper<UserInfo> wraUser1=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser1.eq("user_type", "ADMIN");
		wraUser1.eq("state", 1);
		wraUser1.eq("locked", 2);
		UserInfo userInfo1=userInfoService.selectFirst(wraUser1);
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("user_type", "USERADMIN");
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			Assert.throwException("您没有权限添加司机账号,请联系系统管理员"+userInfo1.getLoginname());
		}
		validate(dto);
		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());	
		Wrapper<DriverInfo> wrapper=new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.eq("mobile", dto.getMobile());
		wrapper.eq("state", 1);
		DriverInfo list=super.selectFirst(wrapper);
		if(dto.getFdId()!=null){
			list=super.selectById(dto.getFdId());
		}
		if(list!=null){
			Assert.throwException(list.getMobile()+"账号已存在，不可重复添加");
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
			dto.setState(1);
			dto.setCreateTime(new Date());
			dto.setCreatorId(creatorId);
			super.insert(dtoToEntityMapper.map(dto, DriverInfo.class));			
		}
		UserInfoDto usDriver=new UserInfoDto();
		usDriver.setLoginname(dto.getDriverName());
		usDriver.setMobile(dto.getMobile());
		usDriver.setUserType("DRIVERA");
		usDriver.setState(dto.getState());
		usDriver.setCreateTime(dto.getCreateTime());
		userInfoService.addNowUser(usDriver);
		return entityToDtoMapper.map(dto, DriverInfoDto.class);
	}
	
	//删除
	@Override
	public String deleteByIds(String ids){
		if(ids.isEmpty()){
			Assert.throwException("未选中要删除的行");
		}
		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
		String[] id = ids.split(";");
		for(String fdId:id){
			DriverInfo driver=super.selectById(fdId);
			driver.setState(2);
			driver.setLastAltorId(creatorId);
			driver.setLastAlterTime(new Date());
			super.updateById(driver);
			userInfoService.deletOne(driver.getDriverName(), driver.getMobile(),"DRIVERA");
		}
		return ids;
		
	}
	
	 //定时刷新账号状态（判断是否失效）
    @Override
    public String  updateUserState(){
    	Wrapper<DriverInfo> wrapper=new CriterionWrapper<DriverInfo>(DriverInfo.class);
    	wrapper.eq("state", 1);
    	List<DriverInfo> list=super.selectList(wrapper);
    	for(DriverInfo user:list){
    		if(user.getLastAlterTime()!=null){
    			Date dt1=null;
        		Date dt2=null;
        		Calendar calendar = Calendar.getInstance();
        		calendar.setTime(user.getLastAlterTime());
        		calendar.add(Calendar.DAY_OF_YEAR,1);
        		if(calendar.getTime().before(new Date())){
        			user.setState(0);
        			super.updateById(user);
        		}
    		}
    	}
    	System.err.println("刷新DriverInfo状态完成");
		return "状态更新成功";    	
    }
    
    //新增司机账号
    @Override
    public String addDriver(DriverInfo dto){
    	String loginName=String.valueOf(baseContextHandler.getUsername());
		Wrapper<SupplierInfo> wraSupplier=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
		wraSupplier.eq("name", loginName);
		wraSupplier.eq("state", 1);
		SupplierInfo supplierInfo=supplierInfoService.selectFirst(wraSupplier);
		if(supplierInfo==null){
			Assert.throwException("您没有权限添加司机账号。");
		}
		validate(entityToDtoMapper.map(dto,DriverInfoDto.class));
		Wrapper<DriverInfo> wrapper=new CriterionWrapper<DriverInfo>(DriverInfo.class);
		wrapper.eq("mobile", dto.getMobile());
		wrapper.eq("state",1);
		DriverInfo list=super.selectOne(wrapper);
		System.err.println(list);
		if(list!=null){	
			log.info(dto.getMobile()+"的账号已注册，不可重复注册");
			Assert.throwException(dto.getMobile()+"的账号已注册，不可重复注册");
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
			dto.setSupplierId(supplierInfo.getFdId());
			dto.setSupplierName(supplierInfo.getName());
			dto.setCreatorId(supplierInfo.getFdId());
			dto.setCreateTime(new Date());
			super.insert(dtoToEntityMapper.map(dto, DriverInfo.class));
			UserInfoDto usDriver=new UserInfoDto();
			usDriver.setLoginname(dto.getDriverName());
			usDriver.setMobile(dto.getMobile());
			usDriver.setUserType("DRIVERA");
			usDriver.setState(dto.getState());
			usDriver.setCreateTime(dto.getCreateTime());
			usDriver.setCreatorId(dto.getCreatorId());
			userInfoService.addNowUser(usDriver);
		}
		return "新增【"+dto.getDriverName()+"】账号成功";   	
    }
}
