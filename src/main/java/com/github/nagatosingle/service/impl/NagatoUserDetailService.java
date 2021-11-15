package com.github.nagatosingle.service.impl;

import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoAuthUser;
import com.github.nagatosingle.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Description:
 * <p>
 * date: 2021/10/22
 *
 * @author wangzisfa
 * @version 0.31
 */
@Service
@Slf4j
public class NagatoUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NagatoAuthUser user = userMapper.findAuthUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("当前用户名不存在或密码错误");
        } else {
            System.out.println(user.getPassword());
            System.out.println("通过mybatis 查询到");
            return new User(username, user.getPassword(), user.isEnabled(),
                    user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                    user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("user:add"));
        }
        

//        NagatoAuthUser user = new NagatoAuthUser();
//        user.setUsername(username);
//        user.setPassword(this.encoder.encode("123456"));
//        System.out.println("通过 UserDetailService" + username);
    }
}