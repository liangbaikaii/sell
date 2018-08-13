package com.lq.sell.utils;

import com.lq.sell.config.ICodeEnum;

public class EnumUtils {

    public static<T extends ICodeEnum>  T getBycode(Number code, Class<T> enumClazz){
           for( T each:enumClazz.getEnumConstants()){
               if(each.getCode().equals(code)){
                   return each;
               }
           }
           return null;

    }
}
