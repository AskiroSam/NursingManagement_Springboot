package com.stedu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
public class WebConfig implements WebMvcConfigurer {

    @Value("${my.picdir}")
    private String picdir;

    //服务器资源向外界映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //访问此地址下的文件会去Locations里的路径里面找
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + picdir);
    }

}
