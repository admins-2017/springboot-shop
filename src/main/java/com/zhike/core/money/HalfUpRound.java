package com.zhike.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author kang
 * εδΈεζ΄
 */
public class HalfUpRound implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal multiply = original.multiply(discount);
        BigDecimal finalPrice = multiply.setScale(2, RoundingMode.HALF_UP);
        return finalPrice;
    }
}
