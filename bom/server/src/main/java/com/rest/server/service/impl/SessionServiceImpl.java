package com.rest.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rest.server.entity.Session;
import com.rest.server.repository.SessionRepository;
import com.rest.server.service.SessionService;
import com.rest.server.util.AESUtil;
import com.rest.server.util.CookieUtil;
import com.rest.server.util.IpUtil;
import com.rest.server.util.TokenUtil;


@Service
@Transactional
public class SessionServiceImpl implements SessionService{
	
	private static final Log log = LogFactory.getLog(SessionServiceImpl.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	public Session createUserSession( String userName , Long userId , HttpServletRequest request , HttpServletResponse response ) {
		
		List<Session> sessions = sessionRepository.findSessionByUserId(userId);

		//创建Session
		Session session=null;
		if( sessions!=null && sessions.size()>0 ){
			session=sessions.get(0);
		}else{
			session = new Session();
		}
		session.setUserName(userName);
		session.setUserId(userId);
		session.setStatus(Session.STATUS_LOGIN);
		session.setSessionIp(IpUtil.getIp(request));
		try {
			session.setSessionSign(AESUtil.encrypt(
					String.valueOf(session.getId())+":"+System.currentTimeMillis(),
					env.getProperty("session.key")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		sessionRepository.save(session);
		
		//设置cookie
		CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE,
						session.getSessionSign(), request.getContextPath(), true, -1);

		return session;
	}
	

	@Override
	public Session findSessionByToken(String token) {
		String sessionId = null;
		try {
			
			String sessionInfo = AESUtil.decrypt(token,env.getProperty("session.key"));
			sessionId = sessionInfo.split(":")[0];
			
		} catch (Exception e) {
			log.error(e);
		}
		
		if(StringUtils.isEmpty(sessionId)){
			return null;
		}
		
		Session session = sessionRepository.findOne(sessionId);
		
		if(!session.getSessionSign().contains(token)){
			log.warn("token:"+token+" invalid , new token:"+session.getSessionSign());
			return null;
		}

		return session;
	}
	
	
	@Override
	public void logoutSessionByToken(String token) {
		Session session = findSessionByToken(token);
		if( session != null ){
			session.setStatus(0);
			sessionRepository.save(session);
		}
	}


	@Override
	public List<Session> findSessionsByUserId(Long userId) {
		return sessionRepository.findSessionByUserId(userId);
	}


}
