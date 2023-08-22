package com.handle.globalhandle.starter.handle;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.StrUtil;
import com.handle.globalhandle.starter.config.GolbanhandleConfig;
import com.handle.globalhandle.starter.consts.AccessLimit;
import com.handle.globalhandle.starter.utils.IPUtils;
import com.handle.globalhandle.starter.utils.RedisUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java.util.List;


public class GlobalInterceptor implements HandlerInterceptor {// 实现HandlerInterceptor接口

    @Resource
    RedisUtil redisUtils;

    @Resource
    GolbanhandleConfig golbanhandleConfig;

    /**
     * 访问控制器方法前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean success = true;
        if(!StrUtil.hasBlank(golbanhandleConfig.blackip)){
            String ip = IPUtils.getRealIP(request);
            List<String> blackIps = StrSpliter.split(golbanhandleConfig.blackip, ',', 0, true, true);
            for(int i=0;i<blackIps.size();i++){
                if(ip.equals(blackIps.get(i))){
                    success = false;
                }
            }
        }
        if(!StrUtil.hasBlank(golbanhandleConfig.whiteip)){
            success = false;
            String ip = IPUtils.getRealIP(request);
            List<String> whiteIps = StrSpliter.split(golbanhandleConfig.whiteip, ',', 0, true, true);
            for(int i=0;i<whiteIps.size();i++){
                if(ip.equals(whiteIps.get(i))){
                    success = true;
                }
            }
        }
        if(!success){
            String path="/Error/401";
            request.getRequestDispatcher(path).forward(request,response);
            return success;
        }
        success = this.interceptDDos(request,response,handler);
        if(golbanhandleConfig.isinterceptor){
            success =   golbanhandleConfig.getDoPrehandle().apply(golbanhandleConfig.getToken(),request);
            if(!success){
                String path="/Error/401";
                request.getRequestDispatcher(path).forward(request,response);
            }
        }
        return success;
    }




    private boolean interceptDDos(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (null == accessLimit) {
                return true;
            }
            Long seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needToken();

            if (needLogin) {
                //判断是否登录
                boolean isAuthToken =  golbanhandleConfig.getDoPrehandle().apply(golbanhandleConfig.getToken(),request);
                if(!isAuthToken){
                    String path="/Error/401";
                    request.getRequestDispatcher(path).forward(request,response);
                    return isAuthToken;
                }
            }
            String ip=request.getRemoteAddr();
            String key = request.getServletPath() + ":" + ip ;
            Integer count = (Integer) redisUtils.get(key);

            if (null == count || -1 == count) {
                redisUtils.set(key, 1,seconds, TimeUnit.SECONDS);
                return true;
            }

            if (count < maxCount) {
                count = count+1;
                redisUtils.set(key, count,0l);
                return true;
            }

            String path="/Error/503";
            request.getRequestDispatcher(path).forward(request,response);
        }

        return true;
    }

    /**
     * 访问控制器方法后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println(new Date() + "--postHandle:" + request.getRequestURL());
    }

    /**
     * postHandle方法执行完成后执行，一般用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println(new Date() + "--afterCompletion:" + request.getRequestURL());
    }
}
