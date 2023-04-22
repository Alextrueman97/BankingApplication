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
@Table(name = "saving_account")
@AttributeOverrides({
	@AttributeOverride(name = "accountId", column = @Column(name = "account_id")),
	@AttributeOverride(name = "accountNumber", column = @Column(name = "saving_account_number")),
	@AttributeOverride(name = "balance", column = @Column(name = "balance")),
	@AttributeOverride(name = "dateOpened", column = @Column(name = "date_opened")),
})
public class SavingAccount extends BankAccount {

	@Id
	@Column(name = "savingAccountNumber")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String accountNumber = "SAV" + String.format("%07d", new Random().nextInt(10000000));
	@OneToMany(mappedBy = "savingAccountNumber")
	private List <Transactions> transactions;
	
	public SavingAccount(String accountNumber, List<Transactions> transactions) {
		super();
		this.accountNumber = accountNumber;
		this.transactions = transactions;
	}

	public SavingAccount(String accountNumber) {
		super();
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	
	
	
}
