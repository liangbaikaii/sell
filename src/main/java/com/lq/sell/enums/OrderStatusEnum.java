package com.lq.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    PAY(1, "已付款"),
    NOPAY(-1, "未付款"),
    OVERTIME(-2, "已作废"),
    ERROR(-3, "发生未知错误");
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
