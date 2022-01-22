package com.github.nagatosingle.dao;

import com.github.nagatosingle.entity.NagatoAuthUser;
import com.github.nagatosingle.entity.NagatoRegisterProfile;
import com.github.nagatosingle.entity.NagatoUserProfile;
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
    NagatoUserProfile findUserProfileByUuid(String uuid);
    Integer findUserNoByUsername(String username);
    Integer findUserNoByUserNoGenerate(String userNoGenerate);
    Integer findUserNoByUserPhone(String phone);
    String findUserPasswordByUserNo(Integer userNo);
    String findUserNoGenerateByUserNo(Integer userNo);
    String findUserNoGenerateByPhone(String phone);
    String findUsernameByUserNo(Integer userNo);
    Integer findUsernameValidation(String username);
    Integer createPlainUser(NagatoRegisterProfile user);
    Integer createUserPassword(NagatoRegisterProfile user);
    Integer createUserIconDefault(Integer userNo, String defaultURL);
    Integer createUserCreditDefault(Integer userNo, Double defaultCredit);
    Integer createUserSignDefault(Integer userNo, String defaultSign);
    Integer createUserEmailDefault(Integer userNo);
}
