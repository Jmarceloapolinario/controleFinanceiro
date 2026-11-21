package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.TransactionRequest;
import com.jm.controleFinanceiro.controller.response.PageResponse;
import com.jm.controleFinanceiro.controller.response.TransactionResponse;
import com.jm.controleFinanceiro.enums.TransactionType;
import com.jm.controleFinanceiro.exceptions.InsufficientBalanceException;
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
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private  final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Caching(evict = {
            @CacheEvict(value = "transaction", allEntries = true),
            @CacheEvict(value = "transactions", allEntries = true),
            @CacheEvict(value = "accounts", allEntries = true),
            @CacheEvict(value = "account", allEntries = true)
    })
    @Transactional
    public TransactionResponse crateTransaction(TransactionRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Account account = accountRepository.findByUserAndId(user, request.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));

        request.setAccountId(account.getId());
        Transaction newTransaction = transactionMapper.toTransaction(request);
        newTransaction.setUser(user);
        if (newTransaction.getType() == TransactionType.EXPENSE || newTransaction.getType() == TransactionType.TRANSFER_OUT || newTransaction.getType() == TransactionType.INVESTMENT) {
            if (account.getBalance().compareTo(newTransaction.getAmount()) < 0) {
                throw new InsufficientBalanceException("Insufficient balance in the account");
            }
            account.setBalance(account.getBalance().subtract(newTransaction.getAmount()));
        } else if (newTransaction.getType() == TransactionType.INCOME || newTransaction.getType() == TransactionType.TRANSFER_IN || newTransaction.getType() == TransactionType.REFUND) {
            account.setBalance(account.getBalance().add(newTransaction.getAmount()));
        }
        accountRepository.save(account);
        transactionRepository.save(newTransaction);
        return transactionMapper.toTransactionResponse(newTransaction);
    }

    @Cacheable(value = "transactions",
            key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().principal.getClaim('id') + ':' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort")
    public PageResponse<TransactionResponse> findAll(Pageable pageable) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Page<TransactionResponse> transactionPage = transactionRepository.findAllByUser(user, pageable)
                .map(transactionMapper::toTransactionResponse);

        return new PageResponse<>(transactionPage);
    }

    @Cacheable(value = "transaction",
            key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().principal.getClaim('id') + ':' + #id")
    public TransactionResponse findById(Long id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return transactionMapper.toTransactionResponse(transactionRepository.findByUserAndId(user,id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found")));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "transaction", allEntries = true),
            @CacheEvict(value = "transactions", allEntries = true),
            @CacheEvict(value = "accounts", allEntries = true),
            @CacheEvict(value = "account", allEntries = true)
    })
    public TransactionResponse updateTransaction(Long id, TransactionRequest request){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Transaction transaction = transactionMapper.toTransaction(request);
        Transaction newTransaction = transactionRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        newTransaction.setDescription(transaction.getDescription());
        newTransaction.setAmount(transaction.getAmount());
        newTransaction.setType(transaction.getType());
        Account account = accountRepository.findByUserAndId(user, newTransaction.getAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (newTransaction.getType() == TransactionType.EXPENSE || newTransaction.getType() == TransactionType.TRANSFER_OUT || newTransaction.getType() == TransactionType.INVESTMENT) {
            if (account.getBalance().compareTo(newTransaction.getAmount()) < 0) {
                throw new InsufficientBalanceException("Insufficient balance in the account");
            }
            account.setBalance(account.getBalance().subtract(newTransaction.getAmount()));
        } else if (newTransaction.getType() == TransactionType.INCOME || newTransaction.getType() == TransactionType.TRANSFER_IN || newTransaction.getType() == TransactionType.REFUND) {
            account.setBalance(account.getBalance().add(newTransaction.getAmount()));
        }
        accountRepository.save(account);
        transactionRepository.save(newTransaction);
        return transactionMapper.toTransactionResponse(newTransaction);

    }
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "transaction", allEntries = true),
            @CacheEvict(value = "transactions", allEntries = true),
            @CacheEvict(value = "accounts", allEntries = true),
            @CacheEvict(value = "account", allEntries = true)
    })
    public void deleteTransaction(Long id){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Transaction transaction = transactionRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

}
