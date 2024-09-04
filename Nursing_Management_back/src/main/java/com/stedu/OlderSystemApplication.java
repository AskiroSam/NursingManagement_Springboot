package com.stedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.stedu.mapper")
public class OlderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlderSystemApplication.class, args);
    }

}
