package org.matoujun.cloud.api.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author matoujun

 */
@Configuration
@Slf4j
public class CloudDataSource {
    @Autowired
    private Environment env;

    @Bean(name = "cloudDS")
    @Primary
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(env.getProperty("app.datasource.url"));
        dataSource.setDriverClassName(env.getProperty("app.datasource.driverClassName"));
        dataSource.setUsername(env.getProperty("app.datasource.username"));
        dataSource.setPassword(env.getProperty("app.datasource.password"));
        dataSource.setConnectionTimeout(Long.parseLong(env.getProperty("app.datasource.hikari.connectionTimeout")));
        dataSource.setIdleTimeout(Long.parseLong(env.getProperty("app.datasource.hikari.idleTimeout")));

        dataSource.addDataSourceProperty("cachePrepStmts", Boolean.valueOf(env.getProperty("app.datasource.hikari.cachePrepStmts")));
        dataSource.addDataSourceProperty("prepStmtCacheSize", Long.valueOf(env.getProperty("app.datasource.hikari.prepStmtCacheSize")));
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", Long.valueOf(env.getProperty("app.datasource.hikari.prepStmtCacheSqlLimit")));
        dataSource.addDataSourceProperty("useServerPrepStmts", Boolean.valueOf(env.getProperty("app.datasource.hikari.useServerPrepStmts")));
        dataSource.addDataSourceProperty("maintainTimeStats", Boolean.valueOf(env.getProperty("app.datasource.hikari.maintainTimeStats")));
        dataSource.addDataSourceProperty("useLocalSessionState", Boolean.valueOf(env.getProperty("app.datasource.hikari.useLocalSessionState")));
        dataSource.addDataSourceProperty("rewriteBatchedStatements", Boolean.valueOf(env.getProperty("app.datasource.hikari.rewriteBatchedStatements")));
        dataSource.addDataSourceProperty("cacheResultSetMetadata", Boolean.valueOf(env.getProperty("app.datasource.hikari.cacheResultSetMetadata")));
        dataSource.addDataSourceProperty("cacheServerConfiguration", Boolean.valueOf(env.getProperty("app.datasource.hikari.cacheServerConfiguration")));
        dataSource.addDataSourceProperty("maximumPoolSize", Integer.valueOf(env.getProperty("app.datasource.hikari.maximumPoolSize")));

        return dataSource;
    }


    @Bean(name="cloudJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("cloudDS")DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }

    @Bean(name="cloudNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("cloudDS")DataSource dataSource) {
        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(dataSource);
        return template;
    }

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(@Qualifier("cloudDS")DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.hibernate.show_sql"));
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.setProperty("hibernate.connection.autocommit", "true");
        jpaProperties.setProperty("hibernate.connection.CharSet", "utf8");
        jpaProperties.setProperty("hibernate.connection.characterEncoding", "utf8");
        jpaProperties.setProperty("hibernate.connection.useUnicode", "true");
        jpaProperties.setProperty("hibernate.dialect.storage_engine", "innodb");
//        jpaProperties.setProperty("hibernate.dialect", "org.matoujun.cloud.datasource.CustomMysqlDialect");

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new
                LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.matoujun.cloud");
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
        localContainerEntityManagerFactoryBean.afterPropertiesSet();

        return localContainerEntityManagerFactoryBean.getObject();
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory
                                                                 entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
