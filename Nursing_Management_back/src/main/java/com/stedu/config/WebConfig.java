package com.stedu.config;

import com.stedu.interceptor.JwInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwInterceptor jwtInterceptor;
    @Value("${my.picdir}")
    private String picdir;

    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(jwtInterceptor)
    //            .addPathPatterns("/admin/**");
    //}

    //服务器资源向外界映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //访问此地址下的文件会去Locations里的路径里面找
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + picdir);
    }

}
