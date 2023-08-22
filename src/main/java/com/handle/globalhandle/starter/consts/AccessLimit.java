package com.handle.globalhandle.starter.consts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于拦截过于频繁的请求
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    /**
     * 设置访问时间，以秒为分钟
     */
    long seconds();
    /**
     * 设置特定时间内访问次数
     */
    int maxCount();

    /**
     * 设置是否需要验证token
     */
    boolean needToken() default false;
}