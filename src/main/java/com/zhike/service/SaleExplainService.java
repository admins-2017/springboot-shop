package com.zhike.service;

import com.zhike.model.SaleExplain;

import java.util.List;

/**
 * @author Administrator
 */
public interface SaleExplainService {
    /**
     * 获取销售规则
     * @return
     */
    List<SaleExplain> getSaleExplainFixedList();
}
