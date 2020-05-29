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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com")
public class AppConfig {
    @Bean(name = "fileDAO")
    public FileDAO fileDAO(){
        return new FileDAO();
    }

    @Bean(name = "storageDAO")
    public StorageDAO storageDAO(){
        return new StorageDAO();
    }

    @Bean(name = "fileService")
    public FileService fileService(){
        return new FileService();
    }

    @Bean(name = "storageService")
    public StorageService storageService(){
        return new StorageService();
    }

    @Bean(name = "fileController")
    public FileController fileController(){
        return new FileController();
    }

    @Bean(name = "storageController")
    public StorageController storageController(){
        return new StorageController();
    }

    @Bean(name = "transferController")
    public TransferController transferController(){
        return new TransferController();
    }
}
