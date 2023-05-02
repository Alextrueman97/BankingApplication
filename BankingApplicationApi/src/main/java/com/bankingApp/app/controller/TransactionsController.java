package com.bankingApp.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.app.service.CurrentAccountService;
import com.bankingApp.app.service.SavingAccountService;
import com.bankingApp.app.service.TransactionsService;
import com.bankingApp.app.service.UserAccountService;
import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.SavingAccount;
import com.bankingApp.app.models.TransactionType;
import com.bankingApp.app.models.Transactions;
import com.bankingApp.app.models.UserAccount;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionsController {

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private CurrentAccountService currentAccountService;
	@Autowired
	private SavingAccountService savingAccountService;
	@Autowired
	private TransactionsService transactionsService;
	@Autowired
	private HttpServletRequest request;
	
	
	//Showing Users current account transactions
	@GetMapping("/currentAccount") 
	public ResponseEntity<List<Transactions>> getCurrentAccountTransactions(HttpServletRequest request){
		HttpSession session = request.getSession(false);
			if(session == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		UserAccount user = (UserAccount) session.getAttribute("user");
			if(user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		List<CurrentAccount> currentAccounts = currentAccountService.getCurrentAccountByUser(user);
		if (currentAccounts.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		CurrentAccount currentAccount = currentAccounts.get(0);
		List<Transactions> transactions = transactionsService.getTransactionHistoryForCurrentAccount(currentAccount);
		return ResponseEntity.ok().body(transactions);
	}
	
	//showing Users saving account transactions
	@GetMapping("SavingAccount")
	public ResponseEntity<List<Transactions>> getSavingAccountTransactions(HttpServletRequest request){
		HttpSession session = request.getSession(false);
			if (session == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		UserAccount user = (UserAccount) session.getAttribute("user");
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		List<SavingAccount> savingAccounts = savingAccountService.getSavingAccountByUser(user);
		if (savingAccounts.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		SavingAccount savingAccount = savingAccounts.get(0);
		List<Transactions> transactions = transactionsService.getTransactionHistoryForSavingAcocunt(savingAccount);
		return ResponseEntity.ok().body(transactions);
	}
	
	
	// allowing deposit into current account
	@PostMapping("/depositCa")
	public ResponseEntity<?> depositCurrentAccount(@RequestParam("amount") double amount){
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
					return ResponseEntity.badRequest().body("Error depositing amount. Please try again.");				
				}
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	
	//allowing withdrawal from current account
	@PostMapping("/withdrawalCa")
	public ResponseEntity<String> withdrawalCa(double amount) {
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
	
	//deposit to saving account
	@PostMapping("/depositSa")
	public ResponseEntity<?> depositSavingAccount(@RequestParam("amount") double amount, @RequestParam("savingAccount") SavingAccount savingAccount){
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    UserAccount user = (UserAccount) session.getAttribute("user");
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    List<SavingAccount> savingAccounts = savingAccountService.getSavingAccountByUser(user);
	    if(savingAccounts.contains(savingAccount)) {
	        SavingAccount updatedAccount = savingAccountService.deposit(savingAccount, amount);
	        if (updatedAccount != null) {
	            //add deposit transaction to the transactions table
	            Transactions depositTransaction = new Transactions(user, null, savingAccount, TransactionType.DEPOSIT, amount, LocalDate.now());
	            transactionsService.saveTransaction(depositTransaction);
	            //return response with updated balance and deposit amount
	            return ResponseEntity.ok().body("Deposited: " + amount + " New Balance: " + updatedAccount.getBalance());
	        } else {
	            return ResponseEntity.badRequest().body("Error depositing amount. Please try again.");               
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	} 
	
	// withdrawal from saving account as the user can have multiple saving accounts we need to pass the saving account to use as a parameter
	@PostMapping("/withdrawalSa")
	public ResponseEntity<String> withdrawalSa(@RequestParam("amount") double amount, @RequestParam("savingAccount") SavingAccount savingAccount) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    UserAccount user = (UserAccount) session.getAttribute("user");
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    if (savingAccount.getBalance() < amount) {
	        Transactions reversalTransaction = new Transactions(user, null, savingAccount, TransactionType.REVERSAL, amount, LocalDate.now());
	        transactionsService.saveTransaction(reversalTransaction);
	        return ResponseEntity.badRequest().body("Withdrawal amount exceeds balance.");
	    }

	    double newBalance = savingAccount.getBalance() - amount;
	    savingAccount.setBalance(newBalance);

	    // Adding to transactions table
	    Transactions withdrawalTransaction = new Transactions(user, null, savingAccount, TransactionType.WITHDRAWAL, amount, LocalDate.now());
	    transactionsService.saveTransaction(withdrawalTransaction);

	    String message = String.format("Withdrawal of %.2f successful. New balance: %.2f", amount, newBalance);
	    return ResponseEntity.ok(message);
	}
}
