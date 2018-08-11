package com.lq.sell.utils;

import com.lq.sell.config.ICodeEnum;

public class EnumUtils {

    public static<T extends ICodeEnum>  T getBycode(Number code, Class<T> enumClazz){
           for( T each:enumClazz.getEnumConstants()){
               if(code.equals(each.getCode())){
                   return each;
               }
           }
           return null;

    }
}
