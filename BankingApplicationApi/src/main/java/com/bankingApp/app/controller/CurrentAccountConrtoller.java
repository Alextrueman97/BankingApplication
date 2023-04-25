package com.bankingApp.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.app.service.CurrentAccountService;
import com.bankingApp.app.service.TransactionsService;
import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.Transactions;
import com.bankingApp.app.models.TransactionType;
import com.bankingApp.app.models.UserAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/currentAccount")
public class CurrentAccountConrtoller {

	@Autowired
	private CurrentAccountService currentAccountService;
	@Autowired
	private TransactionsService transactionsService;
	@Autowired
	private HttpServletRequest request;
	
	@PostMapping("/createCurrentAccount")
	public ResponseEntity<?> createCurrentAccount(@RequestParam("balance") double balance,
												  @RequestParam("dateOpened") LocalDate dateOpened,
												  HttpServletRequest request){
		HttpSession session = request.getSession(false);
			if(session == null)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		UserAccount userAccount = (UserAccount) session.getAttribute("user");
			if (userAccount == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		CurrentAccount currentAccount = new CurrentAccount(balance, dateOpened);
		currentAccount.setAccountId(userAccount);
		try {
			CurrentAccount savedInfo = currentAccountService.createNewCurrentAccount(currentAccount);
			//Adding to transactions table
			Transactions openingBalanceTransaction = new Transactions(userAccount, currentAccount, null, TransactionType.OPENING_BALANCE, balance, dateOpened);
			transactionsService.saveTransaction(openingBalanceTransaction);
				return ResponseEntity.ok(currentAccount);
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.badRequest().body("Error creating current account. Please try again.");
		}
	}
	
	
	@GetMapping("/currentAccount")
	public ResponseEntity<CurrentAccount> viewCurrentAccount(){
		HttpSession session = request.getSession(false);
			if (session == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		UserAccount user = (UserAccount) session.getAttribute("user");
			if(user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<CurrentAccount> currentAccounts = currentAccountService.getCurrentAccountByUser(user);
			if (currentAccounts.size() > 0) {
				CurrentAccount currentAccount = currentAccounts.get(0);
				return ResponseEntity.ok(currentAccount);
			}else {
				return ResponseEntity.notFound().build();
			}
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestParam("amount") double amount){
		HttpSession session = request.getSession(false);
		if (session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		UserAccount user = (UserAccount) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<CurrentAccount> currentAccounts = currentAccountService.getCurrentAccountByUser(user);
		if(currentAccounts.size() > 0) {
			CurrentAccount currentAccount = currentAccounts.get(0);
			CurrentAccount updatedAccount = currentAccountService.deposit(currentAccount, amount);
				if (updatedAccount != null) {
					//add deposit transaction to the transactions table
					Transactions depositTransaction = new Transactions(user, currentAccount, null, TransactionType.DEPOSIT, amount, LocalDate.now());
					transactionsService.saveTransaction(depositTransaction);
					//return response with updated balance and deposit amount
					return ResponseEntity.ok().body("Deposited: " + amount + " New Balance: " + updatedAccount.getBalance());
				} else {
					return ResponseEntity.badRequest().body("Error depositing amount. Please try again.");				}
		}else {
			return ResponseEntity.notFound().build();
		}
	} 
	
	public ResponseEntity<String> withdraw(double amount) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    UserAccount user = (UserAccount) session.getAttribute("user");
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    List<CurrentAccount> currentAccounts = currentAccountService.getCurrentAccountByUser(user);
	    if (currentAccounts.size() == 0) {
	        return ResponseEntity.notFound().build();
	    }

	    CurrentAccount currentAccount = currentAccounts.get(0);

	    if (currentAccount.getBalance() - amount < currentAccount.getOverdraftAmount()) {
	    	Transactions reversalTransaction = new Transactions(user, currentAccount, null, TransactionType.REVERSAL, amount, LocalDate.now());
	        transactionsService.saveTransaction(reversalTransaction);
	        return ResponseEntity.badRequest().body("Withdrawal amount exceeds overdraft limit.");
	    }

	    double newBalance = currentAccount.getBalance() - amount;
	    currentAccount.setBalance(newBalance);

	    // Adding to transactions table
	    Transactions withdrawalTransaction = new Transactions(user, currentAccount, null, TransactionType.WITHDRAWAL, amount, LocalDate.now());
	    transactionsService.saveTransaction(withdrawalTransaction);

	    String message = String.format("Withdrawal of %.2f successful. New balance: %.2f", amount, newBalance);
	    return ResponseEntity.ok(message);
	}
}
