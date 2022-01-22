package com.github.nagatosingle.entity.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@Data
@Builder
public class UserLoginDTO {
    @Nullable
    public String remoteAddress;
    @Nullable
    public String verificationCode;
    @Nullable
    public String uuid;
    @Nullable
    public String phone;
    @Nullable
    public String username;
    public String password;
}
