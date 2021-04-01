package com.zhike.api.v1;


import com.zhike.model.Sku;
import com.zhike.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("")
    public List<Sku> getSkuListInIds(@RequestParam(name = "ids", required = false) String ids) {
        if(ids==null || ids.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        return skuService.getSkuListByIds(idList);
    }
}
