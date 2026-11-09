package com.jm.controleFinanceiro.controller;

import com.jm.controleFinanceiro.controller.request.UserRequest;
import com.jm.controleFinanceiro.controller.response.UserResponse;
import com.jm.controleFinanceiro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody UserRequest userRequest){
        return userService.create(userRequest);
    }


}
