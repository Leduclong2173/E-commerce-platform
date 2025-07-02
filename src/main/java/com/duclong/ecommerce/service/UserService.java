package com.duclong.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.Role;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.CategoryRepository;
import com.duclong.ecommerce.repository.OrderRepository;
import com.duclong.ecommerce.repository.ProductRepository;
import com.duclong.ecommerce.repository.RoleRepository;
import com.duclong.ecommerce.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(
        UserRepository userRepository,
        RoleRepository roleRepository,
        ProductRepository productRepository,
        OrderRepository orderRepository
    ){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
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

    public boolean checkEmailExist(String email){
        return this.userRepository.existsByEmail(email);
    }

    public boolean checkUsernameExist(String username){
        return this.userRepository.existsByUsername(username);
    }

    public User getUserByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(long id){
        return this.userRepository.findById(id);
    }

    public List<User> searchByUsernameOrId(String keyword) {
        if (keyword == null){
            return getAllUser();
        }
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
        }
        return this.userRepository.searchByUsernameOrId(keyword, id);
    }

    public void updateUserRole(Long id){
        User user = getUserById(id);
        Role role = getRoleByName("SHOP");
        user.setRole(role);
        this.userRepository.save(user);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }

}
