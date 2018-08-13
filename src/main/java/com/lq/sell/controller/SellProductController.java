package com.lq.sell.controller;

import com.lq.sell.dataobject.ProductCategory;
import com.lq.sell.dataobject.ProductInfo;
import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.OrderStatusEnum;
import com.lq.sell.enums.ProductStatusEnum;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.form.ProductInfoForm;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.CategoryService;
import com.lq.sell.service.OrderService;
import com.lq.sell.service.ProductService;
import com.lq.sell.utils.EnumUtils;
import com.lq.sell.utils.UniqueKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellProductController {
    @Autowired
    private ProductService productService;


    @Autowired
    private CategoryService categoryService;

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


    @PostMapping("/update")
    public ModelAndView saveAndupdate( ProductInfoForm updateProductInfo, BindingResult bindingResult) {

        Map<String, Object> map = new HashMap<>();
        map.put("url", "/sell/seller/product/list");
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().toString());
        }
        try {
            if (updateProductInfo == null) {
                throw new SellException(ResultEnum.PARAM_ERROR.getMessage());
            }
            ProductInfo productInfo = new ProductInfo();
            if (updateProductInfo.getProductId() != null) {
                productInfo = productService.findOne(updateProductInfo.getProductId());
                updateProductInfo.setCreateTime(productInfo.getCreateTime());
            } else {
                updateProductInfo.setProductId(UniqueKey.generateUniqueKey());
                updateProductInfo.setCreateTime(new Date());
            }
            updateProductInfo.setUpdateTime(new Date());
            BeanUtils.copyProperties(updateProductInfo, productInfo);
            productService.save(productInfo);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("order/error", map);
        }
        map.put("msg", "更新或者保存商品成功");
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
            log.info("e", e.getMessage());
            return new ModelAndView("order/error", map);
        }
        map.put("msg", "更新商品成功");
        return new ModelAndView("order/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(String productId, Map<String, Object> map) {
        map.put("url", "/sell/seller/product/list");
        try {
            ProductInfo productInfo=new ProductInfo();
            if(!StringUtils.isEmpty(productId)){
                productInfo = productService.findOne(productId);
            }
            List<ProductCategory> productCategoryList = categoryService.findAll();
            map.put("productInfo", productInfo);
            map.put("productCategoryList", productCategoryList);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            log.info("e", e.getMessage());
            return new ModelAndView("order/error", map);
        }
        map.put("msg", "成功");
        if(productId==null|| "".equals(productId)){
            return new ModelAndView("product/add", map);
        }
        return new ModelAndView("product/update", map);

    }


}
