package com.jm.controleFinanceiro.controller;

import com.jm.controleFinanceiro.controller.request.AccountRequest;
import com.jm.controleFinanceiro.controller.request.DepositRequest;
import com.jm.controleFinanceiro.controller.response.AccountResponse;
import com.jm.controleFinanceiro.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @PostMapping("/deposit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse deposit(@Valid @PathVariable Long id, @RequestBody DepositRequest request) {
        return accountService.deposit(id,request.getAmount());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AccountResponse> findAll(Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse findById(@PathVariable Long id) {
        return accountService.findById(id);
    }
}
