package com.lq.sell.config;

import com.lq.sell.sellException.AuthException;
import com.lq.sell.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * com.lq.sell.controller.Seller*.*(..))" + "&& !execution(public * com.lq.sell.controller.SellerUserController.*(..))")
    public void check() {
    }

    @Before("check()")
    public void checkAuth() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String cookieValueOfToken = CookieUtils.get(request);
        log.info("token is:" + cookieValueOfToken);
        if (StringUtils.isBlank(cookieValueOfToken)) {
            log.info("token is:" + cookieValueOfToken + "token不存在cookie");
            throw new AuthException("token不存在cookie");
        }
        Object o = redisTemplate.opsForValue().get(cookieValueOfToken);
        log.info("redis token is:" + o);
        Set keys = redisTemplate.keys("*");
        keys.forEach(e -> {
            log.info(e.toString());
        });
        if (o == null) {
            log.info("token is:" + cookieValueOfToken + "token不存在cookie");
            throw new AuthException("token不存在redis 或者已过期");
        }
    }
}
