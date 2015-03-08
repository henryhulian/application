package com.rest.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.rest.server.entity.Session;


@CacheConfig(cacheNames="session")
public interface SessionService {
	
	/**
	 * 创建Session
	 * @param userName
	 * @param userId
	 * @param userIp
	 * @return
	 */
	public Session createUserSession( String userName , Long userId , HttpServletRequest request , HttpServletResponse response );
	
	@Cacheable(key="#p0")
	public Session findSessionByToken(String token);
	
	/**
	 * 根据Token登出
	 * @param token
	 */
	@CacheEvict(key="#p0")
	public void logoutSessionByToken(String token);
	
	public List<Session> findSessionsByUserId( Long userId );
	
}
