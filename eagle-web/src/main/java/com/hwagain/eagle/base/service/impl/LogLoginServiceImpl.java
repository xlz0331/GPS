package com.hwagain.eagle.base.service.impl;

import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.dto.LogLoginDto;
import com.hwagain.eagle.base.mapper.LogLoginMapper;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;

import bsh.StringUtil;

import java.util.Date;
import java.util.List;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 用户登录日志表 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@Service("logLoginService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Value("${sys.config.download.basePath}")
    private String DOWNLOAD_FOLDER;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(LogLogin.class, LogLoginDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(LogLoginDto.class, LogLogin.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	public void updateLogLoginUser(LogLogin logLoginUser) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public LogLoginDto addOne(LogLoginDto dto){
		super.insert(dtoToEntityMapper.map(dto, LogLogin.class));
		return dto;
		
	}

	//查询
	@Override
	public List<LogLogin> findByType(String type,Long userId,String sessionId){
		
		Wrapper<LogLogin> wrapper=new CriterionWrapper<LogLogin>(LogLogin.class);
		wrapper.eq("terminal_type", type);
		wrapper.eq("session_id", sessionId);
		wrapper.eq("user_id", userId);
		List<LogLogin> list=super.selectList(wrapper);
		if (sessionId.equals("eagleBug")) {
			for(LogLogin entity:list){
				entity.setTerminalInfo(DOWNLOAD_FOLDER+entity.getTerminalInfo());
			}
		}
		return list;
		
	}
	
	//上传app端异常log
	@Override
	public String addLog(String log){
		LogLogin logLogin=new LogLogin();
		logLogin.setFdId(Long.valueOf(IdWorker.getId()));
		logLogin.setTerminalInfo(log);
		logLogin.setSessionId("eagleBug");
		logLogin.setCreateTime(new Date());
		logLogin.setUserId(Long.valueOf(BaseContextHandler.getUserID()));
		super.insert(logLogin);
		return "异常日志上传成功";
		
	}
}
