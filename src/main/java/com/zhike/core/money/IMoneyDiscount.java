package com.zhike.core.money;

import java.math.BigDecimal;

public interface IMoneyDiscount {

    /**
     * 计算折后金额
     * @param original 原价
     * @param discount 折扣率
     * @return 折后金额
     */
    BigDecimal discount(BigDecimal original,BigDecimal discount);
}
