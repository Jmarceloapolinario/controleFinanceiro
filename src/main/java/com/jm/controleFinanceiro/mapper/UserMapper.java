package com.jm.controleFinanceiro.mapper;


import com.jm.controleFinanceiro.controller.request.UserRequest;
import com.jm.controleFinanceiro.controller.response.UserResponse;
import com.jm.controleFinanceiro.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(Users user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    Users toUser(UserRequest userRequest);

}
