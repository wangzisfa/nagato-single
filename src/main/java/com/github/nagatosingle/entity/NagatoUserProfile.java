package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Description:
 * <p>
 * date: 2021/11/10
 *
 * @author wangzisfa
 * @version 0.31
 */
@Data
@Builder
public class NagatoUserProfile {
    public String username;
    public int gender;
    public String userIcon;
}
