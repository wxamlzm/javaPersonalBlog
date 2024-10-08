package com.zoomdev.personalblog.config;

import com.zoomdev.personalblog.security.JwtAuthenticationEntryPoint;
import com.zoomdev.personalblog.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置AuthenticationManager使我们的UserDetailsService和密码编码器
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt强哈希函数
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        // 暴露 AuthenticationManage 作为 Bean
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable() // 禁用CSRF保护，因为我们使用jwt
                .authorizeRequests().antMatchers("/api/auth/**").permitAll() //允许所有人访问/api/auth/**路径
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) // 设置认证失败的处理
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 使用无状态会话

        // 在UsernamePasswordAuthenticationFilter之前添加JWT过滤器
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
