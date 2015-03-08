package com.rest.server.config;

import java.io.IOException;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableCaching
public class CacheConfig {

	@Autowired
	private Environment env;

	@Bean
	public CacheManager cacheManager() {

		CacheManager cacheManager;

		try {
			DefaultCacheManager defaultCacheManager = null;
			if (env.getProperty("cache.infinispan.config.enable", Boolean.class,true) == true) {
				defaultCacheManager = new DefaultCacheManager("config/infinispan.xml");
			} else {
				defaultCacheManager = new DefaultCacheManager();
			}
			defaultCacheManager.getCache();
			cacheManager = new SpringEmbeddedCacheManager(defaultCacheManager);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return cacheManager;
	}

}
