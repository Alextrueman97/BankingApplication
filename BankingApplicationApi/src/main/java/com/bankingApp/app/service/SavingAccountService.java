package com.bankingApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingApp.app.models.SavingAccount;
import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.repositories.SavingAccountRepository;

@Service
public class SavingAccountService {

	@Autowired
	private SavingAccountRepository savingAccountRepository;
	
	public SavingAccount createNewSavingAccount(SavingAccount savingAccount) {
		return savingAccountRepository.save(savingAccount);
	}
	
	public List<SavingAccount> getSavingAccountByUser(UserAccount userAccount){
		return savingAccountRepository.findByAccountId(userAccount);
	}
	
}
