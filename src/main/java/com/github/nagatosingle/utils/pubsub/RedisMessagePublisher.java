package com.github.nagatosingle.utils.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * Description:
 * <p>
 * date: 2021/11/15
 *
 * @author wangzisfa
 * @version 0.31
 */
@Slf4j
public class RedisMessagePublisher implements MessagePublisher{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ChannelTopic channelTopic;
    
    private RedisMessagePublisher() {}
    
    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic) {
        this.channelTopic = channelTopic;
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public void publish(String message) {
        log.info("RedisMessagePublisher " + channelTopic.getTopic());
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
