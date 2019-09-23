package com.hwagain.eagle.supplier.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.Stack;
import com.hwagain.eagle.base.dto.DriverInfoDto;
import com.hwagain.eagle.base.entity.DriverInfo;
import com.hwagain.eagle.role.entity.Role;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.entity.SupplierInfo;
import com.hwagain.eagle.supplier.mapper.SupplierInfoMapper;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.user.dto.UserInfoDto;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.framework.api.org.api.ISysOrgService;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.util.PageDto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.w;

/**
 * <p>
 * 供应商信息表 服务实现类
 * </p>
 *
 * @author lufl
 * @since 2019-02-22
 */
@Service("supplierInfoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService {
	
	@Autowired
	private SupplierInfoMapper supplierInfoMapper;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
//	@Autowired BaseContextHandler baseContextHandler;
	@Autowired IUserInfoService userInfoService;
	@Autowired ISysOrgService sysOrgService;
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(SupplierInfo.class, SupplierInfoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(SupplierInfoDto.class, SupplierInfo.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Override
	public String save(SupplierInfoDto supplierInfoDto) {
		validate(supplierInfoDto);
		SupplierInfo entity = dtoToEntityMapper.map(supplierInfoDto, SupplierInfo.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(SupplierInfoDto supplierInfoDto) {
		validate(supplierInfoDto);
		SupplierInfo entity = dtoToEntityMapper.map(supplierInfoDto, SupplierInfo.class);
		entity.setLastAlterTime(new Date());
		super.updateById(entity);
		return "修改成功";
	}

	@Override
	public String delete(String fdId) {
		SupplierInfo entity = new SupplierInfo();
		entity.setFdId(Long.parseLong(fdId));
		entity.setState(2);
		super.updateById(entity);
		return "删除成功";
	}

	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		for(String fd:id){
			Long fdId=Long.parseLong(fd);
			SupplierInfo role=super.selectById(fd);
			if(role==null){
				Assert.throwException("找不到该供应商");
			}
			SupplierInfo entity=new SupplierInfo();
			entity.setFdId(fdId);
			entity.setState(2);
			entity.setLastAlterTime(new Date());
			entity.setLastAltorId(Long.valueOf(UserUtils.getUserInfo().getFdId()));
			super.updateById(entity);
			userInfoService.deletOne(role.getName(), role.getMobile(),"SUPPLIER");
		}
		return "删除成功";
	}

	@Override
	public List<SupplierInfoDto> findAll() {
//		Wrapper<SupplierInfo> wrinfo=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
//		List<SupplierInfo> supplierInfos=super.selectList(wrinfo);
//		int n=supplierInfos.size();
//		int x;
//		int num = 0;
//		Stack<Integer> stack = new Stack<>();
//        while(n>0){
//            x=n%8;
//            stack.push(x);
//            n/=8;
//        }
//        while(!stack.isEmpty()){
//            num = stack.pop();
//            System.err.format("%d",num);
//        }
		Wrapper<SupplierInfo> wrapper = new CriterionWrapper<>(SupplierInfo.class);
		wrapper.notIn("state", 2);
		List<SupplierInfo> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, SupplierInfoDto.class);
	}

	@Override
	public SupplierInfo findOne(String fdId) {
		return super.selectById(fdId);
	}

	private void validate(SupplierInfoDto supplierInfoDto) {
		Assert.notBlank(supplierInfoDto.getName(), "供应商名称不能为空");
//		Assert.notBlank(supplierInfoDto.getLegalPerson(), "法人不能为空");
//		Assert.notBlank(supplierInfoDto.getLegalPersonIdCardNo(), "法人身份证号码不能为空");
//		Assert.notBlank(supplierInfoDto.getDutyParagraph(), "税号不能为空");
//		Assert.notBlank(supplierInfoDto.getContact(), "联系电话不能为空");
		Assert.notBlank(supplierInfoDto.getMobile(), "手机号不能为空");
		Assert.notBlank(supplierInfoDto.getCode(), "供应商类型不能为空");
//		Assert.notNull(supplierInfoDto.getRegionId(), "所属区域ID不能为空");
//		Assert.notBlank(supplierInfoDto.getBankName(), "开户行名称不能为空");
//		Assert.notBlank(supplierInfoDto.getBankAccount(), "开户行账号不能为空");
//		Assert.notBlank(supplierInfoDto.getBankAccountName(), "开户行户名不能为空");
//		Assert.notBlank(supplierInfoDto.getBankAddress(), "开户行户名不能为空");
//		Assert.notNull(supplierInfoDto.getState(), "状态不能为空");
		if (supplierInfoDto.getFdId() != null) {
//			Assert.notNull(supplierInfoDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
//			Assert.notNull(supplierInfoDto.getCreatorId(), "创建人ID不能为空");
		}
	}
	
	@Override
	public PageDto<SupplierInfoDto> findByPage(int pageNum, int pageSize,String name,String mobile) throws CustomException {
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("user_type", "USERADMIN");
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			PageDto<SupplierInfoDto> lia = new PageDto<SupplierInfoDto>();
			return lia;
		}
		PageDto<SupplierInfoDto> pageDto = new PageDto<SupplierInfoDto>();
		pageDto.setPage(pageNum + 1);
		pageDto.setPageSize(pageSize);
		Wrapper<SupplierInfo> wrapper = new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
		wrapper.eq("state", 1);
//		wrapper.eq("name", name);
//		wrapper.eq("mobile", mobile);
		wrapper.like("mobile", mobile, SqlLike.DEFAULT);
		wrapper.like("name", name, SqlLike.DEFAULT);
		wrapper.orderBy("code");
		Page<SupplierInfo> page = super.selectPage(new Page<SupplierInfo>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), SupplierInfoDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	
	@Override
	public SupplierInfoDto addOne(SupplierInfoDto dto){
//		Wrapper<UserInfo> wraUser1=new CriterionWrapper<UserInfo>(UserInfo.class);
//		wraUser1.eq("user_type", "ADMIN");
//		wraUser1.eq("state", 1);
//		wraUser1.eq("locked", 2);
//		UserInfo userInfo1=userInfoService.selectFirst(wraUser1);
//		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
//		wraUser.eq("user_type", "USERADMIN");
//		wraUser.eq("state", 1);
//		wraUser.eq("locked", 2);
//		wraUser.eq("loginname", UserUtils.getUserInfo().getName());
//		UserInfo userInfo=userInfoService.selectFirst(wraUser);
//		if(userInfo==null){
//			Assert.throwException("您没有权限添加司机账号,请联系系统管理员"+userInfo1.getLoginname());
//		}
//		
		if(dto.getMobile().equals("null")){
			dto.setMobile("");
		}
		Wrapper<SupplierInfo> wrinfo=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
		List<SupplierInfo> supplierInfos=super.selectList(wrinfo);
		int n=supplierInfos.size();
		int x;
		int num = 0;
		int i=0;
		String num1="";
		Stack<Integer> stack = new Stack<>();
        while(n>0){
            x=n%8;
            stack.push(x);
            n/=8;
        }
        while(!stack.isEmpty()){
        	i++;
            num = stack.pop();
            num1=num1+String.valueOf(num);
            System.err.println(num1);
        }
        if(i==3){
        	num1="0"+num1;
        }
        if(i==2){
        	num1="00"+num1;
        }
        if(i==1){
        	num1="000"+num1;
        }
        if(i==0){
        	num1="0000";
        }
        String code="S"+num1;
        dto.setCode(code);
//        validate(dto);
//		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
//		Date dt1=null;
//		Date dt2=null;
		if(dto.getLastAlterTime()!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dto.getLastAlterTime());
			calendar.add(Calendar.DAY_OF_YEAR,1);
			calendar.getTime();
			if(new Date().after(dto.getCreateTime())&&new Date().before(calendar.getTime())){
				dto.setState(1);
			}else{
				dto.setState(0);
			}		
		}else{
			dto.setState(1);
		}
		Wrapper<SupplierInfo> wrapper=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
		wrapper.eq("name", dto.getName());
//		wrapper.eq("mobile", dto.getMobile());
//		wrapper.eq("state", 1);
		SupplierInfo list=super.selectFirst(wrapper);
		if(list!=null){
			dto.setFdId(list.getFdId());
			dto.setCode(list.getCode());
//			dto.setCreatorName(creatorName);
//			dto.setLastAlterTime(new Date());
//			dto.setLastAltorId(creatorId);
			super.updateById(dtoToEntityMapper.map(dto, SupplierInfo.class));
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
//			dto.setCreateTime(new Date());
//			dto.setCreatorId(creatorId);
//			dto.setCreatorName(creatorName);
			super.insert(dtoToEntityMapper.map(dto, SupplierInfo.class));			
		}
		UserInfoDto usDriver=new UserInfoDto();
		usDriver.setLoginname(dto.getName());
		usDriver.setMobile(dto.getMobile());
		usDriver.setUserType("SUPPLIER");
		usDriver.setState(dto.getState());
		if(dto.getCreateTime()==null){
			usDriver.setCreateTime(new Date());
//			usDriver.setLastAlterTime(dto.getLastAlterTime());
		}else{
			usDriver.setCreateTime(dto.getCreateTime());
			usDriver.setLastAlterTime(dto.getLastAlterTime());
		}
		userInfoService.addNowUser(usDriver);
		return entityToDtoMapper.map(dto, SupplierInfoDto.class);
	}
	@Override
	public String addSupplier(SupplierInfo entity){
		validate(entityToDtoMapper.map(entity, SupplierInfoDto.class));
		Wrapper<SupplierInfo> wrapper=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
		wrapper.eq("mobile", entity.getMobile());
		wrapper.eq("state", 1);
		SupplierInfo supplierInfo=selectOne(wrapper);
		if(supplierInfo!=null){
			Assert.throwException(entity.getMobile()+"账号已存在");
		}
		entity.setFdId(IdWorker.getId());
		entity.setCreateTime(new Date());
		entity.setCreatorId(Long.valueOf(UserUtils.getUserInfo().getFdId()));
		super.insert(entity);
		UserInfoDto usDriver=new UserInfoDto();
		usDriver.setLoginname(entity.getName());
		usDriver.setMobile(entity.getMobile());
		usDriver.setUserType("SUPPLIER");
		usDriver.setState(entity.getState());
		usDriver.setCreateTime(new Date());
		userInfoService.addNowUser(usDriver);
		return "新增供应商成功";
		
	}
	 //定时刷新账号状态（判断是否失效）
    @Override
    public String  updateUserState(){
    	Wrapper<SupplierInfo> wrapper=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
    	wrapper.eq("state", 1);
    	List<SupplierInfo> list=super.selectList(wrapper);
    	for(SupplierInfo user:list){
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
    	System.err.println("刷新SupplierInfo状态完成");
		return "状态更新成功";    	
    }
    
    @Override
    public SupplierInfoDto findByName(String supplierName){
    	Wrapper<SupplierInfo> wrapper=new CriterionWrapper<SupplierInfo>(SupplierInfo.class);
		wrapper.eq("name",supplierName);
		wrapper.eq("state", 1);
		SupplierInfo list=super.selectFirst(wrapper);
		return entityToDtoMapper.map(list, SupplierInfoDto.class);
    }

	@Override
	public List<SupplierInfoDto> findAllByName(String name,String mobile) {
		Wrapper<SupplierInfo> wrapper = new CriterionWrapper<>(SupplierInfo.class);
		wrapper.like("mobile", mobile, SqlLike.DEFAULT);
		wrapper.like("name", name, SqlLike.DEFAULT);
		wrapper.eq("state", 1);
		wrapper.orderBy("code");
		wrapper.orderBy("name");
		List<SupplierInfo> list = super.selectList(wrapper);
		List<SupplierInfoDto> listA=entityToDtoMapper.mapAsList(list, SupplierInfoDto.class);
		List<SupplierInfoDto> listB=new ArrayList<>();
		for(SupplierInfoDto dto:listA){
			dto.setCreator(sysOrgService.getPersonNameById(String.valueOf(dto.getCreatorId())));
			listB.add(dto);
		}
		return listB;
	}
	
	@Override
	public List<SupplierInfoDto> findAllByCode(String code){
		Wrapper<SupplierInfo> wrapper = new CriterionWrapper<>(SupplierInfo.class);
		wrapper.eq("code", code);
		wrapper.eq("state", 1);	
		wrapper.orderBy("code");
		wrapper.orderBy("name");
		List<SupplierInfo> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, SupplierInfoDto.class);		
	}
	
	//查询供应商名称跟电话号码给OA
	@Override
	public List<Map<String, String>> findAllSupplierForOA(){
		List<Map<String, String>> suppliers=new ArrayList<>();
		Wrapper<SupplierInfo> wrapper = new CriterionWrapper<>(SupplierInfo.class);
		wrapper.eq("state", 1);	
		wrapper.orderBy("code");
		wrapper.orderBy("name");
		List<SupplierInfo> list = super.selectList(wrapper);
		for(SupplierInfo sInfo:list){
			Map<String, String> suppler=new HashMap<String,String>();
			suppler.put("name", sInfo.getName());
			suppler.put("moblie", sInfo.getMobile());
			suppler.put("type", sInfo.getCode());
			suppliers.add(suppler);
		}
		return suppliers;
		
	}
}
