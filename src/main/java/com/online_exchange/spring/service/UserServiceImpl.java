package com.online_exchange.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_exchange.spring.model.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	MailService mailService;

	@Override
	public void sendUserConfirmation(User user) {
		mailService.sendEmail(user);
	}
	
}
