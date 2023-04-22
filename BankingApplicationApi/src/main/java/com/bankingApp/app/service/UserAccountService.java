package com.bankingApp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.repositories.UserAccountRepository;

@Service
public class UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	public UserAccount login(String username, String password) {
		return userAccountRepository.login(username, password);
	}
	
	public UserAccount register(UserAccount userAccount) {
		return userAccountRepository.save(userAccount);
	}
}
