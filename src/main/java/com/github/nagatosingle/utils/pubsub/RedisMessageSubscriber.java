package com.github.nagatosingle.utils.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>
 * date: 2021/11/15
 *
 * @author wangzisfa
 * @version 0.31
 */

@Service
public class RedisMessageSubscriber implements MessageListener {
    public static List<String> messageList = new ArrayList<>();
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        System.out.println("Message received : " + message);
    }
}
