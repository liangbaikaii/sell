package com.lq.sell.repository;

import com.lq.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductInfoRepository  extends JpaRepository<ProductInfo,String>{

    List<ProductInfo> findByProductStatus(Integer productStatus);

    @Query(value = "update product_info  set product_status =?1 where  product_id=?2",nativeQuery = true)
    @Modifying
    void updataState(Integer productStatus, String productId);
}
