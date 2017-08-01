package com.okayaok.books.config;

import com.okayaok.books.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security权限验证的配置
 *
 * @author hang_xiao
 *         2017/7/11.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入自定义的UserDetailsService
     *
     * @return UserDetailsService
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UsersService();
    }

    /**
     * 设置密码的加密方式
     *
     * @return 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * 将自定义的AuthenticationProvider注入到认证管理中
     *
     * @param auth 认证授权
     * @throws Exception 认证异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 配置路径的权限控制
     *
     * @param http 请求权限控制
     * @throws Exception 认证请求异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //关闭CSRF
                .csrf()
                    .disable()
                .authorizeRequests()
                    //不需要验证就可以访问的请求
                    .antMatchers("/static/**", "/login", "/register").permitAll()
                    //验证通过后才能访问的请求
                    .antMatchers("/", "/search/**", "/users/**", "/roles/**").authenticated()
                    .and()
                //配置记住我功能，失效时间为7天
                .rememberMe()
                    .key("booksRememberMe")
                    .rememberMeParameter("rememberMe")
                    .tokenValiditySeconds(24*60*60*7)
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll();
    }

    /**
     * 在内存中注入用户信息
     *
     * @param auth 认证授权
     * @throws Exception 异常信息
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("user").roles("ADMIN");
    }

}
