package com.github.nagatosingle.configure;

import com.github.nagatosingle.utils.jwt.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 * <p>
 * date: 2021/11/08
 *
 * @author wangzisfa
 * @version 0.31
 */
@Configuration
public class NagatoWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .maxAge(3000)
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(nagatoAuthInterceptor());
    }

    @Bean
    public NagatoAuthInterceptor nagatoAuthInterceptor() {
        return new NagatoAuthInterceptor();
    }

    @Bean
    public JwtTokenService jwtTokenService() {
        return new JwtTokenService();
    }
}
