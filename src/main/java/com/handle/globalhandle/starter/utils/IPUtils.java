package com.handle.globalhandle.starter.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 毕晓东
 * @Date: 2023/08/21/16:41
 * @Description:
 */
public class IPUtils {
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * @return ip
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
    /**
     * @description:判断ip是否在某个ip段内
     * @param:
     * @return:
     * @author:JiangJunpeng
     * @date:2020/6/1
     */
    public static boolean isInRange(String ip, String ipAreas) {
        if (StrUtil.isBlank(ip) || StrUtil.isBlank(ipAreas)) {
            return false;
        }
        String[] ips = ip.split("\\.");
        String[] ipAreaArr = ipAreas.split(";");
        for (String s : ipAreaArr) {
            String[] ipArea = s.split("\\.");
            if (ips[0].equals(ipArea[0]) && ips[1].equals(ipArea[1]) && ips[2].equals(ipArea[2])) {
                String[] ipEnds = ipArea[3].split("-");
                if (Integer.parseInt(ipEnds[0]) <= Integer.parseInt(ips[3]) && Integer.parseInt(ips[3]) <= Integer.parseInt(ipEnds[1])) {
                    return true;
                }
            }
        }
        return false;
    }
}
