package com.zoomdev.personalblog.config;

import com.zoomdev.personalblog.security.AuthenticationStrategy;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 标记这个类为spring的配置类
@EnableWebSecurity // 启动Spring Security的web安全功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${app.security.jwt.enabled:true}") // 从配置文件中读取JWT启动状态，默认为true
    private boolean jwtEnabled;

    private final AuthenticationStrategy jwtAuthenticationStrategy;
    private final AuthenticationStrategy noAuthenticationStrategy;

    @Autowired
    public SecurityConfig(
            @Lazy @Qualifier("jwtAuthenticationStrategy") AuthenticationStrategy jwtAuthenticationStrategy,
            @Qualifier("noAuthenticationStrategy") AuthenticationStrategy noAuthenticationStrategy) {
        this.jwtAuthenticationStrategy = jwtAuthenticationStrategy;
        this.noAuthenticationStrategy = noAuthenticationStrategy;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (jwtEnabled) {
            jwtAuthenticationStrategy.configure(httpSecurity);
        } else {
            noAuthenticationStrategy.configure(httpSecurity);
        }
    }

    @Bean
    public AuthenticationStrategy authenticationStrategy() {
        return jwtEnabled ? jwtAuthenticationStrategy : noAuthenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 返回自定义的PasswordEncoder实现
        return new PasswordEncoder(){
            @Override
            public String encode(CharSequence rawPassword){
                // 使用SHA256进行加密
                return DigestUtils.sha256Hex(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword){
                // 验证密码
                return encodedPassword.equals(DigestUtils.sha256Hex(rawPassword.toString()));
            }
        };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
