package com.bankingApp.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankingApp.app.models.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

	
}
