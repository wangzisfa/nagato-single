package com.github.nagatosingle.controller;

import com.github.nagatosingle.utils.pubsub.MessagePublisher;
import com.github.nagatosingle.utils.pubsub.RedisMessagePublisher;
import com.github.nagatosingle.utils.pubsub.RedisMessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Description:
 * <p>
 * date: 2021/10/22
 *
 * @author wangzisfa
 * @version 0.31
 */
@RestController
@RequestMapping("/basic")
@Slf4j
public class BaseController {
    
    
    @Autowired
    private MessagePublisher messagePublisher;
    
    @GetMapping("hello")
    public String hello() {
        String message = "message : " + UUID.randomUUID();
        messagePublisher.publish(message);
        log.info(String.valueOf(RedisMessageSubscriber.messageList));
        return "hello";
    }
    
    
    
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/invalidate")
    public ResponseEntity<?> invalidateToken() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
