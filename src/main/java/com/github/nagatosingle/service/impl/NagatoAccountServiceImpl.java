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
//        String redisCode = redisTemplate.opsForValue().get()
        
        //写入数据库
        NagatoRegisterProfile register = NagatoRegisterProfile.builder()
                .uuid(uuid)
                .username(user.getUsername())
                .realName(user.getRealName())
                .gender(user.getGender())
                .password(bcryptPassword)
                .build();
//        UserRegisterProfileDTO register = UserRegisterProfileDTO.builder()
//                .username(user.getUsername())
//                .password(bcryptPassword)
//                .build();
        manager.createAccount(register);
        return new NagatoResponseEntity().message(ResponseMessage.OK);
    }


    @Override
    public NagatoResponseEntity validateUser(UserLoginDTO userLoginDTO) {
        // 分为哪些步骤呢?
        // 用户名 密码验证, 验证成功就进入
        // 可以是用户名也可以是工号
        // 用户名合法性, 密码合法性
        Integer userNo;

        // 账户登录参数判断
        if (userLoginDTO.getUuid() == null) {
            userNo = mapper.findUserNoByUsername(userLoginDTO.getUsername());
            log.info(String.valueOf(userNo));
            userLoginDTO.setUuid(mapper.findUserNoGenerateByUserNo(userNo));
        }
        else {
            userNo = mapper.findUserNoByUserNoGenerate(userLoginDTO.getUuid());
            log.info(String.valueOf(userNo));
            userLoginDTO.setUsername(mapper.findUsernameByUserNo(userNo));
        }

        // 查找比对密码
        String bcrypted = mapper.findUserPasswordByUserNo(userNo);
        log.info("user password : " + userLoginDTO.getPassword());
        char[] chars = userLoginDTO.getPassword().toCharArray();
        log.info("user password chars : " + Arrays.toString(chars));
        log.info("bcrypted : " + bcrypted);
        BCrypt.Result r = BCrypt.verifyer().verify(userLoginDTO.getPassword().toCharArray(), bcrypted);
        if (r.verified) {
            String token = tokenService.generateToken(JwtUserDetail.builder()
                            .username(userLoginDTO.getUsername())
                            .userNoGenerate(userLoginDTO.getUuid())
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
    
    @Override
    public NagatoResponseEntity invalidateUser(String token) {
        redisTemplate.boundHashOps(RedisKey.ACCESS_TOKEN).delete(token);

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
