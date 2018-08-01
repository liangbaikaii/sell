package com.lq.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    CART_NOT_EMPTY(6,"购物车不能为空"),
    PARAM_ERROR(5,"参数不合法"),
    UPDATE_ORDER_ERROR(4,"更新订单失败"),
    FINISH_ORDER_ERROR(3, "完结订单失败"),
    CANCEL_ORDER_ERROR(2, "取消订单失败"),
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
