package com.github.nagatosingle.controller;

import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.entity.request.UserLoginDTO;
import com.github.nagatosingle.entity.request.UserRegisterProfileDTO;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.service.interfaces.AccountService;
import com.github.nagatosingle.utils.SmsUtil;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
@Slf4j
public class AccountController {
//    @Autowired
    private final AccountService accountService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenService jwtTokenService;
    private final SmsUtil smsUtil;
//    @Autowired
//    private NagatoRedisClientDetailService redisClientDetailService;
//    private ConsumerTokenServices consumerTokenServices;

    /**
     * 添加用户
     * 实际上应该从后台添加, 不设置注册界面
     * @param userRegister 用户注册信息
     * @return ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterProfileDTO userRegister, HttpServletRequest request) {
        log.info("user phone : " + userRegister.getPhone());
        log.info("user code : " + userRegister.getVerificationCode());
        log.info("user ip : " + request.getRemoteAddr());
//        NagatoResponseEntity response = accountService.createUser(userRegister);
        return returnStatement(null);
    }

    /**
     * @param userLoginDTO 用户登录dto
     * @return 返回一个 access token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        NagatoResponseEntity response = accountService.validateUser(userLoginDTO);
        return returnStatement(response);
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("logout : " + token);
        NagatoResponseEntity response = accountService.invalidateUser(token);
        return returnStatement(response);
    }
    
    @PostMapping("/passwordRetrieve")
    public ResponseEntity<?> passwordRetrieve() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/sendVerificationCode")
    public ResponseEntity<?> sendVerificationCode(HttpServletRequest request) {
        String number = request.getParameter("number");
        String remoteAddress = request.getRemoteAddr();
        if (number == null)
            return new ResponseEntity<>(new NagatoResponseEntity().message(ResponseMessage.PARAMETER_NOT_MATCH),
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(smsUtil.sendSms(number, remoteAddress), HttpStatus.OK);
    }
    
    
    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @PostMapping("/updateUserIcon")
    public ResponseEntity<?> updateUserIcon() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private ResponseEntity<?> returnStatement(@Nullable NagatoResponseEntity response) {
        return response == null ? new ResponseEntity<>(HttpStatus.OK) :
                StringUtils.equals(response.getMessage(), ResponseMessage.OK) ?
                new ResponseEntity<>(response, HttpStatus.OK) :
                new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
