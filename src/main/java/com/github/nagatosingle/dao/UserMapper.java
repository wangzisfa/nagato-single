package com.github.nagatosingle.dao;

import com.github.nagatosingle.entity.NagatoAuthUser;
import com.github.nagatosingle.entity.NagatoUserProfile;
import com.github.nagatosingle.entity.request.UserRegister;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * <p>
 * date: 2021/11/09
 *
 * @author wangzisfa
 * @version 0.31
 */
@Mapper
public interface UserMapper {
    NagatoAuthUser findAuthUserByUsername(String username);
    NagatoUserProfile findUserProfileByUsername(String username);
    void createUser(NagatoUserProfile user);
}
