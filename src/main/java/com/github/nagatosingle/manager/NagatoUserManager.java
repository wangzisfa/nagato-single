package com.github.nagatosingle.manager;

import com.github.nagatosingle.constants.NagatoUserDefault;
import com.github.nagatosingle.constants.annotations.Manager;
import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoRegisterProfile;
import com.github.nagatosingle.entity.NagatoUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * <p>
 * date: 2021/10/22
 *
 * @author wangzisfa
 * @version 0.31
 */
@Manager
public class NagatoUserManager {
    @Autowired
    private UserMapper userMapper;
    
    
    public NagatoUserProfile getUserProfile(String uuid) {
        NagatoUserProfile user = userMapper.findUserProfileByUuid(uuid);
        if (user.getUserCurrentMood() == null)
            user.setUserCurrentMood(NagatoUserDefault.USER_PROPERTY_DEFAULT);
        if (user.getEmail() == null)
            user.setEmail(NagatoUserDefault.USER_PROPERTY_DEFAULT);

        return user;
    }

    @Transactional
    public void createAccount(NagatoRegisterProfile user) {
        Integer userNo = userMapper.createPlainUser(user);
        System.out.println(userNo + " asdfasdffff");
        user.setUserNo(userNo);
        userMapper.createUserPassword(user);
        userMapper.createUserIconDefault(userNo, NagatoUserDefault.USER_ICON_DEFAULT);
        userMapper.createUserCreditDefault(userNo, NagatoUserDefault.USER_CREDIT_DEFAULT);
        userMapper.createUserSignDefault(userNo, NagatoUserDefault.USER_SIGN_DEFAULT);
        userMapper.createUserEmailDefault(userNo);
    }
}
