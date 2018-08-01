package com.lq.sell.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lq.sell.VO.ResultVO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVOUtils {

    public  static ResultVO  success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        resultVO.setData(object);
        return  resultVO;
    }

    public  static ResultVO  success(Object object,Integer code,String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return  resultVO;
    }
    public  static ResultVO  success(){
        return  success(null);
    }

    public  static ResultVO  error(Integer code,String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return  resultVO;
    }

    public  static ResultVO  error(){
        return  error(-10000,"error");
    }
}
