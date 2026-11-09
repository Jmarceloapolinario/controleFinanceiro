package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.TransactionRequest;
import com.jm.controleFinanceiro.controller.response.TransactionResponse;
import com.jm.controleFinanceiro.mapper.TransactionMapper;
import com.jm.controleFinanceiro.model.Transaction;
import com.jm.controleFinanceiro.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @CacheEvict(value = "transaction", allEntries = true)
    public TransactionResponse crateTransaction(TransactionRequest request) {
        Transaction newTransaction = transactionMapper.toTransaction(request);
        transactionRepository.save(newTransaction);
        return transactionMapper.toTransactionResponse(newTransaction);
    }

    @Cacheable(value = "transaction")
    public Page<TransactionResponse> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable)
                .map(transactionMapper::toTransactionResponse);
    }

    @Cacheable(value = "transaction", key = "#id")
    public TransactionResponse findById(Long id) {
        return transactionMapper.toTransactionResponse(transactionRepository.findById(id).orElse(null));
    }

}
