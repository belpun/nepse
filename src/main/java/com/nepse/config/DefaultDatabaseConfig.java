package com.nepse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepse.dao.CompanyRepository;
import com.nepse.dao.GenericRepository;
import com.nepse.dao.ICompanyRepository;
import com.nepse.dao.IGenericRepository;

//
//import java.util.Properties;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.apache.commons.dbcp.DriverManagerConnectionFactory;
//import org.apache.commons.dbcp.PoolableConnectionFactory;
//import org.apache.commons.dbcp.PoolingDataSource;
//import org.apache.commons.pool.impl.GenericObjectPool;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.persistenceunit.BitaModulePersistenceUnitManager;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.bita.core.config.support.DbConfigResolver;
//import com.bita.core.jmx.DbConnectionMBean;
//import com.bita.core.model.BitaEntityModule;
//import com.bita.core.support.ConfigurableHibernatePersistence;
//import com.bita.core.support.PasswordDecryptor;
//import com.bita.experimental.StatsWrappedObjectPool;
//
@Configuration
public class DefaultDatabaseConfig {
//	@Value("#{properties['db.uri']}")
//	private String uri = "";
//	@Value("#{properties['db.username']}")
//	private String username = "";
//	
//        @Value("#{properties['db.password']}")
//        private String password = "";
//        
//        @Value("#{properties['db.maxActive']}")
//        private int maxActive = 10;
//        
//        @Value("#{properties['db.minIdle']}")
//        private int minIdle = 10;
//        
//        @Value("#{properties['db.showSql']}")
//        private boolean showSql = false;
//
//	@Value("#{properties['search.indexLocation']}")
//	private String searchIndexLocation = "";
//
//
////	@Bean
////	public PasswordDecryptor passwordDecryptor() {
////		PasswordDecryptor passwordDecryptor = new PasswordDecryptor();
////		passwordDecryptor.setPassword(this.password);
////		return passwordDecryptor;
////	}
//
////	@Bean
////	public DbConfigResolver.DbConfig dbConfig() {
////		return DbConfigResolver.resolve(this.uri);
////	}
	
	@Bean
	public IGenericRepository genericRepository() {
		GenericRepository genericRepository = new GenericRepository();
		return genericRepository;
		
//		return DbConfigResolver.resolve(this.uri);
	}
	
	@Bean
	public ICompanyRepository companyRepository() {
		ICompanyRepository genericRepository = new CompanyRepository();
		return genericRepository;
		
//		return DbConfigResolver.resolve(this.uri);
	}

////	@Bean
////	public PlatformTransactionManager transactionManager(
////			EntityManagerFactory entityManagerFactory) {
////		JpaTransactionManager transactionManager = new JpaTransactionManager(
////				entityManagerFactory);
////		return transactionManager;
////	}
//
////	@Bean
////	public JpaVendorAdapter vendorAdapter() {
////		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
////		adapter.setDatabase(dbConfig().getVendorDatabase());
////		adapter.setDatabasePlatform(dbConfig().getPlatform());
////		adapter.setShowSql(false);
////		return adapter;
////	}
//
////	@Bean
////	public BitaEntityModule defaultEntityModule() {
////		return new BitaEntityModule();
////	}
//
////	@Bean
////	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
////			BitaEntityModule[] entityModules) {
////		LocalContainerEntityManagerFactoryBean fac = new LocalContainerEntityManagerFactoryBean();
////		fac.setPersistenceUnitName("starCore");
////		fac.setJpaVendorAdapter(vendorAdapter());
////		Properties properties = new Properties();
////		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
////
////		properties.setProperty("hibernate.cache.use_query_cache", "true");
////		properties.setProperty("hibernate.cache.provider_class","org.hibernate.cache.EhCacheProvider");
////		properties.setProperty("hibernate.generate_statistics", "true");
////
////		properties.setProperty("hibernate.cache.use_structured_entries", "true");
////		properties.setProperty("hibernate.cache.use_minimal_puts", "true");
////		properties.setProperty("hibernate.show_sql", showSql ? "true" : "false");
////		if(showSql){
////	                properties.setProperty("hibernate.format_sql", "true");
////		}
////		BitaModulePersistenceUnitManager pum = new BitaModulePersistenceUnitManager(
////				entityModules);
////		fac.setPersistenceUnitManager(pum);
////		fac.setJpaProperties(properties);
////
////		DataSource dataSource = datasource();
////
////		pum.setDefaultDataSource(dataSource);
////		pum.setPersistenceXmlLocation("classpath:META-INF/bita-persistence.xml");
////		fac.setDataSource(dataSource);
////		pum.afterPropertiesSet();
////
////		fac.setPersistenceProvider(configurableHibernatePersistence());
////
////		return fac;
////	}
//
////	@Bean
////	public ConfigurableHibernatePersistence configurableHibernatePersistence() {
////		return new ConfigurableHibernatePersistence();
////	}
//
//	@Bean
//	public StatsWrappedObjectPool connectionPool() {
//		StatsWrappedObjectPool connectionPool = new StatsWrappedObjectPool();
//		GenericObjectPool delegate = connectionPool.getDelegate();
//		delegate.setTestOnBorrow(false);
//		delegate.setTestOnReturn(false);
//		delegate.setTestWhileIdle(false);
//		delegate.setMinIdle(minIdle);
//		delegate.setMaxActive(maxActive);
//		return connectionPool;
//	}
//
//	@Bean
//	public DataSource datasource() {
//
//		DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
//
//		this.uri, this.username, passwordDecryptor().getPassword());
//
//		StatsWrappedObjectPool connectionPool = this.connectionPool();
//		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
//				connectionFactory, connectionPool, null, null, false, false);
//
//		return new PoolingDataSource(poolableConnectionFactory.getPool());
//	}
//
//	@Bean
//	public JdbcTemplate jdbcTemplate(
//			@Value("#{datasource}") DataSource datasource) {
//		return new JdbcTemplate(datasource);
//	}
//
//	@Bean
//	public DbConnectionMBean dbConnectionMBean() {
//		return new DbConnectionMBean();
//	}
}
