package com.bankingApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.repositories.CurrentAccountRepository;

@Service
public class CurrentAccountService {

	@Autowired
	private CurrentAccountRepository currentAccountRepository;
	
	public CurrentAccount createNewCurrentAccount(CurrentAccount currentAccount) {
		return currentAccountRepository.save(currentAccount);
	}
	
	public List<CurrentAccount> getCurrentAccountByUser(UserAccount userAccount){
		return currentAccountRepository.findByAccountId(userAccount);
	}
	
	public CurrentAccount saveCurrentAccount(CurrentAccount currentAccount) {
		return currentAccountRepository.save(currentAccount);
	}
	
	public CurrentAccount deposit(CurrentAccount currentAccountNumber, double amount) {
		return currentAccountRepository.deposit(currentAccountNumber, amount);
	}
	
	public CurrentAccount withdraw(CurrentAccount currentAccountNumber, double amount) {
	    return currentAccountRepository.withdraw(currentAccountNumber, amount);
	}
}
