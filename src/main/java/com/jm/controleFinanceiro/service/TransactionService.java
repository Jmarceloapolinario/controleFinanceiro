package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.TransactionRequest;
import com.jm.controleFinanceiro.controller.response.TransactionResponse;
import com.jm.controleFinanceiro.exceptions.ResourceNotFoundException;
import com.jm.controleFinanceiro.mapper.TransactionMapper;
import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Transaction;
import com.jm.controleFinanceiro.model.Users;
import com.jm.controleFinanceiro.repository.AccountRepository;
import com.jm.controleFinanceiro.repository.TransactionRepository;
import com.jm.controleFinanceiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private  final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = "transaction", allEntries = true)
    @Transactional
    public TransactionResponse crateTransaction(TransactionRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Account account = accountRepository.findByUserAndId(user, request.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));

        request.setAccountId(account.getId());
        Transaction newTransaction = transactionMapper.toTransaction(request);
        newTransaction.setUser(user);
        account.setBalance(account.getBalance().add(newTransaction.getAmount()));
        accountRepository.save(account);
        transactionRepository.save(newTransaction);
        return transactionMapper.toTransactionResponse(newTransaction);
    }

    @Cacheable(value = "transaction")
    public Page<TransactionResponse> findAll(Pageable pageable) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findAllByUser(user,pageable)
                .map(transactionMapper::toTransactionResponse);
    }

    @Cacheable(value = "transaction", key = "#id")
    public TransactionResponse findById(Long id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return transactionMapper.toTransactionResponse(transactionRepository.findByUserAndId(user,id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found")));
    }

}
