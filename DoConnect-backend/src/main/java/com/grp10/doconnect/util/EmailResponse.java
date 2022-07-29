package com.grp10.doconnect.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailResponse {
	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String toEmail, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("sks120498@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		System.out.println("sending email");

		mailSender.send(message);

		System.out.println("email Sent successfully!! ");

	}

}
