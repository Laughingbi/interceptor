package com.handle.globalhandle.starter.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 毕晓东
 * @Date: 2023/08/22/17:30
 * @Description:
 */
@Component
public class ApplicationContextUtils  implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    //获取Bean
    public static <T> T getBean(Class<T> requiredType){
        return getApplicationContext().getBean(requiredType);
    }
    public static <T> T getBean(String name){
        return (T) getApplicationContext().getBean(name);
    }
}
