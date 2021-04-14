package com.zhike.core;

import com.zhike.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * ThreadLocal 线程安全
 *
 */
public class LocalUser {

    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();


    public static void setUser(User user,Integer scope){
        Map<String,Object> map = new HashMap<>(10);
        map.put("user",user);
        map.put("scope",scope);
        LocalUser.threadLocal.set(map);
    }

    /**
     * 释放线程池
     */
    public static void clear(){
        LocalUser.threadLocal.remove();
    }

    public static User getUser(){
        Map<String, Object> stringObjectMap = LocalUser.threadLocal.get();
        User user =(User) stringObjectMap.get("user");
        return user;
    }

    public static Integer getScope(){
        Map<String, Object> stringObjectMap = LocalUser.threadLocal.get();
        Integer scope =(Integer) stringObjectMap.get("scope");
        return scope;
    }
}
