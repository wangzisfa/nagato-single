package com.github.nagatosingle.entity.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Description: 用户注册补充个人信息
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@Data
@Builder
public class UserRegisterProfileDTO {
    public String realName;
    public String gender;
    public String password;
    public String passwordConfirm;
    public String username;
    @Nullable
    public String phone;
    @Nullable
    public String verificationCode;
    @Nullable
    public String remoteAddress;
}
