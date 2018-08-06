package com.lq.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatMpConfig {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;



    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getAppid());
        wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getSecret());
        wxMpInMemoryConfigStorage.setToken(wechatAccountConfig.getToken());
//        wxMpInMemoryConfigStorage.setAesKey("m8lg6owoQN2xK79ku5JW0JJXxz7QepX2BWlj5NHFsi8");
        return wxMpInMemoryConfigStorage;
    }
}
