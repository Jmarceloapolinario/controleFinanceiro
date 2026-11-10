package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.exceptions.ResourceNotFoundException;
import com.jm.controleFinanceiro.model.Scope;
import com.jm.controleFinanceiro.repository.ScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScopesService {

    private final ScopeRepository scopeRepository;

    public Scope findByName(String name) {
        return scopeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Scope not found for name "+name));
    }
}
