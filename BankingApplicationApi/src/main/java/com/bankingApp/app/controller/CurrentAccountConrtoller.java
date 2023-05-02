package com.bankingApp.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:3000")
public class CurrentAccountConrtoller {

	@Autowired
	private CurrentAccountService currentAccountService;
	@Autowired
	private TransactionsService transactionsService;
	@Autowired
	private HttpServletRequest request;
	
	
	//This create current account method will only allow the user to have 1 current account (if they already have a current account they cannot create a new one)
	@PostMapping("/createCurrentAccount")
	public ResponseEntity<?> createCurrentAccount(@RequestParam("balance") double balance,
	                                               @RequestParam("dateOpened") LocalDate dateOpened,
	                                               HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    UserAccount userAccount = (UserAccount) session.getAttribute("user");
	    if (userAccount == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    List<CurrentAccount> currentAccounts = currentAccountService.getCurrentAccountByUser(userAccount);
	    if (!currentAccounts.isEmpty()) {
	        return ResponseEntity.badRequest().body("You can only have one current account.");
	    }

	    CurrentAccount currentAccount = new CurrentAccount(balance, dateOpened);
	    currentAccount.setAccountId(userAccount);

	    try {
	        CurrentAccount savedInfo = currentAccountService.createNewCurrentAccount(currentAccount);
	        // Adding to transactions table
	        Transactions openingBalanceTransaction = new Transactions(userAccount, currentAccount, null, TransactionType.OPENING_BALANCE, balance, dateOpened);
	        transactionsService.saveTransaction(openingBalanceTransaction);
	        return ResponseEntity.ok(currentAccount);
	    } catch (DataIntegrityViolationException ex) {
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
	
	
}
