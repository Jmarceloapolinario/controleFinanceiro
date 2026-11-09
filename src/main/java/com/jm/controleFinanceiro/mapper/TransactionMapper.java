package com.jm.controleFinanceiro.mapper;

import com.jm.controleFinanceiro.controller.request.TransactionRequest;
import com.jm.controleFinanceiro.controller.response.TransactionResponse;
import com.jm.controleFinanceiro.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "categoryId", source = "category.id")
    TransactionResponse toTransactionResponse(Transaction transaction);

    @Mapping(target = "account.id", source = "accountId")
    @Mapping(target = "category.id", source = "categoryId")
    Transaction toTransaction(TransactionRequest transaction);
}
