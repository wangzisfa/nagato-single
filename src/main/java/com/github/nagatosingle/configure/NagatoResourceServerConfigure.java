//package com.github.nagatosingle.configure;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
///**
// * Description:
// * <p>
// * date: 2021/10/22
// *
// * @author wangzisfa
// * @version 0.31
// */
//@EnableResourceServer
//@Configuration
//public class NagatoResourceServerConfigure extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/py/**").permitAll()
//                .antMatchers("/oauth/**").permitAll()
//                .antMatchers("/actuator/**").permitAll()
//                .antMatchers("/basic/**").permitAll()
//                .antMatchers("/platform/**").authenticated()
//                .and()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .csrf().disable()
//                .cors();
//    }
//}
