package com.zhike.vo;

import com.zhike.model.Category;
import com.zhike.model.Coupon;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Getter
@Setter
@ToString
public class CouponCategoryVO extends CouponPureVO {
    private List<CategoryPureVo> categories ;

    public CouponCategoryVO(Coupon coupon) {
        super(coupon);
        this.categories = new ArrayList<>();
        List<Category> categories = coupon.getCategoryList();
        categories.forEach(category -> {
            CategoryPureVo vo = new CategoryPureVo(category);
            this.categories.add(vo);
        });
    }
}
