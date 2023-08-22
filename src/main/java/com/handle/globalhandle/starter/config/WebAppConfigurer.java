package com.handle.globalhandle.starter.config;

import com.handle.globalhandle.starter.handle.GlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Web配置类
 */
@Configuration
public class WebAppConfigurer implements  WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    //关键，将拦截器作为bean写入配置中
    @Bean
    public GlobalInterceptor globalInterceptor(){
        logger.info("--------初始化全局拦截器-----------");
        return new GlobalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器组成一个拦截器链
        // addPathPatterns用于添加拦截规则
        // excludePathPatterns用户排除拦截
        logger.info("--------添加拦截-----------");
        registry.addInterceptor(globalInterceptor()).addPathPatterns("/**").excludePathPatterns("/Error/**"); //对来自/** 全路径请求进行拦截
    }

}
