package com.lq.sell.dataobject;

import com.lq.sell.enums.OrderStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class OrderMaster {

    @Id
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
}
