package com.bansari.user_metadata.service;

import org.springframework.stereotype.Service;

import com.bansari.user_metadata.auth.AuthenticationRequest;
import com.bansari.user_metadata.auth.AuthenticationResponse;
import com.bansari.user_metadata.model.User;

@Service
public interface UserService {

	public User createUserAccount(User user);
	public User verifyUserAccount(User user);
	public AuthenticationResponse authenticate(AuthenticationRequest request);
}
