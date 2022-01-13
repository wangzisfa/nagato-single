package com.github.nagatosingle.entity.request;

import lombok.Builder;
import lombok.Data;

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
    public String uuid;
    public String username;
    public String password;
}
