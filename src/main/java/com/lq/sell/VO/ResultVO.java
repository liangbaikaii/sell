package com.lq.sell.VO;


import lombok.Data;

import java.io.Serializable;

/**
 * http response  statanrd data
 */
@Data
public class ResultVO<T>  implements Serializable{
    private  Integer code;
    private  T data;
    private  String msg;

}
