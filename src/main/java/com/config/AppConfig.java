package com.config;

import com.dao.FileDAO;
import com.dao.StorageDAO;
import com.controller.FileController;
import com.controller.StorageController;
import com.controller.TransferController;
import com.service.FileService;
import com.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
