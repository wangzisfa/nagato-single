package com.github.nagatosingle.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nagatosingle.utils.pubsub.MessagePublisher;
import com.github.nagatosingle.utils.pubsub.RedisMessagePublisher;
import com.github.nagatosingle.utils.pubsub.RedisMessageSubscriber;
import com.github.nagatosingle.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Description:
 * <p>
 * date: 2021/10/20
 *
 * @author wangzisfa
 * @version 0.31
 */
@Configuration
public class NagatoRedisConfigure {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    
    /**
     * redis 发布订阅频道名
     */
    @Value("${redis.channel.name}")
    private String REDIS_CHANNEL_NAME;
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        
        return template;
    }
    
//    @Bean
//    public RedisTemplate<String, String> redisTemplateString() {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//
//        return template;
//    }
    
//    @Bean
//    public RedisService redisService() {
//        return new RedisService();
//    }
    
    
    /**
     * 消息监听
     * @return 消息监听适配器
     */
    @Bean
    public MessageListenerAdapter redisMessageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }
    
    
    /**
     * 消息监听容器
     * @param redisConnectionFactory 工厂
     * @return 消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer =
                new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(redisMessageListener(), topic());
        
        return redisMessageListenerContainer;
    }
    
    @Bean
    public MessagePublisher messagePublisher() {
        return new RedisMessagePublisher(redisTemplate(), topic());
    }
    
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(REDIS_CHANNEL_NAME);
    }

}
