package com.lq.sell.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lq.sell.dataobject.OrderDetail;
import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.form.OrderForm;
import com.lq.sell.sellException.SellException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderForm2orderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCreateTime(new Date());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerName(orderForm.getName());

        List<OrderDetail> orderDetailList =null;
        try {
            Gson gson = new Gson();
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}
            .getType());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
