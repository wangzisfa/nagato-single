package com.github.nagatosingle.manager;

import com.github.nagatosingle.constants.annotations.Manager;
import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoUserProfile;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    
    public NagatoUserProfile getUserProfile(String username) {
        return userMapper.findUserProfileByUsername(username);
    }
}
