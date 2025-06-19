package com.duclong.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Role;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.RoleRepository;
import com.duclong.ecommerce.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(
        UserRepository userRepository,
        RoleRepository roleRepository
    ){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }
}
