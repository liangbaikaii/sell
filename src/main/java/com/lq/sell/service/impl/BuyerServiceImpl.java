package com.lq.sell.service.impl;

import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.BuyerService;
import com.lq.sell.service.OrderService;
import com.lq.sell.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl  implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            throw new SellException(ResultEnum.OPENID_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            throw new SellException(ResultEnum.OPENID_ERROR);
        }
        return orderDTO;
    }
}
