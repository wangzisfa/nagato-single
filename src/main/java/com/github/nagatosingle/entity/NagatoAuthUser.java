package com.github.nagatosingle.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 * <p>
 * date: 2021/10/22
 *
 * @author wangzisfa
 * @version 0.31
 */
@Data
public class NagatoAuthUser implements Serializable {
    
    private static final long serialVersionUID = -7258762650217819891L;
    
    private String username;
    
    private String password;
    
    private boolean accountNonExpired = true;
    
    private boolean accountNonLocked = true;
    
    private boolean credentialsNonExpired = true;
    
    private boolean enabled = true;
}
