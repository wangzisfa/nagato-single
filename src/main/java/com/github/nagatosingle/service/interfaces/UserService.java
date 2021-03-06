package com.github.nagatosingle.service.interfaces;

import com.github.nagatosingle.entity.response.NagatoResponseEntity;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
public interface UserService {
    NagatoResponseEntity findByUuid(String uuid);
    NagatoResponseEntity getUserDetailInfoByUserGenerated(String uuid);
}
