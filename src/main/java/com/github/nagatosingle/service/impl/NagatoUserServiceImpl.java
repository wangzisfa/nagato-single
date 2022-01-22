package com.github.nagatosingle.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.entity.NagatoUserProfile;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.manager.NagatoUserManager;
import com.github.nagatosingle.service.interfaces.UserService;
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
public class NagatoUserServiceImpl implements UserService {
    @Autowired
    private NagatoUserManager userManager;
    
    /**
     * basic user profile
     * @param uuid uuid
     * @return Response
     */
    @Override
    public NagatoResponseEntity findByUuid(String uuid) {

        return new NagatoResponseEntity()
                .data(userManager.getUserProfile(uuid))
                .message(ResponseMessage.OK);
    }
    
    /**
     * 主界面用户信息请求
     * @param uuid 用户的uuid
     * @return Response
     */
    @Override
    public NagatoResponseEntity getUserDetailInfoByUserGenerated(String uuid) {
        
        
        return null;
    }
}
