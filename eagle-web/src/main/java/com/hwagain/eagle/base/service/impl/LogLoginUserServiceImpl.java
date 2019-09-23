package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.LogLoginUser;
import com.alibaba.druid.support.logging.Log;
import com.hwagain.eagle.base.dto.LogLoginUserDto;
import com.hwagain.eagle.base.mapper.LogLoginUserMapper;
import com.hwagain.eagle.base.service.ILogLoginUserService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-21
 */
@Configurable
@EnableScheduling
@Component
@Service("logLoginUserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogLoginUserServiceImpl extends ServiceImpl<LogLoginUserMapper, LogLoginUser> implements ILogLoginUserService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(LogLoginUser.class, LogLoginUserDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(LogLoginUserDto.class, LogLoginUser.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//判断用户是否在线
//	@Scheduled(cron = "0 0 1 ? * *") // 每天1点执行-首先执行
	@Scheduled(cron = "0 */2 * * * ?") // 5秒一次【 测试用】
	@Override
	public List<LogLoginUser> findAll(){
		Date doDate=new Date();
		Wrapper<LogLoginUser> wrapper=new CriterionWrapper<LogLoginUser>(LogLoginUser.class);
		wrapper.eq("state", 1);
		List<LogLoginUser> list=super.selectList(wrapper);
		if(list.size()>0){
			for(LogLoginUser user:list){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				Date dt1=null;
				Date dt2=null;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(user.getLastAlterTime());
				calendar.add(Calendar.MINUTE,10);
				calendar.getTime();
				System.err.println("||"+calendar.getTime()+"||"+new Date());
				String nowTime = df.format(doDate);
				String sessionTime = df.format(calendar.getTime());
				if(new Date().after(calendar.getTime())){
					user.setState(2);
					System.err.println(">>>>>>>>>>..."+doDate);
					super.updateById(user);
				}
			}
		}
		return list;		
	}
	
	//查询
	@Override
	public List<LogLoginUser> findByType(Integer state){
		Wrapper<LogLoginUser> wrapper=new CriterionWrapper<LogLoginUser>(LogLoginUser.class);
		wrapper.eq("state", state);
		List<LogLoginUser> list=super.selectList(wrapper);
		return list;		
	}
	
	//删除
	@Override
	public String  deleteByFdId(Long fdId){
		LogLoginUser logLoginUser=super.selectById(fdId);
		if(logLoginUser!=null){
			super.deleteById(fdId);
		}
		return "删除成功";		
	}
	
	//查询所有在途司机（是否在线）
	@Override
	public List<LogLoginUserDto> findAllTransport(){
		Wrapper<LogLoginUser> wrapper=new CriterionWrapper<LogLoginUser>(LogLoginUser.class);
		wrapper.eq("parent_id", 1);
		List<LogLoginUser> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, LogLoginUserDto.class);
		
	}
}
