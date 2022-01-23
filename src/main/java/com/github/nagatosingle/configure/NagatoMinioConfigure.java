package com.github.nagatosingle.configure;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class NagatoMinioConfigure {
	private final MinioConfigurationProperties configurationProperties;


	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
				.endpoint(configurationProperties.getUrl(), 9001, false)
				.credentials(configurationProperties.getUser(), configurationProperties.getPassword())
				.build();
	}
}
