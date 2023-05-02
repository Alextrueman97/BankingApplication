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

import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.SavingAccount;
import com.bankingApp.app.models.TransactionType;
import com.bankingApp.app.models.Transactions;
import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.service.SavingAccountService;
import com.bankingApp.app.service.TransactionsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("savingAccount")
@CrossOrigin(origins = "http://localhost:3000")
public class SavingAccountController {

	@Autowired
	private SavingAccountService savingAccountService;
	@Autowired
	private TransactionsService transactionsService;
	@Autowired
	private HttpServletRequest request;
	
	@PostMapping("/createSavingAccount")
	public ResponseEntity<?> createSavingAccount(@RequestParam(value = "balance", defaultValue = "0") double balance,
											     @RequestParam("dateOpened") LocalDate dateOpened,
											     HttpServletRequest request){
		HttpSession session = request.getSession(false);
			if(session == null)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			
		UserAccount userAccount = (UserAccount) session.getAttribute("user");
			if (userAccount == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		SavingAccount savingAccount = new SavingAccount(0, dateOpened);
		savingAccount.setAccountId(userAccount);
		try {
			SavingAccount savedInfo = savingAccountService.createNewSavingAccount(savingAccount);
			//Adding to transactions as opening balance
			Transactions openingBalanceTransaction = new Transactions(userAccount, null, savingAccount, TransactionType.OPENING_BALANCE, balance, dateOpened);
			transactionsService.saveTransaction(openingBalanceTransaction);
				return ResponseEntity.ok(savingAccount);
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.badRequest().body("Error creating saving account. Please try again.");
		}
	}
	
	@GetMapping("/savingAccount")
	public ResponseEntity<SavingAccount> viewSavingAccount(){
		HttpSession session = request.getSession(false);
			if (session == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		UserAccount user = (UserAccount) session.getAttribute("user");
			if(user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<SavingAccount> savingAccounts = savingAccountService.getSavingAccountByUser(user);
			if (savingAccounts.size() > 0) {
				SavingAccount savingAccount = savingAccounts.get(0);
				return ResponseEntity.ok(savingAccount);
			}else {
				return ResponseEntity.notFound().build();
			}
	}
}
