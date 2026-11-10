package com.jm.controleFinanceiro.mapper;

import com.jm.controleFinanceiro.controller.request.AccountRequest;
import com.jm.controleFinanceiro.controller.response.AccountResponse;
import com.jm.controleFinanceiro.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    AccountResponse toAccountResponse(Account account);

    Account toAccount(AccountRequest accountRequest);
}
