package com.lq.sell.dto;

import com.lq.sell.dataobject.OrderDetail;
import com.lq.sell.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    private Integer payStatus = OrderStatusEnum.NOPAY.getCode();

    private Date createTime;
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
