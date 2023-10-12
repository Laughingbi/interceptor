package com.handle.globalhandle.starter.consts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 毕晓东
 * @Date: 2023/10/12/10:47
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  AutoIdempotent {
    long expireTime() default 10000;

}
