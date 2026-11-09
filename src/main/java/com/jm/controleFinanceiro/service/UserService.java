package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.UserRequest;
import com.jm.controleFinanceiro.controller.response.UserResponse;
import com.jm.controleFinanceiro.exceptions.ResourceAlreadyExistsException;
import com.jm.controleFinanceiro.mapper.UserMapper;
import com.jm.controleFinanceiro.model.Users;
import com.jm.controleFinanceiro.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @CacheEvict(value = "users", allEntries = true)
    public UserResponse create(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use, email: " + request.getEmail());
        }

        Users newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Users user = userRepository.save(newUser);
        return userMapper.toUserResponse(user);
    }

}
