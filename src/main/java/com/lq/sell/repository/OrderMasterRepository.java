package com.lq.sell.repository;

import com.lq.sell.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    List<OrderMaster> findByBuyerOpenid(String buyerId);
}
