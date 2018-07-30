package com.lq.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_STOCK_ERROR(-10,"商品库存错误"),
    PRODUCT_NOT_EXIST(10, "商品不存在");
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
