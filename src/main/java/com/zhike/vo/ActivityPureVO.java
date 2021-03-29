package com.zhike.vo;

import com.zhike.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityPureVO {

    private Long id;
    private String title;
    private String entranceImg;
    private Boolean online;
    private String remark;
    private String startTime;
    private String endTime;

    public ActivityPureVO(Activity activity) {
        BeanUtils.copyProperties(activity,this);
    }

}
