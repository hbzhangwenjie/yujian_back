package com.yingzi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.yingzi.guanguai.dao")
@EnableCaching
public class GuanguaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuanguaiApplication.class, args);
    }
}
