package com.lq.sell.service.impl;

import com.lq.sell.SellApplication;
import com.lq.sell.dataobject.ProductCategory;
import com.lq.sell.repository.ProductCategoryRepository;
import com.lq.sell.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final Logger logger = LoggerFactory.getLogger(SellApplication.class);
    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public ProductCategory findOne(Integer categoryId) {
        logger.info(" in CategoryServiceImpl  time [] ProductCategory findOne[]",new Date(), categoryId);
        return productCategoryRepository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        logger.info(" in CategoryServiceImpl time [] ProductCategory findAll",new Date());
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categotyTypeList) {
        logger.info( "in CategoryServiceImpl  time [] ProductCategory findByCategoryTypeIn[]",new Date(), categotyTypeList);
        return productCategoryRepository.findByCategoryTypeIn(categotyTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        logger.info(" in CategoryServiceImpl  time [%d] ProductCategory save[]", productCategory);
        return productCategoryRepository.save(productCategory);
    }
}
