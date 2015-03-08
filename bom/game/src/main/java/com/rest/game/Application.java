package com.rest.game;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.rest")
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class ,DataSourceAutoConfiguration.class , WebMvcAutoConfiguration.class /*, JpaRepositoriesAutoConfiguration.class , HibernateJpaAutoConfiguration.class*/})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}