package com.lq.sell.service;

import com.lq.sell.dataobject.ProductInfo;
import com.lq.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //TODO
    // +- 库存
    void  increaseStock(List<CartDTO>  cartDTOList);

    void  decreaseStock(List<CartDTO>  cartDTOList);

}
