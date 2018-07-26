package com.lq.sell;

import com.lq.sell.dataobject.ProductCategory;
import com.lq.sell.repository.ProductCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(SellApplication.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(2, 3,4,5);
        List<ProductCategory> byCategoryTypeIn = productCategoryRepository.findByCategoryTypeIn(list);
        byCategoryTypeIn.forEach(productCategory -> {
            System.out.println(productCategory);
        });
//        ProductCategory productCategory=new ProductCategory("test",4);
//        productCategoryRepository.save(productCategory);
    }

}
