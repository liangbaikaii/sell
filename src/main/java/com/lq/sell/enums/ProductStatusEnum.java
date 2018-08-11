package com.lq.sell.enums;

import com.lq.sell.config.ICodeEnum;
import lombok.Getter;

@Getter
public enum ProductStatusEnum  implements ICodeEnum{

    UP(0, "上架"),
    Down(1,"下架");

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
