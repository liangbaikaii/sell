package com.lq.sell.controller;


import com.lq.sell.VO.ResultVO;
import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.form.OrderForm;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.BuyerService;
import com.lq.sell.service.OrderService;
import com.lq.sell.utils.OrderForm2orderDTOConverter;
import com.lq.sell.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("创建订单参数不合法[]", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2orderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new SellException(ResultEnum.CART_NOT_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtils.success(map);
    }


    @RequestMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "openid",defaultValue = "eeeeeeeeeeeeeeeeeee111111") String openid,
                                         @RequestParam(value = "page",defaultValue = "0")  Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")  Integer size) {

        if (StringUtils.isEmpty(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
          log.info(orderDTOPage.toString());
          log.info("openid-->",openid);
        return ResultVOUtils.success(orderDTOPage.getContent());
    }


    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtils.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtils.success();
    }
}
