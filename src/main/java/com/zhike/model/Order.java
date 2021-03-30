package com.zhike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zhike.core.enumeration.OrderStatus;
import com.zhike.dto.OrderAddressDTO;
import com.zhike.util.CommonUtil;
import com.zhike.util.GenericAndJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Administrator
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    /**
     * 订单编号
     */
    private String orderNo;

    private Long userId;
    /**
     * 订单原始总价格
     */
    private BigDecimal totalPrice;
    /**
     * 当前订单商品数量
     */
    private Integer totalCount;
    /**
     *  过期时间
     */
    private Date expiredTime;
    /**
     *  用户下单时间
     */
    private Date placedTime;
    /**
     *  快照图片
     */
    private String snapImg;
    /**
     *  快照标题
     */
    private String snapTitle;
    /**
     *  购买商品信息
     */
    private String snapItems;
    /**
     * 地址
     */
    private String snapAddress;
    /**
     *  支付id
     */
    private String prepayId;
    /**
     * 订单最终价格
     */
    private BigDecimal finalTotalPrice;
    /**
     *  订单状态
     */
    private Integer status;

    /**
     * JsonIgnore 修饰get方法不加入序列化
     * @return
     */
    @JsonIgnore
    public OrderStatus getStatusEnum() {
        return OrderStatus.toType(this.status);
    }

    /**
     * 判断订单是否过期
     * @return
     */
    public Boolean needCancel() {
//        判断订单状态是否为未支付状态 如果不是未支付则直接返回false
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return true;
        }
//        判断时间是否过期
        boolean isOutOfDate = CommonUtil.isOutOfDate(this.getExpiredTime());
        if (isOutOfDate) {
            return true;
        }
        return false;
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems() {
        List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {
                });
        return list;
    }


    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO o = GenericAndJson.jsonToObject(this.snapAddress,
                new TypeReference<OrderAddressDTO>() {
                });
        return o;
    }

    public void setSnapAddress(OrderAddressDTO address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }
}
