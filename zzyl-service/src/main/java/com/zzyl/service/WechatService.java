package com.zzyl.service;

/**
 * @Descriptioin WechatService
 * @Author AvA
 * @Date 2024-10-20
 */
public interface WechatService {

    /**
     * 获取openid
     * @param code
     * @return
     */
    String getOpenid(String code);

    /**
     * 获取手机号
     * @param code
     * @return
     */
    String getPhone(String code);
}
