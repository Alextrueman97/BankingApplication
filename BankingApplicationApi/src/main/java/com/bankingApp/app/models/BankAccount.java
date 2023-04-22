package com.bankingApp.app.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccount {

	@Id
	private String accountNumber;
	@OneToOne
	@JoinColumn(name="account_id")
	private UserAccount accountId;
	private double balance;
	private LocalDate dateOpened;
}
