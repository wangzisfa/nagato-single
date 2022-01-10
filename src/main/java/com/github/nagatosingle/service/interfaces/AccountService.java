package com.github.nagatosingle.service.interfaces;

import com.github.nagatosingle.entity.request.UserLogin;
import com.github.nagatosingle.entity.request.UserRegister;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import org.springframework.http.ResponseEntity;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
public interface AccountService {
    NagatoResponseEntity createUser(UserRegister user);
    NagatoResponseEntity validateUser(UserLogin user);
    NagatoResponseEntity invalidateUser(String token);
    NagatoResponseEntity passwordRetrieve();
    NagatoResponseEntity changePassword();
    NagatoResponseEntity createVerificationCode();
    NagatoResponseEntity updateProfile();
    NagatoResponseEntity updateUserIcon();
}
