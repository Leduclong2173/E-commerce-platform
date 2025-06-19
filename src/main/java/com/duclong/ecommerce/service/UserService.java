package com.duclong.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository UserRepository;

    public UserService(
        UserRepository userRepository
    ){
        this.UserRepository = userRepository;
    }

    public List<User> getAllUser(){
        return this.UserRepository.findAll();
    }
}
