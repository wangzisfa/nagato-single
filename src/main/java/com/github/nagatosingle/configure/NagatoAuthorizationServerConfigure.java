//package com.github.nagatosingle.configure;
//
//import com.github.nagatosingle.exception.auth.CustomOAuth2Exception;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//import java.util.UUID;
//
///**
// * Created With IntelliJ IDEA
// * <p>
// * date 2021/10/20
// * <p>
// * Description:
// * <p>
// * @author wangzisfa
// * @version 0.31
// */
//@Configuration
//@Slf4j
//@AllArgsConstructor
//@EnableAuthorizationServer
//public class NagatoAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    private RedisConnectionFactory redisConnectionFactory;
////    @Autowired
////    private NagatoRedisClientDetailService nagatoRedisClientDetailService;
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Bean
//    public TokenStore tokenStore() {
//        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//        redisTokenStore.setAuthenticationKeyGenerator(authentication -> UUID.randomUUID().toString());
//
//        return redisTokenStore;
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
//        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
//        userAuthenticationConverter.setUserDetailsService(userDetailsService);
//        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
//
//        converter.setSigningKey("nagato");
//
//        return converter;
//    }
//
////    @Bean
////    public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
////        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
////        return new ResourceOwnerPasswordTokenGranter(authenticationManager, defaultTokenServices, nagatoRedisClientDetailService, oAuth2RequestFactory);
////    }
//
////    @Bean
////    public DefaultOAuth2RequestFactory defaultOAuth2RequestFactory() {
////        return new DefaultOAuth2RequestFactory(nagatoRedisClientDetailService);
////    }
//
//    @Bean
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setReuseRefreshToken(true);
//
//        tokenServices.setAccessTokenValiditySeconds(12 * 60 * 60);
//        tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
//        return tokenServices;
//    }
//
//
//
//    /**
//     * @param clients 用于客户端详细信息
//     * @throws Exception inMemory 抛出
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////        mysql+redis
////        clients.withClientDetails(nagatoRedisClientDetailService);
//        clients.inMemory()
//                .withClient("wangzisfa")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("all")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token");
//    }
//
//    /**
//     * @param endpoints 定义授权和令牌端点以及令牌服务
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.tokenStore(tokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .tokenServices(tokenServices())
//                .userDetailsService(userDetailsService)
//                .authenticationManager(authenticationManager);
//
////        这边通过捕获OAuth2异常拦截用户查询失败
////        自定义的ExceptionHandler没法拦截到
//        endpoints.exceptionTranslator(e -> {
//            if (e instanceof OAuth2Exception) {
//                OAuth2Exception exception = (OAuth2Exception) e;
//                return ResponseEntity
//                        .status(exception.getHttpErrorCode())
//                        .body(new CustomOAuth2Exception("用户名或密码错误"));
//            } else {
//                throw e;
//            }
//        });
//    }
//}
