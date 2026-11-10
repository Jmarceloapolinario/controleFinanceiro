package com.jm.controleFinanceiro.repository;

import com.jm.controleFinanceiro.model.Transaction;
import com.jm.controleFinanceiro.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByUser(Users user, Pageable pageable);
    Optional<Transaction> findByUserAndId(Users user, Long id);
}
