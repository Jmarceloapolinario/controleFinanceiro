package com.jm.controleFinanceiro.controller;

import com.jm.controleFinanceiro.controller.request.TransactionRequest;
import com.jm.controleFinanceiro.controller.response.TransactionResponse;
import com.jm.controleFinanceiro.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody TransactionRequest request){
        return transactionService.crateTransaction(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponse> findAll(Pageable pageable){
        return transactionService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse findById(@PathVariable Long id){
        return transactionService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request){
        return transactionService.updateTransaction(id ,request);
    }

    @GetMapping("/account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponse> findAllByAccountId(@PathVariable Long accountId, Pageable pageable){
        return transactionService.findAllByAccountId(accountId, pageable);
    }

    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponse> findAllByCategoryId(@PathVariable Long categoryId, Pageable pageable){
        return transactionService.findAllByCategoryId(categoryId, pageable);
    }

    @GetMapping("/date-range")
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponse> findAllByDateRange(@RequestParam LocalDate from, @RequestParam LocalDate to, Pageable pageable){
        return transactionService.findAllByDate(from, to, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "Transaction deleted successfully";
    }

}
