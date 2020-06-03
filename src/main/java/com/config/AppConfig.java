package com.config;

import com.DAO.FileDAO;
import com.DAO.StorageDAO;
import com.controller.FileController;
import com.controller.StorageController;
import com.controller.TransferController;
import com.service.FileService;
import com.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com")
public class AppConfig {
    @Bean
    public FileDAO fileDAO(){
        return new FileDAO();
    }

    @Bean
    public StorageDAO storageDAO(){
        return new StorageDAO();
    }

    @Bean
    public FileService fileService(){
        return new FileService();
    }

    @Bean
    public StorageService storageService(){
        return new StorageService();
    }

    @Bean
    public FileController fileController(){
        return new FileController();
    }

    @Bean
    public StorageController storageController(){
        return new StorageController();
    }

    @Bean
    public TransferController transferController(){
        return new TransferController();
    }
}
