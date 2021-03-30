package com.zhike.logic;

import com.zhike.bo.SkuOrderBO;
import com.zhike.dto.OrderDTO;
import com.zhike.dto.SkuInfoDTO;
import com.zhike.exception.HttpException.ParameterException;
import com.zhike.model.OrderSku;
import com.zhike.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 订单校验类
 */
public class OrderChecker {

    private OrderDTO orderDTO;

    private List<Sku> serverSkuList;

    private CouponChecker couponChecker;

    private Integer maxSkuLimit;

    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();


    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker,Integer maxSkuLimit){
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public void isOk(){
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> orderBOList = new ArrayList<>();
//      判断是否有商品下架
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(),this.serverSkuList.size());
//
        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku,skuInfoDTO);
            this.beyondMaxSkuLimit(skuInfoDTO);

//            累加金额
            serverTotalPrice = serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDTO));
            orderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
//          组装新的对象
            this.orderSkuList.add(new OrderSku(sku,skuInfoDTO));
        }

        this.totalPriceIsOk(orderDTO.getTotalPrice(),serverTotalPrice);

        if (this.couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canBeUsed(orderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPrice(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    /**
     * 获取订单主要图片
     * @return
     */
    public String getLeaderImg() {
        return  this.serverSkuList.get(0).getImg();
    }

    /**
     * 获取订单说明
     * @return
     */
    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    /**
     * 获取订单购买的商品总数
     * @return
     */
    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * 判断 与前端传的价格是否一致
     * @param orderTotalPrice
     * @param serverTotalPrice
     */
    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    /**
     * 判断是否有商品下架 如果传回的商品列表长度和数据库查询的长度一样 则表示没有商品下架 反之抛出异常
     * @param count1
     * @param count2
     */
    private void skuNotOnSale(int count1 , int count2){
        if (count1!=count2){
            throw new ParameterException(50002);
        }
    }

    /**
     * 计算商品价格  价格*数量
     * @param sku
     * @param skuInfoDTO
     * @return
     */
    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO){
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    /**
     * 判断商品是否售罄
     * @param sku
     */
    private void containsSoldOutSku(Sku sku){
        if (sku.getStock() == 0){
            throw new ParameterException(50001);
        }
    }

    /**
     * 判断购买的商品数量是否大于库存数量
     * @param sku
     * @param skuInfoDTO
     */
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }

    /**
     * 判断购买的商品数量大于最大购买数量
     * @param skuInfoDTO
     */
    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }
}
