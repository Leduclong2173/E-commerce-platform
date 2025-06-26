package com.duclong.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByUser(User user);  
}
