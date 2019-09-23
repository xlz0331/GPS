package com.hwagain.eagle.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.JwtUtil;

import io.jsonwebtoken.Claims;

public class AuthorizationInterceptor implements HandlerInterceptor {
	@Autowired private JwtUtil jwtUtil;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 String token = request.getHeader("X-Auth-Token");
		 System.err.println(token);
	        if (StringUtils.isEmpty(token)) {
	            if (request.getCookies() != null) {
	                for (Cookie cookie : request.getCookies()) {
	                    if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
	                        token = cookie.getValue();
	                    }
	                }
	            }
	        }else{
	        	System.err.println(token);
	        	Claims infoFromToken = jwtUtil.parseJWT(token);
	 	        BaseContextHandler.setUsername(infoFromToken.getAudience());
	 	        BaseContextHandler.setUserID(infoFromToken.getId());
	 	        BaseContextHandler.setToken(token);
	        }
	       
//	        return super.preHandle(request, response, handler);
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
