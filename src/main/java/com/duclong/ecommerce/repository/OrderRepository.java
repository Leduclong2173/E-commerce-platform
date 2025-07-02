package com.duclong.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Order;
import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUser(User user);

    List<Order> findAll();

    List<Order> findByOrderItems_Product(Product product);
}
