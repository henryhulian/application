package com.rest.server.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class CaptchaConfig {

	@Bean
	public DefaultKaptcha defaultKaptcha(){
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.image.width","90");
		properties.setProperty("kaptcha.image.height","50");
		properties.setProperty("kaptcha.textproducer.char.string","0123456789");
		properties.setProperty("kaptcha.textproducer.char.length","4");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
