package com.handle.globalhandle.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.BiFunction;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 毕晓东
 * @Date: 2023/08/22/11:05
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "golbal")
public class GolbanhandleConfig {

    public boolean isinterceptor;

    public String token;

    public String whiteip;

    public String blackip;

    /**
     * SSO-Server端：登录函数
     */
    public BiFunction<String, HttpServletRequest, Boolean> doPrehandle = (token, HttpServletRequest) ->  {
        return false;
    };

    public GolbanhandleConfig setpreHandle(BiFunction<String, HttpServletRequest, Boolean> doPrehandle){
            this.doPrehandle = doPrehandle;
            return  this;
    }
}
