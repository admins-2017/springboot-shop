package com.zhike.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 向上舍弃
 */
@Component
public class UpRound implements IMoneyDiscount{
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal multiply = original.multiply(discount);
        BigDecimal finalPrice = multiply.setScale(2, RoundingMode.UP);
        return finalPrice;
    }
}
