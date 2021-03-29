package com.zhike.service;

import com.zhike.model.Activity;

/**
 * @author Administrator
 */
public interface ActivityService {

    /**
     * 根据名称获取活动信息
     * @param name
     * @return
     */
    Activity getActivityByName(String name);


}
