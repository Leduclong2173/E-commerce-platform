package com.duclong.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(
        UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }

    public User handleSaveUser(User user){
        return this.userRepository.save(user);
    }

    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }
}
