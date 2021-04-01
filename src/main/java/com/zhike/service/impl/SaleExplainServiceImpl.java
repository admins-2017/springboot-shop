package com.zhike.service.impl;

import com.zhike.model.SaleExplain;
import com.zhike.repository.SaleExplainRepository;
import com.zhike.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SaleExplainServiceImpl implements SaleExplainService {

    @Autowired
    private SaleExplainRepository saleExplainRepository;

    @Override
    public List<SaleExplain> getSaleExplainFixedList() {
        return this.saleExplainRepository.findByFixedOrderByIndex(true);
    }
}
