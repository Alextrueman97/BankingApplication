package com.bankingApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingApp.app.models.SavingAccount;
import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.repositories.SavingAccountRepository;
import com.bankingApp.app.repositories.TransactionsRepository;

@Service
public class SavingAccountService {

	@Autowired
	private SavingAccountRepository savingAccountRepository;
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	public SavingAccount createNewSavingAccount(SavingAccount savingAccount) {
		return savingAccountRepository.save(savingAccount);
	}
	
	public List<SavingAccount> getSavingAccountByUser(UserAccount userAccount){
		return savingAccountRepository.findByAccountId(userAccount);
	}
	
	public SavingAccount saveSavingAccount(SavingAccount savingAccount) {
		return savingAccountRepository.save(savingAccount);
	}
	
	public SavingAccount deposit(SavingAccount savingAccount, double amount) {
        double newBalance = savingAccount.getBalance() + amount;
        savingAccount.setBalance(newBalance);
        return savingAccountRepository.save(savingAccount);
    }
	
	public SavingAccount withdraw(SavingAccount savingAccount, double amount) {
        double newBalance = savingAccount.getBalance() - amount;
        savingAccount.setBalance(newBalance);
        return savingAccountRepository.save(savingAccount);
    }

}
