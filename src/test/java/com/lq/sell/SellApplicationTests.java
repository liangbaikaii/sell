package com.lq.sell;

import com.lq.sell.dataobject.ProductCategory;
import com.lq.sell.repository.ProductCategoryRepository;
import org.apache.commons.codec.digest.Md5Crypt;
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
//    private final Logger logger = LoggerFactory.getLogger(SellApplication.class);

//    @Autowired
//    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void test() {
        String s =   Md5Crypt.apr1Crypt("123456".getBytes());

    }

}
