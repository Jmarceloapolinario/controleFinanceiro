package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.UserRequest;
import com.jm.controleFinanceiro.controller.response.UserResponse;
import com.jm.controleFinanceiro.exceptions.ResourceAlreadyExistsException;
import com.jm.controleFinanceiro.mapper.UserMapper;
import com.jm.controleFinanceiro.model.Scope;
import com.jm.controleFinanceiro.model.Users;
import com.jm.controleFinanceiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ScopesService scopesService;
    private final PasswordEncoder passwordEncoder;

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public UserResponse create(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use, email: " + request.getEmail());
        }
        Scope scopes = scopesService.findByName("Usuario");

        Users newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setScopes(List.of(scopes));
        Users user = userRepository.save(newUser);
        return userMapper.toUserResponse(user);
    }

}
