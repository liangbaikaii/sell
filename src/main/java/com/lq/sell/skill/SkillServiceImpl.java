package com.lq.sell.skill;


import com.lq.sell.sellException.SellException;
import com.lq.sell.sellException.SkillException;
import com.lq.sell.utils.UniqueKey;
import com.sun.org.apache.xml.internal.security.keys.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SkillServiceImpl implements SkillService {
    private static Map<String, Integer> products;
    private static Map<String, Integer> stock;
    private static Map<String, String> orders;
    private static final long TIME_OUT = 5 * 1000;
    @Autowired
    private RedisLock redisLock;

    static {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 10000);
        stock.put("123456", 10000);
    }

//    @Override
//    public  synchronized String skill(String productId) {
//        int num = stock.get(productId);
//        if (0 == num) {
//            throw new RuntimeException("商品卖完了 活动结束");
//        } else {
//            //下单
//            orders.put(UniqueKey.generateUniqueKey(), productId);
//            num = num - 1;
//
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            stock.put(productId, num);
//        }
//        return null;
//    }


    @Override
    public String skill(String productId) {
        long value = System.currentTimeMillis() + TIME_OUT;

        if (!redisLock.lock(productId, String.valueOf(value))) {
            throw new SkillException("系统繁忙 请稍后再试");
        }

        int num = stock.get(productId);
        if (0 == num) {
            throw new SkillException("商品卖完了 活动结束");
        } else {
            //下单
            orders.put(UniqueKey.generateUniqueKey(), productId);
            num = num - 1;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, num);
        }


        redisLock.unLock(productId, String.valueOf(value));

        return null;
    }

    public String query(String productId) {
        return "端午活动：粽子" + products.get(productId) + "还剩下" + stock.get(productId) + "个"
                + "成功下单的用户数目为" + orders.size() + "人";
    }
}
