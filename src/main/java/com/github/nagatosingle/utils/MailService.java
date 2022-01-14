package com.github.nagatosingle.utils;

import com.github.nagatosingle.service.RedisService;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Description:
 * <p>
 * date: 2021/11/17
 * <p>
 *
 * @author wangzisfa
 */
@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final String emailFrom;
    private final RedisService redisService;


    public MailService(
            JavaMailSender mailSender,
            @Qualifier("verificationCodeEmailFrom")
                    String emailFrom,
            RedisService redisService
    ) {
        this.mailSender = mailSender;
        this.emailFrom = emailFrom;
        this.redisService = redisService;
    }

    public boolean sendVerificationCode(String address) {
        val code = redisService.getVerificationCode(address);
        if (code == null) {
            return false;
        }
        val message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(address);
        message.setSubject("NAGATO 验证码");
        message.setText("您的验证码是: " + code);
        mailSender.send(message);
        return true;
    }
}