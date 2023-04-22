package com.bankingApp.app.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Transactions {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private UserAccount accountId;
	@ManyToOne
	@JoinColumn(name = "current_account_number")
	private CurrentAccount currentAccountNumber;
	@ManyToOne
	@JoinColumn(name = "saving_account_number")
	private SavingAccount savingAccountNumber;
	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionType transactionType;
	private double amount;
	private LocalDate dateOfTransaction;
	
	public Transactions(int transactionId, UserAccount accountId, CurrentAccount currentAccountNumber,
			SavingAccount savingAccountNumber, TransactionType transactionType, double amount,
			LocalDate dateOfTransaction) {
		super();
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.currentAccountNumber = currentAccountNumber;
		this.savingAccountNumber = savingAccountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.dateOfTransaction = dateOfTransaction;
	}

	public Transactions(UserAccount accountId, CurrentAccount currentAccountNumber, SavingAccount savingAccountNumber,
			TransactionType transactionType, double amount, LocalDate dateOfTransaction) {
		super();
		this.accountId = accountId;
		this.currentAccountNumber = currentAccountNumber;
		this.savingAccountNumber = savingAccountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.dateOfTransaction = dateOfTransaction;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public UserAccount getAccountId() {
		return accountId;
	}

	public void setAccountId(UserAccount accountId) {
		this.accountId = accountId;
	}

	public CurrentAccount getCurrentAccountNumber() {
		return currentAccountNumber;
	}

	public void setCurrentAccountNumber(CurrentAccount currentAccountNumber) {
		this.currentAccountNumber = currentAccountNumber;
	}

	public SavingAccount getSavingAccountNumber() {
		return savingAccountNumber;
	}

	public void setSavingAccountNumber(SavingAccount savingAccountNumber) {
		this.savingAccountNumber = savingAccountNumber;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(LocalDate dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	
	
	
}
