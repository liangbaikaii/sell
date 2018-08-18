package com.lq.sell.repository.mapper;

import com.lq.sell.dataobject.ProductCategory;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void test() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());
        productCategory.setCategoryName("mybatis");
        productCategory.setCategoryType(2);
        int insert = mapper.insert(productCategory);
        System.out.println(productCategory.getCategoryId());
        System.out.println(insert);

    }

}