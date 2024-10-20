package com.zzyl.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Descriptioin JwtTokenManagerProperties
 * @Author AvA
 * @Date 2024-10-20
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "zzyl.framework.jwt")
public class JwtTokenManagerProperties implements Serializable {
    /**
     *  签名密码
     */
    private String base64EncodedSecretKey;

    /**
     *  有效时间
     */
    private Integer ttl;
}
