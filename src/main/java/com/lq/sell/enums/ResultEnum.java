package com.lq.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    CANCEL_ORDER_ERROR(2,"取消订单失败"),
    ORDER_STATUS_ERROT(1, "订单状态错误"),
    ORDER_DETAILR_NOT_EXIST(-8, "订单详情不存在"),
    ORDER_NOT_EXIST(-9, "订单不存在"),
    PRODUCT_STOCK_ERROR(-10, "商品库存错误"),
    PRODUCT_NOT_EXIST(-11, "商品不存在");
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
