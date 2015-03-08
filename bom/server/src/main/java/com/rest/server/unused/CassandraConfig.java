package com.rest.server.unused;
/*package com.springrest.restserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import com.datastax.driver.core.SocketOptions;

@Configuration
@EnableCassandraRepositories(basePackages = { "com.springrest.restserver.repository.order" })
public class CassandraConfig {

	@Autowired
	private Environment env;

	@Bean
	public CassandraClusterFactoryBean cluster() {

		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));
		cluster.setSocketOptions(new SocketOptions().setConnectTimeoutMillis(600000));

		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new BasicCassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {

		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(env.getProperty("cassandra.keyspace"));
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.RECREATE);

		return session;
	}

	@Bean
	public CassandraOperations cassandraTemplate() throws Exception {
		return new CassandraTemplate(session().getObject());
	}
}
*/