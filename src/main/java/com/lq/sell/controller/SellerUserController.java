package com.lq.sell.controller;

import com.lq.sell.dataobject.User;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.lq.sell.utils.*;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/user/login")
    public ModelAndView login(String username, String password, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return modelAndView.addObject("msg", "字段为空");
        }
        User user = userServiceImpl.findUserByUsername(username);
        if (user == null) {
            return modelAndView.addObject("msg", "账号不存在");
        }
        try {
            EnyUtils enyUtils = new EnyUtils();
            String enpassword = enyUtils.eccrypt(password);
            System.out.println(enpassword);
            if (!enpassword.equals(user.getPassword())) {
                return modelAndView.addObject("msg", "密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = user.getId() + "-" + UniqueKey.generateUniqueKey();
        redisTemplate.opsForValue().set(token, user.getId(), 30, TimeUnit.MINUTES);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
        modelAndView.addObject("msg", "登录成功");
        modelAndView.addObject("url", "/sell/seller/product/list");
        modelAndView.setViewName("order/success");
        return modelAndView;
    }

    @GetMapping("/index")
    public String index() {
        return "user/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse response) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Cookie[] cookies = requestAttributes.getRequest().getCookies();
        final String[] cookValue = {null};
        Arrays.asList(cookies).forEach(e -> {
            if ("token".equals(e.getName())) {
                cookValue[0] = e.getValue();
                e.setValue(null);
                e.setPath("/");
                e.setMaxAge(0);
                response.addCookie(e);
            }
        });
        redisTemplate.delete(cookValue[0]);

        return "user/login";
    }
}
