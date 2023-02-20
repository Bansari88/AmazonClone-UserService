package com.bansari.user_metadata.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.bansari.user_metadata.auth.AuthenticationRequest;
import com.bansari.user_metadata.auth.AuthenticationResponse;
import com.bansari.user_metadata.configuration.JwtService;
import com.bansari.user_metadata.entity.RoleEntity;
import com.bansari.user_metadata.entity.UserEntity;
import com.bansari.user_metadata.model.User;
import com.bansari.user_metadata.repository.RoleRepository;
import com.bansari.user_metadata.repository.UserRepository;
import com.bansari.user_metadata.util.FunctionalityUTIL;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private FunctionalityUTIL functionalityUTIL;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Override
	public User createUserAccount(User user) {
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		String encodedPassword = functionalityUTIL.encryptPassword(userEntity.getPassword());
		userEntity.setPassword(encodedPassword);

		String verificationCode = functionalityUTIL.generateVerificationCode();
		userEntity.setVerificationCode(verificationCode);
		
		userEntity.setEnabled(false);
		
		UserEntity savedUserEntity = userRepository.save(userEntity);
		
		RoleEntity role = roleRepository.findByRoleName("USER").get();
		
		userEntity.getRoles().add(role);
		role.getUsers().add(userEntity);
		userRepository.save(userEntity);

		user.setVerificationCode(verificationCode);
		user.setEnabled(false);
		user.setUserId(savedUserEntity.getUserId());
		
		try {
			functionalityUTIL.sendVerificationEmail(user, verificationCode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User verifyUserAccount(User user) {

		UserEntity userEntity = userRepository.findById(user.getUserId()).get();
		if (userEntity.getVerificationCode().equals(user.getVerificationCode())) {
			userEntity.setEnabled(true);
			userRepository.save(userEntity);
		}
		return user;
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
		UserEntity userEntity = userRepository.findByEmailId(request.getEmailId()).orElseThrow();
		var jwtToken = jwtService.generateToken(userEntity);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}
