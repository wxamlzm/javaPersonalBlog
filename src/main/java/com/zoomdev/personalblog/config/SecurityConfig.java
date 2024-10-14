package com.zoomdev.personalblog.config;

import com.zoomdev.personalblog.security.AuthenticationStrategy;
import com.zoomdev.personalblog.security.JwtAuthenticationEntryPoint;
import com.zoomdev.personalblog.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 标记这个类为spring的配置类
@EnableWebSecurity // 启动Spring Security的web安全功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${app.security.jwt.enabled:true}") // 从配置文件中读取JWT启动状态，默认为true
    private boolean jwtEnabled;

    @Autowired
    @Qualifier("jwtAuthenticationStrategy")
    private AuthenticationStrategy jwtAuthenticationStrategy;

    @Autowired
    @Qualifier("noAuthenticationStrategy")
    private AuthenticationStrategy noAuthenticationStrategy;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        if(jwtEnabled){
            jwtAuthenticationStrategy.configure(httpSecurity);
        }else{
            noAuthenticationStrategy.configure(httpSecurity);
        }
    }

    @Bean
    public AuthenticationStrategy authenticationStrategy(){
        return jwtEnabled ? jwtAuthenticationStrategy : noAuthenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
