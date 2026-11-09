package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.AccountRequest;
import com.jm.controleFinanceiro.controller.request.TransactionRequest;
import com.jm.controleFinanceiro.controller.response.AccountResponse;
import com.jm.controleFinanceiro.enums.TransactionType;
import com.jm.controleFinanceiro.mapper.AccountMapper;
import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Transaction;
import com.jm.controleFinanceiro.model.Users;
import com.jm.controleFinanceiro.repository.AccountRepository;
import com.jm.controleFinanceiro.repository.TransactionRepository;
import com.jm.controleFinanceiro.repository.UserRepository;
import com.sun.jdi.PrimitiveValue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    @CacheEvict(value = "account", allEntries = true)
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account newAccount = accountMapper.toAccount(accountRequest);
        Account savedAccount = accountRepository.save(newAccount);
        return accountMapper.toAccountResponse(savedAccount);

    }

    @Cacheable(value = "account")
    public Page<AccountResponse> findAll(Pageable pageable) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        return accountRepository.findAllByUser(user,pageable)
                .map(accountMapper::toAccountResponse);
    }

    @Cacheable(value = "account", key = "#id")
    public AccountResponse findById(Long id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return accountMapper.toAccountResponse(accountRepository.findByUserAndId(user, id).orElse(null));
    }

    public AccountResponse deposit(Long id, BigDecimal amount) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Long userId = jwt.getClaim("id");
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        Optional<Account> account = accountRepository.findByUserAndId(user, id);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account newAccount = account.get();

        newAccount.setBalance(newAccount.getBalance().add(amount));

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .description("Um novo deposito na conta: " +newAccount.getName())
                .amount(amount)
                .type(TransactionType.INCOME)
                .accountId(id)
                .categoryId(2L)
                .build();

        transactionService.crateTransaction(transactionRequest);

        return accountMapper.toAccountResponse(accountRepository.save(newAccount));



    }

}
