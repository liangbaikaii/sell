package com.lq.sell.VO;


import lombok.Data;

/**
 * http response  statanrd data
 */
@Data
public class ResultVO<T> {
    private  Integer code;
    private  T data;
    private  String msg;

}
