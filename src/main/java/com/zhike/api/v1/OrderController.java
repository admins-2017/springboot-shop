package com.zhike.api.v1;

import com.zhike.bo.PageCounter;
import com.zhike.core.LocalUser;
import com.zhike.core.interceptors.ScopeLevel;
import com.zhike.dto.OrderDTO;
import com.zhike.exception.httpexception.NotFoundException;
import com.zhike.logic.OrderChecker;
import com.zhike.model.Order;
import com.zhike.service.OrderService;
import com.zhike.util.CommonUtil;
import com.zhike.vo.OrderIdVO;
import com.zhike.vo.OrderPureVO;
import com.zhike.vo.OrderSimplifyVO;
import com.zhike.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("order")
@Validated
public class OrderController {

    private final OrderService orderService;

    @Value("${shop.order.pay-time-limit}")
    private Long payTimeLimit;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ScopeLevel
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = orderService.isOk(uid, orderDTO);
        Long orderId = orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(orderId);
    }

    @ScopeLevel
    @GetMapping("/status/unpaid")
    public PagingDozer getUnpaidSimplifyList(@RequestParam(defaultValue = "0") Integer start,@RequestParam(defaultValue = "10")Integer count){
        PageCounter page = CommonUtil.convertToPageParameter(start,count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    public PagingDozer getOrderByStatus(@PathVariable Integer status,@RequestParam(defaultValue = "0") Integer start,@RequestParam(defaultValue = "10")Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start,count);
        Page<Order> orderPage = this.orderService.getOrderByStatus(status,page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/detail/{id}")
    public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long oid) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(oid);
        return orderOptional.map((o) -> new OrderPureVO(o, payTimeLimit))
                .orElseThrow(() -> new NotFoundException(50009));
    }

}
