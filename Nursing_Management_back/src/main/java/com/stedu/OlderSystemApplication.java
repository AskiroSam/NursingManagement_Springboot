package com.stedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
@MapperScan(basePackages = "com.stedu.mapper")
public class OlderSystemApplication {
    @Value("${spring.servlet.multipart.location}")
    private  String tempDir;

    @Bean
    public void mkDir(){
        File file = new File(tempDir);
        if (file.exists()){
            System.out.println("文件夹存在");
        }else {
            System.out.println("文件夹不存在");
            file.mkdirs(); //创建文件夹
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(OlderSystemApplication.class, args);
    }

}
