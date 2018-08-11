package com.lq.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lq.sell.dataobject.OrderDetail;
import com.lq.sell.enums.OrderStatusEnum;
import com.lq.sell.utils.Date2LongSerializer;
import com.lq.sell.utils.EnumUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    @JsonIgnore
    private Integer orderStatus ;
    @JsonIgnore
    private Integer payStatus;
    private String orderStatusMsg ;
    private String payStatusMsg;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;


    private List<OrderDetail> orderDetailList;

//    @JsonIgnore
//    public OrderStatusEnum getOrderStatusEnum(){
//        return EnumUtils.getBycode(orderStatus,OrderStatusEnum.class);
//    }


}
