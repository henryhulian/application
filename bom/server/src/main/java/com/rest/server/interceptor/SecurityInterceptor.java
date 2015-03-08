package com.rest.server.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rest.server.entity.Session;
import com.rest.server.service.Authorization;
import com.rest.server.service.SessionService;
import com.rest.server.util.CookieUtil;
import com.rest.server.util.IpUtil;
import com.rest.server.util.TokenUtil;



@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter{
	
	private static final Log log = LogFactory.getLog(SecurityInterceptor.class);
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private Authorization authorization;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if (handler instanceof HandlerMethod) {
		    HandlerMethod method = (HandlerMethod) handler;
		    if (method.getMethod().isAnnotationPresent(RolesAllowed.class)) {
		       
		    	 //detect token
	            // if cann't find token return
	            String token = CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE);
	            if( StringUtils.isEmpty(token) ){
	            	 log.warn("Token is empty!");
	            	 response.sendError(401);
	                 return false;
	            }
	            
	            //find session information
                //if cann't find session return
	            Session session = sessionService.findSessionByToken(token);
                if( session == null ){
                	 log.warn("Session is null fro token:"+token);
                	 response.sendError(401);
	                 return false;
                }
                
                //check session status
                if( session.getStatus() == null || session.getStatus() != Session.STATUS_LOGIN ){
                	log.warn("Session status is not login "+session.getStatus()+" for user:"+session.getUserName());
               	 	response.sendError(401);
               	 	return false;
               }
            	
                //check ip
	            if( session.getSessionIp()!=null && !IpUtil.getIp(request).split(",")[0].equals(session.getSessionIp().split(",")[0])){
	            	log.warn("Session ip:"+session.getSessionIp()+" not match client ip:"+IpUtil.getIp(request)+" for user:"+session.getUserName());
	            	 response.sendError(403);
	                 return false;
	            }
	             
	           
	            //Verify user access
	            RolesAllowed rolesAnnotation = method.getMethodAnnotation(RolesAllowed.class);
	            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
	            
	            if( !authorization.isUserAllowed(session.getUserId(), rolesSet)){
	            	 log.warn("User:"+session.getUserName()+" do not has in role:"+rolesSet);
	            	 response.sendError(403);
	                 return false;
	            }
	            
		    }
		}
		
		return super.preHandle(request, response, handler);
	}
}
