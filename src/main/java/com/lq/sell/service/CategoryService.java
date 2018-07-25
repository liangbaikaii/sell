package com.lq.sell.service;

import com.lq.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categotyTypeList);

    ProductCategory  save(ProductCategory productCategory);
}
