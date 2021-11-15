package com.github.nagatosingle.controller;

import com.github.nagatosingle.entity.request.UserRegister;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.service.interfaces.AccountService;
import com.github.nagatosingle.service.interfaces.UserService;
import com.github.nagatosingle.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@AllArgsConstructor
@RestController
@RequestMapping("/platform/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserUtils userUtils;
//    @Autowired
//    private NagatoRedisClientDetailService redisClientDetailService;
    private ConsumerTokenServices consumerTokenServices;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(UserRegister userRegister) {
        return userUtils.userRegisterCheck(userRegister) &&
                accountService.createUser(userRegister).get("data").equals(true) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "Bearer ", "");
        
        if (!consumerTokenServices.revokeToken(token)) {
            throw new Exception("退出登录失败");
        }
        
        return new ResponseEntity<>(new NagatoResponseEntity().message("退出登录成功"), HttpStatus.OK);
    }
    
    @PostMapping("/passwordRetrieve")
    public ResponseEntity<?> passwordRetrieve() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<?> sendVerificationCode() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @PostMapping("/updateUserIcon")
    public ResponseEntity<?> updateUserIcon() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}