package com.bankingApp.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankingApp.app.models.CurrentAccount;
import com.bankingApp.app.models.UserAccount;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, String> {

	public List<CurrentAccount> findByAccountId(UserAccount userAccount);
	
	@Query(value = "update CurrentAccount ca set balance where ca.currenAccountNumber = currentAccountNumber and ca.balance = balance")
	public CurrentAccount transaction(@Param("currentAccountNumber") CurrentAccount currentAccountNumber, @Param("balance") double balance);
	

}
