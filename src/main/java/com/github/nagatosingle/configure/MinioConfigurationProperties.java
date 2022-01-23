package com.github.nagatosingle.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("minio")
public class MinioConfigurationProperties {
	private static final long serialVersionUID = 1602690186208410173L;

	private String url;
	private String user;
	private String password;
}
