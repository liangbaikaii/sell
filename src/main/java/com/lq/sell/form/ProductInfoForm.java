package com.lq.sell.form;

import com.lq.sell.enums.ProductStatusEnum;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductInfoForm {

    private  String productId;

    private  String productName;
    private BigDecimal productPrice;

    /**库存*/
    private  Integer  productStock;

    private  String productDescription;

    private  String productIcon;
    /**0正常 1下架*/
    private  Integer productStatus= ProductStatusEnum.UP.getCode();
    private  Integer categoryType;

    private Date createTime;
    private Date updateTime;
}
