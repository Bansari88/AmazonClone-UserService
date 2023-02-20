package com.bansari.user_metadata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	private Long userId;
	private String username;
	private String emailId;
	private String password;
	private boolean enabled;
	private String verificationCode;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
}
