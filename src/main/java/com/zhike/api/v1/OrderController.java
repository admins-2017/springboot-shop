package com.zhike.api.v1;

import com.zhike.core.LocalUser;
import com.zhike.core.interceptors.ScopeLevel;
import com.zhike.dto.OrderDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("order")
@Validated
public class OrderController {

    @PostMapping("")
    @ScopeLevel
    public void placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid = LocalUser.getUser().getId();

    }
}
