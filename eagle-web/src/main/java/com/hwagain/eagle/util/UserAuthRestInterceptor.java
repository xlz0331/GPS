package com.hwagain.eagle.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.base.dto.LogLoginDto;
import com.hwagain.eagle.base.entity.LogLoginUser;
import com.hwagain.eagle.base.service.ILogLoginUserService;
import com.hwagain.eagle.interceptor.AuthorizationInterceptor;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;

import io.jsonwebtoken.Claims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Catkic
 * @2019/2/17 14:30
 * @description:
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired JwtUtil jwtUtil;
	@Autowired ILogLoginUserService logLoginUserService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler) {
            response.setStatus(404);
            throw new ResourceAccessException("请求资源不存在" + request.getContextPath());
        }
//        System.err.println(request.getQueryString());
//        log.info("<调用接口>"+handler);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明进行用户拦截
        IgnoreUserToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreUserToken.class);
       
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(IgnoreUserToken.class);
        }
        
        if (annotation == null) { 
//        	System.err.println("401");
            return super.preHandle(request, response, handler);
        }else{
            String token = request.getHeader("X-Auth-Token");
//            System.err.println(token);
            if (StringUtils.isEmpty(token)) {
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                            token = cookie.getValue();
                        }
                    }
                }
            }
           if(StringUtils.isEmpty(token)){
        	   log.error("没有登陆，或者token失效\n");
        	   Assert.throwException("没有登陆，或者token失效");
        	   return false;
           }else{
        	   try {
        		   jwtUtil.parseJWT(token);
               } catch (Exception e) {
            	   log.error("\n token失效\n");
            	   Assert.throwException("token已失效");
               }
//	        	HttpSession session = request.getSession();
//	           	System.err.println(request.getRequestURI());
//	           	System.err.println(session.getAttribute("parentId"));
//	           	Long userId=Long.parseLong(String.valueOf(session.getAttribute("userId")));
//	           	Long parentId=Long.parseLong(String.valueOf(session.getAttribute("parentId")));
//	           	String userName=String.valueOf(session.getAttribute("loginName"));
//	          
	        	   Claims info = jwtUtil.parseJWT(token);
	        	   String userName1=String.valueOf(info.get("Loginname"));
	        	   System.err.println("userName1"+userName1);
	        	   String plateNumber=String.valueOf(info.get("PlateNumber"));
	        	   String mobile=String.valueOf(info.get("Mobile"));
	        	   Long parentId=Long.valueOf(String.valueOf(info.get("ParentId")));
	        	   Long userId=Long.parseLong(info.getId());
	    	       BaseContextHandler.setUsername(userName1);
	    	       BaseContextHandler.setUserID(info.getId());
	    	       BaseContextHandler.setToken(token);
	    	       BaseContextHandler.setMobile(mobile);
	    	       BaseContextHandler.setUserType(info.getSubject());
	    	       BaseContextHandler.setPlateNumber(plateNumber);
	    	       BaseContextHandler.setSupplierId(parentId);
	    	       Wrapper<LogLoginUser> wrapper=new CriterionWrapper<LogLoginUser>(LogLoginUser.class);
		           wrapper.eq("user_id",userId);
		           LogLoginUser userlist=logLoginUserService.selectFirst(wrapper);
		           if(userlist!=null){
		           	   userlist.setState(1);
		           	   userlist.setLastAlterTime(new Date());
		           	   userlist.setLastAltorId(userId);
		           	   logLoginUserService.updateById(userlist);
		           }else{
		        	   LogLoginUser user=new LogLoginUser();
		           	   user.setFdId(Long.valueOf(IdWorker.getId()));
		           	   user.setUserId(userId);
		           	   user.setParentId(parentId);
		           	   user.setUserName(userName1);
		           	   user.setCreateTime(new Date());
		           	   user.setCreatorId(userId);
		           	   user.setState(1);
		           	   user.setLastAlterTime(new Date());
		           	   user.setLastAltorId(userId);
		           	   logLoginUserService.insert(user);
		           	}
	           }      
        	}      
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
