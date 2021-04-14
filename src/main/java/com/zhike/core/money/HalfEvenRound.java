package com.zhike.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 银行家算法
 * 4舍6入
 * @author Administrator
 */
public class HalfEvenRound implements IMoneyDiscount{
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal multiply = original.multiply(discount);
        BigDecimal finalPrice = multiply.setScale(2, RoundingMode.HALF_EVEN);
        return finalPrice;
    }
}
