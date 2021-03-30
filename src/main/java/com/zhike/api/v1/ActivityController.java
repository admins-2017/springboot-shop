package com.zhike.api.v1;

import com.zhike.exception.HttpException.NotFoundException;
import com.zhike.model.Activity;
import com.zhike.service.ActivityService;
import com.zhike.vo.ActivityAndCouponVO;
import com.zhike.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * 活动
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getActivityByName(@PathVariable String name){
        Activity activity = activityService.getActivityByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        ActivityPureVO vo = new ActivityPureVO(activity);
        return vo;
    }

    @GetMapping("/name/{name}/with_spu")
    public ActivityAndCouponVO getActivityAndSpuByName(@PathVariable String name){
        Activity activity = activityService.getActivityByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        return new ActivityAndCouponVO(activity);
    }

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }
}
