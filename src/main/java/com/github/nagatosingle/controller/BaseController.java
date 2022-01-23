package com.github.nagatosingle.controller;

import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.utils.pubsub.MessagePublisher;
import com.github.nagatosingle.utils.pubsub.RedisMessagePublisher;
import com.github.nagatosingle.utils.pubsub.RedisMessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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


    public static ResponseEntity<?> returnStatement(@Nullable NagatoResponseEntity response) {
        return response == null ? new ResponseEntity<>("测试接口",HttpStatus.OK) :
                StringUtils.equals(response.getMessage(), ResponseMessage.OK) ?
                        new ResponseEntity<>(response, HttpStatus.OK) :
                        new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param request request
     * @return without Bearer
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(token.indexOf(" ") + 1);
        System.out.println("wangzisfa " + token);
        return token;
    }
    
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
