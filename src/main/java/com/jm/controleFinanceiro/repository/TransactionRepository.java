package com.jm.controleFinanceiro.repository;

import com.jm.controleFinanceiro.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
