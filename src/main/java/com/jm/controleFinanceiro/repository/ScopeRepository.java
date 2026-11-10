package com.jm.controleFinanceiro.repository;

import com.jm.controleFinanceiro.model.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {

    Optional<Scope> findByName(String name);
}
