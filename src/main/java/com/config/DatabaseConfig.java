package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;
import org.springframework.core.env.Environment;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com"})
public class DatabaseConfig {
    private final Environment env;

    @Autowired
    public DatabaseConfig(Environment env){
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("oracle.jdbc.driver.OracleDriver")));
        dataSource.setUrl(env.getProperty("jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL"));
        dataSource.setUsername(env.getProperty("main"));
        dataSource.setPassword(env.getProperty("2210Dracon1983"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource());
        //entityManagerFactory.setPackagesToScan("com");

        //Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        //Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10Dialect");
        additionalProperties.put("hibernate.show_sql", "false");
        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}