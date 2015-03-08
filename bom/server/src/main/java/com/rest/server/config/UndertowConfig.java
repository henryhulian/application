package com.rest.server.config;

import io.undertow.Undertow.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.xnio.Options;

import com.rest.server.interceptor.SecurityInterceptor;

@Configuration
@EnableWebMvc
@Profile("HttpServer")
public class UndertowConfig extends WebMvcAutoConfigurationAdapter {
	
	@Autowired
	Environment env;

	@Autowired
	SecurityInterceptor securityInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
		super.addInterceptors(registry);

	}
	

	@Bean
	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();

		factory.setContextPath(env.getProperty("context.path"));

		factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

			@Override
			public void customize(Builder build) {
				
				Integer maxWorker =  env.getProperty("io.worker.http.max",Integer.class,Runtime.getRuntime().availableProcessors() + 1);
				
				build.setWorkerOption(Options.WORKER_IO_THREADS, env.getProperty("io.core",Integer.class,Runtime.getRuntime().availableProcessors() + 1));
				build.setWorkerOption(Options.WORKER_TASK_CORE_THREADS,
						maxWorker / 2);
				build.setWorkerOption(Options.WORKER_TASK_MAX_THREADS,
						maxWorker);
			}

		});
		return factory;
	}


}