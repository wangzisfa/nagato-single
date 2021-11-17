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
public enum ResponseCode {
    SUCCESS,
    ERROR,
    WARN,
    INFO
}
