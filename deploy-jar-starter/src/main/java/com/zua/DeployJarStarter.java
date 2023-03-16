package com.zua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zua.mapper")
public class DeployJarStarter {
    public static void main(String[] args) {
        SpringApplication.run(DeployJarStarter.class);
    }
}
