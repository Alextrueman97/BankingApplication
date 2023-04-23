package com.bankingApp.app.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "user_account")
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	@Column(unique = true)
	private String username;
	@Column(unique = true, columnDefinition = "VARCHAR(50)")
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	@OneToMany(mappedBy = "accountId")
	private List<Transactions> transactions;
	@OneToMany(mappedBy = "accountId")
	private List<CurrentAccount> currentAccounts;
	@OneToMany(mappedBy = "accountId")
	private List<SavingAccount> savingAccounts;
	
	public UserAccount() {
		super();
	}

	public UserAccount(String username, String email, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserAccount(int accountId, String username, String email, String password, String firstName, String lastName,
			List<Transactions> transactions) {
		super();
		this.accountId = accountId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.transactions = transactions;
	}

	public UserAccount(int accountId, String username, String email, String password, String firstName,
			String lastName) {
		super();
		this.accountId = accountId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	
	
}
