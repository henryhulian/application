package com.rest.server.service;

import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface Authorization {

	@Cacheable(value="roles",key="#p0")
	boolean isUserAllowed(final Long userId , final Set<String> rolesSet);
   
	@CacheEvict(value="roles",key="#p0")
	int authorization(final Long userId, final Set<String> rolesSet);
}
