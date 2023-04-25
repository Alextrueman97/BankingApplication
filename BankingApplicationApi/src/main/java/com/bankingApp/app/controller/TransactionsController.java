package com.bankingApp.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bankingApp.app.models.TransactionType;
import com.bankingApp.app.models.Transactions;
import com.bankingApp.app.models.UserAccount;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private CurrentAccountService currentAccountService;
	@Autowired
	private SavingAccountService savingAccountService;
	@Autowired
	private TransactionsService transactionsService;
	
	
}
