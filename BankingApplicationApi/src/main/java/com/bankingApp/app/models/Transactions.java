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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
