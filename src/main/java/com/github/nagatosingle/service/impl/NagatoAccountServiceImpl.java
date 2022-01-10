package com.github.nagatosingle.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alibaba.fastjson.JSON;
import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoUserProfile;
import com.github.nagatosingle.entity.request.UserLogin;
import com.github.nagatosingle.entity.request.UserRegister;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.manager.NagatoUserManager;
import com.github.nagatosingle.service.interfaces.AccountService;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import com.github.nagatosingle.utils.jwt.JwtUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@Service
@Slf4j
public class NagatoAccountServiceImpl implements AccountService {
    @Autowired
    private NagatoUserManager manager;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private JwtTokenService tokenService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
//    private boolean userRegisterCheck(UserRegister user) {
//
//    }
    
    /**
     * 创建用户
     * @param user 用户注册信息
     * @return Response
     */
    @Override
    public NagatoResponseEntity createUser(UserRegister user) {
//        密码检验
        if (!StringUtils.equals(user.password, user.passwordConfirm)) {
            return new NagatoResponseEntity().message(ResponseMessage.USER_NOT_FOUND);
        }
//        当前用户权限写入


//        用户头像写入

//
        NagatoUserProfile userProfile = NagatoUserProfile.builder()
                .username(user.username)
                .password(user.password)
                .uuid(UUID.randomUUID().toString())
                .build();
        mapper.createUser(userProfile);
        
        return new NagatoResponseEntity().message(ResponseMessage.OK);
    }
    
    @Override
    public NagatoResponseEntity validateUser(UserLogin userLogin) {
        //分为哪些步骤呢?
        //用户名 密码验证, 验证成功就进入
        //可以是用户名也可以是工号
        //用户名合法性, 密码合法性
        long userNo;
        if (userLogin.getUserNoGenerate() == null) {
            userNo = mapper.findUserNoByUsername(userLogin.getUsername());
            userLogin.setUserNoGenerate(mapper.findUserNoGenerateByUserNo(userNo));
        }
        else {
            userNo = mapper.findUserNoByUserNoGenerate(userLogin.getUserNoGenerate());
            userLogin.setUsername(mapper.findUserNameByUserNo(userNo));
        }
        String bcrypted = mapper.findUserPasswordByUserNo(userNo);
        BCrypt.Result r = BCrypt.verifyer().verify(userLogin.getPassword().toCharArray(), bcrypted);
        if (r.verified) {
            String token = tokenService.generateToken(JwtUserDetail.builder()
                            .username(userLogin.getUsername())
                            .userNoGenerate(userLogin.getUserNoGenerate())
                            .build(),
                    true);
            return new NagatoResponseEntity()
                    .data(JSON.toJSONString(token))
                    .message(ResponseMessage.OK);
        } else {
            return new NagatoResponseEntity()
                    .message(ResponseMessage.USER_NOT_FOUND);
        }
    }
    
    @Override
    public NagatoResponseEntity invalidateUser(String token) {
        String resToken = token.substring(token.indexOf(" ") + 1);
        redisTemplate.boundHashOps("access-token").delete(resToken);

        return new NagatoResponseEntity()
                .message(ResponseMessage.OK);
    }
    
    @Override
    public NagatoResponseEntity passwordRetrieve() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity changePassword() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity createVerificationCode() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity updateProfile() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity updateUserIcon() {
        return null;
    }
}
