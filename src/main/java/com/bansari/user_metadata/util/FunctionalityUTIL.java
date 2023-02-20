package com.bansari.user_metadata.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bansari.user_metadata.model.User;

@Component
public class FunctionalityUTIL {

	@Autowired
	private JavaMailSender mailSender;

	public String encryptPassword(String passwordPlainText) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(passwordPlainText);
	}

	public String generateVerificationCode() {
		Random random = new Random();
	    int randomNumber = random.nextInt(999999);
	    return String.format("%06d", randomNumber);
	}

	public void sendVerificationEmail(User user, String verificationCode)
			throws MessagingException, UnsupportedEncodingException {
		//String toAddress = user.getEmailId();
		String toAddress = "";
		String fromAddress = "";
		String senderName = "Amazon";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please use a verification code below to verify your registration:<br>"
				+ "<h3>[[verificationCode]]</h3>" + "Thank you,<br>" + "Amazon.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getUsername());

		content = content.replace("[[verificationCode]]", verificationCode);

		helper.setText(content, true);

		mailSender.send(message);

	}
}
