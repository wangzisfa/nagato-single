package com.github.nagatosingle.service.impl;

import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoUserProfile;
import com.github.nagatosingle.entity.request.UserRegister;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.manager.NagatoUserManager;
import com.github.nagatosingle.service.interfaces.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public NagatoResponseEntity validateUser() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity invalidateUser() {
        return null;
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
