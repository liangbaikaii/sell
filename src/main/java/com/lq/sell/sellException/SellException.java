package com.lq.sell.sellException;

import com.lq.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;
    private String desc;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public SellException( String desc) {
        super(desc);
        this.desc = desc;
    }
}
