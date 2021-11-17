package com.github.nagatosingle.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * <p>
 * date: 2021/10/20
 *
 * @author wangzisfa
 * @version 0.31
 */
@Order(2)
@EnableWebSecurity
public class NagatoSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/py/**").permitAll()
                .antMatchers("/basic/**").permitAll()
                .antMatchers("/platform/**").authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors();
    }

    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatcher(new OAuthRequestedMatcher())
//                .authorizeRequests()
//                .antMatchers("/oauth/**").authenticated()
//                .antMatchers("/actuator/**").permitAll()
//                .and()
//                .csrf()
//                .disable();
//    }
//
//    private static class OAuthRequestedMatcher implements RequestMatcher {
//
//        @Override
//        public boolean matches(HttpServletRequest httpServletRequest) {
//            String auth = httpServletRequest.getHeader("Authorization");
//            return (auth != null) && auth.startsWith("Basic");
//        }
//    }
}
