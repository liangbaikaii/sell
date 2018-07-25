package com.lq.sell.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class ProductCategory implements Serializable {
    /**
     * mysql 一定要设置strategy 为IDENTITY（大坑 默认为 auto）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public   ProductCategory(){}

    public  ProductCategory(String categoryName,Integer categoryType){
        this.categoryName=categoryName;
        this.categoryType=categoryType;
    }


}
