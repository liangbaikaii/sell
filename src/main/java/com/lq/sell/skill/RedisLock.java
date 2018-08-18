package com.lq.sell.skill;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis分布式锁
 */
@Component
@Slf4j
public class RedisLock {


    @Autowired
    private StringRedisTemplate template;


    /**
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        if (template.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //防止死锁
        String currentValue = template.opsForValue().get(key);
        //如果锁过期
        if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            log.info(" lock  is  timeout ");
            //获取上一个锁的时间
            String oldValue = template.opsForValue().getAndSet(key, value);
            if (oldValue != null && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }


    public void unLock(String key, String value) {
        String currentVaule = template.opsForValue().get(key);
        try {
            if (currentVaule != null && currentVaule.equals(value)) {
                template.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("unlock  lock  error: " + e.getMessage());
        }

    }
}
