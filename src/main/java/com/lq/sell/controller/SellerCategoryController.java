package com.lq.sell.controller;

import com.lq.sell.dataobject.ProductCategory;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {

        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("cate/list", map);
    }


    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        ProductCategory category = new ProductCategory();
        if (categoryId != null && !"".equals(categoryId)) {
            category = categoryService.findOne(categoryId);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("category", category);
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("cate/update", map);
    }

    @PostMapping("/update")
    public ModelAndView update(ProductCategory category, Map<String, Object> map) throws IllegalAccessException {

        map.put("url", "/sell/seller/category/list");
        try {
            ProductCategory dbCate = new ProductCategory();
            if (category == null && category.getCategoryId() == null) {
                dbCate = categoryService.findOne(category.getCategoryId());
                category.setCreateTime(dbCate.getCreateTime());
            }else {
                category.setCreateTime(new Date());
            }
            category.setUpdateTime(new Date());
            BeanUtils.copyProperties(category, dbCate);
            categoryService.save(dbCate);
            map.put("msg", "success");
        } catch (Exception e) {
            log.error("update category error[]", e.toString());
            map.put("msg", e.toString());
        }
        return new ModelAndView("order/success", map);
    }
}
