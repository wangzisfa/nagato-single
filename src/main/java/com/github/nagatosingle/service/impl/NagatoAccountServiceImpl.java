package com.github.nagatosingle.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alibaba.fastjson.JSON;
import com.github.nagatosingle.constants.RedisKey;
import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoRegisterProfile;
import com.github.nagatosingle.entity.request.UserLoginDTO;
import com.github.nagatosingle.entity.request.UserRegisterProfileDTO;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.manager.NagatoUserManager;
import com.github.nagatosingle.service.interfaces.AccountService;
import com.github.nagatosingle.utils.SmsUtil;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import com.github.nagatosingle.utils.jwt.JwtUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@Service
@Slf4j
public class NagatoAccountServiceImpl implements AccountService {
    @Autowired
    private NagatoUserManager manager;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private JwtTokenService tokenService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
//    private boolean userRegisterCheck(UserRegister user) {
//
//    }
    
    /**
     * 创建用户
     * @param user 用户注册信息
     * @return Response
     */
    @Override
    @Transactional
    public NagatoResponseEntity createUser(UserRegisterProfileDTO user) {
        String bcryptPassword;
        String uuid;
        //用户名校验
        if (mapper.findUsernameValidation(user.getUsername()) == 1) {
            return new NagatoResponseEntity().message(ResponseMessage.USER_NAME_VALIDATION_FAILED);
        }
        //密码检验
        if (!StringUtils.equals(user.getPasswordConfirm(), user.getPassword())) {
            log.info("两次密码: " + user.getPasswordConfirm() + " " + user.getPassword());
            return new NagatoResponseEntity().message(ResponseMessage.USER_NOT_FOUND);
        }
        //密码加密
        bcryptPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

        //faceId可选
        //userNoGenerate生成
        uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        //email模块等待
        //当前用户权限写入
        //用户头像写入


        // 验证码比对
        assert user.getRemoteAddress() != null;
        String redisCode = (String) redisTemplate.opsForValue().get(user.getRemoteAddress());
        if (!SmsUtil.verify(redisCode, user.getVerificationCode())) {
            return new NagatoResponseEntity()
                    .message(ResponseMessage.SMS_VERIFICATION_CODE_NOT_MATCH);
        }
        
        //写入数据库
        NagatoRegisterProfile register = NagatoRegisterProfile.builder()
                .uuid(uuid)
                .username(user.getUsername())
                .realName(user.getRealName())
                .gender(user.getGender())
                .password(bcryptPassword)
                .phone(user.getPhone())
                .build();
//        UserRegisterProfileDTO register = UserRegisterProfileDTO.builder()
//                .username(user.getUsername())
//                .password(bcryptPassword)
//                .build();
        manager.createAccount(register);
        return new NagatoResponseEntity().message(ResponseMessage.OK);
    }

    @Override
    public NagatoResponseEntity validateUserVerificationCode(UserLoginDTO user) {
        String codeInRedis = (String) redisTemplate.opsForValue().get(user.getRemoteAddress());

        if (StringUtils.equals(user.getVerificationCode(), codeInRedis)) {
            // 验证成功后删除键
            if (Boolean.TRUE.equals(redisTemplate.delete(user.getRemoteAddress())))
                return new NagatoResponseEntity()
                        .message(ResponseMessage.OK);
            else
                return new NagatoResponseEntity()
                        .message(ResponseMessage.SERVER_ERROR);
        }
        return new NagatoResponseEntity()
                .message(ResponseMessage.SMS_VERIFICATION_CODE_NOT_MATCH);
    }


    @Override
    public NagatoResponseEntity validateUser(UserLoginDTO user) {
        // 分为哪些步骤呢?
        // 用户名 密码验证, 验证成功就进入
        // 可以是用户名也可以是工号
        // 用户名合法性, 密码合法性
        Integer userNo;

        // 参数兜底
        if (user.getUsername() == null && user.getUuid() == null && user.getPhone() == null) {
            return new NagatoResponseEntity()
                    .message(ResponseMessage.PARAMETER_NOT_MATCH);
        }

        // 账户登录参数判断
        userNo = getUserLoginKey(user);

        // 防止重复登录
        if (redisTemplate.opsForHash().get(RedisKey.ACCESS_TOKEN_REVERSE, user.getUuid()) != null) {
            return new NagatoResponseEntity()
                    .message(ResponseMessage.USER_ALREADY_LOGIN);
        }

        // 查找比对密码
        String bcrypted = mapper.findUserPasswordByUserNo(userNo);
        log.info("user password : " + user.getPassword());
        char[] chars = user.getPassword().toCharArray();
        log.info("user password chars : " + Arrays.toString(chars));
        log.info("bcrypted : " + bcrypted);
        BCrypt.Result r = BCrypt.verifyer().verify(user.getPassword().toCharArray(), bcrypted);
        if (r.verified) {
            String token = tokenService.generateToken(JwtUserDetail.builder()
                            .username(user.getUsername())
                            .userNoGenerate(user.getUuid())
                            .build(),
                    true);
            return new NagatoResponseEntity()
                    .data(JSON.toJSONString(token))
                    .message(ResponseMessage.OK);
        } else {
            return new NagatoResponseEntity()
                    .message(ResponseMessage.USER_NOT_FOUND);
        }
    }

    public Integer getUserLoginKey(UserLoginDTO user) {
        Integer userNo;
        if (user.getUsername() != null) {
            userNo = mapper.findUserNoByUsername(user.getUsername());
            log.info(String.valueOf(userNo));
            user.setUuid(mapper.findUserNoGenerateByUserNo(userNo));
        } else if (user.getUuid() != null) {
            userNo = mapper.findUserNoByUserNoGenerate(user.getUuid());
            log.info(String.valueOf(userNo));
            user.setUsername(mapper.findUsernameByUserNo(userNo));
        } else {
            userNo = mapper.findUserNoByUserPhone(user.getPhone());
            log.info(String.valueOf(user));
            user.setUuid(mapper.findUserNoGenerateByPhone(user.getPhone()));
        }

        return userNo;
    }
    
    @Override
    public NagatoResponseEntity invalidateUser(String token) {
        String uuid = (String) redisTemplate.opsForHash().get(RedisKey.ACCESS_TOKEN, token);
        redisTemplate.boundHashOps(RedisKey.ACCESS_TOKEN).delete(token);
        redisTemplate.boundHashOps(RedisKey.ACCESS_TOKEN_REVERSE).delete(uuid);
        return new NagatoResponseEntity()
                .message(ResponseMessage.OK);
    }
    
    @Override
    public NagatoResponseEntity passwordRetrieve() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity changePassword() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity createVerificationCode() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity updateProfile() {
        return null;
    }
    
    @Override
    public NagatoResponseEntity updateUserIcon() {
        return null;
    }
}
