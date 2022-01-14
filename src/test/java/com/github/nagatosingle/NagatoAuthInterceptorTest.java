package com.github.nagatosingle;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;


public class NagatoAuthInterceptorTest {
	@Test
	public void yamlParser() {
		YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
		YamlMapFactoryBean bean1 = new YamlMapFactoryBean();
		bean1.setResources(new ClassPathResource("uri-matchlist.yaml"));
		bean.setResources(new ClassPathResource("uri-matchlist.yaml"));
		Properties p = bean.getObject();
		System.out.println(p);
	}
}
