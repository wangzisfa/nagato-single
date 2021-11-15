package com.github.nagatosingle.constants.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Description:
 * <p>
 * date: 2021/11/09
 *
 * @author wangzisfa
 * @version 0.31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Manager {
}
