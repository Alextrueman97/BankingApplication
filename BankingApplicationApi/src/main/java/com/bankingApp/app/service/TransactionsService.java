package com.bankingApp.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.TransactionType;
import com.bankingApp.app.models.Transactions;
import com.bankingApp.app.repositories.CurrentAccountRepository;
import com.bankingApp.app.repositories.SavingAccountRepository;
import com.bankingApp.app.repositories.TransactionsRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionsService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	@Autowired
	private CurrentAccountRepository currentAccountRepository;
	@Autowired
	private CurrentAccountService currentAccountService;
	@Autowired
	private SavingAccountRepository savingAccountRepository;
	@Autowired
	private SavingAccountService savingAccountService;
	
	public void saveTransaction(Transactions transaction) {
		transactionsRepository.save(transaction);
	}
	
	public List<Transactions> getTransactionHistoryForCurrentAccount(CurrentAccount currentAccount) {
	    return transactionsRepository.findByCurrentAccountNumber(currentAccount);
	}
	
	
}
