package com.rest.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.rest")
@EntityScan("com.rest")
public class RepositoryConfig {

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {

		BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
		boneCPDataSource.setDriverClass(env.getProperty("spring.datasource.driverClassName"));
		boneCPDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		boneCPDataSource.setUsername(env.getProperty("spring.datasource.username"));
		boneCPDataSource.setPassword(env.getProperty("spring.datasource.password"));
		boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
		boneCPDataSource.setIdleMaxAgeInMinutes(420);
		boneCPDataSource.setMaxConnectionsPerPartition(env.getProperty("cp.connection.max",Integer.class));
		boneCPDataSource.setMinConnectionsPerPartition(10);
		boneCPDataSource.setPartitionCount(3);
		boneCPDataSource.setAcquireIncrement(5);
		boneCPDataSource.setStatementsCacheSize(100);
		boneCPDataSource.setDisableConnectionTracking(true);
		
		return boneCPDataSource;

	}


	/*@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(Boolean.parseBoolean(env.getProperty("spring.jpa.show-sql")));
		vendorAdapter
				.setDatabasePlatform(env.getProperty("spring.jpa.database-platform"));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.springrest.restserver.domain");
		factory.setDataSource(dataSource());

		Properties properties = new Properties();
		properties
				.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties
		.setProperty("hibernate.cache.provider_class", "true");
		properties.setProperty("hibernate.cache.provider_class",
				"net.sf.ehcache.hibernate.SingletonEhCacheProvider");
		
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "false");

		factory.setJpaProperties(properties);

		factory.afterPropertiesSet();

		return factory.getObject();
	}
	

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		JpaDialect jpaDialect = new HibernateJpaDialect();
		txManager.setEntityManagerFactory(entityManagerFactory());
		txManager.setJpaDialect(jpaDialect);
		return txManager;
	}*/
}
