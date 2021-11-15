package com.github.nagatosingle.service.impl;

import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.request.UserRegister;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.manager.NagatoUserManager;
import com.github.nagatosingle.service.interfaces.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    @Override
    public NagatoResponseEntity createUser(UserRegister user) {
        mapper.createUser(user);
        
        return null;
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
