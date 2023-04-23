package com.bankingApp.app.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
public class BankAccount {

	@Id
	private String accountNumber;
	@ManyToOne
	@JoinColumn(name="account_id")
	private UserAccount accountId;
	private double balance;
	private LocalDate dateOpened;
	
	public BankAccount() {
		super();
	}

	public BankAccount(UserAccount accountId, String accountNumber, double balance, LocalDate dateOpened) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.dateOpened = dateOpened;
	}

	public BankAccount(double balance, LocalDate dateOpened) {
		super();
		this.balance = balance;
		this.dateOpened = dateOpened;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LocalDate getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(LocalDate dateOpened) {
		this.dateOpened = dateOpened;
	}
	
	public UserAccount getAccountId() {
		return accountId;
	}

	public void setAccountId(UserAccount accountId) {
		this.accountId = accountId;
	}
}
