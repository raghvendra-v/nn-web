package com.nn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
class CustomRestMvcConfiguration {
	@Value("${rest.api.base.path}")
	String basePath;

	@Value("${rest.api.returnBodyOnCreate}")
	Boolean returnBody;

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {

		return new RepositoryRestConfigurerAdapter() {

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				config.setBasePath(basePath);
				config.setReturnBodyForPutAndPost(returnBody);
			}
		};
	}
}
