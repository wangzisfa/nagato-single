package com.github.nagatosingle.dao;

import com.github.nagatosingle.entity.NagatoAuthUser;
import com.github.nagatosingle.entity.NagatoRegisterProfile;
import com.github.nagatosingle.entity.NagatoUserProfile;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

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
    Integer findUserNoByUsername(String username);
    Integer findUserNoByUserNoGenerate(String userNoGenerate);
    String findUserPasswordByUserNo(Integer userNo);
    String findUserNoGenerateByUserNo(Integer userNo);
    String findUsernameByUserNo(Integer userNo);
    Integer findUsernameValidation(String username);
    Integer createPlainUser(NagatoRegisterProfile user);
    void createUserPassword(NagatoRegisterProfile user);
}
