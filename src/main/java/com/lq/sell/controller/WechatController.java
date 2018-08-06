package com.lq.sell.controller;

import com.lq.sell.config.WechatAccountConfig;
import com.lq.sell.config.WechatMpConfig;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.sellException.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(wechatAccountConfig.getReturnUrl(), WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("获得code redirectUrl{}", redirectUrl);

        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        log.info("redirect code{}  state{}", code, returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("wechat  error{}", e.getError().getErrorMsg());
            throw new SellException(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("openid{}", openId);
        log.info("enter");
        log.info("return url{}", returnUrl);
        return "redirect:" + "https://www.imooc.com/" + "?openid=" + openId;
    }


    @RequestMapping("/check")
    public void check(ServletResponse response, String timestamp, String nonce, String signature, String echostr) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("不合法");
//            throw new SellException(ResultEnum.WECHAT_ERROR);
        }
        PrintWriter o = null;
        try {
            o = new PrintWriter(response.getWriter());
            o.print(echostr);
        } catch (IOException e) {
            log.error("写回微信端错误{}", e.getMessage());
        } finally {
            o.close();
        }
    }
}
