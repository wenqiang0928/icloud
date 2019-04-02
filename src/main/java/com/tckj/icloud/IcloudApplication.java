package com.tckj.icloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tckj.icloud.mapper")
public class IcloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcloudApplication.class, args);
    }

}
