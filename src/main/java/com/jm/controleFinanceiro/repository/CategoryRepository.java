package com.jm.controleFinanceiro.repository;

import com.jm.controleFinanceiro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
