package com.bankingApp.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingApp.app.models.SavingAccount;
import com.bankingApp.app.models.UserAccount;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, String> {

	
	public List<SavingAccount> findByAccountId(UserAccount accountId);
}
