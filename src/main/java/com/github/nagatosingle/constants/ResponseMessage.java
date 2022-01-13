package com.github.nagatosingle.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Description:
 * <p>
 * date: 2021/11/17
 * <p>
 *
 * @author wangzisfa
 */
@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum ResponseMessage {
    ;
    public static final String OK = "操作成功";
    public static final String USER_NOT_FOUND = "用户名或密码错误";
    public static final String USER_NAME_VALIDATION_FAILED = "用户名不合法";
}
