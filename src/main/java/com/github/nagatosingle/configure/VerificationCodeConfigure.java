package com.github.nagatosingle.configure;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.Duration;
import java.util.Properties;

/**
 * Description:
 * <p>
 * date: 2021/11/17
 * <p>
 *
 * @author wangzisfa
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({VerificationCodeConfigureProperties.class})
public class VerificationCodeConfigure {
    private final VerificationCodeConfigureProperties properties;
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        
        javaMailSender.setUsername(verificationCodeEmailFrom());
        javaMailSender.setPassword(verificationCodeEmailPassword());
        
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");
        
        
        return javaMailSender;
    }
    
    
    @Bean
    public String verificationCodeEmailFrom() {
        return properties.getFrom();
    }
    
    @Bean
    public String verificationCodeEmailPassword() {
        return properties.getPassword();
    }
    
    @Bean
    public Duration verificationCodeMinSendInterval() {
        return properties.getMinSendInterval();
    }
    
    @Bean
    public Duration verificationCodeExpireTime() {
        return properties.getExpireTime();
    }
}
