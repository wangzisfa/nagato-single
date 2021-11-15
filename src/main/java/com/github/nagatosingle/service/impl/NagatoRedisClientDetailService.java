//package com.github.nagatosingle.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.nagatosingle.service.RedisService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.client.BaseClientDetails;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import javax.sql.DataSource;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * Created With IntelliJ IDEA
// * 这个{@code detailsService }实际上是用来识别 client 的, 如果在 {@link com.github.nagatosingle.configure.NagatoAuthorizationServerConfigure}
// * 没有配置 {@code configure(ClientDetailsServiceConfigurer clients) { clients.withClientDetails() }}
// * 而是使用 {@code clients.inMemory() }存储则无关紧要
// * @Author: wangzisfa
// * @Date: 2021/10/20
// * @Description:
// */
//@Service
//@Slf4j
//public class NagatoRedisClientDetailService extends JdbcClientDetailsService {
//
//    /**
//     * 定义映射在redis中的账户登录 token 详细信息
//     */
//    private static final String CACHE_CLIENT_KEY = "client_details";
//
//
//    private final RedisService redisService;
//
//    /**
//     *
//     * @param dataSource mysql数据源通过maven spring-boot-jdbc-starter自动注入
//     * @param redisService 通过common模块中的 {@link com.github.nagatosingle.configure.NagatoRedisConfigure} 定义的 Bean 注入
//     */
//    public NagatoRedisClientDetailService(DataSource dataSource, RedisService redisService) {
//        super(dataSource);
//        this.redisService = redisService;
//    }
//
//    @Override
//    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
//        ClientDetails clientDetails = null;
//        String value = (String) redisService.hget(CACHE_CLIENT_KEY, clientId);
//        if (StringUtils.isBlank(value)) {
//            clientDetails = cacheAndGetClient(clientId);
//        } else {
//            clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
//        }
//
//        return clientDetails;
//    }
//
//    /**
//     * 这边的client id实际上是用于 spring security 进行识别client的, 可以当作一个前端服务器的唯一标识
//     * 如果其他前端请求如 postman 或者 curl 进行请求的时候输入的 client_id 或 client_secret 错误的话
//     * 将会返回 403
//     * 用户在注册的时候的用户名和密码实际上和这个没有任何关系.
//     * @param clientId
//     * @return
//     */
//    public ClientDetails cacheAndGetClient(String clientId) {
//        ClientDetails clientDetails = null;
////        spring 中的 jdbc 方法
//        System.out.println("通过 cache");
//        clientDetails = super.loadClientByClientId(clientId);
//        if (clientDetails != null) {
//
//            BaseClientDetails baseClientDetails = (BaseClientDetails) clientDetails;
//            Set<String> autoApproveScopes = baseClientDetails.getAutoApproveScopes();
//            if (!CollectionUtils.isEmpty(autoApproveScopes)) {
//                baseClientDetails.setAutoApproveScopes(autoApproveScopes.stream().map(this::convert).collect(Collectors.toSet()));
//            }
//            redisService.hset(CACHE_CLIENT_KEY, clientId, JSONObject.toJSONString(baseClientDetails));
//        }
//
//        return clientDetails;
//    }
//
//    public void clearCache(String clientId) {
//        if (redisService.hget(CACHE_CLIENT_KEY, clientId) != null) {
//            redisService.hdel(CACHE_CLIENT_KEY, clientId);
//        }
//    }
//
////    public void removeRedisCache(String clientId) {
////        redisService.hdel(CACHE_CLIENT_KEY, clientId);
////    }
////
////    public void loadAllClientToCache() {
////        if (redisService.hasKey(CACHE_CLIENT_KEY)) {
////            return ;
////        }
////        log.info("将oauth_client_details全表刷入redis");
////        List<ClientDetails> list = super.listClientDetails();
////
////        if (CollectionUtils.isEmpty(list)) {
////            log.error("oauth_client_details表数据为空, 请检查");
////        }
////        list.forEach(client -> redisService.hset(CACHE_CLIENT_KEY, client.getClientId(), JSONObject.toJSONString(client)));
////    }
//
//    private String convert(String value) {
//        final String LogicTrue = "1";
//        return LogicTrue.equals(value) ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
//    }
//}
