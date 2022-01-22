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
    public static final String SMS_ERROR = "短信服务状态异常";
    public static final String SMS_REDIS_KEY_NOT_EXPIRED = "当前ip持有验证码未过期";
    public static final String PARAMETER_NOT_MATCH = "当前请求参数不匹配";
    public static final String SMS_VERIFICATION_CODE_NOT_MATCH = "验证码错误";
    public static final String USER_ALREADY_LOGIN = "当前用户已登录";
    public static final String SERVER_ERROR = "当前系统异常, 请稍后再试";
}
