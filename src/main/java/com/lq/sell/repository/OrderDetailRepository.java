package com.lq.sell.repository;

import com.lq.sell.dataobject.OrderDetail;
import com.lq.sell.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId(String orderId);
}
