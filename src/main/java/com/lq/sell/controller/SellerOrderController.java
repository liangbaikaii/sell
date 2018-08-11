package com.lq.sell.controller;

import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.OrderStatusEnum;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.service.OrderService;
import com.lq.sell.utils.EnumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Map<String, Object> map = new HashMap<>();
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        if (orderDTOPage != null && orderDTOPage.getContent() != null) {
            orderDTOPage.getContent().forEach(e -> {
                e.setOrderStatusMsg(EnumUtils.getBycode(e.getOrderStatus(), OrderStatusEnum.class).getMsg());
                e.setPayStatusMsg(EnumUtils.getBycode(e.getPayStatus(), OrderStatusEnum.class).getMsg());
            });
        }
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        return new ModelAndView("order/list", map);
    }


    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", "/sell/seller/order/list");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            if (orderDTO != null) {
                OrderDTO cancelOrderDTO = orderService.cancel(orderDTO);
            }
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("order/error", map);
        }
        map.put("msg", "取消订单成功");
        return new ModelAndView("order/success", map);
    }


    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", "/sell/seller/order/detail");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            log.info("orderDTO-->{}", orderDTO);
            map.put("orderDTO",orderDTO);
        } catch (Exception e) {
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getCause().toString());
            return new ModelAndView("order/error", map);
        }
        return new ModelAndView("order/detail", map);
    }


    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", "/sell/seller/order/list");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            if(orderDTO!=null){
                orderDTO = orderService.finish(orderDTO);
            }
            map.put("orderDTO",orderDTO);
            map.put("msg", "完结成功");
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("order/error", map);
        }
        return new ModelAndView("order/success", map);
    }
}
