package com.zhike.service.impl;

import com.zhike.model.Activity;
import com.zhike.repository.ActivityRepository;
import com.zhike.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity getActivityByName(String name) {
        return activityRepository.findByName(name);
    }


}
