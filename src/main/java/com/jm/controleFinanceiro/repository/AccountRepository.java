package com.jm.controleFinanceiro.repository;

import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAllByUser(Users user, Pageable pageable);
    Optional<Account> findByUserAndId(Users user, Long id);
}
