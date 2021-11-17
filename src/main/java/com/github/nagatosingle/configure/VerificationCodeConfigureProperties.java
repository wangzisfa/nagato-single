package com.github.nagatosingle.configure;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Description:
 * <p>
 * date: 2021/11/17
 * <p>
 *
 * @author wangzisfa
 */
@Data
@NoArgsConstructor
@ConfigurationProperties("verification-code")
public class VerificationCodeConfigureProperties {
    private String from;
    private String password;
    private Duration minSendInterval;
    private Duration expireTime;
}
