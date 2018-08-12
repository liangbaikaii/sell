package com.lq.sell.controller;

import com.lq.sell.dataobject.ProductInfo;
import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.OrderStatusEnum;
import com.lq.sell.enums.ProductStatusEnum;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.OrderService;
import com.lq.sell.service.ProductService;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "2") Integer size) {
        Map<String, Object> map = new HashMap<>();
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        if (productInfoPage != null && productInfoPage.getContent() != null) {
            productInfoPage.getContent().forEach(e -> {
                e.setProductStatusMsg(EnumUtils.getBycode(e.getProductStatus(), OrderStatusEnum.class).getMsg());
//                e.setPayStatusMsg(EnumUtils.getBycode(e.getPayStatus(), OrderStatusEnum.class).getMsg());
            });
        }
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        return new ModelAndView("product/list", map);
    }


    @GetMapping("/update")
    public ModelAndView update(ProductInfo updateProductInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", "/sell/seller/product/list");
        try {
            if (updateProductInfo == null) {
                throw new SellException(ResultEnum.PARAM_ERROR.getMessage());
            }
            productService.save(updateProductInfo);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("order/error", map);
        }
        map.put("msg", "更新商品成功");
        return new ModelAndView("order/success", map);
    }

    @GetMapping("/updateState")
    public ModelAndView updateState(ProductInfo updateProductInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", "/sell/seller/product/list");
        try {
            if (updateProductInfo == null) {
                throw new SellException(ResultEnum.PARAM_ERROR.getMessage());
            }
            productService.updataState(updateProductInfo.getProductStatus(), updateProductInfo.getProductId());
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            log.info("e",e.getMessage());
            return new ModelAndView("order/error", map);
        }
        map.put("msg", "更新商品成功");
        return new ModelAndView("order/success", map);
    }
}
