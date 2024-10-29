package com.zzyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *  权限核心配置类
 */
@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //忽略地址
        http.authorizeHttpRequests()
                .antMatchers( "/security/login" )
                .permitAll();

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS );//关闭session
        http.headers().cacheControl().disable();//关闭缓存

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * BCrypt密码编码
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}