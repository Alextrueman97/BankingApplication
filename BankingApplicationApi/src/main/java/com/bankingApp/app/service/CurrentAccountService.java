package com.bankingApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.repositories.CurrentAccountRepository;
import com.bankingApp.app.repositories.TransactionsRepository;

@Service
public class CurrentAccountService {

	@Autowired
	private CurrentAccountRepository currentAccountRepository;
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	public CurrentAccount createNewCurrentAccount(CurrentAccount currentAccount) {
		return currentAccountRepository.save(currentAccount);
	}
	
	public List<CurrentAccount> getCurrentAccountByUser(UserAccount userAccount){
		return currentAccountRepository.findByAccountId(userAccount);
	}
	
	public CurrentAccount saveCurrentAccount(CurrentAccount currentAccount) {
		return currentAccountRepository.save(currentAccount);
	}
	
	public CurrentAccount deposit(CurrentAccount currentAccount, double amount) {
        double newBalance = currentAccount.getBalance() + amount;
        currentAccount.setBalance(newBalance);
        return currentAccountRepository.save(currentAccount);
	}
	
	public CurrentAccount withdraw(CurrentAccount currentAccount, double amount) {
	    double currentBalance = currentAccount.getBalance();
	    double overdraftAmount = currentAccount.getOverdraftAmount();
	    if ((currentBalance - amount) < -overdraftAmount) {
	        return null;
	    } else {
	        double newBalance = currentBalance - amount;
	        currentAccount.setBalance(newBalance);
	        return currentAccountRepository.save(currentAccount);
	    }
	}
}

