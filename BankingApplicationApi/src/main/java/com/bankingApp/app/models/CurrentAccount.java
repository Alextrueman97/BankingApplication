package com.bankingApp.app.models;

import java.util.List;
import java.util.Random;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "current_account")
@AttributeOverrides({
	@AttributeOverride(name = "accountId", column = @Column(name = "account_id")),
	@AttributeOverride(name = "accountNumber", column = @Column(name = "current_account_number")),
	@AttributeOverride(name = "balance", column = @Column(name = "balance")),
	@AttributeOverride(name = "dateOpened", column = @Column(name = "date_opened")),
})
public class CurrentAccount extends BankAccount {

	@Id
	@Column(name = "current_account_number")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String accountNumber = "CUR" + String.format("%07d", new Random().nextInt(10000000));
	@Column(name="overdraftAmount")
	private double overdraftAmount = -1000;
	@OneToMany(mappedBy = "currentAccountNumber")
	private List <Transactions> transactions;
	
	public CurrentAccount(String accountNumber, double overdraftAmount, List<Transactions> transactions) {
		super();
		this.accountNumber = accountNumber;
		this.overdraftAmount = overdraftAmount;
		this.transactions = transactions;
	}

	public CurrentAccount(String accountNumber, double overdraftAmount) {
		super();
		this.accountNumber = accountNumber;
		this.overdraftAmount = overdraftAmount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(double overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	
	
	
}
