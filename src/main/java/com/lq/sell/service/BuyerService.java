package com.lq.sell.service;

import com.lq.sell.dto.OrderDTO;

public   interface BuyerService {

    OrderDTO  findOrderOne(String openid,String orderId);

    OrderDTO cancelOrder(String openid,String orderId);
}
