package com.zhike.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Administrator
 * ip地址获取类
 */
@Component
public class HttpRequestProxy {

    private static HttpServletRequest request;
    private static final String UNKNOWN  = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String ADDRESSING = ",";


    @Autowired
    public void setRequest(HttpServletRequest request) {
        HttpRequestProxy.request = request;
    }

    public static String getRequestUrl() {
        return HttpRequestProxy.request.getRequestURI();
    }

    public static String getRemoteRealIp() {
        HttpServletRequest request = HttpRequestProxy.request;
        String ipAddress ;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals(LOCALHOST)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    assert inet != null;
                    ipAddress = inet.getHostAddress();
                }
            }
            int defaultLength;
            defaultLength = 15;
            if (ipAddress != null && ipAddress.length() > defaultLength) {
                if (ipAddress.indexOf(ADDRESSING) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }
}

