package com.zua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.zua.mapper")
@EnableTransactionManagement
public class DeployJarStarter {
    public static void main(String[] args) {
        SpringApplication.run(DeployJarStarter.class);
    }
}
