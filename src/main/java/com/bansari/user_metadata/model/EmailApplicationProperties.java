package com.bansari.user_metadata.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
@Configuration
public class EmailApplicationProperties {

	private String username;
	private String password;
}
