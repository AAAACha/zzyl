package com.zzyl.service.impl;

import com.google.common.collect.Lists;
import com.zzyl.constant.Constants;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.entity.Member;
import com.zzyl.mapper.MemberMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.service.MemberService;
import com.zzyl.service.WechatService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.remote.TargetedNotification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Descriptioin MemberServiceImpl
 * @Author AvA
 * @Date 2024-10-20
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    static ArrayList DEFAULT_NICKNAME_PREFIX = Lists.newArrayList(
            "生活更美好",
            "大桔大利",
            "日富一日",
            "好柿开花",
            "柿柿如意",
            "一椰暴富",
            "大柚所为",
            "杨梅吐气",
            "天生荔枝"
    );

    @Override
    public LoginVo login(UserLoginRequestDto userLoginRequestDto) {
        String openId = wechatService.getOpenid(userLoginRequestDto.getCode());

        Member member = memberMapper.getByOpenId(openId);

        if(ObjectUtil.isEmpty(member)){
            member = Member.builder().openId(openId).build();
        }

        String phone = wechatService.getPhone(userLoginRequestDto.getPhoneCode());

        saveOrUpdate(member,phone);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.JWT_USERID,member.getId());
        claims.put(Constants.JWT_USERNAME,member.getName());

        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claims);

        return LoginVo.builder().token(token).nickName(member.getName()).build();
    }

    private void saveOrUpdate(Member member, String phone) {
        if (ObjectUtil.notEqual(phone, member.getPhone())) {
            member.setPhone(phone);
        }

        if (ObjectUtil.isNotEmpty(member.getId())) {
            memberMapper.update(member);
            return;
        }

        String nickName = DEFAULT_NICKNAME_PREFIX.get((int) (Math.random() * DEFAULT_NICKNAME_PREFIX.size()))
                + StringUtils.substring(member.getPhone(), 7);

        member.setName(nickName);
        memberMapper.save(member);
    }
}
