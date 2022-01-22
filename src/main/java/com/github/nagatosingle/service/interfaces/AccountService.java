package com.github.nagatosingle.service.interfaces;

import com.github.nagatosingle.entity.request.UserLoginDTO;
import com.github.nagatosingle.entity.request.UserRegisterProfileDTO;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
public interface AccountService {
    NagatoResponseEntity createUser(UserRegisterProfileDTO user);
    NagatoResponseEntity validateUserVerificationCode(UserLoginDTO user);
    NagatoResponseEntity validateUser(UserLoginDTO user);
    NagatoResponseEntity invalidateUser(String token);
    NagatoResponseEntity passwordRetrieve();
    NagatoResponseEntity changePassword();
    NagatoResponseEntity createVerificationCode();
    NagatoResponseEntity updateProfile();
    NagatoResponseEntity updateUserIcon();
}
