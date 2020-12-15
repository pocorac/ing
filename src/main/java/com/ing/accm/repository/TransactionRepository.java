package com.ing.accm.repository;

import com.ing.accm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findByAccountIdAndTransactionDateAfter (Long accountId, ZonedDateTime transactionDate);
}
